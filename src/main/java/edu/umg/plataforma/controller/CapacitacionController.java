package edu.umg.plataforma.controller;

import edu.umg.plataforma.entity.CapacitacionEntity;
import edu.umg.plataforma.exception.ExcepcionPersonalizada;
import edu.umg.plataforma.service.CapacitacionService;
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
@RequestMapping("api/v1/capacitacion")
@RequiredArgsConstructor
public class CapacitacionController {
    private final CapacitacionService capacitacionService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<List<CapacitacionEntity>> listadoCapacitaciones() {
        try {
            List<CapacitacionEntity> capacitacionEntity = capacitacionService.listadoCapacitaciones();
            if (!capacitacionEntity.isEmpty()) {
                return ResponseEntity.ok(capacitacionEntity);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idCapacitacion}")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<?> encontrarCapacitacion(@Valid @PathVariable Integer idCapacitacion) {
        try {
            if (Objects.isNull(idCapacitacion)) {
                return ResponseEntity.noContent().build();
            }
            CapacitacionEntity capacitacionEntity = capacitacionService.encontrarCapacitacion(idCapacitacion);
            return ResponseEntity.ok(capacitacionEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> crearCapacitacion(@Valid @RequestBody CapacitacionEntity capacitacionEntity) {
        try {
            if (Objects.isNull(capacitacionEntity)) {
                return ResponseEntity.badRequest().body("El cuerpo de la solicitud no puede estar vacío.");
            }
            CapacitacionEntity nuevaCapacitacion = capacitacionService.crearCapacitacion(capacitacionEntity);
            return ResponseEntity.ok(nuevaCapacitacion);
        } catch (ValidationException ve) {
            return ResponseEntity.unprocessableEntity().body(ve.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> modificarCapacitacion(@Valid @RequestBody CapacitacionEntity capacitacionEntity) {
        try {
            if (Objects.isNull(capacitacionEntity)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(capacitacionService.modificarCapacitacion(capacitacionEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @DeleteMapping("/{idCapacitacion}")
    @PreAuthorize("hasRole('DIRECTOR_SALUD')")
    public ResponseEntity<String> eliminarCapacitacion(@Valid @PathVariable Integer idCapacitacion) {
        try {
            capacitacionService.eliminarCapacitacion(idCapacitacion);
            return ResponseEntity.ok("CapacitacionEntity eliminado correctamente");
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
