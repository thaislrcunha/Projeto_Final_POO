package LojaBolsas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LojaController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/loja")
    public String listarProdutos(Model model) {

        // [CORREÇÃO] Use o método que busca da memória, e não o que lê do CSV
        // Antes: produtoService.carregarProdutosDoCsv();
        // Agora:
        List<Produto> listaProdutos = produtoService.listarProdutosAtuais();

        // Verifica se a lista não está vazia e loga
        if (listaProdutos.isEmpty()) {
            System.out.println("Lista de produtos vazia (memória).");
        } else {
            System.out.println("Produtos recuperados da memória. Total: " + listaProdutos.size());
        }

        model.addAttribute("produtos", listaProdutos);

        return "pagina-produtos";
    }
}