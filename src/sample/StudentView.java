package sample;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class StudentView {
    StudentModel model;
    Controller control;

    private TabPane startView;
    TableView students = new TableView();

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
        info.setContent(gridOne);

        info.setText("Find Information");
        startView.getTabs().add(info);

            //tab two
        Tab student = new Tab();
        student.setText("Studentlist");
        TableColumn<String, String> nameColumn = new TableColumn<>("Name");
        TableColumn<String, String> idColumn = new TableColumn<>("Student ID");
        TableColumn<String, String> courseColumn = new TableColumn<>("Courses");
        TableColumn<String, String> gradeColumn = new TableColumn<>("Grade");
        students.getColumns().addAll(nameColumn, idColumn, courseColumn, gradeColumn);
        student.setContent(students);
        startView.getTabs().add(student);

            //tab three
        Tab courses = new Tab();
        courses.setText("Courselist");
        startView.getTabs().add(courses);

    }

    public Parent asParent() {
        return startView;
    }
}
