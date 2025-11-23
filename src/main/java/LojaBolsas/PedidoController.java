package LojaBolsas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private ProdutoService produtoService; // Usa a memória carregada do CSV

    // 1. Receber Novo Pedido
    @PostMapping("/novo")
    public ResponseEntity<String> criarPedido(@RequestBody DadosPedido dados) {
        try {
            Pedido pedido = new Pedido();
            pedido.setIdCliente(dados.idCliente);
            pedido.setDataPedido(LocalDate.now());
            pedido.setStatusPedido(StatusPedido.PENDENTE);

            // A. Validação de Estoque (Antes de qualquer coisa)
            for (DadosPedido.ItemDTO itemJson : dados.itens) {
                Produto produtoReal = produtoService.buscarPorId(itemJson.idProduto);

                if (produtoReal == null) {
                    return ResponseEntity.badRequest().body("❌ Erro: Produto ID " + itemJson.idProduto + " não encontrado.");
                }

                if (itemJson.quantidade > produtoReal.getEstoque()) {
                    return ResponseEntity.badRequest().body(
                            "❌ Erro: O produto '" + produtoReal.getNome() +
                                    "' tem apenas " + produtoReal.getEstoque() + " unidades disponíveis."
                    );
                }

                // Adiciona ao pedido
                pedido.adicionarItem(produtoReal, itemJson.quantidade);
            }

            // B. Configuração do Pagamento
            IPagamento estrategiaPagamento = switch (dados.metodoPagamento.toUpperCase()) {
                case "PIX" -> new PagamentoPix();
                case "BOLETO" -> {
                    PagamentoBoleto b = new PagamentoBoleto();
                    b.setCodigoBarras("1234.5678.9012.0000");
                    yield b;
                }
                case "CARTAO" -> {
                    PagamentoCartaoCredito c = new PagamentoCartaoCredito();
                    c.setNomeTitular(dados.nomeTitular);
                    c.setNumeroCartao(dados.numeroCartao);
                    if (dados.dataValidade != null) c.setDataValidade(LocalDate.parse(dados.dataValidade));
                    yield c;
                }
                default -> throw new IllegalArgumentException("Método inválido: " + dados.metodoPagamento);
            };

            pedido.setMetodoPagamento(estrategiaPagamento);

            // C. Tentativa de Pagamento
            boolean aprovado = pedido.confirmarPedido();

            if (aprovado) {
                // D. SUCESSO: Baixa o estoque na memória
                for (DadosPedido.ItemDTO itemJson : dados.itens) {
                    produtoService.atualizarEstoque(itemJson.idProduto, itemJson.quantidade);
                }

                return ResponseEntity.ok("✅ Pedido Confirmado! Total: R$ " + String.format("%.2f", pedido.calcularTotal()));
            } else {
                return ResponseEntity.status(402).body("❌ Pagamento Recusado (Saldo insuficiente ou erro no cartão).");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar: " + e.getMessage());
        }
    }

    // 2. Listar Produtos (Para o Site)
    @GetMapping("/produtos")
    public List<Produto> listarProdutos() {
        // Retorna a lista da memória do Service (com estoque atualizado)
        return produtoService.listarProdutosAtuais();
    }
}