package com.observationclass.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_name"),
        @UniqueConstraint(columnNames = "email")
})
public class Account extends CommonEntity {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "campus_id")
    private Integer campusId;

    @Column(name="department_id")
    private Integer departmentId;

    @ManyToMany(
            //bi loi o day
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )

    private Set<Role> roles = new HashSet<>();
}
