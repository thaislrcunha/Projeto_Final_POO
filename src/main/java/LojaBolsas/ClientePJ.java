package LojaBolsas;

import java.time.LocalDate;

public class ClientePJ extends Cliente {
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private LocalDate dataAbertura;


    public ClientePJ(int idCliente, String nome, String telefone, String email, String userName,
                     String password,  Endereco endereco, String cnpj, String razaoSocial,
                     String inscricaoEstadual, LocalDate dataAbertura) {

        super(idCliente, nome, telefone, email, userName, password, endereco);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.inscricaoEstadual = inscricaoEstadual;
        this.dataAbertura = dataAbertura;
    }

    //validar cnpj -> verificar


    // getters/setters
    public String getCNPJ(){
    return this.cnpj;
    }public void setCNPJ(String cnpj){
        this.cnpj = cnpj;
    }

    public String getRazaoSocial(){
        return this.razaoSocial;
    } public void setRazaoSocial(String razaoSocial){
        this.razaoSocial = razaoSocial;}

    public String getInscricaoEstadual(){
        return this.inscricaoEstadual;
    } public void setInscricaoEstadual(String inscricaoEstadual){
        this.inscricaoEstadual = inscricaoEstadual;}

    public LocalDate getDataAbertura(){
        return this.dataAbertura;
    } public void setDataAbertura(LocalDate dataAbertura){
        this.dataAbertura = dataAbertura;
    }
}