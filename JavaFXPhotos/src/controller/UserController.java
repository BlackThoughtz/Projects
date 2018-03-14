package controller;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Album;
import model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by butlr on 11/14/2017.
 */
@SuppressWarnings("Duplicates")
public class UserController implements Logoff, DirectoryDelete{

    @FXML
    TextField albumField, renameField;

    @FXML
    Button userLogoffButton, createAlbumButton, delAlbumButton, renameAlbumButton, openAlbumButton;

    @FXML
    ListView<Album> albumListView;

    private ObservableList<Album> obsUserAlbums;
    private List<Album> currUserAlbums;

    private User currUser;


    /*Load user, load users albums into observable list then display*/
    public void intialize() {

        currUserAlbums = new ArrayList<>();
        currUserAlbums = currUser.getUserAlbums();
        obsUserAlbums = FXCollections.observableArrayList();

  try {
           obsUserAlbums.addAll(currUserAlbums);
           albumListView.setItems(obsUserAlbums);
           albumListView.getSelectionModel().select(0);
       }catch (Exception e){

           return;
       }

    }

    @FXML
    public void addAlbum(ActionEvent event) throws IOException {

        if (albumField.getText().equals("")) {
            Alert inputError = new Alert(Alert.AlertType.ERROR);
            inputError.setTitle("Try again.");
            inputError.setContentText("Album Field Cannot be left blank");
            inputError.showAndWait();
            return;
        }

        for(Album a: currUserAlbums) {
            if (albumField.getText().trim().equals(a.getName())) {
                Alert inputError = new Alert(Alert.AlertType.ERROR);
                inputError.setTitle("Try again.");
                inputError.setContentText("Album Already Exist");
                inputError.showAndWait();
                return;
            }
        }


        Album albumToAdd = new Album(albumField.getText().trim());
        try {
            currUserAlbums.add(albumToAdd);
            obsUserAlbums.add(albumToAdd);
            albumListView.setItems(obsUserAlbums);
            albumListView.getSelectionModel().select(0);


            File albumDir = new File(System.getProperty("user.dir") + currUser.userName + File.separator + albumField.getText().trim());

            albumDir.mkdirs();
            albumField.clear();
            currUser.writeData(currUser);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void toPhotoList(ActionEvent event) {
        int index = albumListView.getSelectionModel().getSelectedIndex();

        try{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/PhotoList.fxml"));


        Parent photoListParent = fxmlLoader.load();
        Scene photoListScene = new Scene(photoListParent);

        PhotoListController photoListController = fxmlLoader.getController();
        photoListController.setUser(currUser.getUserName());
        photoListController.intialize(currUserAlbums.get(index));
        Stage photoListStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        photoListStage.setScene(photoListScene);
        photoListStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void userLogoff(ActionEvent event) throws IOException {
        logoff(event);
    }


    @FXML
    public void deleteAlbum(ActionEvent event) throws IOException {
        int index = albumListView.getSelectionModel().getSelectedIndex();
        String albumToDelete = albumListView.getItems().get(index).getName();

        currUser.userAlbums.remove(index);
        currUserAlbums.remove(index);
        obsUserAlbums.remove(index);

        deleteDirectory(new File(System.getProperty("user.dir") + currUser.userName + File.separator + albumToDelete));
        currUser.writeData(currUser);

    }

    @FXML
    public void renameAlbum(ActionEvent event) throws IOException {
        int index = albumListView.getSelectionModel().getSelectedIndex();
        String albumToBeRenamed = obsUserAlbums.get(index).getName();
        String albumRename = renameField.getText().trim();

        if (renameField.getText().equals("")) {
            Alert inputError = new Alert(Alert.AlertType.ERROR);
            inputError.setTitle("Try again.");
            inputError.setContentText("Album Field Cannot be left blank");
            inputError.showAndWait();
            return;
        }

        for(Album a: currUserAlbums) {
            if (renameField.getText().trim().equals(a.getName())) {
                Alert inputError = new Alert(Alert.AlertType.ERROR);
                inputError.setTitle("Try again.");
                inputError.setContentText("Album Already Exist");
                inputError.showAndWait();
                return;
            }
        }

        File albumTBR = new File(System.getProperty("user.dir") + currUser.userName + File.separator + albumToBeRenamed);

        File newAlbumName = new File(System.getProperty("user.dir") + currUser.userName + File.separator + albumRename);

        albumTBR.renameTo(newAlbumName);
        currUser.userAlbums.get(index).setName(albumRename);
        currUserAlbums.get(index).setName(albumRename);
        obsUserAlbums.get(index).setName(albumRename);
        System.out.println(currUser.userAlbums.get(index).getName() + " " + currUserAlbums.get(index).getName() + " " + obsUserAlbums.get(index).getName());
        albumListView.refresh();

        currUser.writeData(currUser);

    }


    public User setUser(String u) throws IOException, ClassNotFoundException {
        return this.currUser = User.readData(u);
    }

}
