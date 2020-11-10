package sample;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class StudentView {
    StudentModel model;
    Controller control;

    private GridPane startView;

    public StudentView(StudentModel model, Controller control) {
        this.model = model;
        this.control = control;
        createAndConfigure();
    }

    private void createAndConfigure() {
        startView =  new GridPane();
    }

    public Parent asParent() {
        return startView;
    }
}
