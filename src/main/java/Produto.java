public class Produto{
    private int idProduto;
    private String nome;
    private String descricao;
    private float preco;
    private int estoque;
    private TipoProduto tipo;
    //construtor
    public Produto(int idProduto,String nome,String descricao,float preco,int estoque,TipoProduto tipo){
        this.idProduto=idProduto;
        this.nome=nome;
        this.descricao=descricao;
        this.preco=preco;
        this.estoque=estoque;
        this.tipo=tipo;
    }
    //getters
    public int idProduto(){
        return idProduto;
    }
    public String getNome(){
        return nome;
    }
    public String getDescricao(){
        return descricao;
    }
    public float getPreco(){
        return preco;
    }
    public int getEstoque(){
        return estoque;
    }
    public TipoProduto getTipo(){
        return tipo;
    }
    //setters
    public void setIdProduto(int idProduto){
        this.idProduto=idProduto;
    }
    public void setNome(String nome){
        this.nome=nome;
    }
    public void setDescricao(String descricao){
        this.descricao=descricao;
    }
    public void setPreco(float preco){
        this.preco=preco;
    }
    public void setEstoque(int estoque){
        this.estoque=estoque;
    }
    public void setTipo(TipoProduto tipo){
        this.tipo=tipo;
    }
    //outros métodos
    public void atualizarEstoqe(int quantidade){
        this.estoque+=quantidade;
    }

}
