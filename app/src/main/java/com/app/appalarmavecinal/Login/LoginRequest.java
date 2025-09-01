package com.app.appalarmavecinal.Login;

class LoginRequest {
    private String email;
    private String pass;

    public LoginRequest(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    // Getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }
}