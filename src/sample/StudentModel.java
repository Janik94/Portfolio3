package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn = null;
    Statement stmt = null;
    String url;
    PreparedStatement pStmt = null;

    public StudentModel(String url){
        this.url = url;
    }

    public void connect() throws SQLException {
        conn = getConnection(this.url);
    }

    public void createStatement() throws SQLException{
        this.stmt = conn.createStatement();
    }

    public ArrayList<Student> studentQuery() {
        ArrayList<Student> studentNames = new ArrayList<>();
        String sql = "SELECT * FROM Students;";
        ResultSet rs;
        try{
            rs = stmt.executeQuery(sql);
            while(rs!=null && rs.next()) {
                Student stud = new Student(rs.getInt(1),rs.getString(2), rs.getString(3));
                studentNames.add(stud);
            }
            }catch(SQLException e){
                e.printStackTrace();
            System.out.println(e.getMessage());
            }
        return studentNames;
    }

    public ArrayList<Courses> courseQuery() {
        ArrayList<Courses> courses = new ArrayList<>();
        String sql = "SELECT * FROM Courses;";
        ResultSet rs;
        try{
            rs = stmt.executeQuery(sql);
            while(rs!=null && rs.next()) {
                //String courseID, String name, String teacher, Integer year, String semester
                Courses c = new Courses(rs.getString(1),rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                courses.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return courses;
    }
}


