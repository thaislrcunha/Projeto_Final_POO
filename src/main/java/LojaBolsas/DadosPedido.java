package LojaBolsas; // Ajuste para seu pacote

import java.util.List;

public class DadosPedido {
    // Dados gerais do pedido
    public int idCliente;
    public String metodoPagamento; // "PIX", "BOLETO", "CARTAO"

    // Dados específicos para Cartão (opcionais se for Pix)
    public String numeroCartao;
    public String nomeTitular;
    public String dataValidade; // O site envia como String "2025-12-31"

    // Lista simplificada de itens
    public List<ItemDTO> itens;

    // Subclasse para representar { idProduto: 1, quantidade: 2 }
    public static class ItemDTO {
        public int idProduto;
        public int quantidade;
    }
}