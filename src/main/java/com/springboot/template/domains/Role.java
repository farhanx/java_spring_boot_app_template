package com.springboot.template.domains;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.Timestamp;

@Entity(name = "Role")
@Table(name="role")
@Getter @Setter @RequiredArgsConstructor
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "description", nullable = false)
    private String description;

}