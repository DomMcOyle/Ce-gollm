module org.openjfx.SuDPall {
    requires javafx.controls;
	requires javafx.base;
    requires transitive javafx.graphics;
    
    exports org.openjfx.SuDPall;
    opens org.openjfx.SuDPall to javafx.graphics, javafx.fxml, javafx.base;
	opens org.openjfx.entities to javafx.base;
}
