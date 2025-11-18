import java.time.LocalDate;

// import br.com.caelum.stella.format.CPFFormatter; // REMOVIDO
// import br.com.caelum.stella.validation.CPFValidator; // REMOVIDO
// import br.com.caelum.stella.validation.InvalidStateException; // REMOVIDO

public class ClientePF extends Cliente {
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;

    public ClientePF(int idCliente, String nome, String telefone, String email, String userName,
                     String password, Endereco endereco, String cpf, String rg, LocalDate dataNascimento) {

        super(idCliente, nome, telefone, email, userName, password, endereco);

        // A VALIDAÇÃO FOI REMOVIDA PARA O TESTE
        // setCpf(cpf);
        this.cpf = cpf; // Apenas atribui o valor

        setRg(rg);
        this.dataNascimento = dataNascimento;
    }

    // --- MÉTODOS SIMPLIFICADOS ---

    public String getCpf() {
        return cpf;
    }

    public String getCpfFormatado() {
        // Retorna o CPF puro, já que não temos o formatador
        return cpf;
    }

    public void setCpf(String cpf) {
        // Validação removida para o teste
        this.cpf = cpf;
    }

    // O método isCpfValido() foi removido pois não é mais necessário

    // --- Getters/Setters restantes (sem alteração) ---

    public String getRg(){
        return rg;
    } public void setRg(String rg){
        this.rg = rg;}

    public LocalDate getDataNascimento(){
        return dataNascimento;
    } public void setDataNascimento(LocalDate dataNascimento){
        this.dataNascimento = dataNascimento;}
}