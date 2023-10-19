package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.SolicitudCapacitacionEntity;
import edu.umg.plataforma.repository.SolicitudCapacitacionRepository;
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
public class SolicitudCapacitacionServiceImpl implements SolicitudCapacitacionService {
    private final SolicitudCapacitacionRepository solicitudCapacitacionRepository;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudCapacitacionEntity> listadoSolicitudesCapacitacion() {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return solicitudCapacitacionRepository.findAll();
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para ver el listado de solicitudes de capacitacion");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudCapacitacionEntity encontrarSolicitudCapacitacion(Integer idSolicitudCapacitacion) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return solicitudCapacitacionRepository.findById(idSolicitudCapacitacion).orElseThrow(null);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para buscar una solicitud de capacitacion");
        }
    }

    @Override
    @Transactional
    public SolicitudCapacitacionEntity crearSolicitudCapacitacion(SolicitudCapacitacionEntity solicitudCapacitacion) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return solicitudCapacitacionRepository.save(solicitudCapacitacion);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para crear una solicitud de capacitacion");
        }
    }

    @Override
    @Transactional
    public SolicitudCapacitacionEntity modificarSolicitudCapacitacion(SolicitudCapacitacionEntity solicitudCapacitacion) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return solicitudCapacitacionRepository.save(solicitudCapacitacion);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para modificar una solicitud de capacitacion");
        }
    }

    @Override
    @Transactional
    public void eliminarSolicitudCapacitacion(Integer idSolicitudCapacitacion) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            Optional<SolicitudCapacitacionEntity> solicitudCapacitacionEntityExistente = solicitudCapacitacionRepository.findById(idSolicitudCapacitacion);
            if (solicitudCapacitacionEntityExistente.isPresent()) {
                solicitudCapacitacionRepository.delete(solicitudCapacitacionEntityExistente.get());
            } else {
                throw new NoSuchElementException("La solicitud de capacitacion no se encuentra");
            }
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para eliminar una solicitud de capacitacion");
        }
    }
}
