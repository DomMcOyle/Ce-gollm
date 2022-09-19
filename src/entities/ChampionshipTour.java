package entities;

import java.util.HashMap;
import java.util.LinkedList;

public class ChampionshipTour extends Tournament {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4776968057139783067L;
	
	HashMap<String, LinkedList<Match[]>> first_round;
	HashMap<String, LinkedList<Match[]>> second_round;
	boolean with_groups;
	
	
	public ChampionshipTour(String name, LinkedList<Team> teams, boolean with_return, HashMap<Team, String> groups) {
		
		super(name, teams, with_return);

		first_round = new HashMap<>();
		if(groups == null) {
			Match [] first_day = new Match[teams.size()/2];
			for(Team t : teams) {
				
			}
			
		} else {
			
		}
		
		
	}

}
