package br.com.agenda_facil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class ContatoController {
    @GetMapping ("/")
    public String listar () {
        return "contatos";
    }

    @GetMapping ("/contatos/novo")
    public String novo(){
        return "formulario";
    }

}
