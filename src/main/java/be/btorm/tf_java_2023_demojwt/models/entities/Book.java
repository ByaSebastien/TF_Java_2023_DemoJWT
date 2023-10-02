package be.btorm.tf_java_2023_demojwt.models.entities;

import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BOOK")
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id","title"})
@ToString(of = {"id","title"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false,unique = true,length = 50)
    @Getter @Setter
    private String title;

    @Column(nullable = true)
    @Getter @Setter
    private String description;

    @ManyToMany(mappedBy = "favorites",fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers(){
        return Set.copyOf(this.users);
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        this.users.remove(user);
    }
}
