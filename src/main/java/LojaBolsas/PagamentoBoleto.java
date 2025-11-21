package LojaBolsas;

public class PagamentoBoleto implements IPagamento {
    
    private String codigoBarras;
    private StatusPagamento statusPagamento;

    @Override
    public boolean processarPagamento( double valor){
        statusPagamento = StatusPagamento.APROVADO;
        return true;
    }

    public StatusPagamento getStatus(){
        return statusPagamento;
    }

    public String getCodigoBarras(){
        return codigoBarras;}
    public void setCodigoBarras(String codigoBarras){
        this.codigoBarras = codigoBarras;}
}
