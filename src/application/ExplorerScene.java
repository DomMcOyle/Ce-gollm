package application;

import java.util.LinkedList;

import entities.Match;
import entities.Player;
import entities.Team;
import entities.Tournament;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.Constants;

public class ExplorerScene extends Scene {
	Button showTeamButton;
	Button showMatchesButton;
	Button showTopTeamsButton;
	Button showTopPlayersButton;
	BorderPane framePane;
	Tournament toShow;
	
	public ExplorerScene(BorderPane mainPane, double dim, double dim2, Tournament toShow) {
		super(mainPane, dim, dim2);
		mainPane.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
		this.framePane = mainPane;
		this.toShow = toShow;
		
		HBox topRow = new HBox();
		topRow.setAlignment(Pos.CENTER);
		topRow.setPadding(new Insets(25, 25, 25, 25));
		topRow.setSpacing(5d);
		topRow.setId(Constants.ID_DARKER_BOX);
		showTeamButton = new Button(Constants.BUTTON_TEAMS);
		showTeamButton.setOnAction(e -> {
			showTeams();
		});
		showMatchesButton = new Button(Constants.BUTTON_MATCHES);
		showMatchesButton.setOnAction(e->{
			showMatches();
		});
		showTopTeamsButton = new Button(Constants.BUTTON_TOP);
		showTopPlayersButton = new Button(Constants.BUTTON_PLAYERS);
		
		topRow.getChildren().addAll(showTeamButton,
				showMatchesButton, showTopTeamsButton, showTopPlayersButton);
		
		mainPane.setTop(topRow);
		
		HBox bottomRow = new HBox();
		bottomRow.setAlignment(Pos.CENTER_LEFT);
		bottomRow.setPadding(new Insets(25, 25, 25, 25));
		bottomRow.setSpacing(5d);
		bottomRow.setId(Constants.ID_DARKER_BOX);
		Button backButton = new Button(Constants.BUTTON_BACK);
		backButton.setOnAction(e->{
			Main.setTournamentSelection();
			Main.setTitle(Constants.WINDOW_NAME);
		});
		bottomRow.getChildren().addAll(backButton);
		
		mainPane.setBottom(bottomRow);
		
	}
	
