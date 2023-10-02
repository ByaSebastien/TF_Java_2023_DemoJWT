package be.btorm.tf_java_2023_demojwt.services;

import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {

    Book create(Book book);

    List<Book> getAll();

    Book getOne(Long id);

    Book update(Long id, Book book);

    void delete(Long id);

    void addFavorite(Long userId,Long bookId);
}
