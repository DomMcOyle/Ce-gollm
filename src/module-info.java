module cegollm_fx {
	requires javafx.controls;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens entities to javafx.base;
}
