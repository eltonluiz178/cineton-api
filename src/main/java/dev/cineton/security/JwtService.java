package dev.cineton.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // gera o token com o email como subject
    public String generateToken(UserDetails userDetails) {
        long nowMillis = System.currentTimeMillis();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities())
                .issuedAt(new Date(nowMillis))
                .expiration(new Date(nowMillis + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    // extrai o email(username) do token
    public String extractUsername(String token) {

        var claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token) // Valida a assinatura e expiração
                .getPayload(); // Retorna o payload

        return claims.getSubject();
    }

    // valida se o token pertence ao usuário e não expirou
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String usernameFromToken = extractUsername(token);
        return usernameFromToken.equals(userDetails.getUsername());
    }

    // converte o secret string em SecretKey
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
