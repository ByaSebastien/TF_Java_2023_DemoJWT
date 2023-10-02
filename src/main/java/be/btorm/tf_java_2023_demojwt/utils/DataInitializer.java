package be.btorm.tf_java_2023_demojwt.utils;

import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import be.btorm.tf_java_2023_demojwt.models.entities.security.RoleType;
import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import be.btorm.tf_java_2023_demojwt.repositories.BookRepository;
import be.btorm.tf_java_2023_demojwt.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BCryptUtils bCryptUtils;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setEmail("admin@test.be");
        admin.setRole(RoleType.ADMIN);
        admin.setPassword(bCryptUtils.hash("Test1234="));
        userRepository.save(admin);

        User seb = new User();
        seb.setEmail("seb@test.be");
        seb.setRole(RoleType.USER);
        seb.setPassword(bCryptUtils.hash("Test1234="));
        userRepository.save(seb);

        Book martine = new Book();
        martine.setTitle("Martine à la mer");
        martine.setDescription("Qu'elle est belle cette mer");
        bookRepository.save(martine);

        Book toto = new Book();
        toto.setTitle("toto en Egypte");
        toto.setDescription("Qu'elle est belle cette pyramide");
        bookRepository.save(toto);

        Book roger = new Book();
        roger.setTitle("Roger au café");
        roger.setDescription("Qu'elle est bonne cette bière");
        bookRepository.save(roger);
    }
}
