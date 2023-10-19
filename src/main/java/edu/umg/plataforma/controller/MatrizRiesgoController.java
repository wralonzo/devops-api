package edu.umg.plataforma.controller;

import edu.umg.plataforma.entity.MatrizRiesgoEntity;
import edu.umg.plataforma.exception.ExcepcionPersonalizada;
import edu.umg.plataforma.service.MatrizRiesgoService;
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
@RequestMapping("api/v1/matriz-riesgo")
@RequiredArgsConstructor
public class MatrizRiesgoController {
    private final MatrizRiesgoService matrizRiesgoService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<List<MatrizRiesgoEntity>> listadoMatrizesRiesgo() {
        try {
            List<MatrizRiesgoEntity> matrizesRiesgo = matrizRiesgoService.listadoMatrizesRiesgo();
            if (!matrizesRiesgo.isEmpty()) {
                return ResponseEntity.ok(matrizesRiesgo);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idMatrizRiesgo}")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<?> encontrarMatrizRiesgo(@Valid @PathVariable Integer idMatrizRiesgo) {
        try {
            if (Objects.isNull(idMatrizRiesgo)) {
                return ResponseEntity.noContent().build();
            }
            MatrizRiesgoEntity matrizRiesgoEntity = matrizRiesgoService.encontrarMatrizRiesgo(idMatrizRiesgo);
            return ResponseEntity.ok(matrizRiesgoEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> crearMatrizRiesgo(@Valid @RequestBody MatrizRiesgoEntity matrizRiesgoEntity) {
        try {
            if (Objects.isNull(matrizRiesgoEntity)) {
                return ResponseEntity.badRequest().body("El cuerpo de la solicitud no puede estar vacío.");
            }
            MatrizRiesgoEntity nuevaSuscripcion = matrizRiesgoService.crearMatrizRiesgo(matrizRiesgoEntity);
            return ResponseEntity.ok(nuevaSuscripcion);
        } catch (ValidationException ve) {
            return ResponseEntity.unprocessableEntity().body(ve.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> modificarMatrizRiesgo(@Valid @RequestBody MatrizRiesgoEntity matrizRiesgoEntity) {
        try {
            if (Objects.isNull(matrizRiesgoEntity)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(matrizRiesgoService.modificarMatrizRiesgo(matrizRiesgoEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @DeleteMapping("/{idMatrizRiesgo}")
    @PreAuthorize("hasRole('DIRECTOR_SALUD')")
    public ResponseEntity<String> eliminarMatrizRiesgo(@Valid @PathVariable Integer idMatrizRiesgo) {
        try {
            matrizRiesgoService.eliminarMatrizRiesgo(idMatrizRiesgo);
            return ResponseEntity.ok("MaatrizRiesgoEntity eliminado correctamente");
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
