# language: pt
@pesquisas
Funcionalidade: Realizar pesquisas em listagem
  Como um usuário
  Quero filtrar as informações em minhas listagems
  Afim de facilitar a visualização do items cadastrados

  Cenário: Filtrar controladores pelo status
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
    E o sistema deverá mostrar o status do controlador como "Em Edição"

  Cenário: Na listagem de áreas filtrar pelo númeor da área
    Dado o usuário acessar a tela de listagem de áreas
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

  Cenário: Na listagem de auditoria filtrar por valores que contenham
    Dado o usuário acesse a listagem de "auditorias"
    Quando o usuário clicar em pesquisar
    E preencher o campo data inicial com "03/10/2016"
    E preencher o campo data final com "04/10/2016"
    E preencher o campo "Classe" com "Modelo"
    E preencher o campo "Tipo" com "UPDATE"
    E o usuário clicar no botão pesquisar
    Então o sistema deverá mostrar "7" items na tabela
    E o sistema deverá mostrar na tabela o valor "Modelo Controladores"

  @wip
  Cenário: Na listagem de auditoria filtrar por valores iguais
    # TODO - arrumar o bug #1281
    # E selecionar igual para "Classe"
    # Dado o usuário acesse a listagem de "auditorias"
    # Quando o usuário clicar em pesquisar
    # E preencher o campo "Classe" com "Contorladores"
    # E preencher o campo "Tipo" com "UPDATE"
    # E o usuário clicar no botão pesquisar
    # Então o sistema deverá mostrar "7" items na tabela
    # E o sistema deverá mostrar na tabela o valor "Modelo Controladores"
