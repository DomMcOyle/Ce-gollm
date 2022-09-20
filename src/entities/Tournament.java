package entities;

import java.io.Serializable;
import java.util.LinkedList;
import javafx.beans.property.SimpleStringProperty;

public abstract class Tournament implements Serializable{
	private static final long serialVersionUID = 984165086748423846L;
	private String name;
	private LinkedList<Team> teams;
	boolean with_return;
	String kind;
	
	public Tournament(String name, LinkedList<Team> teams, boolean with_return, String kind) {
		this.name = name;
		this.teams = teams;
		this.with_return = with_return;
		this.kind = kind;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<Team> getTeams() {
		return teams;
	}
	public Team getTeam(String name) {
		for(Team t: teams) {
			if(t.getName().equals(name)){
				return t;
			}
		}
		return null;
	}
	public void setTeams(LinkedList<Team> teams) {
		this.teams = teams;
	}
	public boolean withReturn() {
		return this.with_return;
	}
	
	public String getKind() {
		return this.kind;
	}
	
	public abstract LinkedList<Match[]> getDays(char group);
	
	public abstract LinkedList<Match[]> getDaysR(char group);
	
}
