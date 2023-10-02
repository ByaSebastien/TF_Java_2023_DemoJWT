package be.btorm.tf_java_2023_demojwt.services.impl;

import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import be.btorm.tf_java_2023_demojwt.repositories.BookRepository;
import be.btorm.tf_java_2023_demojwt.repositories.security.UserRepository;
import be.btorm.tf_java_2023_demojwt.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Book create(Book book) {
        if(bookRepository.existsByTitle(book.getTitle())){
            throw new RuntimeException("This book already exist");
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getOne(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public Book update(Long id, Book book) {
        Book existingBook = bookRepository.findById(id).orElseThrow();
        existingBook.setTitle(book.getTitle());
        existingBook.setDescription(book.getDescription());
        return bookRepository.save(existingBook);
    }

    @Override
    public void delete(Long id) {
        Book existingBook = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(existingBook);
    }

    @Override
    public void addFavorite(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        user.addFavorite(book);

        userRepository.save(user);
        bookRepository.save(book);
    }
}
