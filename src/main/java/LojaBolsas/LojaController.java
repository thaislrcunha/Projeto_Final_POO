package LojaBolsas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LojaController {

    // 1. Injeta o ProdutoService
    @Autowired
    private ProdutoService produtoService;

    // Mantenha o endpoint /loja ou use a raiz /
    @GetMapping("/loja")
    public String listarProdutos(Model model) {

        // 2. Chama o método que lê o CSV
        List<Produto> listaProdutos = produtoService.carregarProdutosDoCsv();

        // 3. Verifica se a lista não está vazia e loga
        if (listaProdutos.isEmpty()) {
            System.out.println("Nenhum produto carregado do CSV. Verifique o arquivo.");
        } else {
            System.out.println("Produtos carregados com sucesso do CSV. Total: " + listaProdutos.size());
        }

        // 4. Passa a lista para o HTML (como antes)
        model.addAttribute("produtos", listaProdutos);

        return "pagina-produtos";
    }
}