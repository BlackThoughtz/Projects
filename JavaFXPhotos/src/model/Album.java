package model;

import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * Created by butlr on 11/20/2017.
 */
public class Album implements Serializable {
    private String name;
    private ArrayList<Photo> photos;
    private Photo oldest;
    private Photo newest;

    public Album(String name){
        this.name =name;
        oldest = null;
        newest = null;
        photos = new ArrayList<Photo>();
    }

    public void setName(String name) {
        this.name =name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

     public void addPhoto(Photo photo) {
        photos.add(photo);
/*        findOldest();
        findNewest();*/

    }

    public void removePhoto(int i) {
        photos.remove(i);
/*
        findOldest();
        findNewest();
*/

    }

 /*   public Photo getPhoto(int i){
        return photos.get(i);

    }*/

    public int number() {

        return photos.size();

    }
/*
    public void findOldest() {
        if (photos.size() ==0){
            return;
        }
        Photo temp = photos.get(0);
        for (Photo current: photos) {
            if (current.getCalender().compareTo(temp.getCalendar) <0) {
                temp = current;
            }
            oldest = temp;
        }
    }

    public void findNewest() {
        if (photos.size() ==0){
            return;
        }
        Photo temp = photos.get(0);
        for (Photo current: photos) {
            if (current.getCalender().compareTo(temp.getCalendar) >0) {
                temp = current;
            }
            newest = temp;
        }
    }

    public String getOldestDate() {
        if (oldest ==null) {
            return "Not found";
        }
        return oldest.getDate();
    }

    public String getNewestDate() {
        if (newest ==null) {
            return "Not found";
        }
        return newest.getDate();
    }


      public getDateRange() {
        return getOldestDate() + "-" + getNewestDate();*//*


}

 public Image getPhoto(int i) {
        if (photos.isEmpty())
            return null;
        return photos.get(i).getImage();
    }*/


    public ArrayList<Photo> getPhotos() {
        return photos;
    }


}
