package com.coconut.ubo.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class College {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "college_id")
    private int id;

    @Column(name = "college_name")
    private String name;

    public College(String name) {
        this.name = name;
    }
}
