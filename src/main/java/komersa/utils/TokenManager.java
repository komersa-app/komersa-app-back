package komersa.utils;

import io.jsonwebtoken.lang.Assert;
import komersa.exception.ProtectedResourceException;
import komersa.helper.JwtHelper;
import komersa.model.Admin;

public class TokenManager {
    public static String extractToken(String token){
        return token.replace("Bearer ", "");
    }
    public static void verifyToken(String token) {
        if (token == null) {
            throw new ProtectedResourceException();
        }
        Admin admin = new Admin(JwtHelper.extractUsername(extractToken(token)));
        Assert.isTrue(JwtHelper.validateToken(token, admin));
    }
}
