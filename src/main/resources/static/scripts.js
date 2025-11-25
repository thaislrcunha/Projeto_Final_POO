// 1. CONFIGURAÇÕES E VARIÁVEIS GLOBAIS
const imagensMap = {
    1: "/imagem/BolsaToteClassica.webp",
    2: "/imagem/MochilaExecutiva.jpg",
    3: "/imagem/CarteiraSlim.webp",
    4: "/imagem/maladeviagemunissexual.jpg",
    5: "/imagem/pochetemasculina.jpg",
    6: "/imagem/mochilafeminina.jpg",
    7: "/imagem/carteirafeminina.jpg",
    8: "/imagem/mochilaunissexual.jpg"
};

let todosProdutos = [];


// 2. CARREGAMENTO DE DADOS (API)
function carregarDados() {
    console.log("Iniciando busca de produtos...");

    fetch('http://localhost:8080/api/pedidos/produtos')
        .then(response => response.json())
        .then(data => {
            console.log("Dados brutos recebidos:", data);

            // Normaliza os dados
            todosProdutos = data.map(produto => {
                let idCorreto = produto.id || produto.idProduto || produto.ID;
                let nomeArquivo = produto.urlImagem ? produto.urlImagem : "sem-foto.jpg";
                let estoqueReal = produto.estoque || 0;

                return {
                    ...produto,
                    idProduto: idCorreto,
                    imagem: `/imagem/${nomeArquivo}`,
                    estoqueVisual: estoqueReal,
                    tipo: produto.tipo || "Geral",
                    cores: produto.cores || ["Preto", "Azul Marinho", "Marrom"]
                };
            });

            console.log("Produtos processados:", todosProdutos);

            // Renderiza vitrine se estiver na home
            if (document.getElementById('container-produtos')) {
                renderizarProdutos(todosProdutos);
            }

            // Renderiza detalhes se estiver na página de produto
            if (document.getElementById('produto-area')) {
                carregarPaginaDetalhes();
            }
        })
        .catch(error => {
            console.error('Erro fatal na API:', error);
            const loading = document.getElementById('loading');
            if(loading) loading.innerText = "Erro ao conectar com o servidor.";
        });
}

// 3. RENDERIZAÇÃO DA VITRINE (Home)
function renderizarProdutos(lista) {
    const container = document.getElementById('container-produtos');
    if (!container) return;

    container.innerHTML = '';

    lista.forEach(produto => {
        if (!produto.idProduto) return;

        const esgotado = produto.estoqueVisual <= 0;
        const textoEstoque = esgotado ? "Produto Esgotado" : "Produto Disponível";
        const classeEstoque = esgotado ? "aviso-esgotado" : "aviso-disponivel";

        let cartao = document.createElement('div');
        cartao.className = 'cartao-produto';

        cartao.innerHTML = `
            <div class="img-container">
                <a href="detalhe-produto.html?id=${produto.idProduto}">
                    <img src="${produto.imagem}" alt="${produto.nome}">
                </a>
            </div>
            <div class="conteudo-produto">
                <h3>${produto.nome}</h3>
                <p class="preco">R$ ${produto.preco.toFixed(2)}</p>
                <p class="${classeEstoque}">${textoEstoque}</p>

                <button class="btn-comprar" 
                    onclick="window.location.href='detalhe-produto.html?id=${produto.idProduto}'" 
                    ${esgotado ? 'disabled' : ''}>
                    ${esgotado ? "Indisponível" : "Comprar"}
                </button>
            </div>
        `;
        container.appendChild(cartao);
    });
}

// 4. PÁGINA DE DETALHES DO PRODUTO
function carregarPaginaDetalhes() {
    const params = new URLSearchParams(window.location.search);
    const idUrl = params.get('id');

    if (!idUrl || idUrl === "undefined") {
        document.getElementById('loading').innerText = "Produto não selecionado.";
        return;
    }

    const produto = todosProdutos.find(p => p.idProduto == idUrl);

    if (!produto) {
        document.getElementById('loading').innerText = "Produto não encontrado.";
        return;
    }

    // Preenche info básica
    const imgEl = document.getElementById('prod-img');
    const nomeEl = document.getElementById('prod-nome');
    const descEl = document.getElementById('prod-desc');
    const precoEl = document.getElementById('prod-preco');
    const tipoEl = document.getElementById('prod-tipo');

    if(imgEl) imgEl.src = produto.imagem;
    if(nomeEl) nomeEl.innerText = produto.nome;
    if(descEl) descEl.innerText = produto.descricao;
    if(precoEl) precoEl.innerText = "R$ " + produto.preco.toFixed(2);
    if(tipoEl) tipoEl.innerText = produto.tipo;

    // --- Lógica de Cores (Dropdown) ---
    const selectCor = document.getElementById('escolha-cor');
    if (selectCor) {
        selectCor.innerHTML = "";
        const coresDisponiveis = (produto.cores && produto.cores.length > 0) ? produto.cores : ["Cor Única"];

        coresDisponiveis.forEach(cor => {
            const option = document.createElement('option');
            option.value = cor;
            option.innerText = cor;
            selectCor.appendChild(option);
        });
    }

    // Atualiza info de estoque e botões
    atualizarEstoqueDetalhe(produto);

    document.getElementById('loading').style.display = 'none';
    document.getElementById('produto-area').style.display = 'flex';
}

