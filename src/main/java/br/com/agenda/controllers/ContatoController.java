package br.com.agenda.controllers;

import br.com.agenda.model.entity.Contato;
import br.com.agenda.model.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contato save (@RequestBody Contato contato) {
        return  repository.save(contato);
    }

    @DeleteMapping("{ìd}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Contato> buscarTodos () {
        return repository.findAll();
    }
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Contato> buscarID (@PathVariable Integer id) {
        return repository.findById(id);
    }

    @PatchMapping("{id}/favorito")
    public void favorite(@PathVariable Integer id, @RequestBody Boolean favorito) {
        Optional<Contato> contato = repository.findById(id);
        contato.ifPresent(c -> {
            c.setFavorito(favorito);
            repository.save(c);
        });
    }
}
