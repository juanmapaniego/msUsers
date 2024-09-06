package com.globallogic.jmpaniego.msusers.model;

import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Phone> phones;
    @CreatedDate
    private LocalDate createdAt;
    private LocalDateTime lastLogin;

    public User(SignUpRequestDTO signUpRequestDTO) {
        this.id = UUID.randomUUID();
        this.active = true;
        this.name = signUpRequestDTO.getName();
        this.password = signUpRequestDTO.getPassword();
        this.email = signUpRequestDTO.getEmail();
        this.phones = signUpRequestDTO.getPhones().stream().map(Phone::new).collect(Collectors.toList());
        this.lastLogin = LocalDateTime.now(); //considero signup como login ya que se le retorna un token
    }
}
