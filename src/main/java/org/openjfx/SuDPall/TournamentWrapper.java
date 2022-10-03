package org.openjfx.SuDPall;

import org.openjfx.entities.Tournament;

import javafx.beans.property.SimpleStringProperty;

public class TournamentWrapper {
	private Tournament t;
	private SimpleStringProperty nameprop;
	TournamentWrapper(Tournament t){
		this.t = t;
		this.nameprop = new SimpleStringProperty(t.getName());
	}
	
	Tournament getTournament() {
		return t;
	}
	
	
	public SimpleStringProperty namepropProperty() {
		
		return nameprop;
	}
}
