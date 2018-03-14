package app;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PhotosMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/login.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        LoginController login = loader.getController();
        /*login.start(primaryStage);*/
        primaryStage.setScene(scene);
        primaryStage.setTitle("Photos74");
        primaryStage.setResizable(false);
        primaryStage.show();
    }catch (Exception e){
        e.printStackTrace();

    }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
