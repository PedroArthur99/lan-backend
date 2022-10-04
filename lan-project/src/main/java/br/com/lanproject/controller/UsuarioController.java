package br.com.lanproject.controller;

import br.com.lanproject.controller.dto.UsuarioDTO;
import br.com.lanproject.model.Usuario;
import br.com.lanproject.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void cadastrar(@RequestBody @Valid UsuarioDTO usuarioDto) {
        this.repository.save(usuarioDto.toModel(encoder));
    }
}