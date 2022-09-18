package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import entities.EliminationTour;
import entities.Match;
import entities.Player;
import entities.Team;
import entities.Tournament;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.Constants;

public class CreateScene extends Scene {
	private ChoiceBox<String> tournamentType;
	private CheckBox setReturn;
	private HashMap<String, String> tmpTeams;
	private HashMap<String, String> tmpPairings;
	private String tournamentName;
	public CreateScene(BorderPane creationPane,double dim, double dim2) {
		super(creationPane, dim, dim2);
		creationPane.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
		tmpTeams = new HashMap<>();
		
		HBox topRow = new HBox();
		topRow.setAlignment(Pos.CENTER_LEFT);
		topRow.setPadding(new Insets(25, 25, 25, 25));
		topRow.setSpacing(5d);
		
		TextField tournamentName = new TextField();
		tournamentName.setPrefWidth(500);
		tournamentName.setPrefHeight(20);
		tournamentName.setPromptText(Constants.CREATION_NAME_PROMPT);
		
		tournamentType = new ChoiceBox<>();
		tournamentType.getItems().add(Constants.CREATION_ELIM);
		tournamentType.getItems().add(Constants.CREATION_CHAMP);
		tournamentType.getItems().add(Constants.CREATION_GROUP);
		tournamentType.setValue(Constants.CREATION_ELIM);
		
		setReturn = new CheckBox(Constants.CHECK_RETURN);
		
		topRow.getChildren().addAll(tournamentName, tournamentType, setReturn);
		
		creationPane.setTop(topRow);
		
		HBox rightRow = new HBox();
		rightRow.setAlignment(Pos.CENTER);
		rightRow.setPadding(new Insets(25, 25, 25, 25));
		rightRow.setSpacing(5d);
		
		TextArea teamPlayers= new TextArea();
		teamPlayers.setPromptText(Constants.CREATION_PLAYERS_PROMPT);
		rightRow.getChildren().add(teamPlayers);
		creationPane.setCenter(rightRow);
		
		VBox leftPart = new VBox();
		leftPart.setAlignment(Pos.TOP_LEFT);
		leftPart.setPadding(new Insets(25, 25, 25, 25));
		leftPart.setSpacing(5d);
		ChoiceBox<String> addedTeams = new ChoiceBox<>();
		addedTeams.setPrefWidth(200);
		TextField teamName = new TextField();
		teamName.setPrefWidth(200);
		teamName.setPromptText(Constants.CREATION_TEAM_PROMPT);
		
		Button addButton = new Button(Constants.BUTTON_ADD);
		addButton.setId(Constants.ID_SMALL_BUTTON);
		Button deleteButton = new Button(Constants.BUTTON_DELETE);
		deleteButton.setId(Constants.ID_SMALL_BUTTON);
		HBox alignAdd = new HBox();
		alignAdd.setPadding(new Insets(25, 25, 25, 25));
		alignAdd.setSpacing(5d);
		alignAdd.setAlignment(Pos.CENTER);
		alignAdd.getChildren().addAll(addButton,deleteButton);
		leftPart.getChildren().addAll(addedTeams, teamName,alignAdd);
		
		creationPane.setLeft(leftPart);
		HBox bottomRow = new HBox();
		bottomRow.setAlignment(Pos.CENTER_RIGHT);
		bottomRow.setPadding(new Insets(25, 25, 25, 25));
		bottomRow.setSpacing(5d);
		
		
		Button nextButton = new Button(Constants.BUTTON_NEXT);
		Button backButton = new Button(Constants.BUTTON_BACK);
		//nextButton.setId(Constants.ID_SMALL_BUTTON);
		
		bottomRow.getChildren().addAll(backButton,nextButton);
		creationPane.setBottom(bottomRow);
		
		addButton.setOnAction(e -> {
			if(teamName.getText()!=null && teamPlayers.getText()!=null ) {
				if(teamName.getText().trim()!="" && teamPlayers.getText().trim()!="") {
					if(!addedTeams.getItems().contains(teamName.getText().trim())) {
						addedTeams.getItems().add(teamName.getText().trim());
					}
					
					tmpTeams.put(teamName.getText().trim(),teamPlayers.getText().trim());
					teamPlayers.setText("");
					teamName.setText("");
					addedTeams.setValue("");
				
			}}
		});
		
		deleteButton.setOnAction(e-> {
			if(teamName.getText()!=null) {
				if(teamName.getText().trim()!="") {
					tmpTeams.remove(teamName.getText().trim());
					addedTeams.getItems().remove(teamName.getText().trim());
					addedTeams.setValue("");
				}
			}});
		
		addedTeams.setOnAction(e -> {
			String selection = addedTeams.getValue();
			teamName.setText(selection);
			teamPlayers.setText(tmpTeams.get(selection));

		});
		backButton.setOnAction(e->{
			Main.setTournamentSelection();
		});
		nextButton.setOnAction(e -> {
			if (!tmpTeams.isEmpty() && tournamentName.getText()!=null) {
				
				
				this.tournamentName = tournamentName.getText();
				if(tournamentName.getText().trim()!="") {
					if(tournamentType.getValue() != Constants.CREATION_CHAMP) {
						this.setEncounters(tournamentType.getValue());
					} else {
						//TODO: aggiungere torneo a campionato
					}
				}
			}
		});
	}

