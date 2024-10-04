package com.gambarte.app.dto;

public class UserDto {
    private Long id;
    private String username;

    // Constructor
    public UserDto(Long id, String username) {
        this.id = id;
        this.username = username;
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
}
