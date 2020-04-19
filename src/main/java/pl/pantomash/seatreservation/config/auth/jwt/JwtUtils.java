package pl.pantomash.seatreservation.config.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.pantomash.seatreservation.model.User;

import java.util.Date;

import static pl.pantomash.seatreservation.config.auth.jwt.JwtProperties.EXPIRATION_TIME;
import static pl.pantomash.seatreservation.config.auth.jwt.JwtProperties.JWT_SECRET;

@Service
public class JwtUtils {
    public String extractLogin(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(JWT_SECRET))
                .setSubject(user.getLogin())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String login = extractLogin(token);
        return login.equals(userDetails.getUsername());
    }
}
