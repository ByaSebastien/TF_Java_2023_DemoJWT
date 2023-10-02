package be.btorm.tf_java_2023_demojwt.services.impl;

import be.btorm.tf_java_2023_demojwt.models.entities.security.RoleType;
import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import be.btorm.tf_java_2023_demojwt.repositories.security.UserRepository;
import be.btorm.tf_java_2023_demojwt.services.UserService;
import be.btorm.tf_java_2023_demojwt.utils.BCryptUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptUtils bCryptUtils;

    public UserServiceImpl(UserRepository userRepository, BCryptUtils bCryptUtils) {
        this.userRepository = userRepository;
        this.bCryptUtils = bCryptUtils;
    }

    @Override
    public User register(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already token");
        }
        String hashedPassword = bCryptUtils.hash(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole(RoleType.USER);
        return userRepository.save(user);
    }

    @Override
    public User login(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        if(!bCryptUtils.verify(user.getPassword(), existingUser.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        return existingUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
