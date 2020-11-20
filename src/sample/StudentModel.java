package sample;

import javafx.scene.control.TextArea;

import java.sql.*;
import java.util.*;


import static java.sql.DriverManager.getConnection;


public class StudentModel {
    Connection conn = null;
    Statement stmt = null;
    String url;
    PreparedStatement pStmt = null;
    ArrayList<Student> studentNames;
    ArrayList<Courses> courses;

    public StudentModel(String url){
        this.url = url;
    }

    public void connect() throws SQLException {
        conn = getConnection(this.url);
    }

    public void createStatement() throws SQLException{
        this.stmt = conn.createStatement();
    }
        //method to extract student information from database
        //and add each student as an object with properties: id, name and city
    public ArrayList<Student> studentQuery() {
        studentNames = new ArrayList<>();
            //sql statement that gets all information from the Students table
        String sql = "SELECT * FROM Students;";
            //resultSet is a table of data representing a database result set
            //initially cursor points to first row
        ResultSet rs;
        try{
                //since there are no placeholders("?") we need to use a statement
                //and execute it with the String from above
            rs = stmt.executeQuery(sql);
                //rs.next() is used to move cursor to next row from current position
                //rs!=null just makes sure that our ResultSet is not empty
            while(rs!=null && rs.next()) {
                    //initialization of new students with required input
                    //rs.get.. returns data of a specified column in the current row
                    //data returned must have same data type as get method
                    //new Student(int ID, String name, String city)
                Student stud = new Student(rs.getInt(1),rs.getString(2), rs.getString(3));
                    //adding students in a public arraylist
                studentNames.add(stud);
            }
            }catch(SQLException e){
                e.printStackTrace();
            System.out.println(e.getMessage());
            }
        return studentNames;
    }

        //method to extract information about courses from database
    public ArrayList<Courses> courseQuery() {
        courses = new ArrayList<>();
            //sql statement to extract all information from database
        String sql = "SELECT * FROM Courses;";
            //from here resultSet works just as in the method studentQuery()
            //only difference is that we make course objects instead of student objects
        ResultSet rs;
        try{
            rs = stmt.executeQuery(sql);
            while(rs!=null && rs.next()) {
                    //new Courses(String courseID, String name, String teacher, Integer year, String semester)
                Courses c = new Courses(rs.getString(1),rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                courses.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return courses;
    }

        //method to extract specific information about students
    public void preparedStudStmtToQuery() {
            //sql statement joins student table with grade table in the database
            //then extracts the id and name of student as well as course and grades the student signed up for/passed
            //the is a placeholder where we in another method will add the student id, for which a information request is made
        String sql = "SELECT G.StudentID, S.Name, G.CourseID, G.Grade From Grades as G " +
                "JOIN Students as S on G.StudentID = ? " +
                "WHERE G.StudentID = S.SID;";
        try
        {
            //pStmt save a prepared statement with the string from above
            //so that we can input the "missing" student id
        pStmt = conn.prepareStatement(sql);
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

        //method to extract specific information for students
        //prepared Statement from preparedStudStmtToQuery() will be used here
    public void findStudInfo(Integer studId, TextArea area) {
        try{
                //here we input the student id in the first(and only) placeholder
                //and after that we just work with a resultSet again
            pStmt.setInt(1, studId);
            ResultSet rs = pStmt.executeQuery();
            while(rs != null && rs.next()) {
                            //input course and grade into textarea for the students
                        if(rs.getString(4) == null) {
                            area.appendText(rs.getString(3) + " has not been graded yet.\n");
                        }else {
                            area.appendText(rs.getString(3) + ", Grade: " + rs.getString(4) + "\n");
                        }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

        //method to prepare a statement used to find a students average grade
    public void preparedAvgQuery() {
            //sql statement that will find a students average grade
            //AVG ignores "null", so if a student did not pass a course yet, it will ignore this entry
        String sql = "SELECT StudentId, AVG(G.Grade) From Grades as G " +
                "WHERE G.StudentID = ?;";
        try {
            pStmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        //method to extract average grade from our prepared statement
    public void findAvgGrade(int id, TextArea area){
        try{
                //setting placeholder to the student id we want to work with
            pStmt.setInt(1, id);
                //using a resultSet and extracting information again
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()) {
                for (Student stud : studentNames) {
                        //this checks each student in "studentNames" and if the id is equal to the id from the combobox
                        //then we add the average grade to the textarea for the students
                    if(stud.getId()==id){
                        area.appendText("Average Grade: "+rs.getDouble(2));
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

        //only additional information for the courses is the average grade
        //this method prepare this statement
    public void preparedCourseStmtToQuery(){
        String sql = "SELECT AVG(Grade) FROM Grades WHERE CourseID = ?";
        try
        {
            pStmt = conn.prepareStatement(sql);
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
        //method to get the average grade for the course
        //then we also just add this information to the student object we request information about
    public void findCourseInfo(String cId, TextArea area) {
        try{
            pStmt.setString(1, cId);
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()) {
                for(Courses course : courses){
                        //if statement to check that we get the information about the course
                        //the user requested
                    if(course.getCourseID().equals(cId)){
                        area.appendText("Course: "+course.getCourseID()+ ", "+course.getName()+ ", "+course.getSemester() +" "+course.getYear()+
                                "\n Teacher: "+course.getTeacher());
                            //if the average grade of the course is null
                            //there are no grades yet, so the course has not been graded
                        if (rs.getString(1) !=null) {
                            area.appendText("\n Average Grade: " + rs.getDouble(1));
                        } else {
                            area.appendText("\n The course is not finished yet.");
                        }
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void preparedAddStmtToQuery(){
        String sql = "UPDATE Grades SET Grade = ? WHERE CourseID = ? AND StudentID = ? AND Grade IS NULL;";
        try
        {
            pStmt = conn.prepareStatement(sql);
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
        //method to change add a grade, if a course has not been grade yet
        //decided to return "value", so that method does not need another input
    public Integer changeGrade(String grade, Student stud, String course){
        Integer value = null;
        try {
            pStmt.setString(1, grade);
            pStmt.setString(2, course);
            pStmt.setInt(3, stud.getId());
                //returns 0 if grade can not be changed because grade already exists
                //returns 1 if grade can be changed because it was "null"
            value = pStmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
            //returns either 0 or 1
        return value;
    }

        //select courses that have a grade as "null" once
        //these courses will be shown in the combobox for the courses in the add grade tab
    public ArrayList<String> nullCourses(){
        String sql = "SELECT DISTINCT CourseID, Grade From Grades Where Grade is null;";
        ResultSet rs;
        ArrayList<String> nullCourses = new ArrayList<>();
        try
        {
            rs = stmt.executeQuery(sql);
            while(rs!= null && rs.next()){
                nullCourses.add(rs.getString(1));
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println(nullCourses);
    return nullCourses;
    }
}


