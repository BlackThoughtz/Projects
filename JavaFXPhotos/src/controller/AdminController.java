package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


/**
 * Created by butlr on 11/14/2017.
 */
@SuppressWarnings("Duplicates")
public class AdminController implements Logoff, DirectoryDelete{

    @FXML
    private Button addUser,deleteUser,logoffButton;

    @FXML
    ListView<String> userListView;

    @FXML
     TextField editField;

    private File userTxt = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "dat" + File.separator + "UserList.txt" );
    ArrayList<String> userList;

    ObservableList<String> observeUsers = FXCollections.observableArrayList();



    public void initialize() throws IOException{
        userList = createList();
        observeUsers.addAll(userList);
        userListView.setItems(observeUsers);
        userListView.getSelectionModel().select(0);

    }

    @FXML
    private void createUser(ActionEvent event) {
        String userToAdd = editField.getText();
        if (editField.getText().equals("")){
            Alert inputError = new Alert(Alert.AlertType.ERROR);
            inputError.setTitle("Try again.");
            inputError.setContentText("Field Cannot be left blank");
            inputError.showAndWait();
            return;

        }
        for(String user: userList){
            if(userToAdd.equals(user)){
                Alert inputError = new Alert(Alert.AlertType.ERROR);
                inputError.setTitle("Try again.");
                inputError.setContentText("User Already Exist");
                inputError.showAndWait();
                return;
            }

        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(userTxt, true));
            PrintWriter out = new PrintWriter(bw);
            User addedUser = new User(userToAdd);
            userList.add(userToAdd);
            observeUsers.add(userToAdd);
            out.println(userToAdd);
            addedUser.writeData(addedUser);
            out.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        editField.clear();



    }

    @FXML
    private void deleteUser(ActionEvent event) throws IOException {

        boolean deleted = false;
        int index = userListView.getSelectionModel().getSelectedIndex();
        String userToDelete = userListView.getItems().get(index);

        File temp = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "dat" + File.separator + "tmp.txt" );


        BufferedReader br = new BufferedReader(new FileReader(userTxt));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

        String tempLine;

        while((tempLine = br.readLine()) != null){
            if(tempLine.trim().equals(userToDelete)){
                deleted = true;
                continue;
            }

            bw.write(tempLine);
            bw.newLine();
        }
        bw.close();
        br.close();
        if(deleted){

            Files.move(temp.toPath(),userTxt.toPath(), StandardCopyOption.REPLACE_EXISTING);
            temp.deleteOnExit();

            deleteDirectory(new File(System.getProperty("user.dir") +  userToDelete));

            observeUsers.remove(index);
            userList.remove(index);
        }

    }

    /*Helper delete method*/


    @FXML
    public void adminLogoff(ActionEvent event) throws IOException {
        logoff(event);
    }

    private ArrayList<String> createList() throws IOException {
        BufferedReader br;
        FileReader fr;
        ArrayList<String> temp = new ArrayList<String>();
        String sCurrentLine;
        fr = new FileReader(userTxt);
        br = new BufferedReader(fr);

        while((sCurrentLine = br.readLine()) != null){
            temp.add(sCurrentLine);
        }

        fr.close();
        br.close();


        return temp;

    }
}



