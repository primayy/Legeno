package com.example.billage.backend.api.data;

public class UserAccount {
    private String fintech_num;
    private String bank_code;
    private String bank_name;
    private String account_num;

    public UserAccount(String fintech_num, String bank_code, String bank_name, String account_num) {
        this.fintech_num = fintech_num;
        this.bank_code = bank_code;
        this.bank_name = bank_name;
        this.account_num = account_num;
    }

    public String getFintech_num() {
        return fintech_num;
    }

    public String getBank_code() {
        return bank_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getAccount_num() {
        return account_num;
    }
}
