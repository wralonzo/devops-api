package edu.umg.plataforma.entity;

import edu.umg.plataforma.util.EstadoAsignacionHerramientaEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "asignacion_herramientas")
public class AsignacionHerramientaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion_herramienta", nullable = false)
    private Integer idAsignacionHerramienta;

    @ManyToOne()
    @JoinColumn(name = "id_herramienta", nullable = false)
    private HerramientaEntity herramienta;

    @ManyToOne()
    @JoinColumn(name = "id_suscripcion", nullable = false)
    private SuscripcionEntity suscripcion;

    @Column(name = "fecha_asignacion", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAsignacion;

    @Column(name = "fecha_inicio", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoAsignacionHerramientaEnum estado;
}
