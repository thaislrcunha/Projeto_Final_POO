import java.time.LocalDate;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ClientePF extends Cliente {
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;

    public ClientePF(int idCliente, String nome, String telefone, String email, String userName,
                     String password, Endereco endereco, String cpf, String rg, LocalDate dataNascimento) {
        super(idCliente, nome, telefone, email, userName, password, endereco);
        setCpf(cpf);   // valida e normaliza
        setRg(rg);     // valida simples
        this.dataNascimento = dataNascimento;
    }

    // CPF
    private static String somenteDigitos(String s) {
        return s.replaceAll("\\D", "");
    }
    public String getCpf() {
        return cpf; }

    public String getCpfFormatado() {
        return cpf == null ? null : new CPFFormatter().format(cpf);
    }

    public void setCpf(String cpf) {
        if (!isCpfValido(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.cpf = somenteDigitos(cpf); // garante só números
    }
    private static boolean isCpfValido(String cpf) {
        if (cpf == null) return false;
        String limpo = somenteDigitos(cpf);// remove . e -
        try {
            new CPFValidator().assertValid(limpo); //validator padrão: só dígitos
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }

    //getters/setters
    public String getRg(){
        return rg;
    } public void setRg(String rg){
        this.rg = rg;}

    public LocalDate getDataNascimento(){
        return dataNascimento;
    } public void setDataNascimento(LocalDate dataNascimento){
        this.dataNascimento = dataNascimento;}
}
