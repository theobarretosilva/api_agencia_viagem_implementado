package com.example.api_agencia_viagem.service;

import com.example.api_agencia_viagem.dominio.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO cadastrarUsuario(UserDTO user);
    List<UserDTO> listarUsuarios();
    UserDTO buscarUsuarioPorId(Long id);
    UserDTO buscarUsuarioPorEmail(String email);
    UserDTO atualizarUsuario(Long id, UserDTO user);
    void excluirUsuario(Long id);
}
