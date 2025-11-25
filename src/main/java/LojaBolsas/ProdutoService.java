package LojaBolsas;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct; // Se der erro, use javax.annotation.PostConstruct
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProdutoService {

    // Memória do estoque (carregada apenas uma vez)
    private List<Produto> estoqueGlobal = new ArrayList<>();

    @PostConstruct
    public void inicializarEstoque() {
        this.estoqueGlobal = carregarProdutosDoCsv();
        System.out.println("✅ Estoque carregado. Total de produtos: " + estoqueGlobal.size());
    }

    public List<Produto> listarProdutosAtuais() {
        return this.estoqueGlobal;
    }

    public Produto buscarPorId(int id) {
        return estoqueGlobal.stream()
                .filter(p -> p.getIdProduto() == id)
                .findFirst()
                .orElse(null);
    }

    public void atualizarEstoque(int idProduto, int qtdComprada) {
        Produto produto = buscarPorId(idProduto);
        if (produto != null) {
            int novoEstoque = Math.max(0, produto.getEstoque() - qtdComprada);
            produto.setEstoque(novoEstoque);
            System.out.println("📉 Estoque atualizado: " + produto.getNome() + " | Restam: " + novoEstoque);
        }
    }

    private List<Produto> carregarProdutosDoCsv() {
        List<Produto> produtos = new ArrayList<>();
        String arquivoCsv = "produtos.csv";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(arquivoCsv),
                StandardCharsets.UTF_8))) {

            // Pula a linha de cabeçalho
            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (dados.length >= 7) {
                    try {
                        int id = Integer.parseInt(limparTexto(dados[0]));
                        String nome = limparTexto(dados[1]);
                        String descricao = limparTexto(dados[2]);
                        float preco = Float.parseFloat(limparTexto(dados[3]));
                        int estoque = Integer.parseInt(limparTexto(dados[4]));
                        TipoProduto tipo = TipoProduto.valueOf(limparTexto(dados[5]));
                        String urlImagem = limparTexto(dados[6]);

                        List<String> cores = new ArrayList<>();
                        for (int i = 7; i < dados.length; i++) {
                            String corLimpa = limparTexto(dados[i]);
                            if (!corLimpa.isEmpty()) {
                                cores.add(corLimpa);
                            }
                        }

                        produtos.add(new Produto(id, nome, descricao, preco, estoque, tipo, urlImagem, cores));

                    } catch (Exception e) {
                        System.err.println("⚠️ Erro ao processar linha: " + linha + " -> " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Erro fatal ao ler 'produtos.csv': " + e.getMessage());
            e.printStackTrace();
        }
        return produtos;
    }

    //Metodo auxiliar para remover aspas e espaços extras
    private String limparTexto(String texto) {
        if (texto == null) return "";
        texto = texto.trim();
        if (texto.startsWith("\"") && texto.endsWith("\"")) {
            return texto.substring(1, texto.length() - 1);
        }
        return texto;
    }
}