package sample;

import javafx.collections.FXCollections;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;


public class StudentView {
    StudentModel model;
    Controller control;

    private TabPane startView;
    TableView<Student> students = new TableView<>();
    TableView<Courses> courses = new TableView<>();

    Label studentLabel = new Label("Choose Student ID: ");
    Label courseLabel = new Label("Choose Course ID");
    ComboBox<String> studentBox = new ComboBox<>();
    ComboBox<Courses> courseBox = new ComboBox<>();

    Button exitButton = new Button("Exit");

    public StudentView(StudentModel model, Controller control) {
        this.model = model;
        this.control = control;
        createAndConfigure();
    }

    private void createAndConfigure() {
        startView =  new TabPane();
            //Tab one
        GridPane gridOne = new GridPane();
        Tab info = new Tab();
        gridOne.setMinSize(300,200);
        gridOne.setPadding(new Insets(10,10,10,10));
        gridOne.setHgap(1);
        gridOne.setVgap(5);

        gridOne.add(studentLabel, 1,1);
        gridOne.add(courseLabel,1,3);

            //add stuff in student box
        gridOne.add(studentBox,15,1);

            //add stuff in box
        gridOne.add(courseBox,15,3);

        info.setContent(gridOne);
        info.setText("Find Information");
        startView.getTabs().add(info);

            //tab two
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
        startView.getTabs().add(studentTab);

            //tab three
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
        startView.getTabs().add(courseTab);

    }

    public Parent asParent() {
        return startView;
    }
}
