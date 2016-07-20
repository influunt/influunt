# language: pt
@crud @perfis @interfaces
Funcionalidade: tela de cadastro de perfis

  Cenário: Listagem de perfis
    Dado que exista ao menos um perfil cadastrado no sistema
    Quando o usuário acessar a tela de listagem de perfis
    Então deve ser exibida uma lista com os perfis já cadastrados no sistema

  Cenário: Acesso à tela de novo perfil
    Quando o usuário acessar a tela de listagem de perfis
    E clicar no botão de Novo Perfil
    Então o sistema deverá redirecionar para o formulário de Cadastro de novos Perfis

  Cenário: Cadastro de perfis
    Quando o usuário acessar a tela de cadastro de novos perfis
    E o usuario preencher o campo "Nome" com "Administrador"
    E clicar no botão de salvar
    Então o registro do perfil deverá ser salvo com nome igual a "Administrador"
    E o sistema deverá retornar à tela de listagem de perfis

  Cenário: Acesso à tela de detalhes de um perfil
    Quando o usuário acessar a tela de listagem de perfis
    E clicar no botão de visualizar perfil
    Então o sistema deverá redirecionar para a tela de visualização de perfis

  Cenário: Acesso à tela de edição de perfis
    Quando o usuário acessar a tela de listagem de perfis
    E clicar no botão de editar perfil
    Então o sistema deverá redirecionar para o formulário de edição de perfis

  Cenário: Edição de perfis
    Quando o usuário acessar o formulário de edição de perfis
    E o usuario preencher o campo "Nome" com "Técnico"
    E clicar no botão de salvar
    Então o registro do perfil deverá ser salvo com nome igual a "Técnico"
    E o sistema deverá retornar à tela de listagem de perfis

  Cenário: Exclusão de perfis sem confirmação do usuário
    Quando o usuário acessar a tela de listagem de perfis
    E clicar no botão de excluir um perfil
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o perfil
    Quando o usuário responde não
    Então nenhum perfil deve ser excluído

  Cenário: Exclusão de perfis com confirmação do usuário
    Quando o usuário acessar a tela de listagem de perfis
    E clicar no botão de excluir um perfil
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o perfil
    Quando o usuário responde sim
    Então o perfil deverá ser excluido
