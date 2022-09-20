package entities;

import java.io.Serializable;

import utility.Constants;

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
		this.group = Constants.DEFAULT_GROUP;
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
	
	public boolean isBye() {
		return this.hometeam.isDummy() || this.outteam.isDummy();
	}
	
	public String toString() {
		if (this.isBye()){
			Team not_dummy = (this.hometeam.isDummy()?this.outteam:this.hometeam);
			return not_dummy.getName() + " " + Constants.RECEIVES_BYE;
		} else {
			return hometeam.getName() + " vs. " + outteam.getName();
		}
	}

	
}
