// 1. MAPA DE IMAGENS (Associa ID ao caminho da foto)
const imagensMap = {
    1: "/imagem/BolsaToteClassica.webp",
    2: "/imagem/MochilaExecutiva.jpg",
    3: "/imagem/CarteiraSlim.webp",
    4: "/imagem/MaladeLuxo.webp"
};

// Variáveis globais
let todosProdutos = [];
let carrinhoCount = 0;

// 2. CARREGAR DADOS DO JAVA
async function carregarDados() {
    try {
        const resposta = await fetch('/api/produtos');
        if (!resposta.ok) throw new Error("Erro na conexão com Java");

        const dados = await resposta.json();

        // Mapeamento Seguro: Garante que todo produto tenha idProduto e imagem
        todosProdutos = dados.map(produto => {
            const idReal = produto.idProduto || produto.id; // Aceita os dois nomes
            return {
                ...produto,
                idProduto: idReal,
                imagem: imagensMap[idReal] || "/imagem/padrao.jpg"
            };
        });

        // Só desenha se estiver na página da loja
        if (document.getElementById('container-produtos')) {
            renderizarProdutos(todosProdutos);
        }

    } catch (erro) {
        console.error("Erro ao carregar:", erro);
    }
}

// 3. DESENHAR VITRINE (LOJA)
function renderizarProdutos(lista) {
    const container = document.getElementById('container-produtos');
    if (!container) return;

    container.innerHTML = '';

    lista.forEach(produto => {
        const cartao = document.createElement('div');
        cartao.classList.add('cartao-produto');

        const esgotado = produto.estoque <= 0;

        // Formatação visual do Tipo (ex: BolsaFeminina -> Bolsa Feminina)
        let tipoFormatado = produto.tipo ? produto.tipo.toString().replace(/([A-Z])/g, ' $1').trim() : "Item";

        cartao.innerHTML = `
            <div class="img-container">
                <a href="/detalhe-produto.html?id=${produto.idProduto}">
                    <img src="${produto.imagem}" alt="${produto.nome}">
                </a>
            </div>
            <div class="conteudo-produto">
                <h2>${produto.nome}</h2>
                <p>${produto.descricao}</p>
                <p class="preco">R$ ${produto.preco.toFixed(2)}</p>
                <span style="background:#eee; padding:2px 6px; border-radius:4px; font-size:0.8em;">${tipoFormatado}</span>
                
                <p class="estoque" style="${esgotado ? 'color:red' : ''}">
                    ${esgotado ? "Indisponível" : "Estoque: " + produto.estoque}
                </p>

                <button class="btn-comprar" 
                    onclick="adicionarAoCarrinho(${produto.idProduto})" 
                    ${esgotado ? 'disabled' : ''}>
                    ${esgotado ? "Esgotado" : "Comprar"}
       
                </button>
            </div>
        `;
        container.appendChild(cartao);
    });
}

// 4. FUNÇÃO DE COMPRAR (SALVAR NO NAVEGADOR)
function adicionarAoCarrinho(idClicado) {
    // Encontra o produto na lista
    const produto = todosProdutos.find(p => p.idProduto == idClicado);

    if (!produto) {
        alert("Erro: Produto não encontrado!");
        return;
    }

    if (produto.estoque <= 0) {
        alert("Produto esgotado!");
        return;
    }

    // Baixa estoque visual
    produto.estoque--;
    renderizarProdutos(todosProdutos);

    // --- LÓGICA DE SALVAR ---
    let carrinho = JSON.parse(localStorage.getItem('meuCarrinho')) || [];

    // Verifica se já existe no carrinho
    const itemExistente = carrinho.find(item => item.idProduto == produto.idProduto);

    if (itemExistente) {
        itemExistente.qtd += 1;
    } else {
        carrinho.push({
            idProduto: produto.idProduto,
            nome: produto.nome,
            preco: parseFloat(produto.preco), // Força ser número
            imagem: produto.imagem,
            qtd: 1
        });
    }

    localStorage.setItem('meuCarrinho', JSON.stringify(carrinho));
    atualizarContadorTopo();

    alert(`✅ ${produto.nome} adicionado ao carrinho!`);
}

// 5. EXTRAS
function atualizarContadorTopo() {
    const contador = document.getElementById('contador-carrinho');
    if (!contador) return;

    const carrinho = JSON.parse(localStorage.getItem('meuCarrinho')) || [];
    const total = carrinho.reduce((acc, item) => acc + item.qtd, 0);
    contador.innerText = total;
}

function verificarUsuarioLogado() {
    const menu = document.getElementById('menu-usuario');
    if (!menu) return;

    const usuario = JSON.parse(localStorage.getItem('usuarioLogado'));
    if (usuario) {
        menu.innerHTML = `<span>Olá, ${usuario.nome}</span> <button onclick="logout()">Sair</button>`;
    } else {
        menu.innerHTML = `<a href="/login.html" style="color:white">Entrar</a>`;
    }
}

function logout() {
    localStorage.removeItem('usuarioLogado');
    window.location.reload();
}

// INICIALIZAÇÃO
document.addEventListener('DOMContentLoaded', () => {
    // Se estiver na loja, carrega produtos. Se não, só atualiza topo.
    if (document.getElementById('container-produtos')) {
        carregarDados();
    }
    verificarUsuarioLogado();
    atualizarContadorTopo();
});