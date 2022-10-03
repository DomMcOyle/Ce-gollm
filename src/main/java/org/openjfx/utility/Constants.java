package org.openjfx.utility;


public class Constants {

	// Proprieta' della connessione di default 
	public static final String CREATION_NAME_PROMPT = "Nome del torneo...";
	public static final String CREATION_PLAYERS_PROMPT = "Inserisci giocatori...";
	public static final String CREATION_TEAM_PROMPT = "Nome squadra";
	public static final String CREATION_ELIM = "Eliminazione";
	public static final String CREATION_CHAMP = "Campionato";
	public static final String CREATION_GROUP = "Gironi";
	
	// Limiti numerici
	public static final int WINDOWW = 900;
	public static final int WINDOWH = 500;

	
	// Titolo della finestra del programma
	public static final String WINDOW_NAME = "SuDPall";
	
	// Path per le risorse utilizzate nel programma
	public static final String PATH_LOG = "log.txt";
	public static final String PATH_THEME = "theme.css";

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
	public static final String BUTTON_GENERATE_NEW_ROUND = "Prossimo Round";
	public static final String BUTTON_GOALS = "Classifica goal";
	public static final String BUTTON_ASSISTS = "Classifica assist";
	public static final String BUTTON_GENERATE_ELIM = "Genera eliminatoria";
	public static final String BUTTON_SHOW_ELIM = "Eliminatoria";
	public static final String BUTTON_SHOW_CHAMP = "Campionato";

	
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
	public static final String TEAM_NAME_LABEL = "Squadra";
	public static final String PLAYED_MATCHES_LABEL = "PG";
	public static final String WINS_LABEL = "V";
	public static final String LOSSES_LABEL = "S";
	public static final String DRAWS_LABEL = "P";
	public static final String POINTS_LABEL = "Pts";
	public static final String SCORED_LABEL = "G+";
	public static final String SUFFERED_LABEL = "G-";
	public static final String DIFF_LABEL = "Diff";
	public static final String PENALTY_LABEL = "Penalità";
	public static final String PLAYER_NAME_LABEL = "Giocatore";
	public static final String GOALS_LABEL = "Goal segnati";
	public static final String ASSISTS_LABEL = "Assist effettuati";
	public static final String SELECT_TEAM_LABEL = "Seleziona le squadre:";

	// Contenuti delle finestre di alert
	public static final String CONFIRM_DELETION = "Sicuro di voler cancellare il torneo ";
	public static final String ERROR_MIS_MATCH = "Più di due squadre assegnate al match ";
	public static final String ERROR_LESS_MATCH = "E' presente un girone/match con una sola squadra.";
	public static final String ERROR_NOT_POWER = "Il numero di squadre non è consono per un torneo ad eliminazione.";
	public static final String ERROR_LOADING_TOUR = "Errore durante il caricamento della lista dei tornei.";
	public static final String ERROR_SAVING_TOUR = "Errore durante la memorizzazione dei Tornei";
	public static final String CONTENT_TEXT_NO_TOUR_INFO = "Non e' stato trovato il file \"saved_tournaments\" con i tornei salvati.";
	public static final String ERROR_CANNOT_GENERATE_NEXT_ROUND = "Non è possibile generare il prossimo round: ";
	public static final String WARN_NO_TOP = "Classifica non generata per torneo ad eliminazione.";
	public static final String ERROR_GENERIC_MESSAGE = "E' stato riscontrato un errore durante l'esecuzione. Per maggiori informazioni"
			+ "visionare il file di log.";
	public static final String ERROR_PRODUCING_LOG = "Errore durante la generazione del file di log.";


	// CSS IDs
	public static final String ID_SMALL_BUTTON = "smallButton";
	public static final String ID_DROPDOWN = "dropDown";
	public static final String ID_WELCOME_LABEL = "welcomeLabel";
	public static final String ID_PREDICTION_LABEL = "predictionLabel";
	public static final String ID_PREDICTION_BUTTON = "predictionButton";
	public static final String ID_SERVER_LABEL = "serverLabel";
	public static final String ID_DARKER_BOX = "darkerBox";
	public static final String ID_LESS_DARKER_BOX = "lessDarkerBox";
	public static final String ID_CHECK_LABEL = "checkLabel";

	// Internal constants
	public static final char DEFAULT_GROUP = '*';
	// for championships with an odd number of teams, it may be necessary to add a
	// dummy team to indicate byes.
	public static final String DUMMY_TEAM_NAME = " ";
	public static final String PATH_SERIAL_TOUR = "./saved_tournaments.sticki";
	public static final String GENERATE_GOALS = "goals";
	public static final String GENERATE_ASSISTS = "assists";
	public static final String RESET_BUTTONS = "reset";
	

	

	


	
	
	
	
	
	
	
	

	



	

}
