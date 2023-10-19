package edu.umg.plataforma.controller;

import edu.umg.plataforma.entity.UsuarioEntity;
import edu.umg.plataforma.exception.ExcepcionPersonalizada;
import edu.umg.plataforma.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<List<UsuarioEntity>> listadoUsuarios() {
        try {
            List<UsuarioEntity> usuarioEntities = usuarioService.listadoUsuarios();
            if (!usuarioEntities.isEmpty()) {
                return ResponseEntity.ok(usuarioEntities);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idUsuario}")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<?> encontrarUsuario(@Valid @PathVariable Integer idUsuario) {
        try {
            if (Objects.isNull(idUsuario)) {
                return ResponseEntity.noContent().build();
            }
            UsuarioEntity usuarioEntity = usuarioService.encontrarUsuario(idUsuario);
            return ResponseEntity.ok(usuarioEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('DIRECTOR_SALUD')")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioEntity usuarioEntity) {
        try {
            if (Objects.isNull(usuarioEntity)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarioService.crearUsuario(usuarioEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('DIRECTOR_SALUD')")
    public ResponseEntity<?> modificarUsuario(@Valid @RequestBody UsuarioEntity usuarioEntity) {
        try {
            if (Objects.isNull(usuarioEntity)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarioService.modificarUsuario(usuarioEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('DIRECTOR_SALUD')")
    public ResponseEntity<?> eliminarUsuario(@Valid @PathVariable Integer idUsuario) {
        try {
            usuarioService.eliminarUsuario(idUsuario);
            return ResponseEntity.ok("UsuarioEntity eliminado correctamente");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ExcepcionPersonalizada e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
}
