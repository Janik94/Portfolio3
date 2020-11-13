package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public void preparedStudStmtToQuery() {
        String sql = "SELECT G.StudentID, S.Name, G.CourseID, G.Grade From Grades as G " +
                "JOIN Students as S on G.StudentID = ? " +
                "WHERE G.StudentID = S.SID;";
        try
        {
        pStmt = conn.prepareStatement(sql);
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList findStudInfo(Integer studId) {
        ArrayList info = new ArrayList<>();
        try{
            pStmt.setInt(1, studId);
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()){
                Integer sId = rs.getInt(1);
                String sName = rs.getString(2);
                String cId = rs.getString(3);
                Integer sGrade = rs.getInt(4);
                System.out.println(sId);
                info.add(sId);
                info.add(sName);
                info.add(cId);
                info.add(sGrade);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return info;
    }

    public void preparedCourseStmtToQuery(){
        String sql = "SELECT G.CourseID, G.StudentID,S.Name, G.Grade FROM Grades as G "+
        "JOIN Students S on G.StudentID = S.SID "+
        "WHERE G.CourseID = ?;";
        try
        {
            pStmt = conn.prepareStatement(sql);
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList findCourseInfo(String cId) {
        ArrayList cInfo = new ArrayList<>();
        try{
            pStmt.setString(1, cId);
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()) {
                String Id = rs.getString(1);
                String sName = rs.getString(3); //only one stud right now
                Integer sGrade = rs.getInt(4); //only one grade for now
                cInfo.add(Id);
                cInfo.add(sName);
                cInfo.add(sGrade);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cInfo;
    }
}