	private void showTeams() {
		// at the beginning update the buttons
		updateButtons(Constants.BUTTON_TEAMS);
		BorderPane centralPane = new BorderPane();

		// create the central pane for the frame
		framePane.setCenter(centralPane);
		// add lateral scroll bar bar
		VBox leftPane = new VBox();
		leftPane.setAlignment(Pos.TOP_LEFT);
		leftPane.setPadding(new Insets(25, 25, 25, 25));
		leftPane.setSpacing(5d);
		leftPane.setId(Constants.ID_LESS_DARKER_BOX);
		Label selecTeam = new Label(Constants.TEAM_SELEC_LABEL);
		ChoiceBox<String> teamSelector = new ChoiceBox<>();
		teamSelector.setPrefWidth(200);
		for(Team t: toShow.getTeams()) {
			teamSelector.getItems().add(t.getName());
		}
		Label playedMatches = new Label();
		Label draws = new Label();
		Label wins = new Label();
		Label losses = new Label();
		Label scoredGoals = new Label();
		Label sufferedGoals = new Label();
		Label diffGoals = new Label();
		leftPane.getChildren().addAll(selecTeam,teamSelector,playedMatches, draws, wins, losses,scoredGoals, sufferedGoals, diffGoals);
		ScrollPane leftScroll = new ScrollPane(leftPane);
		leftScroll.setFitToHeight(true);
		centralPane.setLeft(leftScroll);
		
		// update team selection
		teamSelector.setOnAction(e->{
			String selection = teamSelector.getValue();
			Team selectedTeam = toShow.getTeam(selection);
			playedMatches.setText("Partite Giocate: " + selectedTeam.getNumMatches());
			draws.setText("Pareggi: " + selectedTeam.getDraws());
			wins.setText("Vittorie: " + selectedTeam.getWins());
			losses.setText("Sconfitte: "+ selectedTeam.getLosses());
			scoredGoals.setText("Gol Fatti: " + selectedTeam.getScoredGoals());
			sufferedGoals.setText("Gol Subiti: "+selectedTeam.getSufferedGoals());
			diffGoals.setText("Differenza: " + selectedTeam.getDiffGoals());			
			GridPane playersPane = new GridPane();
			playersPane.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
			playersPane.setAlignment(Pos.CENTER_LEFT);
			playersPane.setHgap(20);
			playersPane.setVgap(10);
			playersPane.setPadding(new Insets(30,30,30,30));
			ScrollPane playerScroll = new ScrollPane(playersPane);
			
			centralPane.setCenter(playerScroll);
			
			Label playerName = new Label("Giocatore");
			Label playerGoal = new Label("Gol");
			Label playerAssist = new Label("Assist");
			Label playerReds = new Label("ROSSI");
			Label playerYellows = new Label("GIALLI");
			playerName.setId(Constants.ID_PREDICTION_LABEL);
			playerGoal.setId(Constants.ID_PREDICTION_LABEL);
			playerAssist.setId(Constants.ID_PREDICTION_LABEL);
			playerReds.setId(Constants.ID_PREDICTION_LABEL);
			playerYellows.setId(Constants.ID_PREDICTION_LABEL);
			
			playersPane.add(playerName, 0, 0);
			playersPane.add(playerGoal, 1, 0);
			playersPane.add(playerAssist, 4, 0);
			playersPane.add(playerReds, 7, 0);
			playersPane.add(playerYellows, 10, 0);
			
			int i = 1;
			for(Player p: selectedTeam.getPlayers()) {
				Label name = new Label(p.getName());
				playersPane.add(name,  0, i);
				Label goal = new Label(String.valueOf(p.getGoals()));
				playersPane.add(goal, 1, i);
				Button incGoal = new Button(Constants.BUTTON_PLUS);
				Button decGoal = new Button(Constants.BUTTON_MINUS);
				incGoal.setId(Constants.ID_SMALL_BUTTON);
				decGoal.setId(Constants.ID_SMALL_BUTTON);
				playersPane.add(incGoal, 2, i);
				playersPane.add(decGoal, 3, i);
				incGoal.setOnAction(ee->{
					p.setGoals(p.getGoals()+1);
					goal.setText(String.valueOf(p.getGoals()));
				});
				decGoal.setOnAction(ee->{
					if( p.getGoals()>0){
					p.setGoals(p.getGoals()-1);
					goal.setText(String.valueOf(p.getGoals()));}
				});
				
				Label assist = new Label(String.valueOf(p.getAssists()));
				Button incAss = new Button(Constants.BUTTON_PLUS);
				Button decAss = new Button(Constants.BUTTON_MINUS);
				incAss.setId(Constants.ID_SMALL_BUTTON);
				decAss.setId(Constants.ID_SMALL_BUTTON);
				playersPane.add(assist, 4, i);
				playersPane.add(incAss, 5, i);
				playersPane.add(decAss, 6, i);
				incAss.setOnAction(ee->{
					p.setAssists(p.getAssists()+1);
					assist.setText(String.valueOf(p.getAssists()));
				});
				decAss.setOnAction(ee->{
					if( p.getAssists()>0){
					p.setAssists(p.getAssists()-1);
					assist.setText(String.valueOf(p.getAssists()));}
				});
				
				Label reds = new Label(String.valueOf(p.getReds()));
				Button incRed = new Button(Constants.BUTTON_PLUS);
				Button decRed = new Button(Constants.BUTTON_MINUS);
				incRed.setId(Constants.ID_SMALL_BUTTON);
				decRed.setId(Constants.ID_SMALL_BUTTON);
				playersPane.add(reds, 7, i);
				playersPane.add(incRed, 8, i);
				playersPane.add(decRed, 9, i);
				incRed.setOnAction(ee->{
					p.setReds(p.getReds()+1);
					reds.setText(String.valueOf(p.getReds()));
				});
				decRed.setOnAction(ee->{
					if( p.getReds()>0){
					p.setReds(p.getReds()-1);
					reds.setText(String.valueOf(p.getReds()));}
				});
				
				Label yellows = new Label(String.valueOf(p.getYellows()));
				Button incYel = new Button(Constants.BUTTON_PLUS);
				Button decYel = new Button(Constants.BUTTON_MINUS);
				incYel.setId(Constants.ID_SMALL_BUTTON);
				decYel.setId(Constants.ID_SMALL_BUTTON);
				playersPane.add(yellows, 10, i);
				playersPane.add(incYel, 11, i);
				playersPane.add(decYel, 12, i);
				incYel.setOnAction(ee->{
					p.setYellows(p.getYellows()+1);
					yellows.setText(String.valueOf(p.getYellows()));
				});
				decYel.setOnAction(ee->{
					if( p.getYellows()>0){
					p.setYellows(p.getYellows()-1);
					yellows.setText(String.valueOf(p.getYellows()));}
				});
				
				i++;
			}
		
		});
		
		
	}
	
