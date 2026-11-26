package br.com.agenda_facil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agenda_facil.model.Contato;

@Repository //Identifica esta interface como responsável pelo acesso aos dados
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    //O JpaRepository já fornece os métodos necessários para o CRUD:
    
    //SAVE()
}
