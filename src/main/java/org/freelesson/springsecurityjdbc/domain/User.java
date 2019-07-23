package org.freelesson.springsecurityjdbc.domain;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false,unique = true)
    public String username;

    @Column(nullable = false)
    public String password;

}
