package com.example.qaRestExample.controller;

import com.example.qaRestExample.model.Filme;
import com.example.qaRestExample.repository.FilmeRepository;
import com.example.qaRestExample.response.ResponseHandler;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping(path = "/filme/{codigo}")
    public ResponseEntity consultarFilmePorId(@PathVariable("codigo") Integer codigo) {
        return filmeRepository.findById(codigo)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/filmes")
    public ResponseEntity<Iterable<Filme>> listarTodosOsFilmes() {
        return ResponseEntity.status(HttpStatus.OK).body(filmeRepository.findAll());
    }

    @PostMapping(path = "/salvar")
    public ResponseEntity criarFilme(@RequestBody Filme filme){
        if(filmeRepository.existsById(filme.getCodigo())){
            return ResponseHandler.generateResponse("Filme já cadastrado", HttpStatus.CONFLICT);
        }

        if (filme.getFaixaEtaria().equals(null) || filme.getFaixaEtaria().isEmpty() || filme.getFaixaEtaria().isBlank()){
            return ResponseHandler.generateResponse("Faixa Etaria e obrigatoria", HttpStatus.CONFLICT);
        }

        var objetoFilme = new Filme();
        objetoFilme.setCodigo(filme.getCodigo());
        objetoFilme.setNome(filme.getNome());
        objetoFilme.setFaixaEtaria(filme.getFaixaEtaria());
        objetoFilme.setSinopse(filme.getSinopse());
        objetoFilme.setGenero(filme.getGenero());

        return ResponseEntity.status(HttpStatus.CREATED).body(filmeRepository.save(objetoFilme));
    }

    @DeleteMapping(path = "/filme/{codigo}")
    public void deletarFilmePorId(@PathVariable("codigo") Integer codigo){
        filmeRepository.findById(codigo)
                .map(record -> {
                    filmeRepository.deleteById(codigo);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/filme/{codigo}")
    public ResponseEntity editarFilmePut(@PathVariable("codigo") Integer codigo, @RequestBody @Validated Filme filme){
        Optional<Filme> filmeOject = filmeRepository.findById(codigo);

        if(!filmeOject.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message: Filme não encontrado");
        }

        var filmeEditado = filmeOject.get();
        filmeEditado.getCodigo();
        filmeEditado.setNome(filme.getNome());
        filmeEditado.setSinopse(filme.getSinopse());
        filmeEditado.setGenero(filme.getGenero());
        filmeEditado.setFaixaEtaria(filme.getFaixaEtaria());
        return ResponseEntity.status(HttpStatus.OK).body(filmeRepository.save(filmeEditado));

    }

    @PatchMapping(path = "/filme/{codigo}")
    public ResponseEntity editarFilmeComPatch(@PathVariable("codigo") Integer codigo, @RequestBody Filme filme){
        Optional<Filme> filmeOject = filmeRepository.findById(codigo);

        if(!filmeOject.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message: Filme não encontrado");
        }

        var filmeEditado = filmeOject.get();
        filmeEditado.getCodigo();

        if(filme.getNome() == null){
            filmeEditado.getNome();
        } else {
            filmeEditado.setNome(filme.getNome());
        }

        if(filme.getGenero() == null){
            filmeEditado.getGenero();
        } else {
            filmeEditado.setGenero(filme.getGenero());
        }

        if(filme.getSinopse() == null){
            filmeEditado.getSinopse();
        } else {
            filmeEditado.setSinopse(filme.getSinopse());
        }

        if(filme.getFaixaEtaria() == null){
            filmeEditado.getFaixaEtaria();
        } else {
            filmeEditado.setFaixaEtaria(filme.getFaixaEtaria());
        }

        filmeEditado.setNome(filme.getNome());
        filmeEditado.setSinopse(filme.getSinopse());
        filmeEditado.setGenero(filme.getGenero());
        filmeEditado.setFaixaEtaria(filme.getFaixaEtaria());
        return ResponseEntity.status(HttpStatus.OK).body(filmeRepository.save(filmeEditado));
    }
}
