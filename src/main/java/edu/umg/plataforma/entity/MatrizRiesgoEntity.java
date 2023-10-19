package edu.umg.plataforma.entity;

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
@Table(name = "matriz_riesgos")
public class MatrizRiesgoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matriz_riesgo", nullable = false)
    private Integer idMatrizRiesgo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;

    @Column(name = "area_oportunidad", nullable = false)
    private String areaOportunidad;

    @ManyToOne()
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;
}
