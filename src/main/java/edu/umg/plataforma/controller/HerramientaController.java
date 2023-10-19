package edu.umg.plataforma.controller;

import edu.umg.plataforma.entity.HerramientaEntity;
import edu.umg.plataforma.exception.ExcepcionPersonalizada;
import edu.umg.plataforma.service.HerramientaService;
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
@RequestMapping("api/v1/herramienta")
@RequiredArgsConstructor
public class HerramientaController {
    private final HerramientaService herramientaService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<List<HerramientaEntity>> listadoHerramientas() {
        try {
            List<HerramientaEntity> herramientasEntity = herramientaService.listadoHerramientas();
            if (!herramientasEntity.isEmpty()) {
                return ResponseEntity.ok(herramientasEntity);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idHerramienta}")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<?> encontrarHerramienta(@Valid @PathVariable Integer idHerramienta) {
        try {
            if (Objects.isNull(idHerramienta)) {
                return ResponseEntity.noContent().build();
            }
            HerramientaEntity herramientaEntity = herramientaService.encontrarHerramienta(idHerramienta);
            return ResponseEntity.ok(herramientaEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> crearHerramienta(@Valid @RequestBody HerramientaEntity herramientaEntity) {
        try {
            if (Objects.isNull(herramientaEntity)) {
                return ResponseEntity.badRequest().body("El cuerpo de la solicitud no puede estar vacío.");
            }
            HerramientaEntity nuevaHerramientaEnitity = herramientaService.crearHerramienta(herramientaEntity);
            return ResponseEntity.ok(nuevaHerramientaEnitity);
        } catch (ValidationException ve) {
            return ResponseEntity.unprocessableEntity().body(ve.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> modificarHerramienta(@Valid @RequestBody HerramientaEntity herramientaEntity) {
        try {
            if (Objects.isNull(herramientaEntity)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(herramientaService.modificarHerramienta(herramientaEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @DeleteMapping("/{idHerramienta}")
    @PreAuthorize("hasRole('DIRECTOR_SALUD')")
    public ResponseEntity<String> eliminarHerramienta(@Valid @PathVariable Integer idHerramienta) {
        try {
            herramientaService.eliminarHerramienta(idHerramienta);
            return ResponseEntity.ok("HerramientaEntity eliminado correctamente");
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
