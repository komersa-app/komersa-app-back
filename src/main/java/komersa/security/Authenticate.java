package komersa.security;

public class Authenticate {
    private String username;
    private String password;

    public Boolean authenticate(String username, String password) {
        Boolean isAuthenticated = false;
        if (username.equals(this.username) && password.equals(this.password)) {
            isAuthenticated = true;
        }
        return isAuthenticated;
    }
}
