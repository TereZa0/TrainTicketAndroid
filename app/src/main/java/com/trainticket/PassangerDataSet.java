package com.trainticket;

public class PassangerDataSet {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public PassangerDataSet(String name, String email, String jk) {
        this.name = name;
        this.email = email;
        this.jk = jk;
    }

    String name,email,jk;
}
