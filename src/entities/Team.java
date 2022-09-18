package entities;

import java.io.Serializable;
import java.util.LinkedList;

public class Team implements Serializable, Comparable<Team>{

	private static final long serialVersionUID = -7550579557975939694L;
	private String name;
	private LinkedList<Team> wins;
	private int draws;
	private int losses;
	private int scoredGoals;
	private int sufferedGoals;
	private int points;
	private int penalty_pts;
	private LinkedList<Player> players;
	
	public Team(String name) {
		this.name = name;
		this.wins= new LinkedList<>();
		this.draws=0;
		this.losses=0;
		this.scoredGoals=0;
		this.sufferedGoals=0;
		this.points=0;
		this.penalty_pts=0;
		this.players = new LinkedList<>();
	}
	
	
	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}

	public int getNumMatches() {
		return this.getWins() + this.draws + this.losses;
	}
	public int getWins() {
		return wins.size();
	}

	public void addWin(Team looser) {
		this.wins.add(looser);
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
		if(this.wins.contains(t)) {
			if(t.wins.contains(this)) {
				return 0;
			} else {
				return -1;
				}
		} else if(t.wins.contains(this)) {
			return 1;
		} else {
			return 0;
		}
	}
	@Override
	public int compareTo(Team o) {
		if(this.points > o.getPoints()) {
			return -1;
		} else if(this.points < o.getPoints()) {
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
		return penalty_pts;
	}


	public void setPenaltyPts(int penalty_pts) {
		this.penalty_pts = penalty_pts;
	}
	
	

}
