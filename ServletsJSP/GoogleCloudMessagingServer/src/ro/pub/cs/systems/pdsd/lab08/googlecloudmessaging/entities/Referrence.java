package ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.entities;

public class Referrence {

    private String parentTable;
    private String childTable;
    private String parentAttributeName;
    private String childAttributeName;

    public Referrence() {
        parentTable = new String();
        childTable = new String();
        parentAttributeName = new String();
        childAttributeName = new String();
    }

    public Referrence(String parentTable, String childTable, String parentAttributeName, String childAttributeName) {
        this.parentTable = parentTable;
        this.childTable = childTable;
        this.parentAttributeName = parentAttributeName;
        this.childAttributeName = childAttributeName;
    }

    public String getParentTable() {
        return parentTable;
    }

    public String getChildTable() {
        return childTable;
    }

    public String getParentAttributeName() {
        return parentAttributeName;
    }

    public String getChildAttributeName() {
        return childAttributeName;
    }

    public void setParentTable(String parentTable) {
        this.parentTable = parentTable;
    }

    public void setChildTable(String childTable) {
        this.childTable = childTable;
    }

    public void setParentAttributeName(String parentAttributeName) {
        this.parentAttributeName = parentAttributeName;
    }

    public void setChildAttributeName(String childAttributeName) {
        this.childAttributeName = childAttributeName;
    }

    public String toString() {
        return childTable + "/" + childAttributeName + " referrences " + parentTable + "/" + parentAttributeName;
    }
}
