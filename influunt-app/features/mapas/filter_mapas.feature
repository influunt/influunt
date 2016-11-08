# language: pt
@filter_mapas
Funcionalidade: Realizar filtros no mapa

  Cenário: Exibir o menu para realizar os filtros
    Dado que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar no botão "Ver no mapa"
    Então o sistema deve redirecionar para o mapa
    Quando o usuário clicar no menu filtros
    Então o menu filtros deverá aparecer

  Cenário: Exibir apenas as informações da area 1
    Dado usuário estiver na tela de mapa
    Então o sistema deverá mostrar no mapa "3" controladores
    E o sistema deverá mostrar no mapa "6" aneis

  Cenário: Quero visulizar apenas os controladores da area 1
    Dado usuário estiver na tela de mapa
    E o usuário clicar na opção "Anéis" para filtrar
    Então o sistema deverá mostrar no mapa "3" controladores
    E o sistema deverá mostrar no mapa "0" aneis

  Cenário: Quero filtar no mapa pelo o número do controlador 1.000.0001
    Dado usuário estiver na tela de mapa
    E o usuário clicar na opção "Anéis" para filtrar
    E o usuário selecionar o valor "1.000.0001" para o campo "controladores"
    Então o sistema deverá mostrar no mapa "1" controladores
    E o sistema deverá mostrar no mapa "2" aneis

  Cenário: Quero filtar no mapa pelo o número do controlador 1.000.0002
    Dado usuário estiver na tela de mapa
    E o usuário selecionar o valor "1.000.0002" para o campo "controladores"
    Então o sistema deverá mostrar no mapa "2" controladores
    E o sistema deverá mostrar no mapa "4" aneis

  Cenário: Quero filtar no mapa pelo o número do controlador 1.000.0003
    Dado usuário estiver na tela de mapa
    E o usuário selecionar o valor "1.000.0003" para o campo "controladores"
    Então o sistema deverá mostrar no mapa "3" controladores
    E o sistema deverá mostrar no mapa "6" aneis

  Cenário: Quero remover do meu filtro os controladores selecionados
    Dado usuário estiver na tela de mapa
    Quando o usuário remover o "1.000.0001" selecionado
    Então o sistema deverá mostrar no mapa "2" controladores
    E o sistema deverá mostrar no mapa "4" aneis
    Quando o usuário remover o "1.000.0002" selecionado
    Então o sistema deverá mostrar no mapa "1" controladores
    E o sistema deverá mostrar no mapa "2" aneis
    Quando o usuário remover o "1.000.0003" selecionado
    Então o sistema deverá mostrar no mapa "3" controladores
    E o sistema deverá mostrar no mapa "6" aneis

  Cenário: Quero filtar no mapa pelo agrupamento
    Dado usuário estiver na tela de mapa
    E o usuário selecionar o valor "Corredor da Paulista" para o campo "agrupamentos"
    Então o sistema deverá marcar o agrupamento no mapa
    E o sistema deverá mostrar no mapa "2" controladores
    E o sistema deverá mostrar no mapa "4" aneis

    Cenário: Quero filtar no mapa pela área
    Dado usuário estiver na tela de mapa
    E o usuário selecionar o valor "2" para o campo "areas"
    Então o sistema deverá mostrar no mapa "0" controladores
    E o sistema deverá mostrar no mapa "0" aneis

  Cenário: Sair da tela de Mapas
    Dado usuário estiver na tela de mapa
    Quando o usuário clicar em "Voltar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
