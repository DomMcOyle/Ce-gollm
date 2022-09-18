package application;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import utility.Constants;

public class AlertUtil {
	/** 
	 * Metodo con cui costruire e mostrare una generica finestra di dialogo di tipo Alert.
	 * Tale finestra vedrà specificato un messaggio da mostrare, <code>message</code>, e un
	 * tipo preimpostato tra quelli resi disponibili dall'enumerazione <code>Alert.AlertType</code>
	 * 
	 * @param message <code>String</code> da mostrare nella finestra di dialogo 
	 * @param type parametro di tipo <code>AlertType</code> per indicare il tipo di finestra di dialogo da mostrare
	 * 
	 * @see Alert
	 */
	public boolean showAlert(String message, Alert.AlertType type) {
		
		Alert toShow = new Alert(type);
		toShow.setContentText(message);
		toShow.getDialogPane().getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
		if (type == Alert.AlertType.CONFIRMATION) {
			Optional<ButtonType> option = toShow.showAndWait();
			return ButtonType.OK.equals(option.get());
		} else {
			toShow.show();
			return true;
		}
			
		
	}
}
