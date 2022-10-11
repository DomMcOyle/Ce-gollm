package org.openjfx.SuDPall;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.openjfx.entities.ChampionshipTour;
import org.openjfx.entities.EliminationTour;
import org.openjfx.entities.Match;
import org.openjfx.entities.Player;
import org.openjfx.entities.Team;
import org.openjfx.entities.Tournament;
import org.openjfx.entities.TournamentException;
import org.openjfx.utility.Constants;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ExplorerScene extends Scene {
	Button showTeamButton;
	Button showMatchesButton;
	Button showTopTeamsButton;
	Button showTopPlayersButton;
	Button showEliminationButton;
	BorderPane framePane;
	Tournament toShow;
	Tournament switcheroo;
	
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
		if(!toShow.getKind().equals(Constants.CREATION_ELIM)) {
			showEliminationButton = new Button(Constants.BUTTON_SHOW_ELIM);
			if(!((ChampionshipTour)toShow).hasShowdown()) {
				showEliminationButton.setDisable(true);
			} else {
				this.switcheroo = ((ChampionshipTour)toShow).getShowdown();
			}
			topRow.getChildren().add(showEliminationButton);
			showEliminationButton.setOnAction(e->{
				switchTournament();
			});
		}
		
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
			
			Label playerName = new Label(Constants.PLAYER_NAME_LABEL);
			Label playerGoal = new Label(Constants.GOALS_LABEL);
			Label playerAssist = new Label(Constants.ASSISTS_LABEL);
			Label playerReds = new Label("Rossi");
			Label playerYellows = new Label("Gialli");
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
				
				Button deleteButton = new Button(Constants.BUTTON_DELETE);
				deleteButton.setId(Constants.ID_SMALL_BUTTON);
				deleteButton.setOnAction(ee->{
					boolean isConfirmed = new AlertUtil().showAlert(Constants.CONFIRM_PLAY_DELETION + p.getName() +"?", Alert.AlertType.CONFIRMATION);
					if(isConfirmed) {
						selectedTeam.removePlayer(p);
						teamSelector.fireEvent(e);
					}
				});
				playersPane.add(deleteButton, 13, i);
				i++;
			}
			TextField newPlayer = new TextField();
			Button addButton = new Button(Constants.BUTTON_ADD);
			addButton.setId(Constants.ID_SMALL_BUTTON);
			addButton.setOnAction(ee->{
				if(newPlayer.getText()!=null && !newPlayer.getText().trim().equals("")) {
					Player toAdd = new Player(newPlayer.getText(), selectedTeam.getName());
					selectedTeam.addPlayer(toAdd);
					teamSelector.fireEvent(e);
				}
			});
			
			playersPane.add(newPlayer, 0, selectedTeam.getPlayers().size()+1);
			playersPane.add(addButton, 1, selectedTeam.getPlayers().size()+1);
			
		
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
		daySelector.setPrefWidth(156);
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
		
		Button generateNext = new Button();
		generateNext.setId(Constants.ID_SMALL_BUTTON);
		leftPane.getChildren().add(generateNext);
		
		if(toShow.getKind().equals(Constants.CREATION_ELIM)) {
			generateNext.setText(Constants.BUTTON_GENERATE_NEW_ROUND);
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
		} else {
			generateNext.setText(Constants.BUTTON_GENERATE_ELIM);
			if(!((ChampionshipTour)toShow).hasShowdown()) {
				generateNext.setOnAction(e -> {
					generateShowdown();
				});
			} else {
				generateNext.setDisable(true);
			}
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
		Label scoredGoals = new Label(Constants.SCORED_LABEL);
		Label sufferedGoals = new Label(Constants.SUFFERED_LABEL);
		Label diffGoals = new Label(Constants.DIFF_LABEL);
		Label penaltyPoints = new Label(Constants.PENALTY_LABEL);
		
		teamLabel.setId(Constants.ID_PREDICTION_LABEL);
		playedMatches.setId(Constants.ID_PREDICTION_LABEL);
		wins.setId(Constants.ID_PREDICTION_LABEL);
		losses.setId(Constants.ID_PREDICTION_LABEL);
		draws.setId(Constants.ID_PREDICTION_LABEL);
		points.setId(Constants.ID_PREDICTION_LABEL);
		scoredGoals.setId(Constants.ID_PREDICTION_LABEL);
		sufferedGoals.setId(Constants.ID_PREDICTION_LABEL);
		diffGoals.setId(Constants.ID_PREDICTION_LABEL);
		penaltyPoints.setId(Constants.ID_PREDICTION_LABEL);

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

		BorderPane centralPane = new BorderPane();
		framePane.setCenter(centralPane);
		VBox leftPane = new VBox();
		leftPane.setAlignment(Pos.TOP_LEFT);
		leftPane.setPadding(new Insets(25, 25, 25, 25));
		leftPane.setSpacing(5d);
		leftPane.setId(Constants.ID_LESS_DARKER_BOX);
		
		Button goalButton = new Button(Constants.BUTTON_GOALS);
		Button assistButton = new Button(Constants.BUTTON_ASSISTS);
		goalButton.setOnAction(e->{
			centralPane.setCenter(generateTopPlayers(Constants.GENERATE_GOALS));
			goalButton.setDisable(true);
			assistButton.setDisable(false);
		});
		assistButton.setOnAction(e->{
			centralPane.setCenter(generateTopPlayers(Constants.GENERATE_ASSISTS));
			goalButton.setDisable(false);
			assistButton.setDisable(true);
		});
		goalButton.setId(Constants.ID_SMALL_BUTTON);
		assistButton.setId(Constants.ID_SMALL_BUTTON);
		
		leftPane.getChildren().addAll(goalButton, assistButton);
		centralPane.setLeft(leftPane);
		
		
	}
	
	private Node generateTopPlayers(String kind) {
		LinkedList<Player> playerList = new LinkedList<>();
		LinkedList<Player> sortedList = new LinkedList<>();
		LinkedList<Team> teamList = new LinkedList<>();
		Player top;
		int compResult;
		
		if(toShow.getKind().equals(Constants.CREATION_ELIM)) {
			teamList.addAll(toShow.getTeams());
		} else {
			Set<Character> groups = ((ChampionshipTour)toShow).getGroups();
			for(Character g: groups)
				teamList.addAll(((ChampionshipTour)toShow).getTop(g));
		}
		
		for(Team t: teamList) {
			playerList.addAll(t.getPlayers());
		}
		// sortedList creation
		while(!playerList.isEmpty()) {
			top = playerList.getFirst();
			
			for(int i=1; i<playerList.size();i++) {
				
				compResult = playerList.get(i).topCompare(top,kind);
				if(compResult<0) {
					top = playerList.get(i);
				} else if(compResult==0) {
					// smaller index = team with an higher position
					if(toShow.getKind().equals(Constants.CREATION_ELIM)) {

						if(((EliminationTour)toShow).upperThan(toShow.getTeam(top.getTeamname()), toShow.getTeam(playerList.get(i).getTeamname()))<0) {
							top = playerList.get(i);
						}
					} else {
						if(teamList.indexOf(toShow.getTeam(top.getTeamname())) > 
						teamList.indexOf(toShow.getTeam(playerList.get(i).getTeamname()))){
							top = playerList.get(i);
						}
					}
					
				}
			}
			
			sortedList.addLast(top);
			playerList.remove(top);
		}
		
		GridPane topPane = new GridPane();
		topPane.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
		topPane.setAlignment(Pos.CENTER_LEFT);
		topPane.setHgap(20);
		topPane.setVgap(10);
		topPane.setPadding(new Insets(30,30,30,30));
		ScrollPane topScroll = new ScrollPane(topPane);
		
		Label playerName = new Label(Constants.PLAYER_NAME_LABEL);
		Label teamLabel = new Label(Constants.TEAM_NAME_LABEL);
		Label value = new Label("");
		
		if(kind.equals(Constants.GENERATE_GOALS)) {
			value.setText(Constants.GOALS_LABEL);
		} else if (kind.equals(Constants.GENERATE_ASSISTS)) {
			value.setText(Constants.ASSISTS_LABEL);
		}
		
		playerName.setId(Constants.ID_PREDICTION_LABEL);
		teamLabel.setId(Constants.ID_PREDICTION_LABEL);
		value.setId(Constants.ID_PREDICTION_LABEL);
		
		topPane.add(playerName, 1, 0);
		topPane.add(teamLabel,2 ,0);
		topPane.add(value, 3, 0);
		
		for(int i=0; i<sortedList.size(); i++ ) {
			topPane.add(new Label(String.valueOf(i+1) + "."), 0, i+1);
			topPane.add(new Label(sortedList.get(i).getName()), 1, i+1);
			topPane.add(new Label(sortedList.get(i).getTeamname()), 2, i+1);
			if(kind.equals(Constants.GENERATE_GOALS)) {
				topPane.add(new Label(String.valueOf(sortedList.get(i).getGoals())), 3, i+1);
			} else if (kind.equals(Constants.GENERATE_ASSISTS)) {
				topPane.add(new Label(String.valueOf(sortedList.get(i).getAssists())), 3, i+1);
			}
		}
			
		return topScroll;
	}
	
	private void generateShowdown() {
		if(!toShow.hasEnded()) {
			new AlertUtil().showAlert(Constants.ERROR_CANNOT_GENERATE_NEXT_ROUND + "Il torneo non è terminato.", Alert.AlertType.ERROR);
			return;
		}
		
		LinkedList<Team> teamList = new LinkedList<>();
		int[] groupArr = new int[((ChampionshipTour)toShow).getGroups().size()];
		int k = 0;
		for(Character g: ((ChampionshipTour)toShow).getGroups()) {
			teamList.addAll(((ChampionshipTour)toShow).getTop(g));
			groupArr[k] = ((ChampionshipTour)toShow).getTop(g).size();
			k++;
		}
		
		BorderPane frame = new BorderPane();
		frame.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());

		GridPane grid = new GridPane();
		grid.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(50,50,50,50));
		ScrollPane scrollGrid = new ScrollPane(grid);
		
		LinkedList<CheckBox> checkboxes = new LinkedList<>();
		
		Label selectTeam = new Label(Constants.SELECT_TEAM_LABEL);
		selectTeam.setId(Constants.ID_PREDICTION_LABEL);
		
		Button backButton = new Button(Constants.BUTTON_BACK);
		backButton.setOnAction(e->{
			Main.setThisScene(this);
		});
		Button nextButton = new Button(Constants.BUTTON_NEXT);
		nextButton.setDisable(true);
		
		HBox lowerPane = new HBox();
		lowerPane.setAlignment(Pos.CENTER_RIGHT);
		lowerPane.setPadding(new Insets(25, 25, 25, 25));
		lowerPane.setSpacing(5d);
		CheckBox returnCheck = new CheckBox(Constants.CHECK_RETURN);
		lowerPane.getChildren().addAll(returnCheck, backButton, nextButton);
		
		frame.setTop(selectTeam);
		frame.setCenter(scrollGrid);
		frame.setBottom(lowerPane);
		
		int begin = 0; // row where to start to put checkbozes
		if(groupArr.length!=1) {
			// block needed to deal with arrangements of teams in different groups + to print group labels
			begin = 1;
			Object[] groupNames = ((ChampionshipTour)toShow).getGroups().toArray();
			for(int j=0; j<groupNames.length; j++) {
				Label lab = new Label(Constants.GROUP_NAME + (Character)groupNames[j]);
				lab.setId(Constants.ID_SERVER_LABEL);
				grid.add(lab, j, 0);
			}
		}

		int row = begin; // row counter
		int col = 0; // column counter
		k = 0; // group counter
		
		for(int i=0; i<teamList.size(); i++) {
			// prints the checkbox with the squad name and its position in the respective group
			CheckBox teamName = new CheckBox("" + String.valueOf(row-(begin-1)) + "° " + teamList.get(i).getName() );
			teamName.setId(Constants.ID_CHECK_LABEL);
			teamName.setOnAction(e->{
				// function enabling the "next" button only when an appropriate number of teams are chosen
				int numSel = checkboxes.stream().mapToInt(c -> {
					if(c.isSelected()) {
						return 1;
					} else {
						return 0;}}).sum();
				if(power2(numSel)) {
					nextButton.setDisable(false);
				} else {
					nextButton.setDisable(true);
				}
			});
			grid.add(teamName, col, row);
			checkboxes.add(teamName);
			
			if(groupArr.length!=1) {
				// dealing with group wraparound
				if(row==groupArr[k]) {
					col++;
					k++;
					row = 0;
				}
			}
			row++;
		}
		
		Scene selectTeams = new Scene(frame, Constants.WINDOWW, Constants.WINDOWH);
		nextButton.setOnAction(e->{
			LinkedList<Team> selectedTeams = new LinkedList<>();
			for(int i=0; i<checkboxes.size(); i++) {
				if(checkboxes.get(i).isSelected()) {
					// here we create copies of teams, in order to keep them non interacting
					selectedTeams.add(new Team(teamList.get(i)));
				}
			}
			setEncounters(selectedTeams, selectTeams, returnCheck.isSelected());
		});
		Main.setThisScene(selectTeams);
	}
	
	public boolean power2(int x) {
		// returns true if x is a power of 2 and different from 1
		return x != 0 && x!=1 && ((x & (x - 1)) == 0);
	}
	
	private void setEncounters(LinkedList<Team> selectedTeams, Scene backScene, boolean withReturn) {
		// function needed to show the group/match selection panel
		// duplicate of the function for setting the encounters when creating a torunament
		// quick solution to avoid to change everithing again
		BorderPane pane = new BorderPane();
		pane.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
		
		GridPane internalPane = new GridPane();
		internalPane.setAlignment(Pos.CENTER);
		internalPane.setHgap(40);
		internalPane.setVgap(30);
		internalPane.setPadding(new Insets(30,30,30,30));
		
		Label topTeam = new Label(Constants.CREATION_TEAM_PROMPT);
		Label topKind = new Label("Match");
		topTeam.setId(Constants.ID_PREDICTION_LABEL);
		topKind.setId(Constants.ID_PREDICTION_LABEL);
		internalPane.add(topTeam, 0, 0);
		internalPane.add(topKind, 1, 0);
		
		int i = 1;
		LinkedList<TextField> textFieldList = new LinkedList<>();
		for(Team t: selectedTeams) {
			// setting match selection screen
			internalPane.add(new Label(t.getName()), 0,i);
			TextField tf = new TextField();
			tf.setTextFormatter(new TextFormatter <> (change -> change.getControlNewText().matches("[0-9]*") ? change : null));
			internalPane.add(tf,1, i);
			textFieldList.add(tf);
			i = i + 1;
		}
		
		ScrollPane scroll = new ScrollPane(internalPane);
		pane.setCenter(scroll);
		
		HBox buttonsSpace = new HBox();
		buttonsSpace.setAlignment(Pos.CENTER);
		buttonsSpace.setPadding(new Insets(25, 25, 25, 25));
		buttonsSpace.setSpacing(5d);
		
		Button next = new Button(Constants.BUTTON_NEXT);
		Button back = new Button(Constants.BUTTON_BACK);
		back.setOnAction(e -> {
			Main.setThisScene(backScene);
		});
		
		next.setOnAction(e -> {
			// checkCorrectness is used to check whether the id of the match is correct or if there are more or less than 2
			// teams involved in the same match (in elim) or whether there are groups with a single team (in groups)
			HashMap<String, Integer> checkCorrectness = new HashMap<>();
			HashMap <String, String>tmpPairings = new HashMap<>();
			String discriminant;
			
			for(int j=0; j<textFieldList.size(); j++) {
				discriminant = textFieldList.get(j).getText().trim();
				if(discriminant.equals("") || discriminant == null) {
					new AlertUtil().showAlert("Match non assegnato alla squadra " + selectedTeams.get(j).getName(), Alert.AlertType.ERROR);
				}
				
				if(!checkCorrectness.containsKey(discriminant)) {
						if(Integer.parseInt(discriminant)<1 || Integer.parseInt(discriminant)> selectedTeams.size()/2) {
							new AlertUtil().showAlert("Identificativo del match inserito non valido, riprova. (Inserito: " +
									discriminant + ", permessi tra 1 e " + selectedTeams.size()/2
									, Alert.AlertType.ERROR);
							return;
						}
					//update check correctness
					checkCorrectness.put(discriminant, 1);
					tmpPairings.put(selectedTeams.get(j).getName(), discriminant);
							
				} else {
						checkCorrectness.put(discriminant, checkCorrectness.get(discriminant)+1);
						// check in case of elimination tournament whether there are two or more teams associated with a specific match
						if(checkCorrectness.get(discriminant)>2) {
			
							new AlertUtil().showAlert(Constants.ERROR_MIS_MATCH + discriminant, Alert.AlertType.ERROR);
							return;
						}
							
						tmpPairings.put(selectedTeams.get(j).getName(), discriminant);
				}
				
			}
			
			
			if(checkCorrectness.values().contains(1)) {
				// check if there is a match/group with a single team
				new AlertUtil().showAlert(Constants.ERROR_LESS_MATCH, Alert.AlertType.ERROR);
				return;
			}
			
			HashSet<String> placed = new HashSet<>();
			Match[] arrPairings = new Match[selectedTeams.size()/2];
			for(Team t: selectedTeams) {
				
				if (!placed.contains(t.getName())) {
					for(Team t2: selectedTeams) {					
						if(tmpPairings.get(t.getName()).equals(tmpPairings.get(t2.getName())) && t!=t2) {
							placed.add(t.getName());
							placed.add(t2.getName());
							arrPairings[Integer.parseInt(tmpPairings.get(t.getName()))-1] = new Match(t,t2);
							break;
						}
					}
				}
			}
			// championship parsing
			((ChampionshipTour)this.toShow).setShowdown(new EliminationTour(toShow.getName()+" (E)", 
					selectedTeams,withReturn ,arrPairings));
			// here we enable the elimination phase button
			showEliminationButton.setDisable(false);
			// set the "switcheroo" value
			this.switcheroo = ((ChampionshipTour)this.toShow).getShowdown();
			// empty the center pane
			this.framePane.setCenter(null);
			// reset buttons
			this.updateButtons(Constants.RESET_BUTTONS);
			// go back to the previous scene
			Main.setThisScene(this);
		});
		
		buttonsSpace.getChildren().addAll(back,next);
		pane.setBottom(buttonsSpace);
		
		Scene encounter = new Scene(pane, Constants.WINDOWW, Constants.WINDOWH);		
		Main.setThisScene(encounter);
	}
	
	private void switchTournament() {
		Tournament temp = this.toShow;
		this.toShow = switcheroo;
		switcheroo = temp;
		if(this.showEliminationButton.getText().equals(Constants.BUTTON_SHOW_ELIM)) {
			this.showEliminationButton.setText(Constants.BUTTON_SHOW_CHAMP);
			Main.setTitle(Constants.WINDOW_NAME + " - " + toShow.getName());
		} else {
			this.showEliminationButton.setText(Constants.BUTTON_SHOW_ELIM);
			Main.setTitle(Constants.WINDOW_NAME + " - " + toShow.getName() + " (" + toShow.getKind() + ")");
		}
		this.framePane.setCenter(null);
		updateButtons(Constants.RESET_BUTTONS);
		
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
		case Constants.RESET_BUTTONS:
			showTeamButton.setDisable(false);
			showMatchesButton.setDisable(false);
			showTopPlayersButton.setDisable(false);
			showTopTeamsButton.setDisable(false);
			break;
		}
		
	}
}
