package be.btorm.tf_java_2023_demojwt.repositories;

import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByTitle(String title);

    boolean existsByTitle(String title);
}
