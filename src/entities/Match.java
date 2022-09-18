package entities;

import java.io.Serializable;

public class Match implements Serializable {
	private static final long serialVersionUID = 270863940759776256L;
	Team hometeam;
	Team outteam;
	int homegoals;
	int outgoals;
	char group;
	
	public Match(Team hometeam, Team outteam) {
		this.hometeam = hometeam;
		this.outteam = outteam;
		this.homegoals = -1;
		this.outgoals = -1;
		this.group = '*';
	}
	
	public Match(Team hometeam, Team outteam, char group) {
		this.hometeam = hometeam;
		this.outteam = outteam;
		this.homegoals = -1;
		this.outgoals = -1;
		this.group = group;
	}

	public Team getHometeam() {
		return hometeam;
	}

	public void setHometeam(Team hometeam) {
		this.hometeam = hometeam;
	}

	public Team getOutteam() {
		return outteam;
	}

	public void setOutteam(Team outteam) {
		this.outteam = outteam;
	}

	public int getHomegoals() {
		return homegoals;
	}

	public int getOutgoals() {
		return outgoals;
	}

	public void setResults(int homegoals, int outgoals) {
		this.homegoals = homegoals;
		this.outgoals = outgoals;
		
	}
	public char getGroup() {
		return this.group;
	}
	public boolean hasBeenPlayed() {
		return this.homegoals != -1;
	}
	
	public String toString() {
		return hometeam.getName() + "-" + outteam.getName();
	}

	
}
