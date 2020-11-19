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
        EventHandler<ActionEvent> addGrade = e-> handleAddGrade(view.addGradeBox.getValue(),view.studentGrade.getValue(),
                                view.courseGrade.getValue(), view.addGradeText);
        view.addGradeButton.setOnAction(addGrade);
    }

    public void handleAddGrade(String grade, Student stud, Courses course, TextArea area) {
        area.clear();

        model.preparedAddStmtToQuery();
        Integer returnValue = model.changeGrade(grade,stud,course);
        if(returnValue == 1){
            area.appendText( "Student Name: " +stud.getName()+ "\n Course: "+course+"\n new Grade: "+grade );
        }
        if(returnValue == 0){
            area.appendText("You can not change an existing grade! ");
        }
    }
        //method to fill textArea with information about students
    public void handleStudInfoPrint(Integer id,TextArea studText) {
        //clears textArea before something is printed
        studText.clear();
        //prepares prepared statement for students
        model.preparedStudStmtToQuery();
        //creating all students based on database
        model.findStudInfo(id, studText);
        //preparing prepared statement to get the average grade for each student
        model.preparedAvgQuery();

        model.findAvgGrade(id, studText);

    }


        //method to print the course information
        //same approach as for the students
    public void handleCourseInfoPrint(String cId, TextArea courseText){
        courseText.clear();
            //preparing our statement
        model.preparedCourseStmtToQuery();
            //extracting array list with the course objects
        model.findCourseInfo(cId, courseText);
            //going through array list checking for the wanted course ID and printing information

    }
}
