package com.ace.boot.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;
import java.sql.Blob;

@Entity(name = "user")
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name required")
    private String name;
    @NotBlank(message = "email required")
    @Column(unique = true,nullable = false)
    private String email;
    @NotBlank(message = "address required")
    private String address;
    @Column(nullable = false)
    @NotBlank(message = "password required")
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    private Blob photo;
    @Transient
    private MultipartFile file;
    @Transient
    private String photoString;


}
