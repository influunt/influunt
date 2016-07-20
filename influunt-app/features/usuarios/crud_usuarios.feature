# language: pt
@crud @usuarios @interfaces
Funcionalidade: tela de cadastro de usuários

  Cenário: Listagem de usuários
    Dado que exista ao menos um usuário cadastrado no sistema
    Quando o usuário acessar a tela de listagem de usuários
    Então deve ser exibida uma lista com os usuários já cadastrados no sistema

  Cenário: Acesso à tela de novo usuário
    Quando o usuário acessar a tela de listagem de usuários
    E clicar no botão de Novo Usuário
    Então o sistema deverá redirecionar para o formulário de Cadastro de novos Usuários

  Cenário: Cadastro de usuários
    Quando o usuário acessar a tela de cadastro de novos usuários
    E o usuario preencher o campo "Nome" com "João"
    E o usuario preencher o campo "Login" com "joao"
    E o usuario preencher o campo "Email" com "joao@example.com"
    E o usuario preencher o campo "Senha" com "swordfish"
    E o usuario preencher o campo "Confirmação senha" com "swordfish"
    E o usuario selecionar o valor "1" no campo "Area"
    E o usuario selecionar o valor "Administrador" no campo "Perfil"
    E clicar no botão de salvar
    Então o registro do usuário deverá ser salvo com nome igual a "João"
    E o sistema deverá retornar à tela de listagem de usuários

  Cenário: Acesso à tela de detalhes de um usuário
    Quando o usuário acessar a tela de listagem de usuários
    E clicar no botão de visualizar um usuário
    Então o sistema deverá redirecionar para a tela de visualização de usuários

  Cenário: Acesso à tela de edição de usuários
    Quando o usuário acessar a tela de listagem de usuários
    E clicar no botão de editar usuário
    Então o sistema deverá redirecionar para o formulário de edição de usuários

  Cenário: Edição de usuários
    Quando o usuário acessar o formulário de edição de usuários
    E o usuario preencher o campo "Nome" com "José"
    E o usuario preencher o campo "Login" com "jose"
    E clicar no botão de salvar
    Então o registro do usuário deverá ser salvo com nome igual a "José"
    E o sistema deverá retornar à tela de listagem de usuários

  Cenário: Exclusão de usuários sem confirmação do usuário
    Quando o usuário acessar a tela de listagem de usuários
    E clicar no botão de excluir um usuário
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o usuário
    Quando o usuário responde não
    Então nenhum usuário deve ser excluído

  Cenário: Exclusão de usuários sem confirmação do usuário
    Quando o usuário acessar a tela de listagem de usuários
    E clicar no botão de excluir um usuário
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o usuário
    Quando o usuário responde sim
    Então o usuário deverá ser excluido
