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
        ArrayList<Student> studInfo = model.findStudInfo(id);
        for(Student stud : studInfo){
            if(stud.getId().equals(id)) {
                studText.appendText(stud.getName());
                ArrayList<Integer> grades = stud.getGrades();
                for (int j = 0; j < grades.size(); j++) {
                    if(grades.get(j) == 0){
                        studText.appendText("\n Course: " + stud.getCourses().get(j) + ", no grade yet.");
                } else{
                    studText.appendText("\n Course: " + stud.getCourses().get(j) +"   Grade: " + stud.getGrades().get(j));
                }
            }
            }
        }
        /*ArrayList<ArrayList> studInfo = model.findStudInfo(id);
        for(int i = 0; i < studInfo.size(); i++){
            //studText.appendText(studInfo.get(i).get(1) + "\n");
            if(studInfo.get(i).get(3).equals(0)) {
                studText.appendText("Course ID: " + studInfo.get(i).get(2) + ", no grade yet.\n");
            }else {
                studText.appendText("Course ID: " + studInfo.get(i).get(2) + ", " + studInfo.get(i).get(3) + "\n");
            }
        }*/
        model.preparedAvgQuery();
        ArrayList<Student> avg = model.findAvgGrade(id);
        for(Student stud : avg) {
            if(id.equals(stud.getId())) {
                studText.appendText("\n Student's average grade is : " + stud.getAvgGrade());
            }
        }
    }

    public void handleCourseInfoPrint(String cId, TextArea courseText){
        courseText.clear();
        model.preparedCourseStmtToQuery();
        ArrayList<Double> courseInfo = model.findCourseInfo(cId);
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
