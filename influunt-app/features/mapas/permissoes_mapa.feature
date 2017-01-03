# language: pt
@permissoes_mapa
Funcionalidade: Verificar pelo perfil quais informações o usuário tem acesso no mapa

  Cenário: Usuário sem permissão para ver outra áreas
    Dado que o usuário esteja logado no sistema
    E que o usuário deslogue no sistema
    E o usuário confirmar
    E que o usuário aguarde um tempo de "1000" milisegundos
    Dado o usuário logue no sistema com usuário "mobilab" e perfil administrador
    Quando for desabilitada no perfil visualizar todas as áreas
    E que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar em "Ver no mapa"
    Então o sistema deve redirecionar para o mapa
    Quando o usuário clicar no menu filtros para "abrir"
    E o sistema deverá mostrar no mapa "2" controladores
    E o sistema deverá mostrar no mapa "2" controladores offline
    E o sistema deverá mostrar no mapa "3" aneis
    E o sistema deverá mostrar no mapa "0" aneis offline
    E o usuário não consiga selecionar o valor "2" para o campo "areas"
    E o usuário não consiga selecionar o valor "2.000.0001" para o campo "controladores"
    E o usuário não consiga selecionar o valor "AREA SUL PAULISTA" para o campo "subarea"
