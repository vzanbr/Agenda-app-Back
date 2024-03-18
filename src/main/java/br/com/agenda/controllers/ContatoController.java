package br.com.agenda.controllers;

import br.com.agenda.model.entity.Contato;
import br.com.agenda.model.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
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
    public Page<Contato> list(
            @RequestParam(value = "page", defaultValue = "0")   Integer pagina,
            @RequestParam(value = "size", defaultValue = "10")  Integer tamanhoPagina
    ){
        Sort sort = Sort.by(Sort.Direction.ASC, "nome");
        PageRequest pageRequest = PageRequest.of(pagina, tamanhoPagina, sort);
        return repository.findAll(pageRequest);
    }
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Contato> buscarID (@PathVariable Integer id) {
        return repository.findById(id);
    }

    @PatchMapping("{id}/favorito")
    public void favorite( @PathVariable Integer id ){
        Optional<Contato> contato = repository.findById(id);
        contato.ifPresent( c -> {
            boolean favorito = c.getFavorito() == Boolean.TRUE;
            c.setFavorito(!favorito);
            repository.save(c);
        });
    }
    @PutMapping("{id}/foto")
    public byte[] addPhoto(@PathVariable Integer id,
                           @RequestParam("foto") Part arquivo){
        Optional<Contato> contato = repository.findById(id);
        return contato.map( c -> {
            try{
                InputStream is = arquivo.getInputStream();
                byte[] bytes = new byte[(int) arquivo.getSize()];
                IOUtils.readFully(is, bytes);
                c.setFoto(bytes);
                repository.save(c);
                is.close();
                return bytes;
            }catch (IOException e){
                return null;
            }
        }).orElse(null);
    }
}
