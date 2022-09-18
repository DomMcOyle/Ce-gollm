package entities;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = -5304209767238879447L;
	private String name;
	private String teamname;
	private int goals;
	private int assists;
	private int reds;
	private int yellows;
	private int id;
	
	
	public Player(String name, String teamname, int id) {
		this.teamname = teamname;
		this.name = name;
		this.goals=0;
		this.assists=0;
		this.reds=0;
		this.yellows=0;
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
	public String getName() {
		return name;
	}

	
	public int getGoals() {
		return goals;
	}
	public void setGoals(int goals) {
		this.goals = goals;
	}
	public int getAssists() {
		return assists;
	}
	public void setAssists(int assists) {
		this.assists = assists;
	}
	public int getReds() {
		return reds;
	}
	public void setReds(int reds) {
		this.reds = reds;
	}
	public int getYellows() {
		return yellows;
	}
	public void setYellows(int yellows) {
		this.yellows = yellows;
	}


	public String getTeamname() {
		return teamname;
	}
	
	
}
