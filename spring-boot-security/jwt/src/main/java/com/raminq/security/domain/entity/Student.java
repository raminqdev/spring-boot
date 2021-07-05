package com.raminq.security.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myKeySeq")
    private Long id;

    private Address homeAddress;

    @ElementCollection
    private Set<String> images =new HashSet<>();

}
