package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    StudentModel model;
    StudentView view;

    public Controller(StudentModel model) {
        this.model = model;
        try{
            model.connect();
            model.createStatement();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    /*public ObservableList<Student> getStudents() {
        ArrayList<String> names = model.StationNameQueryStmt();
        ObservableList<Student> student = FXCollections.observableArrayList(names);
        return student;
    }*/
}
