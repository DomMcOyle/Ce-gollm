package application;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import entities.Tournament;
import utility.Constants;

public class SelectionScene extends Scene {
	
	private TableView<TournamentWrapper> tv;
	private TableView.TableViewFocusModel<TournamentWrapper> userFocus;
	Button confirmTour;
	Button addTour;
	Button removeTour;
	
	public SelectionScene(BorderPane selectionPane,double dim, double dim2) {
		super(selectionPane, dim, dim2);
		selectionPane.getStylesheets().add(getClass().getResource(Constants.PATH_THEME).toString());
		ObservableList<TournamentWrapper> tournaments = Main.getTourList();
		
		tv = new TableView<>(tournaments);
		TableColumn<TournamentWrapper, String> nameCol = new TableColumn<TournamentWrapper, String>("Seleziona un Torneo:");
		nameCol.setCellValueFactory(new PropertyValueFactory<TournamentWrapper, String>("nameprop"));
		tv.getColumns().add(nameCol);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		userFocus = new TableView.TableViewFocusModel<>(tv);
		tv.setFocusModel(userFocus);
		
		HBox selectionButtonsLayout = new HBox();
		selectionButtonsLayout.setAlignment(Pos.CENTER);
		selectionButtonsLayout.setPadding(new Insets(25, 25, 25, 25));
		selectionButtonsLayout.setSpacing(5d);
		selectionButtonsLayout.setId(Constants.ID_DARKER_BOX);

		addTour = new Button(Constants.BUTTON_CREATE);
		removeTour = new Button(Constants.BUTTON_DELETE);
		confirmTour = new Button(Constants.BUTTON_OPEN);

		
		updateButtons();
		addTour.setOnAction(e -> {
			Main.setThisScene(new CreateScene(new BorderPane(),Constants.WINDOWW,Constants.WINDOWH));
		});
		
		removeTour.setOnAction(e -> {
			boolean delete = new AlertUtil().showAlert(Constants.CONFIRM_DELETION + "\"" 
					+ userFocus.getFocusedItem().getTournament().getName() + "\"?"
					,Alert.AlertType.CONFIRMATION);
			if (delete){
				tournaments.remove(userFocus.getFocusedItem());
				updateButtons();
			}

		});
		
		confirmTour.setOnAction(e->{
			Main.setThisScene(new ExplorerScene(new BorderPane(), 
					Constants.WINDOWW, 
					Constants.WINDOWH,
					userFocus.getFocusedItem().getTournament()));
		});
		
		selectionButtonsLayout.getChildren().addAll(addTour, removeTour, confirmTour);

		//selectionPane.setTop(currentServerInfo);
		selectionPane.setCenter(tv);
		selectionPane.setBottom(selectionButtonsLayout);
		
		
	}
	public void updateButtons() {
		if (Main.getTourList().size() == 0) {
			
			//Se la tabella viene svuotata, il tasto conferma si disattiva
			confirmTour.setDisable(true);
			removeTour.setDisable(true);
		}
	}
}
