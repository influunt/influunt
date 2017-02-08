# language: pt
@pesquisas
Funcionalidade: Realizar pesquisas em listagem
  Como um usuário
  Quero filtrar as informações em minhas listagems
  Para facilitar a visualização do itens cadastrados

  Cenário: Filtrar controladores pelo status
    Dado o usuário logue no sistema com usuário "mobilab" e perfil administrador
    Dado que o sistema possua controladores cadastrados
    E que o usuário acesse a página de listagem de controladores
    Então o sistema deverá mostrar "4" controladores cadastrados
    Quando o usuário clicar em pesquisar
    E o usuário selecionar o status "CONFIGURADO"
    E o usuário clicar no botão pesquisar
    Então o sistema deverá mostrar "1" controladores cadastrados
    E o sistema deverá mostrar o status do controlador como "Configurado"

  Cenário: Limpar filtro na listagem de controladores
    Dado o usuário esteja na listagem de controladores
    Então o sistema deverá mostrar "1" controladores cadastrados
    Quando o usuário clicar em pesquisar
    E o usuário clicar em limpar a pesquisa
    Então o sistema deverá mostrar "4" controladores cadastrados

  Cenário: Filtrar controladores pela localização que contenha apenas Bela
    Dado o usuário esteja na listagem de controladores
    Quando o usuário clicar em pesquisar
    E preencher o campo "Localização" com "Bela"
    E o usuário clicar no botão pesquisar
    Então o sistema deverá mostrar "2" controladores cadastrados

  Cenário: Filtrar controladores pela área que contenha exatamente o valor
    Dado o usuário esteja na listagem de controladores
    Quando o usuário clicar em pesquisar
    E selecionar igual para "ÁREA"
    E preencher o campo "ÁREA" com "2"
    E o usuário clicar no botão pesquisar
    Então o sistema deverá mostrar "1" controladores cadastrados
    E o sistema deverá mostrar o status do controlador como "Em revisão"

  Cenário: Na listagem de áreas filtrar pelo número da área
    Dado o usuário acessar a tela de listagem de "areas"
    Quando o usuário clicar em pesquisar
    E selecionar igual para "ÁREA"
    E preencher o campo "ÁREA" com "2"
    E o usuário clicar no botão pesquisar
    Então o sistema deverá mostrar "1" items na tabela
    E o sistema deverá mostrar na tabela o valor "2"

  Cenário: Na listagem de modelo controladores filtrar pela descrição
    Dado que o usuário esteja na tela de listagem de modelos
    Quando o usuário clicar em pesquisar
    E selecionar igual para "DESCRIÇÃO"
    E preencher o campo "DESCRIÇÃO" com "Modelo 1"
    E o usuário clicar no botão pesquisar
    Então o sistema deverá mostrar "0" items na tabela
    Quando o usuário clicar em pesquisar
    E o usuário clicar em limpar a pesquisa
    E o usuário clicar em pesquisar
    E selecionar igual para "DESCRIÇÃO"
    E preencher o campo "DESCRIÇÃO" com "Modelo Básico"
    E o usuário clicar no botão pesquisar
    Então o sistema deverá mostrar "1" items na tabela
    E o sistema deverá mostrar na tabela o valor "Modelo Básico"
