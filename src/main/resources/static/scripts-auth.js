// Alterna entre ver Login ou Cadastro
function alternarTelas() {
    document.getElementById('box-login').classList.toggle('hidden');
    document.getElementById('box-cadastro').classList.toggle('hidden');
}

// Mostra campos PF ou PJ dependendo da seleção
function ajustarFormulario() {
    const tipo = document.getElementById('cad-tipo').value;
    if (tipo === 'PF') {
        document.getElementById('campos-pf').classList.remove('hidden');
        document.getElementById('campos-pj').classList.add('hidden');
    } else {
        document.getElementById('campos-pf').classList.add('hidden');
        document.getElementById('campos-pj').classList.remove('hidden');
    }
}

//FUNÇÃO DE CADASTRO
async function fazerCadastro() {
    const tipo = document.getElementById('cad-tipo').value;

    const dados = {
        tipo: tipo,
        nome: document.getElementById('cad-nome').value,
        username: document.getElementById('cad-user').value,
        password: document.getElementById('cad-pass').value,
        email: document.getElementById('cad-email').value,
        telefone: document.getElementById('cad-tel').value,

        // Endereço
        logradouro: document.getElementById('end-logradouro').value,
        numero: document.getElementById('end-num').value,
        bairro: document.getElementById('end-bairro').value,
        cidade: document.getElementById('end-cidade').value,
        estado: document.getElementById('end-est').value,
        cep: document.getElementById('end-cep').value,

        // Específicos (Pega valor se existir, senão vazio)
        cpf: document.getElementById('cad-cpf').value,
        rg: document.getElementById('cad-rg').value,
        dataNascimento: document.getElementById('cad-nasc').value,

        cnpj: document.getElementById('cad-cnpj').value,
        razaoSocial: document.getElementById('cad-razao').value,
        inscricaoEstadual: document.getElementById('cad-ie').value,
        dataAbertura: document.getElementById('cad-abertura').value
    };

    try {
        const response = await fetch('/api/clientes/cadastro', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dados)
        });

        if (response.ok) {
            alert("Cadastro realizado com sucesso! Faça login.");
            alternarTelas();
        } else {
            alert("Erro no cadastro.");
        }
    } catch (error) {
        console.error(error);
        alert("Erro ao conectar com o servidor.");
    }
}

//FUNÇÃO DE LOGIN
async function fazerLogin() {
    const user = document.getElementById('login-user').value;
    const pass = document.getElementById('login-pass').value;

    try {
        const response = await fetch('/api/clientes/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: user, password: pass })
        });

        if (response.ok) {
            const cliente = await response.json();
            alert("Bem-vindo(a), " + cliente.nome + "!");

            // Salva que o usuário está logado no navegador
            localStorage.setItem('usuarioLogado', JSON.stringify(cliente));

            // Redireciona para a loja
            window.location.href = "/loja";
        } else {
            alert("Usuário ou senha incorretos.");
        }
    } catch (error) {
        console.error(error);
        alert("Erro ao tentar login.");
    }
}