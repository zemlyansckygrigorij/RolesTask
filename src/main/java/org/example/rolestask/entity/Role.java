package org.example.rolestask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class User for entity from table users
 */
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "roles_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false , unique=true)
    private Long id;

    @Column(name = "name", nullable = false , unique=true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;
}