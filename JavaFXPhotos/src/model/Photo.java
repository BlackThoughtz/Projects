package model;

import java.io.Serializable;

/**
 * Created by butlr on 11/15/2017.
 */
public class Photo implements Serializable {
    String caption, imagePath, albumPath;
    String imageName;

    public Photo(String imagePath, String caption, String imageName){
        this.imagePath = imagePath;
        this.caption = caption;
        this.imageName = imageName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAlbumPath() {
        return albumPath;
    }

    public void setAlbumPath(String albumPath) {
        this.albumPath = albumPath;
    }
}
