package LojaBolsas;

public class PagamentoDTO {
    public String tipoMetodo; // "PIX", "BOLETO", "CARTAO"
    public double valorTotal;

    // Dados específicos do cartão (opcionais)
    public String numeroCartao;
    public String nomeTitular;
    public String dataValidade; // Vamos receber como String "YYYY-MM-DD"
}