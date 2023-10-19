package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.HerramientaEntity;
import edu.umg.plataforma.repository.HerramientaRepository;
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
public class HerramientaServiceImpl implements HerramientaService {
    private final HerramientaRepository herramientaRepository;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public List<HerramientaEntity> listadoHerramientas() {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return herramientaRepository.findAll();
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para ver el listado de herramientas");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public HerramientaEntity encontrarHerramienta(Integer idHerramienta) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return herramientaRepository.findById(idHerramienta).orElseThrow(null);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para buscar una herramienta");
        }
    }

    @Override
    @Transactional
    public HerramientaEntity crearHerramienta(HerramientaEntity herramientaEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return herramientaRepository.save(herramientaEntity);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para crear una herramienta");
        }
    }

    @Override
    @Transactional
    public HerramientaEntity modificarHerramienta(HerramientaEntity herramientaEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return herramientaRepository.save(herramientaEntity);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para modificar una herramienta");
        }
    }

    @Transactional
    @Override
    public void eliminarHerramienta(Integer idHerramienta) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            Optional<HerramientaEntity> herramientaEntityExistente = herramientaRepository.findById(idHerramienta);
            if (herramientaEntityExistente.isPresent()) {
                herramientaRepository.delete(herramientaEntityExistente.get());
            } else {
                throw new NoSuchElementException("La herramienta no se encuentra");
            }
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para eliminar una herramienta");
        }
    }
}
