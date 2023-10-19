package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.AsignacionHerramientaEntity;
import edu.umg.plataforma.repository.AsignacionHerramientaRepository;
import edu.umg.plataforma.util.RolEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignacionHerramientaServiceImpl implements AsignacionHerramientaService {
    private final AsignacionHerramientaRepository asignacionHerramientaRepository;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionHerramientaEntity> listadoAsignacionesHerramientas() {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return asignacionHerramientaRepository.findAll();
        } else {
            throw new AccessDeniedException("El usuario no tiene permisos para ver la lista de Asignacion de herramientas");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AsignacionHerramientaEntity encontrarAsignacionHerramienta(Integer idAsignacionHerramienta) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return asignacionHerramientaRepository.findById(idAsignacionHerramienta).orElseThrow(null);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para buscar un asignacion de herramienta");
        }
    }

    @Override
    @Transactional
    public AsignacionHerramientaEntity crearAsignacionHerramienta(AsignacionHerramientaEntity asignacionHerramientaEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return asignacionHerramientaRepository.save(asignacionHerramientaEntity);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para crear una asignacion de herramienta");
        }
    }

    @Override
    @Transactional
    public AsignacionHerramientaEntity modificarAsignacionHerramienta(AsignacionHerramientaEntity asignacionHerramientaEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return asignacionHerramientaRepository.save(asignacionHerramientaEntity);
        } else {
            throw new AccessDeniedException("El usuario no tiene permisos para modificar la asignacion de herramientas");
        }
    }

    @Override
    @Transactional
    public void eliminarAsignacionHerramienta(Integer idAsignacionHerramienta) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            Optional<AsignacionHerramientaEntity> asignacionHerramientaEntityExistente = asignacionHerramientaRepository.findById(idAsignacionHerramienta);
            if (asignacionHerramientaEntityExistente.isPresent()) {
                asignacionHerramientaRepository.delete(asignacionHerramientaEntityExistente.get());
            } else {
                throw new NoSuchElementException("La asignacion de herramientas no existe");
            }
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para eliminar la asignacion de herramientas");
        }
    }
}
