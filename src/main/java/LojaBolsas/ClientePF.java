package LojaBolsas;

import java.time.LocalDate;
public class ClientePF extends Cliente {
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;

    public ClientePF(int idCliente, String nome, String telefone, String email, String userName,
                     String password, Endereco endereco, String cpf, String rg, LocalDate dataNascimento) {

        super(idCliente, nome, telefone, email, userName, password, endereco);

        this.cpf = cpf;

        setRg(rg);
        this.dataNascimento = dataNascimento;
    }
    //Getters/Setters
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg(){
        return rg;
    } public void setRg(String rg){
        this.rg = rg;}

    public LocalDate getDataNascimento(){
        return dataNascimento;
    } public void setDataNascimento(LocalDate dataNascimento){
        this.dataNascimento = dataNascimento;}
}
//A validação do cpf é removida