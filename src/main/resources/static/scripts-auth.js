document.addEventListener('DOMContentLoaded', () => {
    // === REFERÊNCIAS ===
    const tabLogin = document.getElementById('tab-login');
    const tabCadastro = document.getElementById('tab-cadastro');

    // CORREÇÃO AQUI: Pegamos o elemento direto pelo ID
    const formLogin = document.getElementById('formLoginReal');
    const formCadastro = document.getElementById('cadastro-form');
    const formForgot = document.getElementById('forgot-password-form');

    const linkForgot = document.getElementById('forgot-password-link');
    const linkBack = document.getElementById('back-to-login');

    const tipoPessoaSelect = document.getElementById('tipo-pessoa');
    const divPF = document.querySelector('.campos-pf');
    const divPJ = document.querySelector('.campos-pj');

    // === 1. LÓGICA DE ABAS (LOGIN / CADASTRO) ===
    function mostrarAba(aba) {
        // Esconde tudo
        formLogin.classList.remove('active');
        formCadastro.classList.remove('active');
        formForgot.classList.remove('active');
        tabLogin.classList.remove('active');
        tabCadastro.classList.remove('active');

        if (aba === 'login') {
            formLogin.classList.add('active');
            tabLogin.classList.add('active');
        } else if (aba === 'cadastro') {
            formCadastro.classList.add('active');
            tabCadastro.classList.add('active');
        } else if (aba === 'forgot') {
            formForgot.classList.add('active');
        }
    }

    if(tabLogin) tabLogin.addEventListener('click', () => mostrarAba('login'));
    if(tabCadastro) tabCadastro.addEventListener('click', () => mostrarAba('cadastro'));
    if(linkForgot) linkForgot.addEventListener('click', (e) => { e.preventDefault(); mostrarAba('forgot'); });
    if(linkBack) linkBack.addEventListener('click', (e) => { e.preventDefault(); mostrarAba('login'); });

    // === 2. ALTERNAR CAMPOS PF / PJ ===
    if(tipoPessoaSelect) {
        tipoPessoaSelect.addEventListener('change', () => {
            if (tipoPessoaSelect.value === 'PF') {
                divPF.classList.add('visivel');
                divPJ.classList.remove('visivel');
            } else {
                divPF.classList.remove('visivel');
                divPJ.classList.add('visivel');
            }
        });
    }

    // === 3. REGRAS DE VALIDAÇÃO (REGEX) ===
    const regras = {
        'cadNome': { regex: /^[a-zA-ZÀ-ÿ\s]+$/, msg: 'Apenas letras e espaços.' },
        'cadEmail': { regex: /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/, msg: 'E-mail inválido.' },
        'cadTelefone': { regex: /^\d{10,11}$/, msg: 'Apenas números (10 ou 11).' },
        'cadUser': { regex: /^.+$/, msg: 'Usuário é obrigatório.' },
        'cadPass': { regex: /^.{4,}$/, msg: 'Mínimo 4 caracteres.' },

        // Numéricos Puros
        'cadCpf': { regex: /^\d{11}$/, msg: 'CPF: 11 dígitos numéricos.' },
        'cadRg': { regex: /^\d+$/, msg: 'RG: Apenas números.' },
        'cadCnpj': { regex: /^\d{14}$/, msg: 'CNPJ: 14 dígitos numéricos.' },
        'cadIE': { regex: /^\d+$/, msg: 'Apenas números.' },

        // Endereço
        'endCep': { regex: /^\d{8}$/, msg: 'CEP: 8 dígitos.' },
        'endNum': { regex: /^\d+$/, msg: 'Apenas números.' },
        'endEstado': { regex: /^[A-Z]{2}$/, msg: 'Sigla (ex: SP).' },
        'endBairro': { regex: /^[a-zA-ZÀ-ÿ\s]+$/, msg: 'Apenas letras.' },
        'endCidade': { regex: /^[a-zA-ZÀ-ÿ\s]+$/, msg: 'Apenas letras.' }
    };

    // === 4. FUNÇÃO DE VALIDAÇÃO ===
    function validarCampo(input) {
        const id = input.id;
        const valor = input.value.trim();
        const spanErro = document.getElementById('error-' + id);

        // Se o input estiver oculto (ex: campo PJ enquanto estou vendo PF), não valida
        if (input.offsetParent === null) return true;
        // Se não tiver span de erro associado, ignora
        if (!spanErro) return true;

        let valido = true;
        let mensagem = "";

        // Regra Especial: DATA
        if (input.type === 'date') {
            if (!valor) {
                // Se for required, falha se vazio
                if(input.hasAttribute('required')) {
                    valido = false; mensagem = "Data obrigatória.";
                }
            } else {
                const ano = parseInt(valor.split('-')[0]);
                const anoAtual = new Date().getFullYear();
                if (ano < 1900 || ano > anoAtual) {
                    valido = false;
                    mensagem = `Ano inválido (Entre 1900 e ${anoAtual}).`;
                }
            }
        }
        // Regras Gerais (Regex)
        else if (regras[id]) {
            if (valor === "" && input.hasAttribute('required')) {
                valido = false; mensagem = "Obrigatório.";
            } else if (valor !== "" && !regras[id].regex.test(valor)) {
                valido = false; mensagem = regras[id].msg;
            }
        }

        // Aplica visual
        if (!valido) {
            spanErro.innerText = mensagem;
            input.classList.add('input-erro');
            return false;
        } else {
            spanErro.innerText = "";
            input.classList.remove('input-erro');
            return true;
        }
    }

    // === 5. ATIVAR VALIDAÇÃO "ON BLUR" ===
    // Procura inputs dentro do formCadastro (que agora é a própria tag form)
    if(formCadastro) {
        const inputsCadastro = formCadastro.querySelectorAll('input');
        inputsCadastro.forEach(input => {
            input.addEventListener('blur', function() {
                validarCampo(this);
            });
            input.addEventListener('input', function() {
                const spanErro = document.getElementById('error-' + this.id);
                if(spanErro) spanErro.innerText = "";
                this.classList.remove('input-erro');
            });
        });

        // === 6. ENVIO DO FORMULÁRIO DE CADASTRO ===
        formCadastro.addEventListener('submit', async (e) => {
            e.preventDefault();
            console.log("Botão cadastrar clicado!");

            // Validar TUDO antes de enviar
            let temErro = false;
            inputsCadastro.forEach(input => {
                if (!validarCampo(input)) temErro = true;
            });

            if (temErro) {
                // Se houver erro visual, foca no primeiro erro
                const primeiroErro = formCadastro.querySelector('.input-erro');
                if(primeiroErro) primeiroErro.focus();
                return;
            }

            // Prepara JSON
            const formData = new FormData(e.target);
            const dto = Object.fromEntries(formData.entries());

            // Adiciona Tipo Manualmente
            dto.tipo = tipoPessoaSelect.value;

            try {
                const response = await fetch('/api/clientes/cadastro', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(dto)
                });

                if (response.ok) {
                    alert("Cadastro realizado com sucesso! Faça login.");
                    e.target.reset();
                    mostrarAba('login');
                } else {
                    const msgErro = await response.text();
                    const divErro = document.getElementById('mensagem-erro-servidor');
                    if(divErro) divErro.innerText = "Erro: " + msgErro;
                    else alert("Erro: " + msgErro);
                }
            } catch (error) {
                console.error(error);
                alert("Erro de conexão com o servidor.");
            }
        });
    }

    // === 7. LOGIN ===
    if (formLogin) {
        formLogin.addEventListener('submit', async (e) => {
            e.preventDefault();
            console.log("Login enviado...");
            const formData = new FormData(e.target);
            const dto = Object.fromEntries(formData.entries());

            try {
                const response = await fetch('/api/clientes/login', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(dto)
                });

                if (response.ok) {
                    if (response.ok) {
                        // 1. Lê o JSON que o ClienteController.java retornou
                        const usuarioLogado = await response.json();

                        // 2. Salva no navegador para usar nas outras páginas
                        localStorage.setItem('usuarioLogado', JSON.stringify(usuarioLogado));

                        // 3. Redireciona
                        window.location.href = '/loja'; // Ou '/loja' dependendo da sua rota
                    }
                } else {
                    alert("Usuário ou senha incorretos.");
                }
            } catch (err) {
                alert("Erro ao tentar login.");
            }
        });
    }
});