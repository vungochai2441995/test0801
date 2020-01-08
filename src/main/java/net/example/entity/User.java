package net.example.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username",unique = true)
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="address")
    private String address;
}