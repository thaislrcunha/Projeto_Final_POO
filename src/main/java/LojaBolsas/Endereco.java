package LojaBolsas;

public class Endereco {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(String logradouro, String numero, String bairro,
                    String cidade, String estado, String cep, String complemento) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.complemento = complemento;
    }

    // getters/setters
    public String getLogradouro() {
        return logradouro;
    } public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;}

    public String getNumero() {
        return numero;
    } public void setNumero(String numero) {
        this.numero = numero;}

    public String getBairro() {
        return bairro;
    } public void setBairro(String bairro) {
        this.bairro = bairro;}

    public String getCidade() {
        return cidade;
    } public void setCidade(String cidade) {
        this.cidade = cidade;}

    public String getEstado() {
        return estado;
    } public void setEstado(String estado) {
        this.estado = estado;}

    public String getCep() {
        return cep;
    } public void setCep(String cep) {
        this.cep = cep;}

    public String getComplemento() {
        return complemento;
    } public void setComplemento(String complemento) {
        this.complemento = complemento;}
}
