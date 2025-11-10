import java.time.LocalDate;

/**
 * Classe principal para testar o fluxo da Loja de Bolsas.
 * Compile e execute este arquivo.
 */
public class Sistema {

    public static void main(String[] args) {

        System.out.println("Iniciando simulação da Loja de Bolsas...");
        System.out.println("=========================================");

        // --- 1. Cadastrando Produtos (usando seu Produto.java) ---
        System.out.println("\n--- 1. Cadastrando Produtos ---");

        Produto prod1 = new Produto(1, "Bolsa Tote Clássica", "Bolsa de couro legítimo", 349.90f, 50, TipoProduto.BolsaFeminina);
        Produto prod2 = new Produto(2, "Mochila Executiva", "Mochila para notebook, resistente à água", 599.00f, 30, TipoProduto.MochilaMasculina);
        Produto prod3 = new Produto(3, "Carteira Slim", "Carteira de couro, cor vermelha", 120.00f, 100, TipoProduto.CarteiraFeminina);
        // Produto para teste de recusa (> 5000)
        Produto prod4 = new Produto(4, "Mala de Luxo", "Mala de viagem em couro de crocodilo", 6000.00f, 5, TipoProduto.BolsaMasculina);

        System.out.println("Produto cadastrado: " + prod1.getNome() + " (Estoque: " + prod1.getEstoque() + ")");
        System.out.println("Produto cadastrado: " + prod2.getNome() + " (Estoque: " + prod2.getEstoque() + ")");
        System.out.println("Produto cadastrado: " + prod4.getNome() + " (Estoque: " + prod4.getEstoque() + ")");


        // --- 2. Cadastrando Cliente (usando o ClientePF.java SIMPLIFICADO) ---
        System.out.println("\n--- 2. Cadastrando Cliente ---");

        Endereco end1 = new Endereco("Rua das Flores", "123", "Centro", "São Paulo", "SP", "01010-010", "Apto 101");

        // Agora isso vai funcionar sem a biblioteca Caelum Stella
        ClientePF cliente1 = new ClientePF(
                101, "Ana Silva", "11999998888", "ana@email.com", "ana.silva", "senha123",
                end1, "111.222.333-44", "12.345.678-9", LocalDate.of(1990, 5, 15)
        );
        System.out.println("Cliente criado: " + cliente1.getNome());
        System.out.println("CPF (sem formatação): " + cliente1.getCpfFormatado());


        // --- 3. SIMULAÇÃO 1: Pedido com PIX (Aprovado) ---
        System.out.println("\n--- 3. Simulação: Pedido com PIX (Aprovado) ---");

        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(1);
        pedido1.setDataPedido(LocalDate.now());
        pedido1.setIdCliente(cliente1.getIdCliente());
        pedido1.setStatusPedido(StatusPedido.PENDENTE);

        System.out.println("Pedido #" + pedido1.getIdPedido() + " criado para " + cliente1.getNome() + ".");

        // Adicionando Itens (usando Pedido.adicionarItem)
        pedido1.adicionarItem(prod1, 1); // 1 Bolsa Tote
        pedido1.adicionarItem(prod3, 2); // 2 Carteiras Slim
        System.out.println("Itens adicionados: 1x " + prod1.getNome() + ", 2x " + prod3.getNome());

        // Calcular Total (usando Pedido.calcularTotal)
        double total1 = pedido1.calcularTotal();
        System.out.println("Valor total do Pedido: R$ " + String.format("%.2f", total1));

        // Processar Pagamento (usando PagamentoPix.java)
        IPagamento metodoPix = new PagamentoPix();
        pedido1.setMetodoPagamento(metodoPix);

        System.out.println("Confirmando pedido...");
        boolean sucessoPix = pedido1.confirmarPedido();

        System.out.println("Resultado da confirmação: " + (sucessoPix ? "SUCESSO" : "FALHA"));
        System.out.println("Status do Pedido: " + pedido1.getStatusPedido()); // Deve ser PROCESSANDO
        System.out.println("Status do Pagamento: " + metodoPix.getStatus()); // Deve ser APROVADO


        // --- 4. SIMULAÇÃO 2: Pedido com Cartão (Recusado por Valor) ---
        System.out.println("\n--- 4. Simulação: Pedido com Cartão (Recusado) ---");
        System.out.println("    (Regra: PagamentoCartaoCredito recusa valores > 5000.00)");

        Pedido pedido2 = new Pedido();
        pedido2.setIdPedido(2);
        pedido2.setDataPedido(LocalDate.now());
        pedido2.setIdCliente(cliente1.getIdCliente());
        pedido2.setStatusPedido(StatusPedido.PENDENTE);

        System.out.println("Pedido #" + pedido2.getIdPedido() + " criado para " + cliente1.getNome() + ".");

        // Adicionando item caro (1x Mala de Luxo de 6000.00)
        pedido2.adicionarItem(prod4, 1);
        System.out.println("Itens adicionados: 1x " + prod4.getNome());

        // Calcular Total
        double total2 = pedido2.calcularTotal();
        System.out.println("Valor total do Pedido: R$ " + String.format("%.2f", total2));

        // Processar Pagamento (usando PagamentoCartaoCredito.java)
        IPagamento metodoCartao = new PagamentoCartaoCredito();
        // Definindo dados do cartão (se sua classe precisar)
        // (Sua classe PagamentoCartaoCredito não parece usar esses dados no construtor,
        // mas se precisasse, seria aqui)
        // metodoCartao.setNumeroCartao("1234...");

        pedido2.setMetodoPagamento(metodoCartao);

        System.out.println("Confirmando pedido...");
        boolean sucessoCartao = pedido2.confirmarPedido();

        System.out.println("Resultado da confirmação: " + (sucessoCartao ? "SUCESSO" : "FALHA"));
        System.out.println("Status do Pedido: " + pedido2.getStatusPedido()); // Deve ser PENDENTE
        System.out.println("Status do Pagamento: " + metodoCartao.getStatus()); // Deve ser RECUSADO

        System.out.println("\n=========================================");
        System.out.println("Simulação concluída.");
    }
}
