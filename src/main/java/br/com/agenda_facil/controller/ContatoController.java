package br.com.agenda_facil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.agenda_facil.model.Contato;
import br.com.agenda_facil.repository.ContatoRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller 
public class ContatoController {

    private final ContatoRepository contatoRepository;

    public ContatoController(ContatoRepository contatoRepository){
        this.contatoRepository = contatoRepository;
    }

        //_______________________READ listar os contatos 
    @GetMapping ("/") //responde ás requisições GET (quando o navegador abre o endereço)
    public String listar (Model model) {

        //Model é a "caixa" que leva dados do Java até a página HTML 
        model.addAttribute("contatos", contatoRepository.findAll());

        //Envia também a quantidade de contatos para a página 
        model.addAttribute("total", contatoRepository.count());

        //Abre o arquivo templates/ contatos.html (sem a extenção)
        return "contatos";
    }

    //_______________CREATE abrir o formulário vazio 

    @GetMapping ("/contatos/novo") //Abre o formulário para cadastrar um novo contato.
    public String novo(Model model){

        //Envia um objeto vazio para o formulário preencher.
        model.addAttribute("contato", new Contato());

        //Informa que estamos cadastrados um novo contato.
        model.addAttribute("titulo", "Novo contato");
        return "formulario";
    }

    //___________________CREATE/ UPDATE salva os dados de formulário

    @PostMapping("/contatos/salvar") //recebe os dados enviado pelo formulário
    public String salvar
    //Valid executa as validações do Model (@NotBlank, @Size, @Email).
    //ModelAttribute monta o objeto Contato com os campos do formulário.
    (@Valid @ModelAttribute("contato") Contato contato,
    //BindingResult guarda o resultado da validação (deve vir logo após o objeto valido).
    BindingResult resultado, Model model ) {

        //Verifica se os dados enviados passaram pelas validações.
        if (resultado.hasErrors()) {
            //Mantém o título correto: sem ID é cadastro, com ID é edição.
            model.addAttribute("titulo", contato.getId() == null? "Novo contato": "Editar contato");

            //Se houver erro, retorna ao formulário sem salvar.
            return "formulario";
        }
        //Se o ID estiver vazio, cadastra(INSERT).
        //Se o ID existir, atualiza o registro (UPDATE).
         contatoRepository.save(contato);

         //Redireciona para a lista. Isso evita reenviar o formulário ao atualizar a página (F5).
         return "redirect:/";
    }
    
    //__________       UPDATE abrir o formulário preenchido

    @GetMapping("/contatos/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Contato contato = contatoRepository.findById(id)
        .orElseThrow(() ->new  IllegalArgumentException ("Contato não encontrado: " + id));

        model.addAttribute("contato", contato);
        model.addAttribute("titulo", "Editar contato");
        
        return "formulario";
    }
    
    //___________________DELETE excluir um contato

  @PostMapping("/contatos/excluir/{id}")
  public String excluir(@PathVariable Long id) {
      contatoRepository.deleteById(id);
      return "redirect:/";
  }
  
}
