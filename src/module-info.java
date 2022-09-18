module cegollm_fx {
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens entities to javafx.base;
}
