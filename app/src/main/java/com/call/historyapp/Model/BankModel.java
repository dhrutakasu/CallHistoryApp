package com.call.historyapp.Model;

public class BankModel {
    String BankName,BankBalance,BankCustomer;
    int BankIcon;

    public BankModel(String bankName, int bankIcon, String bankBalance, String bankCustomer) {
        BankName = bankName;
        BankBalance = bankBalance;
        BankCustomer = bankCustomer;
        BankIcon = bankIcon;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBankBalance() {
        return BankBalance;
    }

    public void setBankBalance(String bankBalance) {
        BankBalance = bankBalance;
    }

    public String getBankCustomer() {
        return BankCustomer;
    }

    public void setBankCustomer(String bankCustomer) {
        BankCustomer = bankCustomer;
    }

    public int getBankIcon() {
        return BankIcon;
    }

    public void setBankIcon(int bankIcon) {
        BankIcon = bankIcon;
    }

    @Override
    public String toString() {
        return "BankModel{" +
                "BankName='" + BankName + '\'' +
                ", BankBalance='" + BankBalance + '\'' +
                ", BankCustomer='" + BankCustomer + '\'' +
                ", BankIcon=" + BankIcon +
                '}';
    }
}
