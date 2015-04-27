package ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.entities;

import java.io.Serializable;

public class Record implements Serializable {

    final public static long serialVersionUID = 10031003L;

    private String attribute;
    private String value;

    public Record() {
        attribute = new String();
        value = new String();
    }

    public Record(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }

}
