package com.crud.crud_project;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class HelloController implements Initializable {
    public ToggleGroup gender;
    public JFXComboBox CourseComboBox;
    //Sql Variables
    Connection connection;
    private static final String Password_name = "root";
    private static final String Inert_Q_url = "insert into students(username,course,gender,startDate,MobileNumber) values(?,?,?,?,?);";
    @FXML
    private RadioButton GenderFemale;
    @FXML
    private RadioButton GenderMale;
    @FXML
    private RadioButton GenderOthers; //isselected()
    @FXML
    private TextField MobileNumber;
    @FXML
    private TableColumn<Student, String> UserCourse;
    @FXML
    private TableColumn<Student, String> UserGender;
    @FXML
    private TableColumn<Student, String> MobileNumberTable;
    @FXML
    private TableColumn<Student, String> UserId;
    @FXML
    private TableColumn<Student, String> StartDate;
    Alert alert;
    @FXML
    private TableColumn<Student, String> UserName;
    @FXML
    private TextField UserNameTextInGUi;
    @FXML
    private TableView<Student> tableToDisplay;
    PreparedStatement preparedStatement;
    void Clear() {
        UserNameTextInGUi.clear();
        MobileNumber.clear();
    }


    @FXML
    void OnClickAdd(ActionEvent event) throws Exception {
        Alert alert ;
        try {
            String Gender = null;
            String User_Name = UserNameTextInGUi.getText();
            PreparedStatement preparedStatement = connection.prepareStatement(Inert_Q_url);
            if (GenderFemale.isSelected())
                Gender = GenderFemale.getText();
            else if (GenderMale.isSelected())
                Gender = GenderMale.getText();
            else if (GenderOthers.isSelected())
                Gender = GenderOthers.getText();
            if (MobileNumber.getText().length() == 10) {
                preparedStatement.setString(1, User_Name);
                preparedStatement.setString(2, (String)CourseComboBox.getSelectionModel().getSelectedItem());
                preparedStatement.setString(3, Gender);
                preparedStatement.setString(4, String.valueOf(LocalDate.now()));
                preparedStatement.setString(5, MobileNumber.getText());
                boolean execute = preparedStatement.execute();
                Clear();
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Successfully added");
                alert.showAndWait();
                MobileNumber.clear();
            }
            else
            {
                alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid data");
                alert.setHeaderText("Enter the valid mobile number");
                alert.showAndWait();
            }
        }
        catch(Exception we)
        {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Give the Required Information");
            alert.setHeaderText("Please enter the details");
            alert.showAndWait();
        }
        Table();
}

    @FXML
    void OnClickDelete(ActionEvent event) throws Exception {

        String Delete_Q = "delete from students where username=? and MobileNumber=?;";
        PreparedStatement ps = connection.prepareStatement(Delete_Q);
        ps.setString(1, UserNameTextInGUi.getText());
        ps.setString(2, MobileNumber.getText());
        int resultSet = ps.executeUpdate();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (resultSet >= 1) {
            Table();
            alert.setTitle("Successfully deleted");
            alert.setHeaderText("The data is deleted");
            alert.showAndWait();
        } else {
            alert.setTitle("Error");
            alert.setHeaderText("Enter the Valid data to delete");
            alert.showAndWait();
        }
        Clear();
    }

    @FXML
    void OnClickUpdate(ActionEvent event) throws Exception {

        String Qure_Update = "update students set username=?,course=?,gender=?,Mobilenumber=? where username=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(Qure_Update);
        preparedStatement.setString(1, UserNameTextInGUi.getText());
        preparedStatement.setString(2, (String) CourseComboBox.getSelectionModel().getSelectedItem());
        String Gender = null;
        if (GenderFemale.isSelected())
            Gender = GenderFemale.getText();
        else if (GenderMale.isSelected())
            Gender = GenderMale.getText();
        else if (GenderOthers.isSelected())
            Gender = GenderOthers.getText();
        preparedStatement.setString(3, Gender);
        preparedStatement.setString(4, MobileNumber.getText());
        preparedStatement.setString(5, UserNameTextInGUi.getText());
        int i = preparedStatement.executeUpdate();
        if (i == 0) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Oh!");
            alert.setHeaderText("Enter the valid data");
            alert.showAndWait();

        } else {
            Table();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Data is successfully Updated");
            alert.showAndWait();
        }
        Clear();
    }

    public void Table() throws Exception {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String Table_Data = "select * from students;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Table_Data);
        while (resultSet.next()) {
            Student student = new Student();
            student.setId(resultSet.getString(1));
            student.setUsername(resultSet.getString(2));
            student.setCourse(resultSet.getString(3));
            student.setGender(resultSet.getString(4));
            student.setStartDate(resultSet.getString(5));
            student.setMobileNumber(resultSet.getString(6));
            students.add(student);
        }
        tableToDisplay.setItems(students);
        UserId.setCellValueFactory(f -> f.getValue().idProperty());
        UserName.setCellValueFactory(f -> f.getValue().usernameProperty());
        StartDate.setCellValueFactory(f -> f.getValue().startDateProperty());
        MobileNumberTable.setCellValueFactory(f -> f.getValue().mobileNumberProperty());
        UserCourse.setCellValueFactory(f -> f.getValue().courseProperty());
        UserGender.setCellValueFactory(f -> f.getValue().genderProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] CoursesAvailable ={"C","C++","Java","Python","C#","Php"};
        CourseComboBox.getItems().addAll(CoursesAvailable);
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud_operation", Password_name, Password_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connected");
        try {
            Table();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tableToDisplay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Student student=tableToDisplay.getSelectionModel().getSelectedItem();
                UserNameTextInGUi.setText(student.getUsername());
                MobileNumber.setText(student.getMobileNumber());
                UserGender.setText(student.getGender());
                UserCourse.setText(student.getCourse());
                CourseComboBox.setValue(student.getCourse());
            }
        });

    }
}