package utility;

import javafx.scene.control.Alert;

public class Constants {

	// Proprieta' della connessione di default 
	public static final String CREATION_NAME_PROMPT = "Nome del torneo...";
	public static final String CREATION_PLAYERS_PROMPT = "Inserisci giocatori...";
	public static final String CREATION_TEAM_PROMPT = "Nome squadra";
	public static final String CREATION_ELIM = "Eliminazione";
	public static final String CREATION_CHAMP = "Campionato";
	public static final String CREATION_GROUP = "Gironi";
	
	// Limiti numerici
	public static final int WINDOWW = 800;
	public static final int WINDOWH = 500;

	
	// Titolo della finestra del programma
	public static final String WINDOW_NAME = "SuDPall";
	public static final String HELP_WINDOW_NAME = "Aiuto";
	
	// Path per le risorse utilizzate nel programma
	public static final String PATH_CLIENT_ICON = "images/icon.png";
	public static final String PATH_GEAR_ICON = "images/gear.png";
	public static final String PATH_HELP_ICON = "images/questionMark.png";
	public static final String PATH_THEME = "theme.css";
	public static final String PATH_SERVER_INFO = "servers.info";
	public static final String PATH_WARNING_ICON = "images/warning.png";
	public static final String PATH_ERROR_ICON = "images/error.png";

	// Contenuti dei pulsanti
	public static final String BUTTON_NEXT = "Avanti";
	public static final String BUTTON_TEAMS = "Squadre";
	public static final String BUTTON_MATCHES = "Partite";
	public static final String BUTTON_CREATE = "Crea Torneo";
	public static final String BUTTON_PLAYERS = "Classifica Giocatori";
	public static final String BUTTON_BACK = "Indietro";
	public static final String BUTTON_TOP = "Classifica";
	public static final String BUTTON_ADD = "Aggiungi";
	public static final String BUTTON_OPEN = "Apri";
	public static final String BUTTON_DELETE = "Elimina";
	public static final String BUTTON_PLUS = "+";
	public static final String BUTTON_MINUS = "-";
	public static final String CHECK_RETURN = "Con ritorno";
	public static final String BUTTON_UPDATE = "Aggiorna";
	public static final String BUTTON_GENERATE_NEW_ELIM = "Prossimo Round";
	
	// Contenuti delle label
	public static final String DAY_NAME = "Giornata ";
	public static final String TEAM_SELEC_LABEL = "Squadre:";
	public static final String DAY_SELEC_LABEL = "Giornate:";
	public static final String RM_INDICATOR ="(R)";
	public static final String MATCH_TITLE_LABEL = "Partita";
	public static final String DASH_LABEL = "-";
	public static final String RECEIVES_BYE = "(bye)";
	public static final String GROUP_NAME = "Girone ";
	public static final String GROUP_SELEC_LABEL = "Gironi:";

	// Contenuti delle finestre di alert
	public static final String CONFIRM_DELETION = "Sicuro di voler cancellare il torneo ";
	public static final String ERROR_MIS_MATCH = "Più di due squadre assegnate al match ";
	public static final String ERROR_LESS_MATCH = "E' presente un girone/match con una sola squadra.";
	public static final String ERROR_NOT_POWER = "Il numero di squadre non è consono per un torneo ad eliminazione.";
	public static final String ERROR_LOADING_TOUR = "Errore durante il caricamento della lista dei tornei.\n";
	public static final String ERROR_SAVING_TOUR = "Errore durante la memorizzazione dei Tornei";
	public static final String CONTENT_TEXT_NO_TOUR_INFO = "Non e' stato trovato il file \"saved_tournaments\" con i tornei salvati.\n";
	public static final String ERROR_CANNOT_GENERATE_NEXT_ROUND = "Non è possibile generare il prossimo round: ";


	// CSS IDs
	public static final String ID_SMALL_BUTTON = "smallButton";
	public static final String ID_DROPDOWN = "dropDown";
	public static final String ID_WELCOME_LABEL = "welcomeLabel";
	public static final String ID_PREDICTION_LABEL = "predictionLabel";
	public static final String ID_PREDICTION_BUTTON = "predictionButton";
	public static final String ID_SERVER_LABEL = "serverLabel";
	public static final String ID_DARKER_BOX = "darkerBox";
	public static final String ID_LESS_DARKER_BOX = "lessDarkerBox";

	// Internal constants
	public static final char DEFAULT_GROUP = '*';
	// for championships with an odd number of teams, it may be necessary to add a
	// dummy team to indicate byes.
	public static final String DUMMY_TEAM_NAME = " ";
	public static final String PATH_SERIAL_TOUR = "./saved_tournaments.sticki";
	
	
	
	
	
	
	

	



	

}
