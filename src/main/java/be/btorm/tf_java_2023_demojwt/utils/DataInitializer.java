package be.btorm.tf_java_2023_demojwt.utils;

import be.btorm.tf_java_2023_demojwt.models.entities.security.RoleType;
import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import be.btorm.tf_java_2023_demojwt.repositories.security.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptUtils bCryptUtils;

    public DataInitializer(UserRepository userRepository, BCryptUtils bCryptUtils) {
        this.userRepository = userRepository;
        this.bCryptUtils = bCryptUtils;
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setEmail("admin@test.be");
        admin.setRole(RoleType.ADMIN);
        admin.setPassword(bCryptUtils.hash("Test1234="));
        userRepository.save(admin);
    }
}
