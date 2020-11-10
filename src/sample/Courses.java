package sample;

public class Courses {
    private final String courseID;
    private final String name;
    private final String teacher;
    private final Integer year;
    private final String semester;

    public Courses(String courseID, String name, String teacher, Integer year, String semester) {
        this.courseID = courseID;
        this.name = name;
        this.teacher = teacher;
        this.year = year;
        this.semester = semester;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public Integer getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }
}
