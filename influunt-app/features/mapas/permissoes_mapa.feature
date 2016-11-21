# language: pt
@permissoes_mapa
Funcionalidade: Verificar pelo perfil quais informações o usuário tem acesso no mapa
  Context:

  Cenário: Usuário sem permissão para ver outra áreas
    Dado o usuário logue no sistema com usuário "mobilab" e perfil administrador
    Quando for desabilitada no perfil visualizar todas as áreas
    E que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar no botão "Ver no mapa"
    Então o sistema deve redirecionar para o mapa
    Quando o usuário clicar no menu filtros
    E o sistema deverá mostrar no mapa "2" controladores
    E o sistema deverá mostrar no mapa "1" aneis
    E o sistema deverá mostrar "2" controladores agrupados
    E o usuário não consiga selecionar o valor "2" para o campo "areas"
    E o usuário não consiga selecionar o valor "2.000.0001" para o campo "controladores"
    E o usuário não consiga selecionar o valor "AREA SUL PAULISTA" para o campo "subarea"
