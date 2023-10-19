package edu.umg.plataforma.entity;

import edu.umg.plataforma.util.EstadoSolicitudCapacitacionEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "solicitud_capacitaciones")
public class SolicitudCapacitacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud_capacitacion", nullable = false)
    private Integer idSolicitudCapacitacion;

    @Column(name = "nombre_empresa", nullable = false)
    private String nombreEmpresa;

    @Column(name = "recibo_pago", nullable = false)
    private String reciboPago;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitudCapacitacionEnum estado;
}
