package edu.umg.plataforma.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "capacitaciones")
public class CapacitacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_capacitacion", nullable = false)
    private Integer idCapacitacion;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "descripcion", nullable = false)
    @Lob
    private String descripcion;

    @Column(name = "contenido", nullable = false)
    @Lob
    private String contenido;
}
