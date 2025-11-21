package LojaBolsas;

import java.time.LocalDate;

public class PagamentoCartaoCredito implements IPagamento {

    private String numeroCartao;
    private String nomeTitular;
    private LocalDate dataValidade;
    private StatusPagamento statusPagamento;

    @Override
    public boolean processarPagamento( double valor){
        if(valor<5000.00){
            statusPagamento = StatusPagamento.APROVADO;
            return true;
        } else {
            statusPagamento = StatusPagamento.RECUSADO;
            return false;
        }
    }

    public StatusPagamento getStatus(){
        return statusPagamento;
    }

    //getters/setters
    public String getNumeroCartao(){
        return numeroCartao;
    } public void setNumeroCartao(String numeroCartao){
        this.numeroCartao = numeroCartao;}

    public String getNomeTitular(){
        return nomeTitular;
    } public void setNomeTitular(String nomeTitular){
        this.nomeTitular = nomeTitular;}

    public LocalDate getDataValidade(){
        return dataValidade;
    } public void setDataValidade(LocalDate dataValidade){
        this.dataValidade = dataValidade;
    }
}
