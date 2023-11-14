package be.btorm.tf_java_2023_demojwt.configs;

import be.btorm.tf_java_2023_demojwt.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils utils;
    //injection de notre UserService avec polymorphisme etant donné que UserService extends UserDetailsService
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtils utils, UserDetailsService userDetailsService) {
        this.utils = utils;
        this.userDetailsService = userDetailsService;
    }


    //En gros la methode sert a mettre dans notre securityContext l'utilisateur qui fait la requete api si il y en a un
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        //Bearer kqdshgijhqilkshgiofudshfiolgfhdiosqhio(token)
        String authorization = request.getHeader("Authorization");
        if(authorization != null){
            //authorizations[0] = "Bearer"
            //authorizations[1] = "kqdshgijhqilkshgiofudshfiolgfhdiosqhio" (token)
            String[] authorizations = authorization.split(" ");
            String type = authorizations[0];
            String token = authorizations[1];
            if(type.equals("Bearer") && !token.equals("")){
                if(utils.isValid(token)){
                    //On recupere l'email depuis le token
                    String email = utils.getEmail(token);
                    //On recupere notre UserDetails (User) via le UserDetailsService
                    UserDetails user = userDetailsService.loadUserByUsername(email);

                    //On affecte le principal (user) les credential (token) et les roles a notre security context avant de rendre la main
                    UsernamePasswordAuthenticationToken upt = new UsernamePasswordAuthenticationToken(user,token,user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(upt);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
