package systemagenda.com.dev.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import systemagenda.com.dev.entity.Usuario;
import systemagenda.com.dev.repository.UsuarioRepository;

@Service
public class UsuarioUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String role = "ROLE_" + usuario.getTipo().toUpperCase();
        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getTipo().toUpperCase())
                .build(); 
    }
}
