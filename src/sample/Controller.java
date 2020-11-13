package sample;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import org.w3c.dom.Text;

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
        ArrayList studInfo = model.findStudInfo(id);
        studText.appendText(studInfo.get(1)+"\n" + "Course ID: "+studInfo.get(2)+", "+studInfo.get(3) + "\n"
                                +"Course ID: "+studInfo.get(6) + ", "+studInfo.get(7));
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
