package edu.umg.plataforma.controller;

import edu.umg.plataforma.entity.AsignacionHerramientaEntity;
import edu.umg.plataforma.exception.ExcepcionPersonalizada;
import edu.umg.plataforma.service.AsignacionHerramientaService;
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
@RequestMapping("api/v1/asignacion-herramienta")
@RequiredArgsConstructor
public class AsignacionHerramientaController {
    private final AsignacionHerramientaService asignacionHerramientaService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<List<AsignacionHerramientaEntity>> listadoAsignacionesHerramientas() {
        try {
            List<AsignacionHerramientaEntity> asignacionesHerramientaEntity = asignacionHerramientaService.listadoAsignacionesHerramientas();
            if (!asignacionesHerramientaEntity.isEmpty()) {
                return ResponseEntity.ok(asignacionesHerramientaEntity);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idAsignacionHerramienta}")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<?> encontrarAsignacionHerramienta(@Valid @PathVariable Integer idAsignacionHerramienta) {
        try {
            if (Objects.isNull(idAsignacionHerramienta)) {
                return ResponseEntity.noContent().build();
            }
            AsignacionHerramientaEntity asignacionHerramientaEntity = asignacionHerramientaService.encontrarAsignacionHerramienta(idAsignacionHerramienta);
            return ResponseEntity.ok(asignacionHerramientaEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> crearAsignacionHerramienta(@Valid @RequestBody AsignacionHerramientaEntity asignacionHerramientaEntity) {
        try {
            if (Objects.isNull(asignacionHerramientaEntity)) {
                return ResponseEntity.badRequest().body("El cuerpo de la solicitud no puede estar vacío.");
            }
            AsignacionHerramientaEntity nuevaAsignacionEntity = asignacionHerramientaService.crearAsignacionHerramienta(asignacionHerramientaEntity);
            return ResponseEntity.ok(nuevaAsignacionEntity);
        } catch (ValidationException ve) {
            return ResponseEntity.unprocessableEntity().body(ve.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> modificarAsignacionHerramienta(@Valid @RequestBody AsignacionHerramientaEntity asignacionHerramientaEntity) {
        try {
            if (Objects.isNull(asignacionHerramientaEntity)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(asignacionHerramientaService.modificarAsignacionHerramienta(asignacionHerramientaEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @DeleteMapping("/{idAsignacionHerramienta}")
    @PreAuthorize("hasRole('DIRECTOR_SALUD')")
    public ResponseEntity<String> eliminarAsignacionHerramienta(@Valid @PathVariable Integer idAsignacionHerramienta) {
        try {
            asignacionHerramientaService.eliminarAsignacionHerramienta(idAsignacionHerramienta);
            return ResponseEntity.ok("AsignacionHerramientaEntity eliminado correctamente");
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
