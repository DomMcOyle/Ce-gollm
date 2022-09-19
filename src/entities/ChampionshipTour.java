package entities;

import java.util.HashMap;
import java.util.LinkedList;

import utility.Constants;

public class ChampionshipTour extends Tournament {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4776968057139783067L;
	
	//hash map of name of group + list of days
	HashMap<Character, LinkedList<Match[]>> first_round;
	HashMap<Character, LinkedList<Match[]>> second_round;
	boolean with_groups;
	
	
	public ChampionshipTour(String name, LinkedList<Team> teams, boolean with_return, HashMap<Team, Character> groups) {
		
		super(name, teams, with_return);

		first_round = new HashMap<>();
		if(groups == null) {
			// without groups
			this.with_groups = false;
			// add an eventual dummy team
			int actual_teams = (teams.size() % 2==0 ? teams.size() : teams.size()+1);
			// prepare a worklist
			LinkedList<Team> workList = new LinkedList<>();
			for(Team t: teams) {
				workList.add(t);
			}
			// add dummy if needed
			if(teams.size()%2!=0) {
				workList.add(new Team(Constants.DUMMY_TEAM_NAME));
			}
			
			// prepare array for match generation
			Integer [] workPairing = new Integer[actual_teams];
			for(int id=0; id<actual_teams; id++) {
				workPairing[id] = id;
			}
			int last;
			LinkedList<Match[]> allDays = new LinkedList<>();
			LinkedList<Match[]> allDaysR = new LinkedList<>();
			
			// in each day fix the first team and "rotate" the others
			for(int day=0; day<actual_teams-1; day++) {
				
				Match [] currentDay = new Match[actual_teams/2];
				for(int pairing=0; pairing<actual_teams/2;pairing++) {
					currentDay[pairing] = new Match(
							workList.get(workPairing[pairing]),
							workList.get(workPairing[pairing + actual_teams/2]));
				}
				last = workPairing[actual_teams-1];
				for(int shift=actual_teams-1; shift>1;shift--) {
					workPairing[shift] = workPairing[shift-1];
				}
				workPairing[2] = last;
				allDays.add(currentDay);
				
				//generate return
				if(with_return) {
					Match [] currentDayR = new Match[actual_teams/2];
					for(int match = 0; match < actual_teams/2; match++) {
						currentDayR[match] = new Match(currentDay[match].getOutteam(), currentDay[match].getHometeam());
					}
					allDaysR.add(currentDayR);
				}
				
			}
			
			// assign rounds
			first_round.put(Constants.DEFAULT_GROUP, allDays);
			if(with_return) {
				second_round = new HashMap<>();
				second_round.put(Constants.DEFAULT_GROUP, allDaysR);
			} else {
				second_round = null;
			}
		} else {
			this.with_groups = true;
		}
		
		
	}

}
