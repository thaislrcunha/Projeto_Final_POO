package LojaBolsas;

public class PagamentoPix implements IPagamento {

    private StatusPagamento status;

    //Construtor
    public PagamentoPix() {
        this.status = StatusPagamento.PENDENTE;
    }

    @Override
    public boolean processarPagamento(double precoTotal) {
        System.out.println("Iniciando processamento de pagamento Pix no valor de R$" + String.format("%.2f", precoTotal));

        if (this.status != StatusPagamento.PENDENTE) {
            System.out.println("Pagamento não pode ser processado, pois o status atual é: " + this.status);
            return false;
        }

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

    @Override
    public StatusPagamento getStatus() {
        return this.status;
    }
}