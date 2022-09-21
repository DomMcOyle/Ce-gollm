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
	
	static public void updateMatch(Match m, int homeResult, int outResult) {
		Team winner = null;
		Team looser = null;
		int winnerGoals = 0;
		int looserGoals = 0;
		
		if(m.hasBeenPlayed()) {
			// if the match has already been played (so if the results need to be updated)
			if(m.getHomegoals()==m.getOutgoals()) {
				//remove a draw if it was a draw
				m.getHometeam().setDraws(m.getHometeam().getDraws()-1);
				m.getOutteam().setDraws(m.getOutteam().getDraws()-1);
				
			} else if (m.getHomegoals()>m.getOutgoals()) {
				// remove the win if it was a win for the hometeam
				m.getHometeam().removeWin(m.getOutteam());
				m.getOutteam().setLosses(m.getOutteam().getLosses()-1);
				
			} else {
				// remove the win if it was a win for the outteam
				m.getOutteam().removeWin(m.getHometeam());
				m.getHometeam().setLosses(m.getHometeam().getLosses()-1);
			}
			
			// update the goal results
			m.getHometeam().setScoredGoals(m.getHometeam().getScoredGoals() - m.getHomegoals());
			m.getOutteam().setScoredGoals(m.getOutteam().getScoredGoals() - m.getOutgoals());
			
			m.getHometeam().setSufferedGoals(m.getHometeam().getSufferedGoals() - m.getOutgoals());
			m.getOutteam().setSufferedGoals(m.getOutteam().getSufferedGoals() -  m.getHomegoals());
		}
		
		if(homeResult > outResult) {
			
			winner = m.getHometeam();
			winnerGoals = homeResult;
			
			looser = m.getOutteam();
			looserGoals = outResult;
		} else if(outResult > homeResult) {
			
			winner = m.getOutteam();
			winnerGoals = outResult;
			
			looser = m.getHometeam();
			looserGoals = homeResult;
		}
		
		// if not draw
		if(winner != null) {
			// update wins and losses
			winner.addWin(looser);
			winner.setScoredGoals(winner.getScoredGoals() + winnerGoals);
			winner.setSufferedGoals(winner.getSufferedGoals() + looserGoals);
			
			looser.setLosses(looser.getLosses()+1);
			looser.setScoredGoals(looser.getScoredGoals() + looserGoals);
			looser.setSufferedGoals(looser.getSufferedGoals() + winnerGoals);
		} else {
			// update draws
			m.getHometeam().setDraws(m.getHometeam().getDraws() + 1);
			m.getOutteam().setDraws(m.getOutteam().getDraws() + 1);
			
			m.getHometeam().setScoredGoals(m.getHometeam().getScoredGoals() + homeResult);
			m.getOutteam().setScoredGoals(m.getOutteam().getScoredGoals() + outResult);
			
			m.getHometeam().setSufferedGoals(m.getHometeam().getSufferedGoals() + outResult);
			m.getOutteam().setSufferedGoals(m.getOutteam().getSufferedGoals() + homeResult);
		}
		
		m.setResults(homeResult, outResult);
	}

	
}
