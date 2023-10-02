package be.btorm.tf_java_2023_demojwt.models.dtos;

import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class BookDetailsDTO {

    private Long id;

    private String title;

    private String description;

    public static BookDetailsDTO fromEntity(Book book){
        return new BookDetailsDTO(book.getId(), book.getTitle(), book.getDescription());
    }
}