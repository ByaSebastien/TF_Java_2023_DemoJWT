package be.btorm.tf_java_2023_demojwt.models.dtos;

import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class BookSimpleDTO {

    private Long id;

    private String title;

    public static BookSimpleDTO fromEntity(Book book){
        return new BookSimpleDTO(book.getId(), book.getTitle());
    }
}
