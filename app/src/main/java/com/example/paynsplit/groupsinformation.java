package com.example.paynsplit;

public class groupsinformation {

    String groupName;
    String groupID;


    public groupsinformation()
    {

    }

    public groupsinformation(String groupName, String groupID) {
        this.groupName = groupName;
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }


}
