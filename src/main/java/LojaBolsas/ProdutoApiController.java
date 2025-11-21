package LojaBolsas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController // Indica que esta classe responde com DADOS (JSON), não HTML
@RequestMapping("/api")
public class ProdutoApiController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public List<Produto> listarProdutosApi() {
        // Pega os dados lidos do CSV e envia para quem pedir (o JavaScript)
        return produtoService.carregarProdutosDoCsv();
    }
}