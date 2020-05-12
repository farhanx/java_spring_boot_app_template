package com.springboot.template.domains;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity(name = "user")
@Table(name="user")
@Getter @Setter @RequiredArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pass_hash", nullable = false)
    private String password;

   // @Column(name = "mobile", nullable = false)
   // private String mobile;

    @Column(name = "creation_date", nullable = false)
    private Long creationDate;

    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "role_id")
    private Role role;

}