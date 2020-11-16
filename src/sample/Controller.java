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

        //method to fill textArea with information about students
    public void handleStudInfoPrint(Integer id,TextArea studText) {
            //clears textArea before something is printed
        studText.clear();
            //prepares prepared statement for students
        model.preparedStudStmtToQuery();
            //creating all students based on database
        model.findStudInfo(id);
            //preparing prepared statement to get the average grade for each student
        model.preparedAvgQuery();
            //receiving array list from "findAvgGrade" which is filled with students and their information
        ArrayList<Student> studInfo = model.findAvgGrade(id);
            //then we go through each student and check if it is the student we want information about
        for(Student stud : studInfo){
            if(stud.getId().equals(id)) {
                studText.appendText(stud.getName()+", Student ID: "+stud.getId());
                    //to make code more readable I chose to make a new array list for the grades of the students
                ArrayList<Integer> grades = stud.getGrades();
                    //since the courses and grades array list in the student object always have the same size
                    //we can go through the grades list and use the same index for the courses
                for (int j = 0; j < grades.size(); j++) {
                    if(grades.get(j) == 0){
                        studText.appendText("\n Course: " + stud.getCourses().get(j) + ", no grade yet.");
                } else{
                    studText.appendText("\n Course: " + stud.getCourses().get(j) +"   Grade: " + stud.getGrades().get(j));
                    }
            }
                studText.appendText("\n Student's average grade is : " + stud.getAvgGrade());
            }
        }
    }

        //method to print the course information
        //same approach as for the students
    public void handleCourseInfoPrint(String cId, TextArea courseText){
        courseText.clear();
            //preparing our statement
        model.preparedCourseStmtToQuery();
            //extracting array list with the course objects
        ArrayList<Courses> courseInfo = model.findCourseInfo(cId);
            //going through array list checking for the wanted course ID and printing information
        for(Courses course : courseInfo) {
            if(course.getCourseID().equals(cId)){
            courseText.appendText("Course: "+course.getCourseID()+ ", "+course.getName()+ ", "+course.getSemester() +" "+course.getYear()+
                                    "\n Teacher: "+course.getTeacher());
            if (course.getAvgGrade() != 0.0) {
                courseText.appendText("\n Average Grade: " + course.getAvgGrade());
            } else {
                courseText.appendText("\n The course is not finished yet.");
            }
            }
        }
    }
}
