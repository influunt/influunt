# language: pt
@crud_perfis @interfaces
Funcionalidade: Realizar os cadastros
  Como um usuário administrador
  Posso realizar um cadastro de perfil no sistema
  Afim possuir perfis cadastrados

  Cenário: Validar a não exclusão de perfis que possuam associação
    Dado o usuário acessar a tela de listagem de "perfis"
    E o usuário na tabela clicar em "Excluir" do registro "Administrador"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então o sistema deverá mostrar "4" items na tabela
    E o sistema exibe uma mensagem "Esse perfil não pode ser removido, pois existe(m) usuário(s) vinculado(s) ao mesmo."

  Cenário: Listagem de Perfis
    Dado que o usuário esteja logado no sistema
    E o usuário acessar a tela de listagem de "perfis"
    Então o sistema deverá redirecionar a listagem de perfis

  Cenário: Acesso à tela de novo perfil
    Dado o usuário acessar a tela de listagem de "perfis"
    Quando o usuário clicar no botão novo Perfil
    Então o sistema deverá redirecionar para o formulário "Perfil"

  Cenário: Validar presença no campo nome
    Quando clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "perfil_nome"

  Cenário: Cadastrar um novo perfil
    Dado o usuário preencher o campo "Nome Perfil" com "Perfil Teste"
    Quando clicar no botão de salvar
    Então o sistema deverá redirecionar a listagem de perfis

  Cenário: Edição de perfil
    Dado o usuário acessar a tela de listagem de "perfis"
    E clicar no botão de "Editar" o "Perfil Teste"
    Quando o usuário preencher o campo "Nome Perfil" com "Perfil Teste 2"
    E clicar no botão de salvar
    Então o perfil deverá ser salvo com nome igual a "Perfil Teste 2"
    E o sistema deverá redirecionar a listagem de perfis

  Cenário: Exclusão de perfis
    Dado o usuário acessar a tela de listagem de "perfis"
    E clicar no botão de "Excluir" o "Perfil Teste 2"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então perfil "Perfil Teste 2" deve ser excluído

  Cenário: Como administrador posso definir os acessos de perfis
    Dado o usuário logue no sistema com usuário "mobilab" e perfil administrador
    E o usuário acessar a tela de listagem de "perfis"
    Então o sistema deverá redirecionar a listagem de perfis
    E o sistema deverá mostrar "4" na listagem
    Quando o usuário clicar em "Definir permissões" do perfil "Administrador"
    E o usuário desmarcar a permisão "[Perfis] - Definir as permissões dos perfis"
    Quando clicar no botão de salvar
    Então o sistema deverá redirecionar a listagem de perfis

  Cenário: Usuário sem permisão para perfis
    Dado que o usuário não possa definir perfis
    E o usuário acessar a tela de listagem de "perfis"
    Então o botão "Definir permissões" não deverá aparecer
