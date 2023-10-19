package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.CapacitacionEntity;
import edu.umg.plataforma.repository.CapacitacionRepository;
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
public class CapacitacionServiceImpl implements CapacitacionService {
    private final CapacitacionRepository capacitacionRepository;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public List<CapacitacionEntity> listadoCapacitaciones() {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return capacitacionRepository.findAll();
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para ver el listado de capacitaciones");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CapacitacionEntity encontrarCapacitacion(Integer idCapacitacion) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return capacitacionRepository.findById(idCapacitacion).orElseThrow(null);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para buscar una capacitacion");
        }
    }

    @Override
    @Transactional
    public CapacitacionEntity crearCapacitacion(CapacitacionEntity capacitacionEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return capacitacionRepository.save(capacitacionEntity);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para crear una capacitacion");
        }
    }

    @Override
    @Transactional
    public CapacitacionEntity modificarCapacitacion(CapacitacionEntity capacitacionEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return capacitacionRepository.save(capacitacionEntity);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para modificar una capacitacion");
        }
    }

    @Override
    @Transactional
    public void eliminarCapacitacion(Integer idCapacitacion) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            Optional<CapacitacionEntity> capacitacionEntityExistente = capacitacionRepository.findById(idCapacitacion);
            if (capacitacionEntityExistente.isPresent()) {
                capacitacionRepository.delete(capacitacionEntityExistente.get());
            } else {
                throw new NoSuchElementException("La capacitacion no se encuentra");
            }
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para eliminar una capacitacion");
        }
    }
}
