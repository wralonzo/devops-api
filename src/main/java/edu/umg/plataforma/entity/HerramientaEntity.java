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
@Table(name = "herramientas")
public class HerramientaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_herramienta", nullable = false)
    private Integer idHerramienta;

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
