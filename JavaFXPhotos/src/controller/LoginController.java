package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
@SuppressWarnings("Duplicates")
public class LoginController{

    @FXML
    private Button loginButton;

    @FXML
    private TextField userField;

    private String username;
    private String userPath = System.getProperty("user.dir");
    private File userTxt = new File(userPath + File.separator + "src" + File.separator + "dat" + File.separator + "UserList.txt" );


    ArrayList<String> userList;


    public void initialize() throws IOException {
        userList = createList();


    }
    @FXML
    public void login(ActionEvent event) throws IOException, ClassNotFoundException {
        username = userField.getText();

        if(username.equals("admin")){

            Parent adminParent = FXMLLoader.load(getClass().getResource("/view/admin.fxml"));
            Scene adminScene = new Scene(adminParent);

            AdminController adminController = new AdminController();
            //adminController.initialize(userList);

            Stage adminStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            adminStage.setScene(adminScene);
            adminStage.show();
            return;
            }

        if(validUser(username)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user.fxml"));
            Parent userParent = loader.load();
            Scene userScene = new Scene(userParent);

            UserController userController = loader.getController();
            userController.setUser(username);
            userController.intialize();
            Stage userStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            userStage.setScene(userScene);
            userStage.show();

        }else{
            Alert inputError = new Alert(Alert.AlertType.ERROR);
            inputError.setTitle("Try again.");
            inputError.setContentText("User Does Not Exist");
            inputError.showAndWait();
        }
        }



        public  ArrayList<String> createList() throws IOException {
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

        public boolean validUser(String inputtedUsername){
            for(String user: this.userList){
                if(user.equals(inputtedUsername)){
                    return true;
                }
            }
            return false;
        }




}
