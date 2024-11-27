package com.example.api_agencia_viagem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_agencia_viagem.entity.Destino;
import com.example.api_agencia_viagem.service.DestinoService;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

    @Autowired
    private DestinoService destinoService;

    @PostMapping
    public ResponseEntity<Destino> createDestino(@RequestBody Destino destino) {
        Destino novoDestino = destinoService.cadastrarDestino(destino);
        return new ResponseEntity<>(novoDestino, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Destino>> getAllDestinos() {
        List<Destino> destinos = destinoService.listarDestinos();
        return new ResponseEntity<>(destinos, HttpStatus.OK);
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Destino>> getDestinoByNameOrLocation(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String localizacao
        ) {
        List<Destino> resultados = destinoService.pesquisarDestinos(nome, localizacao);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destino> getDestinoById(@PathVariable Long id) {
        Destino destino = destinoService.obterDetalhes(id);
        if (destino == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(destino, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Destino> rateDestino(@PathVariable Long id, @RequestParam int nota) {
        Destino destino = destinoService.avaliarDestino(id, nota);
        if (destino == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(destino);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestinoById(@PathVariable Long id) {
        destinoService.excluirDestino(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
