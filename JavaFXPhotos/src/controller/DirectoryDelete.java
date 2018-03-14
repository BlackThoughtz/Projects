package controller;

import java.io.File;

/**
 * Created by butlr on 11/22/2017.
 */
public interface DirectoryDelete {
    default void deleteDirectory(File file){
        File[] directory = file.listFiles();
        if(directory != null){
            for(File f : directory){
                deleteDirectory(f);
            }
        }
        file.delete();
    }
}
