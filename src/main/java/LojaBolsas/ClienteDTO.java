package LojaBolsas;

// Esta classe serve apenas para transportar dados do HTML para o Java
public class ClienteDTO {
    // Dados Comuns
    public String tipo; // "PF" ou "PJ"
    public String nome;
    public String telefone;
    public String email;
    public String username;
    public String password;

    // Endereço
    public String logradouro;
    public String numero;
    public String bairro;
    public String cidade;
    public String estado;
    public String cep;
    public String complemento;

    // Específicos PF
    public String cpf;
    public String rg;
    public String dataNascimento; // Recebemos como String (2000-01-01) e convertemos depois

    // Específicos PJ
    public String cnpj;
    public String razaoSocial;
    public String inscricaoEstadual;
    public String dataAbertura;
}