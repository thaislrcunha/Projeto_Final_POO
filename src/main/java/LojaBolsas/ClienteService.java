package LojaBolsas;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    // Banco de dados em memória
    private List<Cliente> clientesCadastrados = new ArrayList<>();
    private int contadorId = 1;

    public Cliente cadastrar(ClienteDTO dto) {
        // 1. Cria o Endereço (comum a todos)
        Endereco endereco = new Endereco(
                dto.logradouro, dto.numero, dto.bairro,
                dto.cidade, dto.estado, dto.cep, dto.complemento
        );

        Cliente novoCliente;

        // 2. Decide se cria PF ou PJ
        if ("PF".equalsIgnoreCase(dto.tipo)) {
            novoCliente = new ClientePF(
                    contadorId++, dto.nome, dto.telefone, dto.email,
                    dto.username, dto.password, endereco,
                    dto.cpf, dto.rg, LocalDate.parse(dto.dataNascimento)
            );
        } else {
            novoCliente = new ClientePJ(
                    contadorId++, dto.nome, dto.telefone, dto.email,
                    dto.username, dto.password, endereco,
                    dto.cnpj, dto.razaoSocial, dto.inscricaoEstadual,
                    LocalDate.parse(dto.dataAbertura)
            );
        }

        // 3. Salva na lista
        clientesCadastrados.add(novoCliente);
        System.out.println("Cliente cadastrado: " + novoCliente.getNome());
        return novoCliente;
    }

    public Cliente autenticar(String username, String password) {
        // Procura na lista alguém com esse usuário e senha
        Optional<Cliente> cliente = clientesCadastrados.stream()
                .filter(c -> c.getUserName().equals(username) && c.getPassword().equals(password))
                .findFirst();

        return cliente.orElse(null); // Retorna null se não achar
    }
}