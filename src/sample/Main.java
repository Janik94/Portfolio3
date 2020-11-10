package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    static String url = "jdbc:sqlite:C:\\Users\\Bruger\\Desktop\\NIB\\5. Semester\\Software Development\\Portfolios\\Portfolio3\\src\\Students\\Students";
    static StudentModel SDB = new StudentModel(url);
    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller control = new Controller(SDB);
        StudentView view = new StudentView(SDB, control);
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Student Database");
        primaryStage.setScene(new Scene(view.asParent(), 600, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

            

    }
}
