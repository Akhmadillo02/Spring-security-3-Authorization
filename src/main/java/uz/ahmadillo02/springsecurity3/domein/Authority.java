package uz.ahmadillo02.springsecurity3.domein;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "authorityes")
@Data
public class Authority implements Serializable {

    @Id
    private String name;
}
