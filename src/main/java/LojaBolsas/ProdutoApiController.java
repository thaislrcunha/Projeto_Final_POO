package LojaBolsas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProdutoApiController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public List<Produto> listarProdutosApi() {
        // [CORREÇÃO] Mudamos de carregarProdutosDoCsv() para listarProdutosAtuais()
        // Isso garante que o JavaScript receba o estoque atualizado (com as baixas das vendas)
        return produtoService.listarProdutosAtuais();
    }
}