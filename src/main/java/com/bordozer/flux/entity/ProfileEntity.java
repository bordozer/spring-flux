package com.bordozer.flux.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_profile")
@Data
public class ProfileEntity {
    @Id
    private Long id;
    @Column("email")
    private String email;
}
