public interface IPagamento {
    public boolean processarPagamento(double valor);
    StatusPagamento getStatus();
}
