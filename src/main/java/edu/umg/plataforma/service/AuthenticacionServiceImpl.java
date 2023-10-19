package edu.umg.plataforma.service;

import edu.umg.plataforma.auth.RespuestaAuthenticacion;
import edu.umg.plataforma.auth.SolicitudAuthenticacion;
import edu.umg.plataforma.auth.SolicitudRegistro;
import edu.umg.plataforma.entity.UsuarioEntity;
import edu.umg.plataforma.repository.UsuarioRepository;
import edu.umg.plataforma.util.RolEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticacionServiceImpl implements AuthenticacionService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtServiceImpl;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public RespuestaAuthenticacion regsitro(SolicitudRegistro solicitudRegistro) {
        var usuario = UsuarioEntity.builder()
                .nombre(solicitudRegistro.getNombre())
                .apellido(solicitudRegistro.getApellido())
                .email(solicitudRegistro.getEmail())
                .contrasenia(passwordEncoder.encode(solicitudRegistro.getContrasenia()))
                .rol(RolEnum.CLIENTE)
                .build();
        usuarioRepository.save(usuario);
        var jwtToken = jwtServiceImpl.generateToken(usuario);
        return RespuestaAuthenticacion.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    @Transactional
    public RespuestaAuthenticacion authenticar(SolicitudAuthenticacion solicitudAuthenticacion) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        solicitudAuthenticacion.getEmail(),
                        solicitudAuthenticacion.getContrasenia()
                )
        );
        var usuario = usuarioRepository.findByEmail(solicitudAuthenticacion.getEmail()).orElseThrow();
        var jwtToken = jwtServiceImpl.generateToken(usuario);
        return RespuestaAuthenticacion.builder()
                .token(jwtToken)
                .build();
    }
}
