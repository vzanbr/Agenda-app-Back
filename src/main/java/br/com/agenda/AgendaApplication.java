package br.com.agenda;

import br.com.agenda.model.entity.Contato;
import br.com.agenda.model.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgendaApplication {

    @Bean
    public CommandLineRunner commandLineRunner
            (@Autowired ContatoRepository repository) {
        return args -> {
            Contato contato = new Contato();
            contato.setNome("Bia");
            contato.setEmail("B123@gamil.com");
            contato.setFavorito(false);
            repository.save(contato);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(AgendaApplication.class, args);
    }

}
