module com.ingmonika.tgol {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ingmonika.tgol to javafx.fxml;
    exports com.ingmonika.tgol;
    exports com.ingmonika;
    opens com.ingmonika to javafx.fxml;
    exports com.ingmonika.tgol.controladores;
    opens com.ingmonika.tgol.controladores to javafx.fxml;
}