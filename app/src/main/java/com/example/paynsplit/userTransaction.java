package com.example.paynsplit;

public class userTransaction {

    public String userName;
    public String userID;
    public String emailID;
    public userTransaction() {
    }

    public userTransaction(String userID,String userName,String emailID) {

        this.userID=userID;
        this.userName=userName;
        this.emailID=emailID;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String groupID) {
        this.userName = groupID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}
