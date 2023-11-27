package dev.ruben.users.models;


import dev.ruben.users.models.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIOS")
@EntityListeners(AuditingEntityListener.class)
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del usuario", example = "1", required = true)
    private Long id;
    @NotBlank(message = "nombre no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Nombre", example = "Rubén", required = true)
    private String nombre;
    @Column(nullable = false)
    @NotBlank(message = "apellidos no puede estar vacío")
    @Schema(description = "Apellidos del usuario", example = "García", required = true)
    private String apellidos;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username no puede estar vacío")
    @Schema(description = "Nombre de usuario", example = "ruben", required = true)
    private String username;
    @Column(unique = true, nullable = false)
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser válido")
    @Schema(description = "Email del usuario", example = "ruben@gmail.com", required = true)
    @NotBlank(message = "Email no puede estar vacío")
    private String email;
    @NotBlank(message = "Password no puede estar vacío")
    @Length(min = 5, message = "Password debe tener al menos 5 caracteres")
    @Schema(description = "Password del usuario", example = "123456", required = true)
    @Column(nullable = false)
    private String password;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP) // Indicamos que es un campo de tipo fecha y hora
    @Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")

    @Builder.Default
    @Schema(description = "Fecha de creación del usuario", example = "2021-10-12T12:00:00", required = true)
    private LocalDateTime createdAt = LocalDateTime.now();
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP) // Indicamos que es un campo de tipo fecha y hora
    @Column(updatable = true, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Builder.Default
    @Schema(description = "Fecha de actualización del usuario", example = "2021-10-12T12:00:00", required = true)
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Column(columnDefinition = "boolean default false")
    @Builder.Default
    @Schema(description = "Si el usuario está borrado", example = "false", required = true)
    private Boolean isDeleted = false;


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Roles del usuario", example = "ROLE_USER", required = true)
    private Set<Role> roles;

    @Override
    @Operation(summary = "Obtener los roles del usuario", description = "Obtener los roles del usuario")
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    @Operation(summary = "Obtener el usuario", description = "Obtener el usuario")
    public String getUsername() {
        return username;
    }


    @Override
    @Operation(summary = "Obtener si el usuario está activo", description = "Obtener si el usuario está activo")
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Operation(summary = "Obtener si el usuario está bloqueado", description = "Obtener si el usuario está bloqueado")
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Operation(summary = "Obtener si las credenciales del usuario han expirado", description = "Obtener si las credenciales del usuario han expirado")
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Operation(summary = "Obtener si el usuario está activo", description = "Obtener si el usuario está activo")
    public boolean isEnabled() {
        return !isDeleted;
    }
}
