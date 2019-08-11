package org.freelesson.springsecurityjdbc.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false,unique = true)
    @NotNull(message="Username is required")
    public String username;

    
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(nullable = false)
    @NotNull(message="Password is required")
    public String password;
    
    @ManyToMany
    @JoinTable(name="user_role",joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="role_id",referencedColumnName="id"))
    public Set<Role> roles = new HashSet<>();
    

}
