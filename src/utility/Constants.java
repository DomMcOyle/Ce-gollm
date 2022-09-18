package utility;

import javafx.scene.control.Alert;

/**
 * Classe contenente le costanti utilizzate nel client GUI.
 * 
 * @author Domenico Dell'Olio, Giovanni Pio Delvecchio, Giuseppe Lamantea
 *
 */
public class Constants {

	// Proprieta' della connessione di default 
	public static final String CREATION_NAME_PROMPT = "Nome del torneo...";
	public static final String CREATION_PLAYERS_PROMPT = "Inserisci giocatori...";
	public static final String CREATION_TEAM_PROMPT = "Nome squadra";
	public static final String CREATION_ELIM = "Eliminazione";
	public static final String CREATION_CHAMP = "Campionato";
	public static final String CREATION_GROUP = "Gironi";
	public static final String DEFAULT_SERVER_ID = "(Default)";
	
	// Limiti numerici
	public static final int WINDOWW = 800;
	public static final int WINDOWH = 500;

	
	// Titolo della finestra del programma
	public static final String CLIENT_WINDOW_NAME = "Client";
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
	
	// Contenuti delle label
	public static final String LABEL_SELECTION = "Seleziona un'operazione";
	public static final String LABEL_PREDICTION_QUERY = "Seleziona il valore dell'attributo:";
	public static final String LABEL_TABLE_SELECTION = "Inserisci il nome della tabella";
	public static final String LABEL_SERVER_IP_ADDRESS = "Indirizzo IP";
	public static final String LABEL_SERVER_PORT = "Porta";
	public static final String LABEL_SERVER_ID = "Server ID";
	public static final String LABEL_SERVER_CURR = "Server selezionato: ";
	public static final String LABEL_PREDICTED_VALUE = "Valore predetto:\n";
	
	// Nomi delle colonne delle tableView
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_IP_ADDRESS = "Indirizzo IP";
	public static final String COLUMN_PORT = "Porta";
	

	// Contenuti delle finestre di alert
	public static final String CONFIRM_DELETION = "Sicuro di voler cancellare il torneo ";
	public static final String ERROR_MIS_MATCH = "Più di due squadre assegnate al match ";
	public static final String ERROR_LESS_MATCH = "E' presente un girone/match con una sola squadra.";
	public static final String ERROR_SERVER_UNREACHABLE = "Non e' stato possibile raggiungere il server";
	public static final String ERROR_INIT_CONNECTION = "Errore durante la connessione al server";
	public static final String ERROR_SAVING_TREE = "Errore nel salvataggio dei dati da parte del server";
	public static final String ERROR_LOADING_TOUR = "Errore durante il caricamento della lista dei tornei.\n"
			+ "Verra' caricata una lista di default.";
	public static final String ERROR_SAVING_TOUR = "Errore durante la memorizzazione dei Tornei";
	public static final String ERROR_PARSING_IP = "L'indirizzo IP inserito non � valido.\n"
			+ "I valori che compongono l'indirizzo devono essere interi da 0 a 255.";
	public static final String ERROR_PARSING_PORT = "Il numero di porta inserito non e' valido.\n"
			+ "Il numero di porta deve essere un intero fra 1 e 65535.";
	public static final String ERROR_NO_SERVER_ID = "Il server deve avere un identificatore";
	public static final String ERROR_ID_ALREADY_EXISTS = "Un server dal seguente ID e' gia' esistente: ";
	public static final String ERROR_COMMUNICATING = "Errore di comunicazione con il Server";
	public static final String ERROR_COMMUNICATING_BAD_ANSWER = "Errore di comunicazione con il Server: risposta erronea";
	public static final String ERROR_COMMUNICATING_UNEXPECTED_ANSWER = "Errore di comunicazione con il Server: risposta inattesa";
	public static final String ERROR_SENDING_VALUE = "Impossibile inviare la scelta selezionata al server (errore di comunicazione)";
	

	// CSS IDs
	public static final String ID_SMALL_BUTTON = "smallButton";
	public static final String ID_DROPDOWN = "dropDown";
	public static final String ID_WELCOME_LABEL = "welcomeLabel";
	public static final String ID_PREDICTION_LABEL = "predictionLabel";
	public static final String ID_PREDICTION_BUTTON = "predictionButton";
	public static final String ID_SERVER_LABEL = "serverLabel";

	// Messaggi di comunicazione con il server
	public static final int CLIENT_CREATE = 0;
	public static final int CLIENT_LOAD = 2;
	public static final int CLIENT_SAVE = 1;
	public static final int CLIENT_END = -1;
	public static final int CLIENT_PREDICT = 3;
	public static final String CLIENT_ABORT = "#ABORT";
	public static final String SERVER_OK = "OK";
	public static final String SERVER_QUERY = "QUERY";


	// Contenuti di campi testuali
	public static final String HELP_CONTENT_TEXT = "Programma per la creazione ed esplorazione di alberi di regressione.\n\n"
			+ "E' possibile creare un albero connettendosi ad un server adeguato contenente dei dataset.\n"
			+ "Nella schermata delle impostazioni e' possibile specificare il server a cui connettersi tramite indirizzo IP e porta.\n\n"
			+ "L'opzione \"crea\" permette di creare un nuovo albero di regressione a partire da un dataset memorizzato nel server.\n"
			+ "L'opzione \"carica\" permette di caricare un albero di regressione creato in precedenza dal server.\n\n"
			+ "Per selezionare il dataset, e' necessario inserire il nome della tabella in cui e' memorizzato nel Database del server.\n"
			+ "Una volta inserito il nome della tabella, si potra' esplorare l'albero tramite una serie di query a cui rispondere.\n\n"
			+ "Autori: Domenico Dell'Olio, Giovanni Pio Delvecchio, Giuseppe Lamantea";
	
	public static final String CONTENT_TEXT_NO_TOUR_INFO = "Non e' stato trovato il file \"saved_tournaments\" con i tornei salvati.\n";
	public static final String ERROR_MISSING_DASH = "Errore nella battitura dei nomi dei giocatori. Ricontrolla e Riprova.";
	public static final String PATH_SERIAL_TOUR = "./saved_tournaments.sticki";
	public static final String ID_DARKER_BOX = "darkerBox";
	public static final String ID_LESS_DARKER_BOX = "lessDarkerBox";
	

}
