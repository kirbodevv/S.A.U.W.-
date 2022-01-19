module com.kgc.sauwlauncher.launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;


    opens com.kgc.sauwlauncher.launcher to javafx.fxml;
    exports com.kgc.sauwlauncher.launcher;
}