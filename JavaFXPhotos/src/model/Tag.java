package model;
import java.io.Serializable;
public class Tag implements Serializable{
    private String type, value;


    public Tag(String type, String value) {
        this.type = type;
        this.value = value;
    }


    public String getType() {
        return type;
    }


    public String getValue() {
        return value;
    }


    public void setType(String type) {
        this.type = type;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return type + ": " + value;
    }


}
