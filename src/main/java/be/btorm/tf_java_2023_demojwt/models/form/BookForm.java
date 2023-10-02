package be.btorm.tf_java_2023_demojwt.models.form;

import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class BookForm {

    private String title;

    private String description;

    public Book toEntity(){
        Book book = new Book();
        book.setTitle(this.title);
        book.setDescription(this.description);
        return book;
    }
}
