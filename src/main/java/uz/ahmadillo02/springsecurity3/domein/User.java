package uz.ahmadillo02.springsecurity3.domein;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ManyToAny;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String email;

    private String username;

    private String password;

    private boolean activated = false;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name",referencedColumnName ="name" )})
    private Set<Authority>authorities = new HashSet<>();
}

