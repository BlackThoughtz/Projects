package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Album;
import model.User;

import java.io.IOException;

/**
 * Created by butlr on 11/21/2017.
 */
@SuppressWarnings("Duplicates")
public class SlideshowController implements Logoff {
    @FXML
    Button toPhotoListButton, slideshowLogoffButton;

    User currUser;
    Album currUserAlbums;

    public void intialize(){
        currUserAlbums = currUser.userAlbums.get(0);
    }


    @FXML
    public void toPhotoList(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/PhotoList.fxml"));


            Parent photoListParent = fxmlLoader.load();
            Scene photoListScene = new Scene(photoListParent);

            PhotoListController photoListController = fxmlLoader.getController();
            photoListController.setUser(currUser.getUserName());
            photoListController.intialize(currUserAlbums);
            Stage photoListStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            photoListStage.setScene(photoListScene);
            photoListStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    public void slideshowLogoff(ActionEvent event) throws IOException {
        logoff(event);
    }

    public User setUser(String u) throws IOException, ClassNotFoundException {
        return this.currUser = User.readData(u);
    }
}
