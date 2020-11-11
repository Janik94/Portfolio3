package sample;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class StudentView {
    StudentModel model;
    Controller control;
    private TabPane startView;

    Button exitButton = new Button("Exit");

    public StudentView(StudentModel model, Controller control) {
        this.model = model;
        this.control = control;
        createAndConfigure();
    }

    private void createAndConfigure() {
        startView = new TabPane();
        //Tab to find informartion
        startView.getTabs().add(findInfoTab());
        //tab for student registration
        startView.getTabs().add(studentReg());
        //tab for course registration
        startView.getTabs().add(courseReg());
        //tab list of students
        startView.getTabs().add(studentTab());
        //tab list of courses
        startView.getTabs().add(courseTab());


    }

    public Tab findInfoTab() {
        Label studentLabel = new Label("Choose Student ID: ");
        Label courseLabel = new Label("Choose Course ID");

        ComboBox<Student> studentBox = new ComboBox<>();
        studentBox.setItems(FXCollections.observableArrayList(model.studentQuery()));
        /*ArrayList<Integer> studentInfo = new ArrayList<>();
        for(int i = 0; i<model.studentQuery().size(); i++) {
            studentInfo.add(model.studentQuery().get(i).getId());
        }//instead of that shit, make a toString method in student
        ObservableList<Integer> student=FXCollections.observableArrayList(studentInfo);
        studentBox.setItems(student);*/

        ComboBox<String> courseBox = new ComboBox<>();
        ArrayList<String> courseInfo = new ArrayList<>();
        for(int i = 0; i<model.courseQuery().size(); i++) {
            courseInfo.add(model.courseQuery().get(i).getCourseID());
        }
        ObservableList<String> course =FXCollections.observableArrayList(courseInfo);
        courseBox.setItems(course);
        GridPane gridOne = new GridPane();
        Tab infoTab = new Tab();
        gridOne.setMinSize(300, 200);
        gridOne.setPadding(new Insets(10, 10, 10, 10));
        gridOne.setHgap(1);
        gridOne.setVgap(5);

        gridOne.add(studentLabel, 1, 1);
        gridOne.add(courseLabel, 100, 1);

        //add stuff in student box
        gridOne.add(studentBox, 15, 1);

        //add stuff in box
        gridOne.add(courseBox, 115, 1);

        infoTab.setContent(gridOne);
        infoTab.setText("Find Information");
        return infoTab;
    }

    public Tab courseReg() {
        Label courseID = new Label("Course ID:");
        Label name = new Label("Course Name:");
        Label teacher = new Label("Teacher:");
        Label year = new Label("Year:");
        Label semester = new Label("Semester:");
        TextField iDField = new TextField();
        iDField.setPromptText("What is the course id ?");
        iDField.setAlignment(Pos.CENTER);
        TextField nameField = new TextField();
        nameField.setPromptText("What is the name of the course ?");
        nameField.setAlignment(Pos.CENTER);
        TextField teacherField = new TextField();
        teacherField.setPromptText("Who is the teacher ?");
        teacherField.setAlignment(Pos.CENTER);
        TextField yearField = new TextField();
        yearField.setPromptText("What year is the course offered ?");
        yearField.setAlignment(Pos.CENTER);
        TextField semesterField = new TextField();
        semesterField.setPromptText("Which semester is the course offered ?");
        semesterField.setAlignment(Pos.CENTER);

        Tab course = new Tab();
        GridPane grid = new GridPane();
        grid.setMinSize(300,200);
        grid.setPadding(new Insets(10,10,10,10));
        grid.add(courseID, 1,1);
        grid.add(iDField, 200, 1);
        grid.add(name,1,3);
        grid.add(nameField, 200,3);
        grid.add(teacher,1,5);
        grid.add(teacherField,200,5);
        grid.add(year,1,7);
        grid.add(yearField,200,7);
        grid.add(semester,1,9);
        grid.add(semesterField,200,9);
        course.setContent(grid);
        course.setText("Course Registration");
        return course;
    }

    public Tab studentReg() {
        Tab studentReg = new Tab();
        GridPane grid = new GridPane();
        grid.setMinSize(300,200);
        grid.setPadding(new Insets(10,10,10,10));
        studentReg.setContent(grid);
        studentReg.setText("Student Registration");
        return studentReg;
    }



        //method to create a new tab for the course list
    public Tab courseTab() {
        TableView<Courses> courses = new TableView<>();
        courses.setItems(FXCollections.observableArrayList(model.courseQuery()));
        Tab courseTab = new Tab();
        courseTab.setText("Courselist");
        //courseID?
        TableColumn<Courses, String> courseID = new TableColumn<>("CourseID");
        courseID.setCellValueFactory(new PropertyValueFactory<>("CourseID"));
        //courseName
        TableColumn<Courses, String> courseName = new TableColumn<>("Name");
        courseName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        //course Teacher
        TableColumn<Courses, String> teacher = new TableColumn<>("Teacher");
        teacher.setCellValueFactory(new PropertyValueFactory<>("Teacher"));
        //year
        TableColumn<Courses, Integer> year = new TableColumn<>("Year");
        year.setCellValueFactory(new PropertyValueFactory<>("Year"));
        //semester
        TableColumn<Courses, String> semester = new TableColumn<>("Semester");
        semester.setCellValueFactory(new PropertyValueFactory<>("Semester"));

        courses.getColumns().addAll(courseID, courseName, teacher, year, semester);
        courseTab.setContent(courses);
        return courseTab;
    }

        //method to create a new tab for the student list
    public Tab studentTab() {
        TableView<Student> students = new TableView<>();
        students.setItems(FXCollections.observableArrayList(model.studentQuery()));
        Tab studentTab = new Tab();
        studentTab.setText("Studentlist");
        //name column
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //id column
        TableColumn<Student, String> idColumn = new TableColumn<>("Student ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        //citycolumn
        TableColumn<Student, String> cityColumn = new TableColumn<>("Cities");
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("City"));
        //for further addings
        //nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("courses"));
        //TableColumn gradeColumn = new TableColumn<>("Grade");
        students.getColumns().addAll(nameColumn, idColumn, cityColumn);
        studentTab.setContent(students);
        return studentTab;
    }

    public Parent asParent() {
        return startView;
    }

}

