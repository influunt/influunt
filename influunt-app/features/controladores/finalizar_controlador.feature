# language: pt
@finalizar_controlador
Funcionalidade: Finalizar um controlador

  Cenário: Não pode finalizar um controlador que não tenha plano configurado
    Dado que o sistema possui ao menos um controlador cadastrado
    E o usuário acesse a listagem de "controladores"
    Então o sistema "não pode" mostrar o botão "Finalizar Configuração"

  Cenário: Não pode finalizar um controlador que não tenha tabela horária configurada
    Dado que o sistema possua planos para o controlador cadastrado
    E o usuário acesse a listagem de "controladores"
    Então o sistema "não pode" mostrar o botão "Finalizar Configuração"

    Cenário: Mostrar o botão finalizar controlador
    Dado que o sistema possua tabela horária para o controlador cadastrado
    E o usuário acesse a listagem de "controladores"
    Então o sistema "pode" mostrar o botão "Finalizar Configuração"

  Cenário: Não pode finalizar um controlador que não tenha o número do SMEE
    Dado o usuário clicar no botão "Finalizar"
    Então o sistema deverá mostar um modal para salvar o histórico
    Dado o usuário preencha o alert com "Crontrolador principal"
    E o usuário confirmar
    Então o sistema exibe um alerta com a mensagem "O controlador não pode ser finalizado sem o número do SMEE preenchido."
    E o usuário confirmar
    E o sistema deverá mostrar o status do controlador como "Em revisão"
    E o sistema "pode" mostrar o botão "Finalizar Configuração"

  Cenário: Adicionar um SMEE para controlador
    Dado o usuário clicar no botão "Configuração"
    E o usuário clicar em "Editar"
    E o usuário realizar um scroll down
    E o usuário preencher o campo NÚMERO SMEE com "1234"
    Então o sistema exibe um alerta com a mensagem "O endereço pesquisado é RIO BONITO, AV DO x NEUCHATEL, R x GUIDO BONI, PC - (**)?"
    E o usuário confirmar
    E o usuário clicar no botão "Salvar e Avançar"
    Então o sistema irá avançar para o passo "Anéis"

  Cenário: Finalizar o controlador com SMEE
    Dado o usuário acesse a listagem de "controladores"
    Dado o usuário clicar no botão "Finalizar"
    Então o sistema deverá mostar um modal para salvar o histórico
    Dado o usuário preencha o alert com "Crontrolador principal"
    E o usuário confirmar
    Então o sistema deverá mostrar o status do controlador como "Configurado"
    E o sistema "não pode" mostrar o botão "Finalizar Configuração"

  Cenário: Não posso finalizar ao retornar um controlador para edição
    Dado o usuário clicar no botão "Configuração"
    E em resumo clicar em "Editar Configuração de Anéis"
    E o usuário selecionar o anel 2
    E o usuário adicionar imagem ao "Anel 2 como Estágio1"
    E o usuário clicar em "Salvar"
    E que o usuário aguarde um tempo de "5000" milisegundos
    E o usuário acessar a tela de listagem de "controladores"
    Então o sistema deverá mostrar o status do controlador como "Em revisão"
    E o sistema "não pode" mostrar o botão "Finalizar Configuração"
    E o sistema "não pode" mostrar o botão "Ver ou Editar Planos"
    E o sistema "não pode" mostrar o botão "Ver ou Editar Tabela Horária"
