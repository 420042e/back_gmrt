package com.gambarte.app.controller;

import com.gambarte.app.dto.UserDto;
import com.gambarte.app.model.User;
import com.gambarte.app.repository.UserRepository;
import com.gambarte.app.util.ApiResponse;
import com.gambarte.app.util.EncryptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permitir el acceso CORS
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        try {
            // Verificar si el nombre de usuario ya existe
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                logger.warn("El nombre de usuario '{}' ya existe.", user.getUsername());
                return new ResponseEntity<>(
                    new ApiResponse(409, "Username already exists", null),
                    HttpStatus.CONFLICT
                );
            }

            // Codificar la contraseña
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            // Crear un UserDto a partir del usuario registrado
            UserDto userDto = new UserDto(user.getId(), user.getUsername());

            logger.info("Usuario registrado exitosamente: '{}'", user.getUsername());
            return new ResponseEntity<>(
                new ApiResponse(201, "User registered successfully", userDto),
                HttpStatus.CREATED
            );

        } catch (Exception e) {
            logger.error("Error durante el registro de usuario: {}", e.getMessage(), e);
            // Manejo de excepciones: puedes personalizar el mensaje de error y el código de estado
            return new ResponseEntity<>(
                new ApiResponse(500, "An error occurred while processing your request: " + e.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User loginRequest) {
        logger.info("User login attempt: {}", loginRequest.getUsername()); // Registro de intento de login
        
        try {
            // Buscar el usuario por nombre de usuario
            User existingUser = userRepository.findByUsername(loginRequest.getUsername())
                    .orElse(null);
            
            //Test 
            String decryptedPassword = EncryptionUtils.decrypt(loginRequest.getPassword());
            //String decryptedPassword = EncryptionUtils.decrypt("w9oKTqKTtvBuRUVbhQP/qw==");
            var prueba = "La contraseña desencriptada es: " + decryptedPassword;

            // Si no existe el usuario, retornar error
            if (existingUser == null) {
                logger.warn("Invalid login attempt: user {} not found.", loginRequest.getUsername()); // Advertencia si el usuario no existe
                return new ResponseEntity<>(
                    new ApiResponse(401, "Invalid username or password", null),
                    HttpStatus.UNAUTHORIZED
                );
            }

            // Verificar si la contraseña es correcta
            //boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword());
            boolean passwordMatches = passwordEncoder.matches(decryptedPassword, existingUser.getPassword());

            // Si la contraseña no coincide, retornar error
            if (!passwordMatches) {
                logger.warn("Invalid password attempt for user: {}", loginRequest.getUsername()); // Advertencia si la contraseña no coincide
                return new ResponseEntity<>(
                    new ApiResponse(401, "Invalid username or password", null),
                    HttpStatus.UNAUTHORIZED
                );
            }

            // Crear un UserDto a partir del usuario existente
            UserDto userDto = new UserDto(existingUser.getId(), existingUser.getUsername());
            logger.info("User {} logged in successfully.", existingUser.getUsername()); // Información de éxito

            return new ResponseEntity<>(new ApiResponse(200, "User logged in successfully", userDto), HttpStatus.OK);
            
        } catch (Exception e) {
            logger.error("Error during login attempt for user: {}. Error: {}", loginRequest.getUsername(), e.getMessage()); // Registro de error
            // Manejo de excepciones: puedes personalizar el mensaje de error y el código de estado
            return new ResponseEntity<>(
                new ApiResponse(500, "An error occurred while processing your request: " + e.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
