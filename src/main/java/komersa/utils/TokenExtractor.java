package komersa.utils;

public class TokenExtractor {
    public static String extractToken(String token){
        return token.replace("Bearer ", "");
    }
}
