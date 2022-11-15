module com.crud.crud_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.jfoenix;


    opens com.crud.crud_project to javafx.fxml;
    exports com.crud.crud_project;
}