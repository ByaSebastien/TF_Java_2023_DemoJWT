package be.btorm.tf_java_2023_demojwt.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BOOK")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 50)
    private String title;

    @Column(nullable = true)
    private String description;
}
