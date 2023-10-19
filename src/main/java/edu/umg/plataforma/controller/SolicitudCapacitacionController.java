package edu.umg.plataforma.controller;

import edu.umg.plataforma.entity.SolicitudCapacitacionEntity;
import edu.umg.plataforma.exception.ExcepcionPersonalizada;
import edu.umg.plataforma.service.SolicitudCapacitacionService;
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
@RequestMapping("api/v1/solicitud-capacitacion")
@RequiredArgsConstructor
public class SolicitudCapacitacionController {
    private final SolicitudCapacitacionService solicitudCapacitacionService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<List<SolicitudCapacitacionEntity>> listadoSolicitudesCapacitacion() {
        try {
            List<SolicitudCapacitacionEntity> suscripcionesEntity = solicitudCapacitacionService.listadoSolicitudesCapacitacion();
            if (!suscripcionesEntity.isEmpty()) {
                return ResponseEntity.ok(suscripcionesEntity);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idSolicitudCapacitacion}")
    @PreAuthorize("hasAnyRole('DIRECTOR_SALUD', 'ENCARGADO_CAPACITACION', 'AUXILIAR')")
    public ResponseEntity<?> encontrarSolicitudCapacitacion(@Valid @PathVariable Integer idSolicitudCapacitacion) {
        try {
            if (Objects.isNull(idSolicitudCapacitacion)) {
                return ResponseEntity.noContent().build();
            }
            SolicitudCapacitacionEntity solicitudCapacitacionEntity = solicitudCapacitacionService.encontrarSolicitudCapacitacion(idSolicitudCapacitacion);
            return ResponseEntity.ok(solicitudCapacitacionEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> crearSolicitudCapacitacion(@Valid @RequestBody SolicitudCapacitacionEntity solicitudCapacitacionEntity) {
        try {
            if (Objects.isNull(solicitudCapacitacionEntity)) {
                return ResponseEntity.badRequest().body("El cuerpo de la solicitud no puede estar vacío.");
            }
            SolicitudCapacitacionEntity nuevaSolicitudCapacitacion = solicitudCapacitacionService.crearSolicitudCapacitacion(solicitudCapacitacionEntity);
            return ResponseEntity.ok(nuevaSolicitudCapacitacion);
        } catch (ValidationException ve) {
            return ResponseEntity.unprocessableEntity().body(ve.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> modificarSolicitudCapacitacion(@Valid @RequestBody SolicitudCapacitacionEntity solicitudCapacitacionEntity) {
        try {
            if (Objects.isNull(solicitudCapacitacionEntity)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(solicitudCapacitacionService.modificarSolicitudCapacitacion(solicitudCapacitacionEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @DeleteMapping("/{idSolicitudCapacitacion}")
    @PreAuthorize("hasRole('DIRECTOR_SALUD')")
    public ResponseEntity<String> eliminarSolicitudCapacitacion(@Valid @PathVariable Integer idSolicitudCapacitacion) {
        try {
            solicitudCapacitacionService.eliminarSolicitudCapacitacion(idSolicitudCapacitacion);
            return ResponseEntity.ok("SolicitudCapacitacionEntity eliminado correctamente");
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
