package com.example.api_agencia_viagem.service;

import com.example.api_agencia_viagem.dominio.dto.UserDTO;
import com.example.api_agencia_viagem.dominio.entity.UserEntity;
import com.example.api_agencia_viagem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO cadastrarUsuario(UserDTO userDTO) {
        UserEntity userEntity = toEntity(userDTO);
        userEntity = userRepository.save(userEntity);
        return toDTO(userEntity);
    }

    @Override
    public List<UserDTO> listarUsuarios() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO buscarUsuarioPorId(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return toDTO(userEntity);
    }

    @Override
    public UserDTO buscarUsuarioPorEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return toDTO(userEntity);
    }

    @Override
    public UserDTO atualizarUsuario(Long id, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        userEntity.setNome(userDTO.getNome());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setSenha(userDTO.getSenha());

        userEntity = userRepository.save(userEntity);
        return toDTO(userEntity);
    }

    @Override
    public void excluirUsuario(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(UserEntity userEntity) {
        UserDTO dto = new UserDTO();
        dto.setId(userEntity.getId());
        dto.setNome(userEntity.getNome());
        dto.setEmail(userEntity.getEmail());
        return dto;
    }

    private UserEntity toEntity(UserDTO userDTO) {
        UserEntity entity = new UserEntity();
        entity.setId(userDTO.getId());
        entity.setNome(userDTO.getNome());
        entity.setEmail(userDTO.getEmail());
        entity.setSenha(userDTO.getSenha());
        return entity;
    }
}
