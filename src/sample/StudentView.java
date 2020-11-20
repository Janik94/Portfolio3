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
    Button studentButton;
    Button courseButton;
    Button addGradeButton;
    TextArea studentText;
    TextArea courseText;
    TextArea addGradeText;
    ComboBox<Student> studentBox;
    ComboBox<Courses> courseBox;
    ComboBox<String> addGradeBox;
    ComboBox<Student> studentGrade;
    ComboBox<String> courseGrade;

    public StudentView(StudentModel model, Controller control) {
        this.model = model;
        this.control = control;
        createAndConfigure();
    }

        //method to construct the GUI
        //i am using a TabPane which is filled with three different Tabs
    private void createAndConfigure() {
        startView = new TabPane();
        //Tab to find information
        startView.getTabs().add(findInfoTab());
        //tab to add grades
        startView.getTabs().add(gradeTab());
        //tab list of students
        startView.getTabs().add(studentTab());
        //tab list of courses
        startView.getTabs().add(courseTab());
    }

        //method to create the first tab
        //the tab where one can request for information about students and courses
    public Tab findInfoTab() {
            //initializing different labels, button and textAreas with String that shall be printed with initialization
        Label studentLabel = new Label("Choose Student: ");
        Label courseLabel = new Label("Choose Course:");
        studentButton = new Button("Find Student Information");
        courseButton = new Button("Find Course Information");

        studentText = new TextArea("Student information will be printed here.");
        courseText = new TextArea("Course information will be printed here.");
        addGradeText = new TextArea("Information will be printed...");

            //initialization of a new comboBox and adding all my student objects in it
            //this way I can have a toString method in my student class
            //this gives me the possibility to decide how information in the comboBox will be visualized
        studentBox = new ComboBox<>();
        studentBox.setItems(FXCollections.observableArrayList(model.studentQuery()));
        studentBox.getSelectionModel().selectFirst();

            //same way of visualizing courses as I did with the students
        courseBox = new ComboBox<>();
        courseBox.setItems(FXCollections.observableArrayList(model.courseQuery()));
        courseBox.getSelectionModel().selectFirst();


            //then i chose to add a gridPane to this Tab, so that i easily can add all labels, combo boxes and textAreas
        GridPane gridOne = new GridPane();
        gridOne.setMinSize(300, 200);

            //padding sets the distance from sides of canvas
            //(top,right,bottom, left)
        gridOne.setPadding(new Insets(10, 40, 15, 40));
        gridOne.setHgap(1);
        gridOne.setVgap(1);


        //add label and combobox for students
        //i is the column, i1 is the row
        gridOne.add(studentLabel, 0, 1);
        gridOne.add(studentBox, 1, 1);
        gridOne.add(studentButton,2,1);
        gridOne.add(studentText,0,3,20,5);


        //add label and combobox for courses
        gridOne.add(courseLabel, 0, 28);
        gridOne.add(courseBox, 1, 28);
        gridOne.add(courseButton,2,28);
        gridOne.add(courseText,0,30,20,5);

        Tab infoTab = new Tab();

            //add the gridPane to the tab and call the tab "Find Information"
        infoTab.setContent(gridOne);
        infoTab.setText("Find Information");
        return infoTab;
    }


    //method to create a new tab for the student list
    //for this tab I chose to work with a TableView
    //where each column represents information about the student
    public Tab studentTab() {
        TableView<Student> students = new TableView<>();
            //creates new observable list that is backed by our courses array list
            //an observable list is a list that allows listeners(here our tableView) to track changes when they occur
        students.setItems(FXCollections.observableArrayList(model.studentQuery()));
        Tab studentTab = new Tab();
        studentTab.setText("Student list");
            //name column
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
            //cellValueFactory needs to be set to specify how to populate all cells within the table column
            //PropertyValueFactory is a constructor to extract value from a given tableView row item
            //using the given property name
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            //id column
        TableColumn<Student, String> idColumn = new TableColumn<>("Student ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
            //city column
        TableColumn<Student, String> cityColumn = new TableColumn<>("Cities");
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("City"));

        students.getColumns().add(nameColumn);
        students.getColumns().add(idColumn);
        students.getColumns().add(cityColumn);
        studentTab.setContent(students);
        return studentTab;
    }



        //method to create a new tab for the course list
        //I also chose to work with a tableView for the courses
    public Tab courseTab() {
        TableView<Courses> courses = new TableView<>();

        courses.setItems(FXCollections.observableArrayList(model.courseQuery()));
        Tab courseTab = new Tab();
        courseTab.setText("Course list");
            //course ID
        TableColumn<Courses, String> courseID = new TableColumn<>("CourseID");
        courseID.setCellValueFactory(new PropertyValueFactory<>("CourseID"));
            //course name
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

        courses.getColumns().add(courseID);
        courses.getColumns().add(courseName);
        courses.getColumns().add(teacher);
        courses.getColumns().add(year);
        courses.getColumns().add(semester);
        courseTab.setContent(courses);
        return courseTab;
    }

    public Tab gradeTab(){
        addGradeButton = new Button("Add new Grade: ");
        addGradeBox = new ComboBox<>();
        String[] grades = {"-3","00","02","4","7","10","12"};
        addGradeBox.setItems(FXCollections.observableArrayList(grades));
        studentGrade = new ComboBox<>();
        courseGrade = new ComboBox<>();
        studentGrade.setItems(FXCollections.observableArrayList(model.studentNames));
        studentGrade.getSelectionModel().selectFirst();
        courseGrade.setItems(FXCollections.observableArrayList(model.nullCourses()));
        courseGrade.getSelectionModel().selectFirst();

        GridPane grid = new GridPane();
        Label stud = new Label("Choose a student: ");
        Label course = new Label("Choose a course: ");
        Label grade = new Label("Choose a grade: ");
        grid.add(stud,0,1);
        grid.add(studentGrade,0,2);
        grid.add(course,1,1);
        grid.add(courseGrade,1,2);
        grid.add(grade,2,1);
        grid.add(addGradeBox,2,2);
        grid.add(addGradeButton, 0,4);
            //int columnIndex,int rowIndex, int column span, int rowspan
        grid.add(addGradeText,1,15,15,10);

        Tab gradeTab = new Tab("Add new Grade");
        gradeTab.setContent(grid);
        return gradeTab;
    }

        //constructs a parent based on our "startView"
        //this way we can use the "startView" as "scene" input in our main
    public Parent asParent() {
        return startView;
    }

}

