package com.trainticket;

public class VarBank {
    private static int USER_ROLE;
    private static String USER_NAME;

    public VarBank(String USER_NAME, int USER_ROLE) {
        this.USER_NAME = USER_NAME;
        this.USER_ROLE = USER_ROLE;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static void setUserName(String userName) {
        USER_NAME = userName;
    }

    public static int getUserRole() {
        return USER_ROLE;
    }

    public static void setUserRole(int userRole) {
        USER_ROLE = userRole;
    }
}
