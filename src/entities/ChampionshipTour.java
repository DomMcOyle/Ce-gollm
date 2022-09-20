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
	
	
	public ChampionshipTour(String name, LinkedList<Team> teams, boolean with_return, HashMap<Character, LinkedList<Team>> groups) {
		
		super(name, teams, with_return, (groups==null?Constants.CREATION_CHAMP:Constants.CREATION_GROUP));

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
			// TODO: correggere rotazione
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
				workPairing[1] = last;
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
			// TODO AGGIUNGERE GIRONI
		}
		
		
	}


	@Override
	public LinkedList<Match[]> getDays(char group) {
		if(!with_groups) {
			return first_round.get(Constants.DEFAULT_GROUP);
		} else {
			return null; // placeholder
		}
		
	}


	@Override
	public LinkedList<Match[]> getDaysR(char group) {
		if(!with_groups) {
			return second_round.get(Constants.DEFAULT_GROUP);
		} else {
			return null; // placeholder
		}
	}

}
