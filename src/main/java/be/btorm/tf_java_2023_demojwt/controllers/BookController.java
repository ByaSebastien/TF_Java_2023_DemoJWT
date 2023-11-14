package be.btorm.tf_java_2023_demojwt.controllers;

import be.btorm.tf_java_2023_demojwt.models.dtos.BookDetailsDTO;
import be.btorm.tf_java_2023_demojwt.models.dtos.BookSimpleDTO;
import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import be.btorm.tf_java_2023_demojwt.models.form.BookForm;
import be.btorm.tf_java_2023_demojwt.services.BookService;
import be.btorm.tf_java_2023_demojwt.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final JwtUtils jwtUtils;

    public BookController(BookService bookService, JwtUtils jwtUtils) {
        this.bookService = bookService;
        this.jwtUtils = jwtUtils;
    }

    //Oblige à être ADMIN pour faire cet appel API
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

    //Oblige a etre ADMIN pour faire cet appel API
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{bookId}")
    public ResponseEntity<BookDetailsDTO> update(
            @PathVariable Long bookId,
            @RequestBody BookForm form
    ){
        Book book = bookService.update(bookId,form.toEntity());
        return ResponseEntity.ok(BookDetailsDTO.fromEntity(book));
    }

    //Oblige a etre ADMIN pour faire cet appel API
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Object> delete(
            @PathVariable Long bookId
    ){
        bookService.delete(bookId);
        return ResponseEntity.status(200).body("Succeed");
    }

    //Oblige a etre authentifié pour faire cet appel API
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{bookId}")
    public ResponseEntity<Object> addFavorite(
            //Dans authentication se trouve le UsernamePasswordAuthenticationToken qu'on a gérer dans le jwtFilter
            //Grace a ceci pas besoin de demander l'id du user par exemple
            //C'est l'id de l'utilisateur connecté qu'on peut retourver dans le token
            Authentication authentication,
            @PathVariable Long bookId
    ){
        //Dans UsernamePasswordAuthenticationToken se trouve :
        //le token dans les credential
        //l' user dans le principal
        //et les authorities dans authorities
        String token = authentication.getCredentials().toString();
        Long userId = jwtUtils.getId(token);
        bookService.addFavorite(userId,bookId);
        return ResponseEntity.status(200).body("Succeed");
    }
}
