package com.gambarte.app.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    //@JsonIgnore // Evita que el password se incluya en la respuesta JSON
    private String password;

    private boolean enabled = true;

    // Métodos de la interfaz UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si no manejas roles, puedes devolver una autoridad vacía o alguna por defecto
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        // Puedes cambiar esto si manejas lógica de expiración de cuentas
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Puedes cambiar esto si manejas lógica de bloqueo de cuentas
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Puedes cambiar esto si manejas lógica de expiración de credenciales
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Este valor lo puedes manejar para activar o desactivar cuentas
        return enabled;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
