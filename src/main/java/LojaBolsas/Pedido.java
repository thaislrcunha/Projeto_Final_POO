package LojaBolsas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int idPedido;
    private LocalDate dataPedido;
    private StatusPedido statusPedido;
    private int idCliente;
    private IPagamento metodoPagamento;

    private List<Item> itens;

    //Construtores

    public Pedido(int idPedido, LocalDate dataPedido, StatusPedido statusPedido, int idCliente, IPagamento metodoPagamento) {
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.statusPedido = statusPedido;
        this.idCliente = idCliente;
        this.metodoPagamento = metodoPagamento;
        this.itens = new ArrayList<>();
    }

    public Pedido () {
        this.itens = new ArrayList<>();
    }

    //Getters and Setters

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public IPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(IPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    //Métodos Especiais

    public double calcularTotal () {
        double total = 0.0;

        for(Item item : this.itens) {
            total += item.calcularSubTotal();
        }

        return total;

    }

    public void atualizarStatus (StatusPedido novoStatus) {
        this.statusPedido = novoStatus;
    }

    public void adicionarItem (Produto produto, int quantidade) {
        Item novoItem = new Item(produto.getIdProduto(), quantidade, produto.getPreco());
        this.itens.add(novoItem);
    }

    public boolean removerItem (Produto produto) {
        Item itemParaRemover = null;

        for (Item item : this.itens) {
            if (item.getIdProduto() == produto.getIdProduto()) {
                itemParaRemover = item;
                break;
            }
        }

        if (itemParaRemover != null) {
            return this.itens.remove(itemParaRemover);
        }

        return false;
    }

    public double aplicarDesconto (double percentualDesconto) {
        double total = this.calcularTotal();

        if (percentualDesconto <= 0 || percentualDesconto > 100) {
            return total;
        }

        double multiplicador = 1.0 - (percentualDesconto / 100.0);

        return total * multiplicador;

    }

    public boolean confirmarPedido () {
        if (this.itens.isEmpty()) {
            return false;
        }

        double totalPagar = this.calcularTotal();

        boolean pagamentoSucesso = this.metodoPagamento.processarPagamento(totalPagar);

        if (pagamentoSucesso) {
            this.atualizarStatus(StatusPedido.PROCESSANDO);
        } else {
            this.atualizarStatus(StatusPedido.PENDENTE);
        }

        return pagamentoSucesso;
    }

}
