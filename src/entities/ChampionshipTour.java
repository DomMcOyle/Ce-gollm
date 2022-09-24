package entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

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
		if(with_return) {
			second_round = new HashMap<>();
		} else {
			second_round = null;
		}
		
		if(groups == null) {
			// without groups
			this.with_groups = false;
			
			LinkedList<Match[]> allDays = generateDays(teams);
			LinkedList<Match[]> allDaysR = new LinkedList<>();
				
			//generate return
			if(with_return) {
				for(int i=0; i<allDays.size(); i++) {
					Match[] currentDay = allDays.get(i);
					Match[] currentDayR = new Match[currentDay.length];
					for(int match = 0; match < currentDay.length; match++) {
						currentDayR[match] = new Match(currentDay[match].getOutteam(), currentDay[match].getHometeam());
					}
					allDaysR.add(currentDayR);
				}
				
			}
			
			// assign rounds
			first_round.put(Constants.DEFAULT_GROUP, allDays);
			if(with_return) {
				second_round.put(Constants.DEFAULT_GROUP, allDaysR);
			}
		} else {
			this.with_groups = true;
			for(char c : groups.keySet() ) {
				LinkedList<Match[]> allDays = generateDays(groups.get(c));
				LinkedList<Match[]> allDaysR = new LinkedList<>();
					
				//generate return
				if(with_return) {
					for(int i=0; i<allDays.size(); i++) {
						Match[] currentDay = allDays.get(i);
						Match[] currentDayR = new Match[currentDay.length];
						for(int match = 0; match < currentDay.length; match++) {
							currentDayR[match] = new Match(currentDay[match].getOutteam(), currentDay[match].getHometeam());
						}
						allDaysR.add(currentDayR);
					}
					
				}
				first_round.put(c, allDays);
				if(with_return) {
					second_round.put(c, allDaysR);
				}
			}
		}
		
		
	}

	private LinkedList<Match[]> generateDays(LinkedList<Team> teams) {
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
		int firstLast;
		int secondLast;
		LinkedList<Match[]> allDays = new LinkedList<>();
		
		// in each day fix the first team and "rotate" the others
		for(int day=0; day<actual_teams-1; day++) {
			
			// create current day
			Match [] currentDay = new Match[actual_teams/2];

			for(int pairing=0; pairing<actual_teams/2;pairing++) {
				currentDay[pairing] = new Match(
						workList.get(workPairing[pairing]),
						workList.get(workPairing[pairing + actual_teams/2]));
			}
			
			// consider 1 2 3 4
			//			5 6 7 8
			// 4 is first last, and the first row must shift right with the addition of 5 in place of 2 and by locking 1
			// 5 is second last, and the second row must shift left with the addition of 4 in place of the 8
			firstLast = workPairing[(actual_teams/2)-1];
			secondLast = workPairing[(actual_teams/2)];
			// right shift
			for(int shift=(actual_teams/2)-1; shift>1;shift--) {
				workPairing[shift] = workPairing[shift-1];
			}
			// left shift
			for(int shift=actual_teams/2; shift<actual_teams-1; shift++) {
				workPairing[shift] = workPairing[shift+1];
			}
			// replacement
			workPairing[1] = secondLast;
			workPairing[actual_teams-1]=firstLast;
			
			// additon
			allDays.add(currentDay);
			
		}
		return allDays;
	}
	

	@Override
	public LinkedList<Match[]> getDays(char group) {
		if(!with_groups) {
			return first_round.get(Constants.DEFAULT_GROUP);
		} else {
			return first_round.get(group);
		}
		
	}


	@Override
	public LinkedList<Match[]> getDaysR(char group) {
		if(!with_groups) {
			return second_round.get(Constants.DEFAULT_GROUP);
		} else {
			return second_round.get(group);
		}
	}
	
	public Set<Character> getGroups() {
		return first_round.keySet();
	}

	public LinkedList<Team> getTop(char group) {
		// extract all the teams:
		Match[] firstPairings = first_round.get(group).getFirst();
		Team homeTeam;
		Team outTeam;
		LinkedList<Team> tempList = new LinkedList<Team>();
		for(int i=0; i<firstPairings.length; i++) {
			homeTeam = firstPairings[i].getHometeam();
			outTeam = firstPairings[i].getOutteam();
			if(!homeTeam.isDummy()) {
				tempList.add(homeTeam);
			}
			if(!outTeam.isDummy()) {
				tempList.add(outTeam);
			}
		}
		
		Collections.sort(tempList);
		return tempList;
	}

}
