package com.itheima;

public class Account {
    private String cardId;
    private String userName;
    private char sex;
    private String passWord;
    private double money;
    private double Limit;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId){
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName + (sex == '男' ? "先生" : "女士");
    }

    public void setUserName(String userName){
        this.userName = userName; 
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex){
        this.sex = sex;
    }

    public String getPassword() {
        return passWord;
    }

    public void setPassword(String passWord){
        this.passWord = passWord;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money){
        this.money = money;
    }

    public double getLimit() {
        return Limit;
    }
    
    public void setLimit(double Limit){
        this.Limit = Limit;
    }
}
