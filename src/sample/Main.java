package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    static String url = "jdbc:sqlite:C:\\Users\\Bruger\\Desktop\\NIB\\5. Semester\\Software Development\\Portfolios\\Portfolio3\\src\\Students\\Students";
    static StudentModel SDB = new StudentModel(url);
    @Override
    public void start(Stage primaryStage){
        Controller control = new Controller(SDB);
        StudentView view = new StudentView(SDB, control);
        control.setView(view);
        primaryStage.setTitle("Student Database");
        primaryStage.setScene(new Scene(view.asParent(), 500, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

            

    }
}
