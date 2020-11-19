package sample;

import java.util.ArrayList;

    //simple class so that I can create students
    // with the information extracted from the database
public class Student {
    private final String name;
    private final String city;
    private final Integer id;
    private double avgGrade;
        //I chose to use array lists for courses and grades
        //since they are extracted in the same method
        //the grades will have the same index as the course

    /*private final ArrayList<String> courses = new ArrayList<>();
    private final ArrayList<String> grades = new ArrayList<>();*/

    public Student(Integer id, String name, String city) {
        this.name = name;
        this.city = city;
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getName(){return name; }

    public String getCity() {return city;}

    public double getAvgGrade(){
        return this.avgGrade;
    }

    public void setAvgGrade(double avg) {
        this.avgGrade=avg;
    }

    /*public void addCourses(String course){
        this.courses.add(course);
    }

    public ArrayList<String> getCourses(){
        return this.courses;
    }

    public void addGrades(String grade) {
        this.grades.add(grade);
    }

    public ArrayList<String> getGrades(){
        return this.grades;
    }*/


        //I override the toString() method
        //this gives me the possibility to decide what is write in my student ComboBox
    @Override
    public String toString() {
        return id+": "+name;
    }
}