	private void showMatches() {
		// update buttons
		updateButtons(Constants.BUTTON_MATCHES);
		// create new central panel
		BorderPane centralPane = new BorderPane();
		framePane.setCenter(centralPane);
		
		// create new lateral box
		VBox leftPane = new VBox();
		leftPane.setAlignment(Pos.TOP_LEFT);
		leftPane.setPadding(new Insets(25, 25, 25, 25));
		leftPane.setSpacing(5d);
		leftPane.setId(Constants.ID_LESS_DARKER_BOX);
		
		// day selector
		
		ChoiceBox<String> daySelector = new ChoiceBox<>();
		ChoiceBox<Character> groupSelector = new ChoiceBox<>();
	
		Label dayLabel = new Label(Constants.DAY_SELEC_LABEL);
		leftPane.getChildren().addAll(dayLabel, daySelector);
		daySelector.setPrefWidth(200);
		if(toShow.getKind() == Constants.CREATION_GROUP) {
			// TODO: add group visualization
		
			
		} else {
			// list all the "direct match"
			LinkedList<Match[]> days = toShow.getDays(Constants.DEFAULT_GROUP);
			for(int day = 1; day<=days.size(); day++) {
				daySelector.getItems().add(Constants.DAY_NAME + day);
			}
			// list all rematchs (eventually)
			if(toShow.withReturn()) {
				LinkedList<Match[]> daysR = toShow.getDaysR(Constants.DEFAULT_GROUP);
				for(int day = 1; day<=daysR.size(); day++) {
					daySelector.getItems().add(Constants.DAY_NAME + day + Constants.RM_INDICATOR);
				}
			}
		}
		ScrollPane leftScroll = new ScrollPane(leftPane);
		leftScroll.setFitToHeight(true);
		centralPane.setLeft(leftScroll);
		
		daySelector.setOnAction(e -> {
			int selection = Integer.parseInt(daySelector.getValue().split(" ")[1]);
			Match[] selectedDay;
			Character selectedGroup = (groupSelector.getItems().size()==0? Constants.DEFAULT_GROUP : groupSelector.getValue());
			
			if(daySelector.getValue().contains(Constants.RM_INDICATOR)){
				selectedDay = toShow.getDaysR(selectedGroup).get(selection-1);	
			} else {
				selectedDay = toShow.getDays(selectedGroup).get(selection-1);	
			}
			GridPane matchesPane = new GridPane();
			matchesPane.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
			matchesPane.setAlignment(Pos.CENTER_LEFT);
			matchesPane.setHgap(20);
			matchesPane.setVgap(10);
			matchesPane.setPadding(new Insets(30,30,30,30));
			ScrollPane matchesScroll = new ScrollPane(matchesPane);
			
			centralPane.setCenter(matchesScroll);
			
			for(int i=0; i<selectedDay.length; i++) {
				Label matchName = new Label(selectedDay[i].toString());
				Label dash = new Label(Constants.DASH_LABEL);
				TextField homeResult = new TextField();
				TextField outResult = new TextField();
				homeResult.setPrefWidth(40);
				outResult.setPrefWidth(40);
				homeResult.setTextFormatter(new TextFormatter <> (change -> change.getControlNewText().matches("[0-9]*") ? change : null));
				outResult.setTextFormatter(new TextFormatter <> (change -> change.getControlNewText().matches("[0-9]*") ? change : null));
				Button update = new Button(Constants.BUTTON_UPDATE);
				update.setId(Constants.ID_SMALL_BUTTON);
				
				matchesPane.add(matchName, 0, i);
				matchesPane.add(homeResult, 1, i);
				matchesPane.add(dash, 2, i);
				matchesPane.add(outResult, 3, i );
				matchesPane.add(update, 4, i);
			}
			
			
		});
		
		
		
		
		/*
		for(Team t: toShow.getTeams()) {
			daySelector.getItems().add(t.getName());
		}
		TODO: se il torneo è a eliminazione/campionato non servono i gironi
			altrimenti va aggiunto il coso a gironi
		
		*/
	}
	
	
	private void updateButtons(String pressedButton) {
		switch(pressedButton) {
		case Constants.BUTTON_TEAMS:
			showTeamButton.setDisable(true);
			showMatchesButton.setDisable(false);
			showTopPlayersButton.setDisable(false);
			showTopTeamsButton.setDisable(false);
			break;
		case Constants.BUTTON_MATCHES:
			showTeamButton.setDisable(false);
			showMatchesButton.setDisable(true);
			showTopPlayersButton.setDisable(false);
			showTopTeamsButton.setDisable(false);
			break;
		case Constants.BUTTON_TOP:
			showTeamButton.setDisable(false);
			showMatchesButton.setDisable(false);
			showTopPlayersButton.setDisable(false);
			showTopTeamsButton.setDisable(true);
			break;
		case Constants.BUTTON_PLAYERS:
			showTeamButton.setDisable(false);
			showMatchesButton.setDisable(false);
			showTopPlayersButton.setDisable(true);
			showTopTeamsButton.setDisable(false);
			break;
		}
		
	}
}
