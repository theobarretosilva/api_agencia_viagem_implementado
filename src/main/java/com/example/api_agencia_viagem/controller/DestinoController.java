package com.example.api_agencia_viagem.controller;

import com.example.api_agencia_viagem.dominio.entity.DestinoEntity;
import com.example.api_agencia_viagem.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

    @Autowired
    private DestinoRepository destinoRepository;

    @PostMapping
    public ResponseEntity<DestinoEntity> createDestino(@RequestBody DestinoEntity destino) {
        DestinoEntity novoDestino = destinoRepository.save(destino);
        return new ResponseEntity<>(novoDestino, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DestinoEntity>> getAllDestinos() {
        List<DestinoEntity> destinos = destinoRepository.findAll();
        return new ResponseEntity<>(destinos, HttpStatus.OK);
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<DestinoEntity>> getDestinoByNameOrLocation(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String localizacao) {

        List<DestinoEntity> resultados = destinoRepository.findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(nome, localizacao);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinoEntity> getDestinoById(@PathVariable Long id) {
        return destinoRepository.findById(id)
                .map(destino -> new ResponseEntity<>(destino, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<DestinoEntity> rateDestino(@PathVariable Long id, @RequestParam int nota) {
        if (nota < 1 || nota > 10) {
            return ResponseEntity.badRequest().build();
        }

        return destinoRepository.findById(id)
                .map(destino -> {
                    double totalNotas = destino.getMediaAvaliacoes() * destino.getQuantidadeAvaliacoes();
                    totalNotas += nota;
                    destino.setQuantidadeAvaliacoes(destino.getQuantidadeAvaliacoes() + 1);
                    destino.setMediaAvaliacoes(totalNotas / destino.getQuantidadeAvaliacoes());
                    DestinoEntity destinoAtualizado = destinoRepository.save(destino);
                    return new ResponseEntity<>(destinoAtualizado, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 6. Excluir um destino
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestinoById(@PathVariable Long id) {
        if (!destinoRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        destinoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
