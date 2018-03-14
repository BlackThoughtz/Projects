package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by butlr on 11/20/2017.
 */
@SuppressWarnings("Duplicates")
public class PhotoListController implements Logoff {

    User currUser;
    Album userAlbum;
    ArrayList<Photo> userPhotos;

    @FXML
    Button photoListLogoffButton, toAlbumButton, deletePhotoButton, addPhotoButton;

    @FXML
    ListView<HBox> photoListView;

    ObservableList<HBox> obsPhotoListView = FXCollections.observableArrayList();




    public void intialize(Album userAlbum){
        try {
            this.userAlbum = userAlbum;
            this.userPhotos = userAlbum.getPhotos();

            for (Photo p : userAlbum.getPhotos()) {
                HBox initPhoto = new HBox(10);
                Image img = new Image(p.getImagePath());
                ImageView iv = new ImageView(img);
                initPhoto.getChildren().add(iv);
                initPhoto.getChildren().add(new Label(p.getImageName().toString()));
                obsPhotoListView.add(initPhoto);
            }

            photoListView.setItems(obsPhotoListView);
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    @FXML
    public void addPhoto(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fc.getExtensionFilters();

        File imageToAdd = fc.showOpenDialog(null);
        fc.setTitle("Choose Photo to Upload");
        if(imageToAdd == null){
            Alert inputError = new Alert(Alert.AlertType.ERROR);
            inputError.setTitle("Try again.");
            inputError.setContentText("Field Cannot be left blank");
            inputError.showAndWait();
            return;
        }

        Path toPhoto = Paths.get(imageToAdd.getAbsolutePath());
        Photo photoToAdd = new Photo(imageToAdd.getAbsolutePath(),"", toPhoto.getFileName().toString());
        HBox photocell = new HBox(10);

        Image img = new Image((imageToAdd.toURI().toString()));
        ImageView iv = new ImageView(img);
        iv.setFitWidth(100);
        //iv.setFitHeight(100);
        iv.setPreserveRatio(true);
        photocell.getChildren().add(iv);
        photocell.getChildren().add(new Label(photoToAdd.getImageName()));

        obsPhotoListView.add(photocell);
        userAlbum.addPhoto(photoToAdd);
        photoListView.setItems(obsPhotoListView);
        photoListView.getSelectionModel().select(0);
        photoListView.refresh();

        currUser.writeData(currUser);





    }



    public User setUser(String u) throws IOException, ClassNotFoundException {
        return this.currUser = User.readData(u);
    }

    @FXML
    public void toAlbumListView(ActionEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user.fxml"));
        Parent userParent = loader.load();
        Scene userScene = new Scene(userParent);

        UserController userController = loader.getController();
        userController.setUser(currUser.getUserName());
        userController.intialize();
        Stage userStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        userStage.setScene(userScene);
        userStage.show();

    }

    @FXML
    public void photoListLogoff(ActionEvent event) throws IOException {
        logoff(event);
    }

    @FXML
    public void toSlideshow(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/slideshow.fxml"));


            Parent slideshowParent = fxmlLoader.load();
            Scene photoListScene = new Scene(slideshowParent);

            SlideshowController slideshowController = fxmlLoader.getController();
            slideshowController.setUser(currUser.getUserName());
            slideshowController.intialize();
            Stage photoListStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            photoListStage.setScene(photoListScene);
            photoListStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }


    }


}
