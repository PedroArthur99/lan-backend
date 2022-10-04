package br.com.lanproject.controller;

import br.com.lanproject.controller.dto.TokenRequestDTO;
import br.com.lanproject.controller.dto.TokenResponseDTO;
import br.com.lanproject.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenResponseDTO> autenticar(@RequestBody @Valid TokenRequestDTO tokenRequestDto) {
        UsernamePasswordAuthenticationToken dadosLogin = tokenRequestDto.converter();
        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenResponseDTO(token, "Bearer"));
        }
        catch(AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
