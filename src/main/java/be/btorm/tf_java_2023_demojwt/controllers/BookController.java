package be.btorm.tf_java_2023_demojwt.controllers;

import be.btorm.tf_java_2023_demojwt.models.dtos.BookDetailsDTO;
import be.btorm.tf_java_2023_demojwt.models.dtos.BookSimpleDTO;
import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import be.btorm.tf_java_2023_demojwt.models.form.BookForm;
import be.btorm.tf_java_2023_demojwt.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDetailsDTO> create(
            @RequestBody BookForm form
            ){
        Book book = bookService.create(form.toEntity());
        return ResponseEntity.ok(BookDetailsDTO.fromEntity(book));
    }

    @GetMapping
    public ResponseEntity<List<BookSimpleDTO>> getAll(){
        List<BookSimpleDTO> books = bookService.getAll().stream()
                .map(BookSimpleDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDetailsDTO> getOne(
            @PathVariable Long bookId
    ){
        BookDetailsDTO dto = BookDetailsDTO.fromEntity(bookService.getOne(bookId));
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{bookId}")
    public ResponseEntity<BookDetailsDTO> update(
            @PathVariable Long bookId,
            @RequestBody BookForm form
    ){
        Book book = bookService.update(bookId,form.toEntity());
        return ResponseEntity.ok(BookDetailsDTO.fromEntity(book));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Object> delete(
            @PathVariable Long bookId
    ){
        bookService.delete(bookId);
        return ResponseEntity.status(200).body("Succeed");
    }
}