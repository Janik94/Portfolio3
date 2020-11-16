package sample;

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
    public void findStudInfo(Integer studId) {
        try{
                //here we input the student id in the first(and only) placeholder
                //and after that we just work with a resultSet again
            pStmt.setInt(1, studId);
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()){
                for(Student stud : studentNames){
                        //this time we don't create new students
                        //we compare the input id with all students in the public arrayList of students
                        //and then add courses and grades to arrayLists inside each student element
                    if(rs.getInt(1) == (stud.getId())){
                        stud.addCourses(rs.getString(3));
                        stud.addGrades(rs.getInt(4));
                    }
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
    public ArrayList<Student> findAvgGrade(int id){
        try{
                //setting placeholder to the student id we want to work with
            pStmt.setInt(1, id);
                //using a resultSet and extracting information again
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()) {
                for (Student stud : studentNames) {
                        //this checks each student in "studentNames" and if the id is equal to the id from the result set
                        //the we set the average grade in the student object to what we get from the database
                    if (stud.getId().equals(rs.getInt(1))) {
                        stud.setAvgGrade(rs.getDouble(2));
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return studentNames;
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
    public ArrayList<Courses> findCourseInfo(String cId) {
        try{
            pStmt.setString(1, cId);
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()) {
                for(Courses course : courses){
                    if(course.getCourseID().equals(cId)){
                        course.setAvgGrade(rs.getDouble(1));
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return courses;
    }
}


