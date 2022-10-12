package org.openjfx.entities;

import java.io.Serializable;
import java.util.LinkedList;

import org.openjfx.utility.Constants;

/*
 * ASSUMPTIONS THAT CAN BE MADE ON TEAM:
 * - no team can be called " " except for the dummy one
 * - no two teams can have the same name (case sensitive)
 * - due to teams having unique names, the wins list can contain only their name
 */
public class Team implements Serializable, Comparable<Team>{

	private static final long serialVersionUID = -7550579557975939694L;
	private String name;
	private LinkedList<String> wins;
	private int draws;
	private int losses;
	private int scoredGoals;
	private int sufferedGoals;
	private int penaltyPts;
	private LinkedList<Player> players;
	
	public Team(String name) {
		this.name = name;
		this.wins= new LinkedList<>();
		this.draws=0;
		this.losses=0;
		this.scoredGoals=0;
		this.sufferedGoals=0;
		this.penaltyPts=0;
		this.players = new LinkedList<>();
	}
	
	public Team(Team toCopy) {
		// copy constructor
		this.name = toCopy.getName();
		this.wins = new LinkedList<>();
		for(String w : toCopy.wins) {
			this.wins.add(w);
		}
		this.draws = toCopy.getDraws();
		this.losses = toCopy.getLosses();
		this.scoredGoals = toCopy.getScoredGoals();
		this.sufferedGoals = toCopy.getSufferedGoals();
		this.penaltyPts = toCopy.getPenaltyPts();
		this.players = new LinkedList<>();
		for(Player p: toCopy.getPlayers()) {
			this.players.add(new Player(p));
		}
	}
	
	
	public int getPoints() {
		return 3*wins.size() + draws - penaltyPts;
	}



	public int getNumMatches() {
		return this.getWins() + this.draws + this.losses;
	}
	public int getWins() {
		return wins.size();
	}

	public void addWin(Team looser) {
		this.wins.add(looser.getName());
	}

	public int getDraws() {
		return draws;
	}

	public void setDraws(int draws) {
		this.draws = draws;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getScoredGoals() {
		return scoredGoals;
	}

	public void setScoredGoals(int scoredGoals) {
		this.scoredGoals = scoredGoals;
	}

	public int getSufferedGoals() {
		return sufferedGoals;
	}

	public void setSufferedGoals(int sufferedGoals) {
		this.sufferedGoals = sufferedGoals;
	}

	public String getName() {
		return name;
	}

	public LinkedList<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public int getDiffGoals() {
		return this.scoredGoals-this.sufferedGoals;
	}
	
	public int getReds() {
		return players.stream().mapToInt(p -> p.getReds()).sum();
	}
	public int getYellows() {
		return players.stream().mapToInt(p -> p.getYellows()).sum();
	}
	private int checkVictories(Team t) {
		if(this.wins.contains(t.getName())) {
			if(t.wins.contains(this.getName())) {
				return 0;
			} else {
				return -1;
			}
		} else if(t.wins.contains(this.getName())) {
			return 1;
		} else {
			return 0;
		}
	}
	@Override
	public int compareTo(Team o) {
		if(this.getPoints() > o.getPoints()) {
			return -1;
		} else if(this.getPoints() < o.getPoints()) {
			return 1;  
		  
		} else if(this.checkVictories(o)!=0) {
			return this.checkVictories(o);
		} else if(this.getDiffGoals() > o.getDiffGoals()) {
			return -1;
		} else if(this.getDiffGoals()<o.getDiffGoals()) {
			return 1;
		} else if(this.getScoredGoals() > o.getScoredGoals()) {
			return -1;
		} else if(this.getScoredGoals()<o.getScoredGoals()) {
			return 1;
		// se la differenza reti è la stessa, ed anche i gollm fatti, allora non ha senso
		// controllare anche i subiti
		}  else if(this.getReds()*3 + this.getYellows() < o.getReds()*3 + o.getYellows()) {
			return -1;
		} else if(this.getReds()*3 + this.getYellows() > o.getReds()*3 + o.getYellows()) {
			return 1;
		} else return 0;
	}


	public int getPenaltyPts() {
		return penaltyPts;
	}


	public void setPenaltyPts(int penaltyPts) {
		this.penaltyPts = penaltyPts;
	}


	public boolean isDummy() {
		// a team is a dummy team if its name is " ", which is forbidden by creation
		return this.name.equals(Constants.DUMMY_TEAM_NAME);
	}


	public void removeWin(Team outteam) {
		// removes a win from the list of wins
		if( this.wins.contains(outteam.getName()) ) {
			this.wins.remove(outteam.getName());
		}
	}
	
	public boolean equals(Team o) {
		return this.getName().equals(o.getName());
	}

	public void removePlayer(Player p) {
		players.remove(p);
		
	}
	
	public String toString() {
		return this.name;
	}


}
