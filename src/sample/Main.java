package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
        //url where my database is saved
        //needs to be changed if program runs on another computer
    static String url = "jdbc:sqlite:C:\\Users\\Bruger\\Desktop\\NIB\\5. Semester\\Software Development\\Portfolios\\Portfolio3\\src\\Students\\Students";
        //creating a new student model with url as input
    static StudentModel SDB = new StudentModel(url);
    @Override
    public void start(Stage primaryStage){
        Controller control = new Controller(SDB);
        StudentView view = new StudentView(SDB, control);
        control.setView(view);
        primaryStage.setTitle("Student Database");
        primaryStage.setScene(new Scene(view.asParent(), 700, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
