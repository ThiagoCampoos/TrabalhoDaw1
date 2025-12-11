package systemagenda.com.dev.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import systemagenda.com.dev.entity.Usuario;
import systemagenda.com.dev.repository.UsuarioRepository;

@Configuration
public class AdminUserSeeder {

    @Bean
    public CommandLineRunner CreateAdminUser(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByEmail("admin@stardepiller.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Admin");
                admin.setTipo("ADMIN");
                admin.setEmail("admin@stardepiller.com");
                admin.setSenha(passwordEncoder.encode("admin123"));
                usuarioRepository.save(admin);
            }
        };
    }
}
