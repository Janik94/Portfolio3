package sample;

    //simple class so that I can create courses with the information extracted from the database
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

        //I override the toString() method
        //this gives me the possibility to decide what is write in my course ComboBox
    @Override
    public String toString() {
        return courseID+": "+name;
    }
}
