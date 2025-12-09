package systemagenda.com.dev.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import systemagenda.com.dev.entity.Usuario;
import systemagenda.com.dev.service.UsuarioService;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioForm", new Usuario());
        return "usuarios/lista";
    }

    @PostMapping("/usuarios")
    public String salvarUsuario(@Valid @ModelAttribute("usuarioForm") Usuario usuario,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            List<Usuario> usuarios = usuarioService.listarTodos();
            model.addAttribute("usuarios", usuarios);
            return "usuarios/lista";
        }

        // Aqui futuramente você pode criptografar a senha antes de salvar
        usuarioService.salvar(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/{id}/excluir")
    public String excluirUsuario(@PathVariable UUID id) {
        usuarioService.excluir(id);
        return "redirect:/usuarios";
    }
}