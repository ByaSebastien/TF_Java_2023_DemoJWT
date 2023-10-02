package be.btorm.tf_java_2023_demojwt.configs;

import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import be.btorm.tf_java_2023_demojwt.services.UserService;
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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils utils;
    private final UserService userService;

    public JwtFilter(JwtUtils utils, UserService userService) {
        this.utils = utils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if(authorization != null){
            // Bearer jkfdshjkgfdbqkjbfgkjqsbkjfbsqkl
            String[] authorizations = authorization.split(" ");
            // Bearer [0]
            // klsqdhjgfkldsjhlkgjdolisq[1]
            String type = authorizations[0];
            String token = authorizations[1];
            if(type.equals("Bearer") && !token.isEmpty()){
                String email = utils.getEmail(token);
                User user = (User)userService.loadUserByUsername(email);
                if(utils.isValid(token)){
                    UsernamePasswordAuthenticationToken upt = new UsernamePasswordAuthenticationToken(user,user.getId(),user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(upt);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
