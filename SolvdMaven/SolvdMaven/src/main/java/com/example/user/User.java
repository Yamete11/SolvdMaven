package com.example.user;

import java.time.LocalDate;
import java.util.Objects;

public abstract class User {
    protected LocalDate registrationDate;
    protected String email;
    private String password;
    protected String login;

    public User(String email, String password, String login) {
        this.registrationDate = LocalDate.now();
        this.email = email;
        this.password = password;
        this.login = login;
    }

    public abstract String getAccountType();

    public abstract void resetPassword();

    protected final void updateLogin(String newLogin) {
        this.login = newLogin;
        System.out.println("Login updated to: " + newLogin);
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("User Information:\n")
                .append("  Registration Date: ").append(registrationDate).append("\n")
                .append("  Email: ").append(email).append("\n")
                .append("  Login: ").append(login);
        return userInfo.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, login);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) && login.equals(user.login);
    }
}
