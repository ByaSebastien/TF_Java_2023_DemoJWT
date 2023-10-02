package be.btorm.tf_java_2023_demojwt.utils;

import be.btorm.tf_java_2023_demojwt.configs.JwtConfig;
import be.btorm.tf_java_2023_demojwt.models.entities.security.RoleType;
import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtUtils {

    private final JwtConfig config;
    private final JwtParser parser;
    private final JwtBuilder builder;

    public JwtUtils(JwtConfig config) {
        this.config = config;
        SecretKey key = config.secretKey;
        this.parser = Jwts.parserBuilder().setSigningKey(key).build();
        this.builder = Jwts.builder().signWith(key);
    }

    public String generateToken(User user){
        return builder
                .claim("id",user.getId())
                .claim("email",user.getEmail())
                .claim("role",user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + config.expireAt * 1000L))
                .compact();
    }

    public Claims getClaims(String token){
        return parser.parseClaimsJwt(token).getBody();
    }

    public Long getId(String token){
        return getClaims(token).get("id", Long.class);
    }

    public String getEmail(String token){
        return getClaims(token).get("email", String.class);
    }

    public RoleType getRole(String token){
        return  getClaims(token).get("role", RoleType.class);
    }

    public boolean isValid(String token){
        Claims claims = getClaims(token);
        Date now = new Date();
        return getRole(token) != null && now.after(claims.getIssuedAt()) && now.before(claims.getExpiration());
    }
}