	private void setEncounters(String kind) {
		BorderPane pane = new BorderPane();
		pane.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
		
		GridPane internalPane = new GridPane();
		internalPane.setAlignment(Pos.CENTER);
		internalPane.setHgap(40);
		internalPane.setVgap(30);
		internalPane.setPadding(new Insets(30,30,30,30));
		
		Label topTeam = new Label(Constants.CREATION_TEAM_PROMPT);
		Label topKind;
		if(kind == Constants.CREATION_ELIM) {
			topKind = new Label("Match");
		} else {
			topKind = new Label("Girone");
		}
		topTeam.setId(Constants.ID_PREDICTION_LABEL);
		topKind.setId(Constants.ID_PREDICTION_LABEL);
		internalPane.add(topTeam, 0, 0);
		internalPane.add(topKind, 1, 0);
		
		int i = 1;
		for(String k : tmpTeams.keySet()) {
			internalPane.add(new Label(k), 0,i);
			internalPane.add(new TextField(),1, i);
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
			Main.setThisScene(this);
		});
		
		next.setOnAction(e -> {
			HashMap<String, Integer> checkCorrectness = new HashMap<>();
			tmpPairings = new HashMap<>();
			String discriminant;
			for(int j=1; j<=(internalPane.getChildren().size()-2)/2; j++ ) {
				discriminant =((TextField)this.getNodeByIndex(j, 1, internalPane)).getText().trim();
				if(!checkCorrectness.containsKey(discriminant)) {
					checkCorrectness.put(discriminant, 1);
					tmpPairings.put(((Label)this.getNodeByIndex(j, 0, internalPane)).getText(), discriminant);
				} else {
					checkCorrectness.put(discriminant, checkCorrectness.get(discriminant)+1);
					if(kind == Constants.CREATION_ELIM && checkCorrectness.get(discriminant)>2) {
						new AlertUtil().showAlert(Constants.ERROR_MIS_MATCH + discriminant, Alert.AlertType.ERROR);
						return;
					}
				}
			}
			
			if(checkCorrectness.values().contains(1)) {
				new AlertUtil().showAlert(Constants.ERROR_LESS_MATCH, Alert.AlertType.ERROR);
				return;
			}
			Tournament t;
			try {
				t = parseTournament(kind);
			} catch(IndexOutOfBoundsException ee) {
				new AlertUtil().showAlert(Constants.ERROR_MISSING_DASH, Alert.AlertType.ERROR);
				return;
			}
			Main.getTourList().add(new TournamentWrapper(t));
			Main.setTournamentSelection();
		});
		
		buttonsSpace.getChildren().addAll(back,next);
		pane.setBottom(buttonsSpace);
		
		Scene encounter = new Scene(pane, Constants.WINDOWW, Constants.WINDOWH);		
		Main.setThisScene(encounter);
	}

	private Node getNodeByIndex (final int row, final int column, GridPane gridPane) {
	    Node result = null;
	    ObservableList<Node> childrens = gridPane.getChildren();

	    for (Node node : childrens) {
	        if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }

	    return result;
	}
	
	private Tournament parseTournament(String kind) throws IndexOutOfBoundsException{
		LinkedList<Team> teams = new LinkedList<>();
		LinkedList<Match> pairings = new LinkedList<>();
		for(String name: tmpTeams.keySet()) {
			Team teamToAdd = new Team(name);
			String[] playersRaw = tmpTeams.get(name).split("\n");
			int i = 0;
			for(String plname : playersRaw) {
				teamToAdd.addPlayer(new Player(plname, teamToAdd.getName(),i));
				i++;
			}
			teams.add(teamToAdd);
		}
		HashSet<String> placed = new HashSet<>();
		for(Team t: teams) {
			if (!placed.contains(t.getName())) {
				for(Team t2: teams) {
					if(tmpPairings.get(t.getName())==tmpPairings.get(t2.getName())) {
						placed.add(t.getName());
						placed.add(t2.getName());
						pairings.add(new Match(t,t2,'a'));
					}
				}
			}
		}
		Match[] arrPairings = new Match[pairings.size()];
		for(int i=0; i<pairings.size(); i++) {
			arrPairings[i] = pairings.get(i);
		}
		//TODO: sistemare il codice per permettere i gironi
		
		//if(kind == Constants.CREATION_ELIM) {
			return new EliminationTour(this.tournamentName, teams, setReturn.isSelected(),arrPairings);
		//}
		
	}
	

}
