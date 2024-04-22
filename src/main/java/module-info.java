module com.example.ud09entradasalida {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ud09entradasalida to javafx.fxml;
    exports com.example.ud09entradasalida;
}