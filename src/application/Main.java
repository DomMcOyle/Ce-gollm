package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import entities.Tournament;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import utility.Constants;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private static SelectionScene ts;
	private static Stage mainStage;
	private static ObservableList<TournamentWrapper> tournaments;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			tournaments = FXCollections.observableArrayList();
			mainStage = primaryStage;
			mainStage.setOnCloseRequest(e->{
				dumpTournaments();
			});
			loadTournaments();
			ts = new SelectionScene(new BorderPane(),Constants.WINDOWW,Constants.WINDOWH);
			primaryStage.setScene(ts);
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void loadTournaments() {
		try {

			FileInputStream tourInFile = new FileInputStream(Constants.PATH_SERIAL_TOUR);
			ObjectInputStream tourIn = new ObjectInputStream(tourInFile);
			@SuppressWarnings("unchecked")
			// Il compilatore solleva un warning sul cast non sicuro (a causa dell'erasure).
			// Viene ignorato poiche' si e' certi di cio' che e' stato memorizzato.
			LinkedList<Tournament> serializedTournamentList = (LinkedList<Tournament>) tourIn.readObject();
			tournaments = FXCollections.observableArrayList();
			for(Tournament t : serializedTournamentList) {
				tournaments.add(new TournamentWrapper(t));
			}
			tourIn.close();
			tourInFile.close();
		} catch (IOException | ClassNotFoundException e1) {
			/**
			 * In caso di errore durante il caricamento della lista server vengono
			 * mostrate delle finestre di dialogo contestuali e si mantiene la lista di default
			 */
			if (e1 instanceof FileNotFoundException) {
				
				new AlertUtil().showAlert(Constants.CONTENT_TEXT_NO_TOUR_INFO, Alert.AlertType.WARNING);
			} else {

				new AlertUtil().showAlert(Constants.ERROR_LOADING_TOUR, Alert.AlertType.ERROR);
			}
		}
	}
	
	public void dumpTournaments() {
			try {
				/*
				 * Viene impostata la serializzazione della lista di server conosciuti alla chiusura
				 * del programma.
				 */
				FileOutputStream tourOutFile = new FileOutputStream(Constants.PATH_SERIAL_TOUR);
				ObjectOutputStream tourOut = new ObjectOutputStream(tourOutFile);

				/*
				 * Al posto di servers, viene serializzato un ArrayList di MutableServerInformation, ovvero la controparte
				 * mutabile di ServerInformation. Questa scelta e' stata fatta poiche' gli attributi di ServerInformation
				 * non sono serializzabili.
				 */
				LinkedList<Tournament> toSerialize = new LinkedList<>();
				for(TournamentWrapper e: tournaments) {
					toSerialize.add(e.getTournament());
				}
				
				tourOut.writeObject(toSerialize);

				tourOut.close();
				tourOutFile.close();
			} catch (IOException e1) {

				new AlertUtil().showAlert(Constants.ERROR_SAVING_TOUR, Alert.AlertType.ERROR);
				e1.printStackTrace();
			}
		}
	
	
	public static void main(String[] args) {
		launch(args);
	}

	public static void setTournamentSelection() {
		mainStage.setScene(ts);
		ts.updateButtons();
	}
	
	public static void setThisScene(Scene thisScene) {
		mainStage.setScene(thisScene);
	}
	
	public static ObservableList<TournamentWrapper> getTourList(){
		return tournaments;
	}
	
}
