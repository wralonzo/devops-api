package edu.umg.plataforma.service;

import edu.umg.plataforma.entity.MatrizRiesgoEntity;
import edu.umg.plataforma.repository.MatrizRiesgoRepository;
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
public class MatrizRiesgoServiceImpl implements MatrizRiesgoService {
    private final MatrizRiesgoRepository matrizRiesgoRepository;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public List<MatrizRiesgoEntity> listadoMatrizesRiesgo() {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return matrizRiesgoRepository.findAll();
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para ver el listado de matrizes");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MatrizRiesgoEntity encontrarMatrizRiesgo(Integer idMatrizRiesgo) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return matrizRiesgoRepository.findById(idMatrizRiesgo).orElseThrow(null);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para buscar una matriz");
        }
    }

    @Override
    @Transactional
    public MatrizRiesgoEntity crearMatrizRiesgo(MatrizRiesgoEntity matrizRiesgoEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return matrizRiesgoRepository.save(matrizRiesgoEntity);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para crear una matriz");
        }
    }

    @Override
    @Transactional
    public MatrizRiesgoEntity modificarMatrizRiesgo(MatrizRiesgoEntity matrizRiesgoEntity) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            return matrizRiesgoRepository.save(matrizRiesgoEntity);
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para modificar la matriz");
        }
    }

    @Override
    @Transactional
    public void eliminarMatrizRiesgo(Integer idMatrizRiesgo) {
        var jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
        var usuarioLogeado = jwtService.getUsuario(jwtToken);
        if (usuarioLogeado.getRol() == RolEnum.DIRECTOR_SALUD ||
                usuarioLogeado.getRol() == RolEnum.ENCARGADO_CAPACITACION ||
                usuarioLogeado.getRol() == RolEnum.AUXILAR) {
            Optional<MatrizRiesgoEntity> matrizRiesgoExistente = matrizRiesgoRepository.findById(idMatrizRiesgo);
            if (matrizRiesgoExistente.isPresent()) {
                matrizRiesgoRepository.delete(matrizRiesgoExistente.get());
            } else {
                throw new NoSuchElementException("La matriz no se encuentra");
            }
        } else {
            throw new AccessDeniedException("El usuario no tiene permiso para eliminar una matriz");
        }
    }
}
