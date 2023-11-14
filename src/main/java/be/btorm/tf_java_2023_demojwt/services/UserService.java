package be.btorm.tf_java_2023_demojwt.services;

import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import org.springframework.security.core.userdetails.UserDetailsService;

//Avec Spring security on a besoin d'un service qui implemente UserDetailsService
public interface UserService extends UserDetailsService {

    User register(User user);
    User login(User user);
}
