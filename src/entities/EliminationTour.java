package entities;

import java.util.LinkedList;

import utility.Constants;

public class EliminationTour extends Tournament {

	private static final long serialVersionUID = -193299888502831775L;
	LinkedList<Match[]> first_round;
	LinkedList<Match[]> second_round;
	
	public EliminationTour(String name, LinkedList<Team> teams, boolean with_ret, Match[] pairings) {
		super(name, teams, with_ret, Constants.CREATION_ELIM);
		first_round = new LinkedList<>();
		Match [] first_day = pairings;
		first_round.add(first_day);
		if (with_ret) {
			second_round = new LinkedList<>();
			Match[] first_day_ret = new Match[pairings.length];
			for(int i=0; i<first_day.length;i++) {
				first_day_ret[i] = new Match(first_day[i].getOutteam(),
						first_day[i].getHometeam(),
						first_day[i].getGroup());
			}
			second_round.add(first_day_ret);
		}
	}
	
	public void generateNewRound() throws TournamentException {
		Match[] last_day = first_round.getLast();
		if(last_day.length == 1) {
			throw new TournamentException("Finale già generata.");
		}
		for(int i =0; i<last_day.length; i++) {
			if(!last_day[i].hasBeenPlayed()) {
				throw new TournamentException("Match " + last_day[i].toString() + " non disputato." );
			}
			if(last_day[i].getHomegoals() == last_day[i].getOutgoals() && !with_return) {
				throw new TournamentException("Match " + last_day[i].toString() + " finito in pareggio." );
			}
		}
		
		if(with_return) {
			Match[] last_day_r = second_round.getLast();
			for(int i =0; i<last_day.length; i++) {

				if(!last_day_r[i].hasBeenPlayed()) {
					throw new TournamentException("Match " + last_day[i].toString() + "(ritorno) non disputato." );
				}

				if(last_day[i].getHomegoals() + last_day_r[i].getOutgoals() == last_day[i].getOutgoals() + last_day_r[i].getHomegoals()) {
					throw new TournamentException("Impossibile determinare vincitore tra " + last_day[i] + ".");
				}
			}
		}
		
		Match[] new_day = new Match[last_day.length/2];
		int hg1;
		int og1;
		Team team1;
		int hg2;
		int og2;
		Team team2;
		if(!with_return) {
			for(int i =0; i<last_day.length; i = i+2) {
				hg1 = last_day[i].getHomegoals();
				og1 = last_day[i].getOutgoals();
				team1 = ((hg1>og1)?last_day[i].getHometeam():last_day[i].getOutteam()); 
				hg2 = last_day[i+1].getHomegoals();
				og2 = last_day[i+1].getOutgoals();
				team2 = ((hg2>og2) ? last_day[i+1].getHometeam(): last_day[i+1].getOutteam());
				new_day[i/2] = new Match(team1, team2);
				
			}
			
		} else {
			Match[] last_day_r = second_round.getLast();
			Match[] new_day_r = new Match[last_day.length/2];
			for(int i =0; i<last_day.length; i = i+2) {
				hg1 = last_day[i].getHomegoals()+last_day_r[i].getOutgoals(); //invertiti
				og1 = last_day[i].getOutgoals()+last_day_r[i].getHomegoals();
				team1 = ((hg1>og1)?last_day[i].getHometeam():last_day[i].getOutteam()); 
				hg2 = last_day[i+1].getHomegoals()+last_day_r[i+1].getOutgoals();
				og2 = last_day[i+1].getOutgoals()+last_day_r[i+1].getHomegoals();
				team2 = ((hg2>og2) ? last_day[i+1].getHometeam(): last_day[i+1].getOutteam());
				new_day[i/2] = new Match(team1, team2);
				new_day_r[i/2] = new Match(team2, team1);
			}
			second_round.add(new_day_r);
		}
		first_round.add(new_day);
	}

	@Override
	public LinkedList<Match[]> getDays(char group) {
		return first_round;
	}

	@Override
	public LinkedList<Match[]> getDaysR(char group) {
		
		return second_round;
	}

	public int upperThan(Team team1, Team team2) {
		// Comparison method to see if team1 has a better position wrt team2, i.e. team1 has access to a 
		// further round or if they have battled and one of the two has won
		int team1MaxRound = -1;
		int team2MaxRound = -1;
		Match[] round;
		for(int i=this.first_round.size()-1; i>=0 ; i--) {
			round = first_round.get(i);
			
			for(Match m: round) {
				
				if((m.getOutteam().equals(team1) || m.getHometeam().equals(team1)) && team1MaxRound == -1) {
					team1MaxRound = i;
				}
				if((m.getOutteam().equals(team2) || m.getHometeam().equals(team2)) && team2MaxRound == -1) {
					team2MaxRound = i;
				}
			}
			if(team1MaxRound != -1 && team2MaxRound != -1) {
				break;
			}
		}
		
		if(team1MaxRound > team2MaxRound) {
			return 1;
		}
		if(team1MaxRound < team2MaxRound) {
			return -1;
		}
		

		round = first_round.get(team1MaxRound);
		Match m = null;
		int team1Goals = 0;
		int team2Goals = 0;
		boolean foundFlag = false;
		
		for(int j=0; j<round.length; j++) {
			m = round[j];
			
			if(m.getHometeam().equals(team1) && m.getOutteam().equals(team2)){
				team1Goals = m.getHomegoals();
				team2Goals = m.getOutgoals();
				if(this.withReturn() && second_round.get(team1MaxRound)[j].hasBeenPlayed()) {
					team1Goals = team1Goals + second_round.get(team1MaxRound)[j].getOutgoals();
					team2Goals = team2Goals + second_round.get(team1MaxRound)[j].getHomegoals();
				}
				foundFlag = true;
			} else if (m.getOutteam().equals(team1) && m.getHometeam().equals(team2)) {
				team1Goals = m.getOutgoals();
				team2Goals = m.getHomegoals();
				if(this.withReturn() && second_round.get(team1MaxRound)[j].hasBeenPlayed()) {
					team1Goals = team1Goals + second_round.get(team1MaxRound)[j].getHomegoals();
					team2Goals = team2Goals + second_round.get(team1MaxRound)[j].getOutgoals();
				}
				foundFlag = true;
			}
			
			if(foundFlag) {
				if(team1Goals>team2Goals) {
					return 1;
				} else if(team1Goals < team2Goals) {
					return -1;
				} else {
					return 0;
				}
			}
		}
		
		return 0;
	}
	

}
