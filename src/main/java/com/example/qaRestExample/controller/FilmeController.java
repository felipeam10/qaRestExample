package com.example.qaRestExample.controller;

import com.example.qaRestExample.model.Filme;
import com.example.qaRestExample.repository.FilmeRepository;
import com.example.qaRestExample.response.ResponseHandler;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
