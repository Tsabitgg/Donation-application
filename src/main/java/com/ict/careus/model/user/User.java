package com.ict.careus.model.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "phoneNumber")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    @Size(min = 12, max = 13)
    private String phoneNumber;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String profilePicture;

    private String address;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Role role;

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    public User(String username, String phoneNumber, String password, String address, Role role) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.createdAt = new Date();
        this.address = address;
        this.role = role;
    }
}