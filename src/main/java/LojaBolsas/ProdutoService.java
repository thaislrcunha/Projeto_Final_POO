package LojaBolsas;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    public List<Produto> carregarProdutosDoCsv() {
        List<Produto> produtos = new ArrayList<>();

        // O Spring Boot sabe que 'produtos.csv' está em src/main/resources
        String arquivoCsv = "produtos.csv";

        // Usa try-with-resources para garantir que o leitor seja fechado
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(arquivoCsv),
                StandardCharsets.UTF_8))) {

            // Pula a linha de cabeçalho (id,nome,descricao...)
            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                // Remove aspas extras se existirem e divide por vírgula.
                // Isso pode ser ajustado dependendo da complexidade do seu CSV.
                String[] dados = linha.replace("\"", "").split(",");

                if (dados.length >= 6) {
                    try {
                        int id = Integer.parseInt(dados[0].trim());
                        String nome = dados[1].trim();
                        String descricao = dados[2].trim();
                        // Assume que o preço está na quarta coluna (índice 3)
                        float preco = Float.parseFloat(dados[3].trim());
                        int estoque = Integer.parseInt(dados[4].trim());
                        // O TipoProduto precisa ser um Enum válido
                        TipoProduto tipo = TipoProduto.valueOf(dados[5].trim());

                        Produto produto = new Produto(id, nome, descricao, preco, estoque, tipo);
                        produtos.add(produto);

                    } catch (IllegalArgumentException e) {
                        System.err.println("Erro ao processar linha do CSV: " + linha + " -> " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            // Em caso de erro de leitura (ex: arquivo não encontrado)
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }

        return produtos;
    }
}