function atualizarEstoqueDetalhe(produto) {
    const textoEstoque = document.getElementById('prod-estoque');
    const btnComprar = document.getElementById('btn-comprar-detalhe');

    if (!textoEstoque || !btnComprar) return;

    if (produto.estoqueVisual > 0) {
        textoEstoque.innerText = `Estoque disponível: ${produto.estoqueVisual} unidades`;
        textoEstoque.style.color = "#28a745";

        if (produto.estoqueVisual < 5) {
            textoEstoque.innerText = `Corra! Apenas ${produto.estoqueVisual} unidades restantes.`;
            textoEstoque.style.color = "#ff9800";
        }

        btnComprar.disabled = false;
        btnComprar.innerText = "Comprar Agora";
        btnComprar.onclick = () => adicionarAoCarrinho(produto.idProduto);

    } else {
        textoEstoque.innerText = "Produto Esgotado (0 unidades)";
        textoEstoque.style.color = "#dc3545";
        btnComprar.disabled = true;
        btnComprar.innerText = "Indisponível";
        btnComprar.onclick = null;
    }
}

// 5. CARRINHO (Adicionar e Contar)
function adicionarAoCarrinho(idClicado) {
    const produto = todosProdutos.find(p => p.idProduto == idClicado);

    if (!produto) return;

    if (produto.estoqueVisual <= 0) {
        alert("Desculpe, este produto acabou de esgotar!");
        return;
    }

    let corSelecionada = "Padrão";
    const selectCor = document.getElementById('escolha-cor');

    if (selectCor) {
        corSelecionada = selectCor.value;
        if (!corSelecionada || corSelecionada === "") {
            alert("Por favor, selecione uma cor!");
            return;
        }
    }

    let carrinho = JSON.parse(localStorage.getItem('meuCarrinho')) || [];

    const itemExistente = carrinho.find(item =>
        item.idProduto == produto.idProduto && item.cor == corSelecionada
    );

    if (itemExistente) {
        itemExistente.qtd += 1;
    } else {
        carrinho.push({
            idProduto: produto.idProduto,
            nome: produto.nome,
            preco: produto.preco,
            imagem: produto.imagem,
            cor: corSelecionada,
            qtd: 1
        });
    }

    localStorage.setItem('meuCarrinho', JSON.stringify(carrinho));
    atualizarContadorTopo();

    produto.estoqueVisual -= 1;
    atualizarEstoqueDetalhe(produto);

    alert(`✅ ${produto.nome} (${corSelecionada}) adicionado! Restam: ${produto.estoqueVisual}`);
}

function atualizarContadorTopo() {
    const contador = document.getElementById('contador-carrinho');
    if (contador) {
        const carrinho = JSON.parse(localStorage.getItem('meuCarrinho')) || [];
        const total = carrinho.reduce((acc, item) => acc + item.qtd, 0);
        contador.innerText = total;
    }
}

// 6. FILTROS E UTILITÁRIOS
function filtrarProdutos(categoriaAlvo) {
    if (categoriaAlvo === 'Todos') {
        renderizarProdutos(todosProdutos);
        return;
    }

    const listaFiltrada = todosProdutos.filter(produto => {
        if (!produto.tipo) return false;
        return produto.tipo.toString().toLowerCase().includes(categoriaAlvo.toLowerCase());
    });

    renderizarProdutos(listaFiltrada);
}

// 7. LOGIN E USUÁRIO
function verificarUsuarioLogado() {
    const menu = document.getElementById('menu-usuario');
    if (!menu) return;

    const usuario = JSON.parse(localStorage.getItem('usuarioLogado'));
    if (usuario) {
        menu.innerHTML = `
            <span style="color:white; margin-right: 10px;">Olá, ${usuario.nome}</span> 
            <button onclick="logout()" style="padding: 5px 10px; border-radius: 5px; cursor: pointer;">Sair</button>
        `;
    } else {
        menu.innerHTML = `<a href="login.html" style="color: white; text-decoration: none; font-weight: bold;">Entrar / Cadastro</a>`;
    }
}

function logout() {
    localStorage.removeItem('usuarioLogado');
    window.location.reload();
}

// 8. INICIALIZAÇÃO
document.addEventListener('DOMContentLoaded', () => {
    atualizarContadorTopo();
    verificarUsuarioLogado();
    carregarDados();
});

// 9. VALIDAÇÃO DE ESTOQUE (NOVO)
async function verificarDisponibilidadeEstoque() {
    let carrinho = JSON.parse(localStorage.getItem('meuCarrinho')) || [];
    if (carrinho.length === 0) return { ok: false, erro: "Carrinho vazio" };

    try {
        // Busca o estoque real do servidor agora
        const response = await fetch('http://localhost:8080/api/pedidos/produtos');
        const produtosBanco = await response.json();

        // Agrupa quantidades do carrinho por ID (soma cores diferentes do mesmo produto)
        let qtdSolicitadaPorId = {};
        carrinho.forEach(item => {
            qtdSolicitadaPorId[item.idProduto] = (qtdSolicitadaPorId[item.idProduto] || 0) + item.qtd;
        });

        let mensagensErro = [];

        // Compara o solicitado com o estoque real
        for (const [id, qtd] of Object.entries(qtdSolicitadaPorId)) {
            // Normaliza ID (alguns bancos usam id, outros idProduto)
            const produtoReal = produtosBanco.find(p => (p.id || p.idProduto || p.ID) == id);

            if (!produtoReal) {
                mensagensErro.push(`Produto ID ${id} não está mais disponível na loja.`);
            } else if (qtd > produtoReal.estoque) {
                mensagensErro.push(`⚠️ ${produtoReal.nome}: Você pediu ${qtd}, mas só temos ${produtoReal.estoque} no estoque.`);
            }
        }

        if (mensagensErro.length > 0) {
            return { ok: false, erro: "PROBLEMA NO ESTOQUE:\n\n" + mensagensErro.join("\n") + "\n\nPor favor, ajuste seu carrinho." };
        }

        return { ok: true };

    } catch (error) {
        console.error(error);
        return { ok: false, erro: "Erro de conexão ao validar estoque. Tente novamente." };
    }
}