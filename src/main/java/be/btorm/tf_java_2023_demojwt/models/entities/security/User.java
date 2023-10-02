package be.btorm.tf_java_2023_demojwt.models.entities.security;


import be.btorm.tf_java_2023_demojwt.models.entities.Book;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "SECURITY_USER")
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id","email","role"})
@ToString(of = {"id","email","role"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false,length = 150,unique = true)
    @Getter @Setter
    private String email;

    @Column(nullable = false)
    @Getter @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private RoleType role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_BOOK",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
    private Set<Book> favorites = new HashSet<>();

    public Set<Book> getFavorites(){
        return Set.copyOf(this.favorites);
    }

    public void addFavorite(Book book){
        this.favorites.add(book);
        book.addUser(this);
    }

    public void removeFavorite(Book book){
        this.favorites.remove(book);
        book.removeUser(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(this.role.toString());
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
