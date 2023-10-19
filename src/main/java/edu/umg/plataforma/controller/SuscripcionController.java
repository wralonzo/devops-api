package edu.umg.plataforma.controller;

import edu.umg.plataforma.entity.SuscripcionEntity;
import edu.umg.plataforma.exception.ExcepcionPersonalizada;
import edu.umg.plataforma.service.SuscripcionService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
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
@RequestMapping("api/v1/suscripcion")
@RequiredArgsConstructor
public class SuscripcionController {
    private final SuscripcionService suscripcionService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<List<SuscripcionEntity>> listadoSuscripciones() {
        try {
            List<SuscripcionEntity> suscripcionesEntity = suscripcionService.listadoSuscripciones();
            if (!suscripcionesEntity.isEmpty()) {
                return ResponseEntity.ok(suscripcionesEntity);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idSuscripcion}")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<?> encontrarSuscripcion(@Valid @PathVariable Integer idSuscripcion) {
        try {
            if (Objects.isNull(idSuscripcion)) {
                return ResponseEntity.noContent().build();
            }
            SuscripcionEntity suscripcionEntity = suscripcionService.encontrarSuscripcion(idSuscripcion);
            return ResponseEntity.ok(suscripcionEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> crearSuscripcion(@Valid @RequestBody SuscripcionEntity suscripcionEntity) {
        try {
            if (Objects.isNull(suscripcionEntity)) {
                return ResponseEntity.badRequest().body("El cuerpo de la solicitud no puede estar vacío.");
            }
            SuscripcionEntity nuevaSuscripcion = suscripcionService.crearSuscripcion(suscripcionEntity);
            return ResponseEntity.ok(nuevaSuscripcion);
        } catch (ValidationException ve) {
            return ResponseEntity.unprocessableEntity().body(ve.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> modificarSuscripcion(@Valid @RequestBody SuscripcionEntity suscripcionEntity) {
        try {
            if (Objects.isNull(suscripcionEntity)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(suscripcionService.modificarSuscripcion(suscripcionEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @DeleteMapping("/{idSuscripcion}")
    @PreAuthorize("hasRole('DIRECTOR_SALUD')")
    public ResponseEntity<String> eliminarSuscripcion(@Valid @PathVariable Integer idSuscripcion) {
        try {
            suscripcionService.eliminarSuscripcion(idSuscripcion);
            return ResponseEntity.ok("SuscripcionEntity eliminado correctamente");
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
