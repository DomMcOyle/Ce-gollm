package application;

import java.util.LinkedList;
import java.util.Set;

import entities.ChampionshipTour;
import entities.EliminationTour;
import entities.Match;
import entities.Player;
import entities.Team;
import entities.Tournament;
import entities.TournamentException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
		showTopTeamsButton.setOnAction(e-> {
			showTopTeams();
		});
		showTopPlayersButton = new Button(Constants.BUTTON_PLAYERS);
		showTopPlayersButton.setOnAction(e->{
			showTopPlayers();
		});
		
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
		ChoiceBox<String> groupSelector = new ChoiceBox<>();
	
		Label dayLabel = new Label(Constants.DAY_SELEC_LABEL);
		Label groupLabel = new Label(Constants.GROUP_SELEC_LABEL);
		daySelector.setPrefWidth(200);
		if(toShow.getKind().equals(Constants.CREATION_GROUP)) {

			Set<Character> groups = ((ChampionshipTour)toShow).getGroups();
			for(Character g: groups) {
				groupSelector.getItems().add(Constants.GROUP_NAME + g);
			}
			groupSelector.setOnAction(e->{
				LinkedList<Match[]> days = toShow.getDays(groupSelector.getValue().charAt(groupSelector.getValue().length() - 1));
				daySelector.getSelectionModel().clearSelection();
				daySelector.getItems().clear();
				for(int day = 1; day<=days.size(); day++) {
					daySelector.getItems().add(Constants.DAY_NAME + day);
				}
				if(toShow.withReturn()) {
					// add return eventually
					LinkedList<Match[]> daysR = toShow.getDaysR(groupSelector.getValue().charAt(groupSelector.getValue().length() - 1));
					for(int day = 1; day<=daysR.size(); day++) {
						daySelector.getItems().add(Constants.DAY_NAME + day + Constants.RM_INDICATOR);
					}
				}
				// empty the center part
				
				centralPane.setCenter(null);
				
			});
			leftPane.getChildren().addAll(groupLabel, groupSelector);
			
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
		leftPane.getChildren().addAll(dayLabel, daySelector);
		
		if(toShow.getKind().equals(Constants.CREATION_ELIM)) {
			Button generateNext = new Button(Constants.BUTTON_GENERATE_NEW_ELIM);
			generateNext.setId(Constants.ID_SMALL_BUTTON);
			leftPane.getChildren().add(generateNext);
			
			generateNext.setOnAction(e -> {
				try {
					((EliminationTour)toShow).generateNewRound();
				} catch(TournamentException te){
					new AlertUtil().showAlert(Constants.ERROR_CANNOT_GENERATE_NEXT_ROUND + te.getMessage(), Alert.AlertType.ERROR);
					return;
				}
				
				
				if(toShow.withReturn()) {
					daySelector.getItems().add(daySelector.getItems().size()/2,Constants.DAY_NAME + (daySelector.getItems().size()/2 + 1));
					daySelector.getItems().add(Constants.DAY_NAME + (daySelector.getItems().size()/2  + 1) + Constants.RM_INDICATOR);
				} else {
					daySelector.getItems().add(Constants.DAY_NAME + (daySelector.getItems().size() + 1));
				}
			});
		}
		ScrollPane leftScroll = new ScrollPane(leftPane);
		leftScroll.setFitToHeight(true);
		centralPane.setLeft(leftScroll);
		
		daySelector.setOnAction(e -> {
			if(daySelector.getValue()==null) {
				// this check is needed because daySelector setOnAction is triggered if the selector is updated
				return;
			}
			int selection = Integer.parseInt(daySelector.getValue().split("[ (]")[1]);
			Match[] selectedDay;
			Character selectedGroup = (groupSelector.getItems().size()==0? 
					Constants.DEFAULT_GROUP : 
						groupSelector.getValue().charAt(groupSelector.getValue().length() - 1));
			
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
				matchesPane.add(matchName, 0, i);
				if(!selectedDay[i].isBye()) {
					Match currentMatch = selectedDay[i];
					Label dash = new Label(Constants.DASH_LABEL);
					
					TextField homeResult = new TextField();
					TextField outResult = new TextField();
					homeResult.setPrefWidth(40);
					outResult.setPrefWidth(40);
					homeResult.setTextFormatter(new TextFormatter <> (change -> change.getControlNewText().matches("[0-9]*") ? change : null));
					outResult.setTextFormatter(new TextFormatter <> (change -> change.getControlNewText().matches("[0-9]*") ? change : null));
					if(currentMatch.hasBeenPlayed()) {
						homeResult.setPromptText(String.valueOf(currentMatch.getHomegoals()));
						outResult.setPromptText(String.valueOf(currentMatch.getOutgoals()));
					}
					Button update = new Button(Constants.BUTTON_UPDATE);
					update.setId(Constants.ID_SMALL_BUTTON);
					update.setOnAction(ee->{
						// this is some thrash to ease update
						// alreadyPrompted is true if the match has already a result
						boolean alreadyPrompted = currentMatch.hasBeenPlayed();
						// if some results have been introduced or if the match has already a result..
						if((homeResult.getText()!=null && outResult.getText()!=null)|| alreadyPrompted){
							if(alreadyPrompted) {
								// if the previous check was successfull because of the "already Prompted", we assume that
								// if one wants to change the result, then it may need to change only a single result among the two
								if(homeResult.getText().equals("") && !outResult.getText().equals("")) {
									// in that case, we set the text in the TextField that isn't changed from the prompt (here's the shit)
									homeResult.setText(homeResult.getPromptText());
								} else if (!homeResult.getText().equals("") && outResult.getText().equals("")) {
									// same here
									outResult.setText(outResult.getPromptText());
								}
							}
							if(!homeResult.getText().equals("") && !outResult.getText().equals("")) {
								// if and only iff both results are updated, then the results of the match are updated
								Match.updateMatch(currentMatch,Integer.parseInt(homeResult.getText()), Integer.parseInt(outResult.getText()));
								homeResult.setPromptText(homeResult.getText());
								homeResult.setText("");
								outResult.setPromptText(outResult.getText());
								outResult.setText("");
								
							}
								
						}
					});
					
					matchesPane.add(homeResult, 1, i);
					matchesPane.add(dash, 2, i);
					matchesPane.add(outResult, 3, i );
					matchesPane.add(update, 4, i);
				}
			}
			
			
		});
		
	}
	
	private void showTopTeams() {
		// update buttons
		updateButtons(Constants.BUTTON_TOP);
		
		// dont'show top if the tournament is an elimination one
		if(toShow.getKind().equals(Constants.CREATION_ELIM)) {
			Label noTop = new Label(Constants.WARN_NO_TOP);
			framePane.setCenter(noTop);
			return;
		}
		BorderPane centralPane = new BorderPane();
		framePane.setCenter(centralPane);
		
		if(toShow.getKind().equals(Constants.CREATION_CHAMP)) {
			showGroupTop(Constants.DEFAULT_GROUP, centralPane);
			
		} else {
			// create new lateral box
			VBox leftPane = new VBox();
			leftPane.setAlignment(Pos.TOP_LEFT);
			leftPane.setPadding(new Insets(25, 25, 25, 25));
			leftPane.setSpacing(5d);
			leftPane.setId(Constants.ID_LESS_DARKER_BOX);
			ChoiceBox<String> groupSelector = new ChoiceBox<>();
			Label groupLabel = new Label(Constants.GROUP_SELEC_LABEL);
			Set<Character> groups = ((ChampionshipTour)toShow).getGroups();
			for(Character g: groups) {
				groupSelector.getItems().add(Constants.GROUP_NAME + g);
			}
			leftPane.getChildren().addAll(groupLabel, groupSelector);
			centralPane.setLeft(leftPane);
			groupSelector.setOnAction(e->{
				showGroupTop(groupSelector.getValue().charAt(groupSelector.getValue().length()-1), centralPane);
			});
		}
		
	}
	
	private void showGroupTop(char group, BorderPane centralPane) {
		GridPane topPane = new GridPane();
		topPane.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
		topPane.setAlignment(Pos.CENTER_LEFT);
		topPane.setHgap(20);
		topPane.setVgap(10);
		topPane.setPadding(new Insets(30,30,30,30));
		ScrollPane topScroll = new ScrollPane(topPane);
	
		
		Label teamLabel = new Label(Constants.TEAM_NAME_LABEL);
		Label playedMatches = new Label(Constants.PLAYED_MATCHES_LABEL);
		Label wins = new Label(Constants.WINS_LABEL);
		Label losses = new Label(Constants.LOSSES_LABEL);
		Label draws = new Label(Constants.DRAWS_LABEL);
		Label points = new Label(Constants.POINTS_LABEL);
		points.setId(Constants.ID_SERVER_LABEL);
		Label scoredGoals = new Label(Constants.SCORED_LABEL);
		Label sufferedGoals = new Label(Constants.SUFFERED_LABEL);
		Label diffGoals = new Label(Constants.DIFF_LABEL);
		Label penaltyPoints = new Label(Constants.PENALTY_LABEL);
		
		topPane.add(teamLabel,1 ,0);
		topPane.add(playedMatches, 2, 0);
		topPane.add(wins, 3, 0);
		topPane.add(losses, 4, 0);
		topPane.add(draws, 5, 0);
		topPane.add(points, 6, 0);
		topPane.add(scoredGoals, 7, 0);
		topPane.add(sufferedGoals, 8, 0);
		topPane.add(diffGoals, 9, 0);
		topPane.add(penaltyPoints, 10, 0);
		
		LinkedList<Team> sorted = ((ChampionshipTour)toShow).getTop(group);
		Button updateButton = new Button(Constants.BUTTON_UPDATE);
		updateButton.setDisable(true);
		topPane.add(updateButton,0 , sorted.size()+1 , 2, 1);
		updateButton.setId(Constants.ID_SMALL_BUTTON);
		updateButton.setOnAction(e->{
			showGroupTop(group, centralPane);
		});
		for(int i = 0; i<sorted.size(); i++) {
			Team currentTeam = sorted.get(i);
			topPane.add(new Label(String.valueOf(i+1) + "."), 0, i+1);
			topPane.add(new Label(currentTeam.getName()),1 ,i+1);
			topPane.add(new Label(String.valueOf(currentTeam.getNumMatches())),2, i+1);
			topPane.add(new Label(String.valueOf(currentTeam.getWins())), 3, i+1);
			topPane.add(new Label(String.valueOf(currentTeam.getLosses())), 4, i+1);
			topPane.add(new Label(String.valueOf(currentTeam.getDraws())), 5, i+1);
			Label pts = new Label(String.valueOf(currentTeam.getPoints()));
			pts.setId(Constants.ID_SERVER_LABEL);
			topPane.add(pts, 6, i+1);
			topPane.add(new Label(String.valueOf(currentTeam.getScoredGoals())), 7, i+1);
			topPane.add(new Label(String.valueOf(currentTeam.getSufferedGoals())), 8, i+1);
			topPane.add(new Label(String.valueOf(currentTeam.getDiffGoals())), 9, i+1);
			Label penaltyPts = new Label(String.valueOf(currentTeam.getPenaltyPts()));
			topPane.add(penaltyPts, 10, i+1);
			
			Button incPen = new Button(Constants.BUTTON_PLUS);
			Button decPen = new Button(Constants.BUTTON_MINUS);
			incPen.setId(Constants.ID_SMALL_BUTTON);
			decPen.setId(Constants.ID_SMALL_BUTTON);
			
			topPane.add(incPen, 11, i+1);
			topPane.add(decPen, 12, i+1);
			
			incPen.setOnAction(e->{
				currentTeam.setPenaltyPts(currentTeam.getPenaltyPts()+1);
				penaltyPts.setText(String.valueOf(currentTeam.getPenaltyPts()));
				updateButton.setDisable(false);
			});
			
			decPen.setOnAction(e->{
				if(currentTeam.getPenaltyPts()>0) {
					currentTeam.setPenaltyPts(currentTeam.getPenaltyPts()-1);
					penaltyPts.setText(String.valueOf(currentTeam.getPenaltyPts()));
					updateButton.setDisable(false);
				}
			});
			
		}
		centralPane.setCenter(topScroll);

	}
	
	private void showTopPlayers() {
		updateButtons(Constants.BUTTON_PLAYERS);
		
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
