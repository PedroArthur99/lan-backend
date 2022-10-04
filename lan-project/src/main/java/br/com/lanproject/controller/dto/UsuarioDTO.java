package br.com.lanproject.controller.dto;

import br.com.lanproject.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank @Size(min = 6)
    private String senha;

    public UsuarioDTO(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario toModel(PasswordEncoder encoder) {
        return new Usuario(this.nome, this.email, encoder.encode(this.senha));
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
