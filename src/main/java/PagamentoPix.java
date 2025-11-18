/**
 * Implementação da estratégia de pagamento para Pix.
 * Herda o contrato da interface IPagamento.
 */
public class PagamentoPix implements IPagamento {

    // Atributo para guardar o status atual do pagamento.
    private StatusPagamento status;

    // Atributo genérico solicitado no trabalho.
    // Em um projeto real, teria um nome descritivo como "idTransacao" ou "chavePix".
    private int attribute43;

    /**
     * Construtor da classe.
     * Todo novo pagamento Pix começa com o status PENDENTE.
     */
    public PagamentoPix() {
        this.status = StatusPagamento.PENDENTE;
        // Poderíamos inicializar o attribute43 aqui se necessário.
    }

    /**
     * Implementação do método para processar um pagamento Pix.
     * A lógica aqui simula a validação de um pagamento.
     */
    @Override
    public boolean processarPagamento(double precoTotal) {
        System.out.println("Iniciando processamento de pagamento Pix no valor de R$" + String.format("%.2f", precoTotal));

        // Regra de negócio: Não processar se o status não for PENDENTE.
        if (this.status != StatusPagamento.PENDENTE) {
            System.out.println("Pagamento não pode ser processado, pois o status atual é: " + this.status);
            return false;
        }

        // Simulação de uma regra de negócio: Pagamentos Pix devem ter valor positivo.
        if (precoTotal > 0) {
            this.status = StatusPagamento.APROVADO;
            System.out.println("Pagamento Pix APROVADO.");
            return true;
        } else {
            this.status = StatusPagamento.RECUSADO;
            System.out.println("Pagamento Pix RECUSADO por valor inválido.");
            return false;
        }
    }

    /**
     * Implementação do método para obter o status atual do pagamento.
     */
    @Override
    public StatusPagamento getStatus() {
        return this.status;
    }
}