package LojaBolsas;

public class Cliente {
    private int idCliente;
    private String nome;
    private String telefone;
    private String email;
    private String userName;
    private String password; //verificar tipo
    private Endereco endereco;  // composição

    //CONSTRUTORES
    public Cliente(int idCliente, String nome, String telefone,
                   String email, String userName, String password, Endereco endereco) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.endereco = endereco;
    }

    //getters/setters
    public int getIdCliente() {
        return idCliente;
    } public void setIdCliente(int idCliente) {
        this.idCliente = idCliente; }

    public String getNome(){
        return this.nome;
    } public void setNome(String nome){
        this.nome = nome;}

    public String getTelefone(){
        return this.telefone;
    } public void setTelefone(String telefone){
        this.telefone = telefone;}

    public String getEmail(){
        return this.email;
    } public void setEmail(String email){
        this.email = email;}

    public String getUserName(){
        return this.userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getPassword(){ //VERIFICAR
        return this.password;
    } public void setPassword(String password){
        this.password = password;
    }

    public Endereco getEndereco(){
        return this.endereco;
    } public void setEndereco(Endereco endereco){
        this.endereco = endereco;
    }
}
