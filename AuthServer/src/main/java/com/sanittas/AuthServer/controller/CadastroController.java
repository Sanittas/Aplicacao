package com.sanittas.AuthServer.controller;

import com.sanittas.AuthServer.domain.Usuario;
import com.sanittas.AuthServer.services.EmpresaService;
import com.sanittas.AuthServer.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/cadastrar")
public class CadastroController {
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;

    @PostMapping("/empresa/")
    public ResponseEntity<LoginDtoResponse> cadastroUsuario(@RequestBody @Valid EmpresaCriacaoDto empresa) {
        try {
            log.info("Recebida solicitação para cadastrar uma nova empresa: {}", empresa.razaoSocial());
            empresaService.cadastrar(empresa);
            log.info("Empresa cadastrada com sucesso: {}", empresa.razaoSocial());
            return ResponseEntity.status(201).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao cadastrar empresa: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @PostMapping("/usuario/")
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid UsuarioCriacaoDto dados) {
        try {
            Usuario response = usuarioService.cadastrar(dados);
            return ResponseEntity.status(201).body(response); // Criado com sucesso
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

}
