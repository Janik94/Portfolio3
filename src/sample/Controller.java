package sample;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

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

    public void setView(StudentView view){
        this.view = view;
        EventHandler<ActionEvent> printStudInfo = e -> handleStudInfoPrint(view.studentBox.getValue().getId(), view.studentText);
        view.studentButton.setOnAction(printStudInfo);
        EventHandler<ActionEvent> printCourseInfo = e-> handleCourseInfoPrint(view.courseBox.getValue().getCourseID(), view.courseText);
        view.courseButton.setOnAction(printCourseInfo);
    }

    public void handleStudInfoPrint(Integer id,TextArea studText) {
        studText.clear();
        model.preparedStudStmtToQuery();
        ArrayList<ArrayList> studInfo = model.findStudInfo(id);
        for(int i = 0; i < studInfo.size(); i++){
            if(studInfo.get(i).get(3).equals(0)) {
                studText.appendText(studInfo.get(i).get(1) + "\n" + "Course ID: " + studInfo.get(i).get(2) + ", no grade yet. \n");
            }else {
                studText.appendText(studInfo.get(i).get(1) + "\n" + "Course ID: " + studInfo.get(i).get(2) + ", " + studInfo.get(i).get(3) + "\n");
            }
        }
    }

    public void handleCourseInfoPrint(String cId, TextArea courseText){
        courseText.clear();
        model.preparedCourseStmtToQuery();
        ArrayList courseInfo = model.findCourseInfo(cId);
        if(!courseInfo.get(0).equals(0.0)) {
            courseText.appendText("The average Grade for " + cId + " is " + courseInfo.get(0));
        }else{
            courseText.appendText("The course "+cId+" has not been graded yet.");
        }
    }

    /*public ObservableList<Student> getStudents() {
        ArrayList<String> names = model.StationNameQueryStmt();
        ObservableList<Student> student = FXCollections.observableArrayList(names);
        return student;
    }*/
}
