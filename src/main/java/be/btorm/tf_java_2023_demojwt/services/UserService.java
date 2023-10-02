package be.btorm.tf_java_2023_demojwt.services;

import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    User register(User user);
    User login(User user);
}
