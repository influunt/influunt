# language: pt
@finalizar_controlador
Funcionalidade: Finalizar um controlador

  Cenário: Não pode finalizar um controlador que não tenha plano configurado
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    Então o sistema "não pode" mostrar o botão "Finalizar Configuração"

  Cenário: Não pode finalizar um controlador que não tenha tabela horária configurada
    Dado que o sistema possua planos para o controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    Então o sistema "não pode" mostrar o botão "Finalizar Configuração"

    Cenário: Mostrar o botão finalizar controlador
    Dado que o sistema possua tabela horária para o controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    Então o sistema "pode" mostrar o botão "Finalizar Configuração"

  Cenário: Finalizar controlador que esteja com plano e tabela horária configurados
    Dado o usuário clicar no botão "Finalizar"
    Então o sistema deverá mostar um modal para salvar o histórico
    Dado o usuário preencha o alert com "Crontrolador principal"
    E o usuário confirmar
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
    E o sistema deverá mostrar o status do controlador como "Configurado"
    E o sistema "não pode" mostrar o botão "Finalizar Configuração"

  Cenário: Não posso finalizar ao retornar um controlador para edição
    Dado o usuário clicar no botão "Configuração"
    E em resumo clicar em "Editar Configuração de Anéis"
    E o usuário selecionar o anel 2
    E o usuário adicionar imagem ao "Anel 2 como Estágio1"
    E o usuário clicar no botão "Salvar"
    E que o usuário acesse a página de listagem de controladores
    Então o sistema deverá mostrar o status do controlador como "Em Edição"
    E o sistema "não pode" mostrar o botão "Finalizar Configuração"
    E o sistema "não pode" mostrar o botão "Ver ou Editar Planos"
    E o sistema "não pode" mostrar o botão "Ver ou Editar Tabela Horária"
