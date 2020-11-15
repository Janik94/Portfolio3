package sample;

import java.util.ArrayList;

public class Student {
    private final String name;
    private final String city;
    private final Integer id;
    private double avgGrade;
    private final ArrayList<String> courses = new ArrayList<>();
    private final ArrayList<Integer> grades = new ArrayList<>();

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

    public void addCourses(String course){
        this.courses.add(course);
    }
    public ArrayList<String> getCourses(){
        return this.courses;
    }

    public void addGrades(Integer grade) {
        this.grades.add(grade);
    }
    public ArrayList<Integer> getGrades(){
        return this.grades;
    }




    @Override
    public String toString() {
        return id+": "+name;
    }
}
