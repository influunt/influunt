# language: pt
@crud_perfis @interfaces
Funcionalidade: Realizar os cadastros
  Como um usuário administrador
  Posso realizar um cadastro de perfil no sistema
  Afim possuir perfis cadastrados

  Cenário: Listagem de Perfis
    Dado que o usuário esteja logado no sistema
    E que o usuário acesse a página de listagem de perfis
    Então o sistema deverá redirecionar a listagem de perfis

  Cenário: Acesso à tela de novo perfil
    Dado que o usuário acesse a página de listagem de perfis
    Quando o usuário clicar no botão novo Perfil
    Então o sistema deverá redirecionar para o formulário "Perfil"

  Cenário: Validar presença no campo nome
    Quando clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "perfil_nome"

  Cenário: Cadastrar um novo perfil
    Dado o usuário preencher o campo "Nome Perfil" com "Perfil Teste"
    Quando clicar no botão de salvar
    Então o sistema deverá redirecionar a listagem de perfis

  Cenário: Como administrador posso definir os acessos de perfis
    Dado que o usuário esteja logado no sistema
    E que o usuário acesse a página de listagem de perfis
    Então o sistema deverá redirecionar a listagem de perfis
    E o sistema deverá mostrar "5" na listagem
    Quando o usuário clicar em "Definir permissões" do perfil "Administrador"
    E o usuário desmarcar a permisão "[Perfis] - Definir as permissões dos perfis"
    Quando clicar no botão de salvar
    Então o sistema deverá redirecionar a listagem de perfis

  Cenário: Usuário sem permisão para perfis
    Dado que o usuário não possa definir perfis
    E que o usuário acesse a página de listagem de perfis
    Então o botão "Definir permissões" não deverá aparecer 

  Cenário: Edição de cidades
    Dado que o usuário acesse a página de listagem de perfis
    E clicar no botão de "Editar" o "Perfil Teste"
    Quando o usuário preencher o campo "Nome Perfil" com "Perfil Teste 2"
    E clicar no botão de salvar
    Então o perfil deverá ser salvo com nome igual a "Perfil Teste 2"
    E o sistema deverá redirecionar a listagem de perfis

  Cenário: Exclusão de perfis
    Dado que o usuário acesse a página de listagem de perfis
    E clicar no botão de "Excluir" o "Perfil Teste 2"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário responde sim
    Então perfil "Perfil Teste 2" deve ser excluído

    