package sample;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
//import javafx.scene.shape.Line;


public class StudentView {
    StudentModel model;
    Controller control;
    private TabPane startView;
    Button studentButton;
    Button courseButton;
    TextArea studentText;
    TextArea courseText;
    ComboBox<Student> studentBox;
    ComboBox<Courses> courseBox;

    //Button exitButton = new Button("Exit");

    public StudentView(StudentModel model, Controller control) {
        this.model = model;
        this.control = control;
        createAndConfigure();
    }

    private void createAndConfigure() {
        startView = new TabPane();
        //Tab to find informartion
        startView.getTabs().add(findInfoTab());
        //tab list of students
        startView.getTabs().add(studentTab());
        //tab list of courses
        startView.getTabs().add(courseTab());


    }

    public Tab findInfoTab() {
        Label studentLabel = new Label("Choose Student: ");
        Label courseLabel = new Label("Choose Course:");
        studentButton = new Button("Find Student Information");
        courseButton = new Button("Find Course Information");
        studentText = new TextArea();
        studentText.setText("Student information will be printed here.");
        courseText = new TextArea();
        courseText.setText("Course information will be printed here.");


        studentBox = new ComboBox<>();
        studentBox.setItems(FXCollections.observableArrayList(model.studentQuery()));
        studentBox.getSelectionModel().selectFirst();

        courseBox = new ComboBox<>();
        courseBox.setItems(FXCollections.observableArrayList(model.courseQuery()));
        courseBox.getSelectionModel().selectFirst();

        GridPane gridOne = new GridPane();
        gridOne.setMinSize(300, 200);
            //same as margin in fxml
            //(top,right,bottom, left)
        gridOne.setPadding(new Insets(10, 40, 15, 40));
        gridOne.setHgap(1);
        gridOne.setVgap(5);

        /*Line line = new Line(0,20,420,20);
        gridOne.add(line,0,20);*/


        //add label and combobox for students
        //i is the column, i1 is the row
        gridOne.add(studentLabel, 0, 1);
        gridOne.add(studentBox, 0, 2);
        gridOne.add(studentButton,0,3);
        gridOne.add(studentText,30,0,15,10);


        //add label and combobox for courses
        gridOne.add(courseLabel, 0, 30);
        gridOne.add(courseBox, 0, 31);
        gridOne.add(courseButton,0,32);
        gridOne.add(courseText,30,29,15,10);


        Tab infoTab = new Tab();

        infoTab.setContent(gridOne);
        infoTab.setText("Find Information");
        return infoTab;
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

