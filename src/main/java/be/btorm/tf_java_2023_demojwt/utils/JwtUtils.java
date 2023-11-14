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
import java.security.Key;
import java.time.LocalDate;
import java.util.Date;

//Utilitaire qui permet de manipuler les jwtoken
@Component
public class JwtUtils {

    //Class de config qui contient la secretKey et la duree de validation des token
    private final JwtConfig config;
    //Permet d'extraire les info d'un token
    private final JwtParser parser;
    //Permet de generer un token
    private final JwtBuilder builder;

    public JwtUtils(JwtConfig config) {
        //Le parser et le builder doivent bien entendu avoir la meme secret key
        this.config = config;
        this.parser = Jwts.parserBuilder().setSigningKey(config.secretKey).build();
        this.builder = Jwts.builder().signWith(config.secretKey);
    }

    //Methode qui genere le token a partir de notre UserDetails custom
    public String generateToken(User user){
        return builder
                .claim("id",user.getId())
                .claim("email",user.getEmail())
                .claim("role",user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + config.expireAt * 1000L))
                .compact();
    }

    //Methode qui extrait tout les claims d'un token
    public Claims getClaims(String token){
        return parser.parseClaimsJws(token).getBody();
    }

    //Methode qui extrait le claim id du token
    public Long getId(String token){
        return getClaims(token).get("id", Long.class);
    }

    //Methode qui extrait le claim email du token
    public String getEmail(String token){
        return getClaims(token).get("email", String.class);
    }

    //Methode qui extrait le claim role du token
    public String getRole(String token){
        return  getClaims(token).get("role", String.class);
    }

    //Test si le token est toujours valide
    public boolean isValid(String token){
        Claims claims = getClaims(token);
        Date now = new Date();
        return getRole(token) != null && now.after(claims.getIssuedAt()) && now.before(claims.getExpiration());
    }
}
