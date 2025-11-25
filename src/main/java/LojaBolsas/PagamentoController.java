package LojaBolsas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @PostMapping("/processar")
    public ResponseEntity<?> realizarPagamento(@RequestBody PagamentoDTO dto) {
        IPagamento pagamento; // Usamos a interface para polimorfismo

        // 1. Escolhe a estratégia baseada no tipo
        switch (dto.tipoMetodo.toUpperCase()) {
            case "PIX":
                pagamento = new PagamentoPix();
                break;
            case "BOLETO":
                PagamentoBoleto boleto = new PagamentoBoleto();
                // Poderia gerar código de barras aqui
                boleto.setCodigoBarras("1234.5678.9012.3456");
                pagamento = boleto;
                break;
            case "CARTAO":
                PagamentoCartaoCredito cartao = new PagamentoCartaoCredito();
                cartao.setNumeroCartao(dto.numeroCartao);
                cartao.setNomeTitular(dto.nomeTitular);
                // Converte String para LocalDate
                if(dto.dataValidade != null) {
                    cartao.setDataValidade(LocalDate.parse(dto.dataValidade));
                }
                pagamento = cartao;
                break;
            default:
                return ResponseEntity.badRequest().body("Método de pagamento inválido.");
        }

        // 2. Processa o pagamento usando suas classes
        boolean aprovado = pagamento.processarPagamento(dto.valorTotal);

        // 3. Retorna o resultado para o site
        if (aprovado) {
            return ResponseEntity.ok("Pagamento APROVADO via " + dto.tipoMetodo);
        } else {
            // Exemplo: Cartão recusado se valor > 5000
            return ResponseEntity.status(402).body("Pagamento RECUSADO. Verifique o limite ou dados.");
        }
    }
}