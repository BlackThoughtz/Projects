package model;

import java.io.*;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by butlr on 11/15/2017.
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1204190612221926L;
    public String userName;
    public ArrayList<Album> userAlbums;
    public static File storeDir;
    public static String storeFile;

    public User(String name) throws IOException {
        this.userName = name;
        userAlbums = new ArrayList<Album>();
        storeDir = new File(System.getProperty("user.dir") + this.userName );
        storeDir.mkdir();
        storeFile = this.userName + ".dat";

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void writeData(User u) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(System.getProperty("user.dir") + u.userName + File.separator + u.userName + ".dat"));
        oos.writeObject(u);
        oos.close();
    }

    public static User readData(String u) throws IOException, ClassNotFoundException {

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir") + u + File.separator + u + ".dat"));

        User user = (User) ois.readObject();

        ois.close();

        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userAlbums=" + userAlbums +
                '}';
    }

    public void addAlbum(Album userAlbum){
        userAlbums.add(userAlbum);
        return;
    }

    public void listAlbums(ArrayList<Album> userAlbums){
        for(Album a: userAlbums){
            System.out.println(a.getName());
        }
    }

    public ArrayList<Album> getUserAlbums() {
        return userAlbums;
    }
}
