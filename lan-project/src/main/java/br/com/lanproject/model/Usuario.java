package br.com.lanproject.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UsuarioPerfil> perfis = new ArrayList<>();

    @Deprecated
    public Usuario() {}

    public Usuario(@NotBlank String nome,
                   @NotBlank @Email String email,
                   @NotBlank @Size(min = 6) String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
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

    public List<UsuarioPerfil> getPerfis() {
        return perfis;
    }
}
