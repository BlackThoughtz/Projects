package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by butlr on 11/20/2017.
 */
public interface Logoff {

    default void logoff(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage loginStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        loginStage.setScene(loginScene);
        loginStage.show();
    }
}
