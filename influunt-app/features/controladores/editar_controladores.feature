# language: pt
@editar_controlador
Funcionalidade: Editar controlador

  Cenário: O usuário deve conseguir cancelar uma edição de um controlador
    Dado que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar em "Configuração" do controlador "Avenida Nove de Julho com Av. Paulista"
    E em resumo clicar em "Editar"
    E o usuário realize um scroll up
    E o usuário navegar pelo breadcrumb clicando em "Programação"
    Então o sistema deverá mostrar o status do controlador como "Em Edição"
    Quando o usuário clicar em "Configuração" do controlador "Avenida Nove de Julho com Av. Paulista"
    E o usuário clicar no botão "Cancelar Edição"
    E o usuário confirmar
    Então o sistema não deve apresentar erro
    E o sistema deverá redirecionar o usuário para a página de listagem de controladores


  Cenário: Não deve alterar a localizão do controlador
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar no botão "Configuração"
    E em resumo clicar em "Editar Associação Estágio x Grupo Semafórico"
    E o usuário descer o estágio "E1"
    Quando clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Transições Proibidas"
    E que o usuário acesse a página de listagem de controladores
    Então o sistema deverá mostrar na tabela o valor "Av. Paulista com R. Bela Cintra"

  Cenário: Editar atraso de grupo zerando o valor
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar no botão "Configuração"
    E em resumo clicar em "Editar Atraso de Grupo"
    E o usuário realize um scroll up
    E que o usuário marque 15 no campo 1 para transições com perda de direito de passagem
    E o usuário selecionar o anel 2
    E que o usuário confirme que não há configurações a serem feitas nesse anel
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Detectores"
    Dado que o usuário acesse a página de listagem de controladores
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar no botão "Configuração"
    Dado em resumo clicar em "Editar Atraso de Grupo"
    E o usuário realize um scroll up
    E que o usuário marque 0 no campo 1 para transições com perda de direito de passagem
    E que o usuário confirme que não há configurações a serem feitas nesse anel
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Detectores"

  Cenário: Editar transicões proibidas utilizando botão Salvar
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar no botão "Configuração"
    E em resumo clicar em "Editar Transições Proibidas"
    Quando o usuário desmarcar a transição de "E4" para "E3" como proibida
    E o usuário desmarcar a transição de "E3" para "E4" como proibida
    E o usuário marcar a transição de "E1" para "E2" como proibida
    E o usuário na transição proibida "E1-E2" selecionar a alternativa "E3"
    E o usuário selecionar o anel 2
    E que o usuário confirme que não há configurações a serem feitas nesse anel
    E o usuário clicar em "Salvar"
    Então o sistema irá continuar no passo "Transições Proibidas"
    Dado o usuário selecionar o anel 1
    Quando o usuário desmarcar a transição de "E1" para "E2" como proibida
    E o usuário marcar a transição de "E1" para "E3" como proibida
    E o usuário na transição proibida "E1-E3" selecionar a alternativa "E2"
    Quando clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Tabela Entre Verdes"
