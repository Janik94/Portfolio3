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
        studentNames = new ArrayList<>();
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


    public ArrayList<Student> findStudInfo(Integer studId) {
        //ArrayList info = new ArrayList<>();
        try{
            pStmt.setInt(1, studId);
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()){
                /*Integer sId = rs.getInt(1);
                String sName = rs.getString(2);
                String cId = rs.getString(3);
                Integer sGrade = rs.getInt(4);*/
                for(Student stud : studentNames){
                    if(rs.getInt(1) == (stud.getId())){
                        stud.addCourses(rs.getString(3));
                        stud.addGrades(rs.getInt(4));
                    }
                }
                /*ArrayList newInfo =  new ArrayList();
                newInfo.add(sId);
                newInfo.add(sName);
                newInfo.add(cId);
                newInfo.add(sGrade);
                info.add(newInfo);*/
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return studentNames;
    }

    public void preparedAvgQuery() {
        String sql = "SELECT StudentId, AVG(G.Grade) From Grades as G " +
                "WHERE G.StudentID = ?;";
        try {
            pStmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Student> findAvgGrade(int id){
        try{
            pStmt.setInt(1, id);
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()) {
                for (Student stud : studentNames) {
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

    public ArrayList<Double> findCourseInfo(String cId) {
        ArrayList<Double> cInfo = new ArrayList<>();
        try{
            pStmt.setString(1, cId);
            ResultSet rs = pStmt.executeQuery();
            while(rs!= null && rs.next()) {
                double average = rs.getDouble(1);
                cInfo.add(average);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cInfo;
    }
}


