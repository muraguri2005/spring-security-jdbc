package org.freelesson.springsecurityjdbc.domain;

import java.util.HashSet;
import java.util.Set;

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
    
    @ManyToMany
    @JoinTable(name="user_role",joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="role_id",referencedColumnName="id"))
    public Set<Role> roles = new HashSet<>();
    

}
