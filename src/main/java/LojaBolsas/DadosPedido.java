package LojaBolsas;

import java.util.List;

public class DadosPedido {
    // Dados gerais do pedido
    public int idCliente;
    public String metodoPagamento; // "PIX", "BOLETO", "CARTAO"

    // Dados específicos para Cartão
    public String numeroCartao;
    public String nomeTitular;
    public String dataValidade;

    // Lista simplificada de itens
    public List<ItemDTO> itens;

    // Subclasse para representar { idProduto: 1, quantidade: 2 }
    public static class ItemDTO {
        public int idProduto;
        public int quantidade;
    }
}