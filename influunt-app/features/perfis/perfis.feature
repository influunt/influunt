# language: pt
@perfis @interfaces
Funcionalidade: Verificar o perfil e seus acessos
  Como um usuário com um determinado perfil
  Posso ter apenas acesso a algumas funcionalidades
  Afim de verificar o controle de permissões

  Cenário: Para alterar o perfil do usuário ele deverá deslogar do sistema
    Dado que o usuário esteja logado no sistema
    E que o usuário deslogue no sistema
    E o usuário responde sim

  Cenário: O usuário so poderá ver o controlador que ele está associado
    Dado o usuário logue no sistema com usuário "mobilab" e perfil "programador"
    E que possua controladores com áreas diferentes cadastrados
    E que o usuário acesse a página de listagem de controladores
    Então o sistema deverá mostrar "2" controladores cadastrados
    Quando for desabilitada no perfil visualizar todas as áreas
    E que o usuário acesse a página de listagem de controladores
    Então o sistema deverá mostrar "1" controladores cadastrados
    E o sistema deverá mostrar o controlador da área "1"
    Quando a área 2 for setada para o usuário
    E que o usuário acesse a página de listagem de controladores
    Então o sistema deverá mostrar "1" controladores cadastrados
    Então o sistema deverá mostrar o controlador da área "2"

  Cenário: Com o perfil básico não posso ter acesso a certas funcionalidades
    Dado o usuário logue no sistema com usuário "mobilab" e perfil "basico"
    Então o usuário deve ser enviado para a tela de dashboard
    Então o usuário não deve ter acesso ao menu "Auditoria"
    E o usuário não deverá ter acesso a listagem de "auditorias"
    E o usuário não deve ter acesso ao menu "Programação"
    E o usuário não deverá ter acesso a listagem de "controladores"
    E o usuário não deve ter acesso ao menu "Usuários"
    E o usuário não deverá ter acesso a listagem de "usuarios"
    E o usuário não deve ter acesso ao menu "Perfis"
    E o usuário não deverá ter acesso a listagem de "perfis"
    E o usuário não deve ter acesso ao menu "Modelos"
    E o usuário não deverá ter acesso a listagem de "modelos"

  Cenário: Com o perfil básico preciso ter acesso as seguintes funcionalidades
    Dado que o usuário esteja logado no sistema
    Então o usuário deverá ter acesso a "Cidades"
    E o usuário deverá ter acesso a "Fabricantes"
    E o usuário deverá ter acesso a "Áreas"
    E o usuário deverá ter acesso a "Subáreas"

  Cenário: Com o perfil programador não posso ter acesso a certas funcionalidades
    Dado o usuário logue no sistema com usuário "mobilab" e perfil "programador"
    Então o usuário deve ser enviado para a tela de dashboard
    Então o usuário não deve ter acesso ao menu "Auditoria"
    E o usuário não deverá ter acesso a listagem de "auditorias"
    E o usuário não deve ter acesso ao menu "Usuários"
    E o usuário não deverá ter acesso a listagem de "usuarios"
    E o usuário não deve ter acesso ao menu "Perfis"
    E o usuário não deverá ter acesso a listagem de "perfis"

  Cenário: Com o perfil programador preciso ter acesso as seguintes funcionalidades
    Dado que o usuário esteja logado no sistema
    Então o usuário deverá ter acesso a "Programação"
    E o usuário terá a opção de clicar no botão novo controlador
    Dado o usuário navegar pelo breadcrumb clicando em "Programação"
    Então o usuário terá a opção visualizar o botão "Ver no mapa"
