package edu.umg.plataforma.entity;

import edu.umg.plataforma.util.MetodoPagoEnum;
import edu.umg.plataforma.util.SuscripcionEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "suscripciones")
public class SuscripcionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suscripcion", nullable = false)
    private Integer idSuscripcion;

    @Enumerated(EnumType.STRING)
    private SuscripcionEnum suscripcion;

    @Column(name = "fecha_inicio", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaIncio;

    @Column(name = "fecha_fin", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private MetodoPagoEnum metodoPago;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @ManyToOne()
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;
}
