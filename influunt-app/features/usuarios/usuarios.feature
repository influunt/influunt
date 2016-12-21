#language: pt
@usuarios
Funcionalidade: Realizar cadastro de usuário

  Cenário: Listagem de usuários
    Dado que o usuário esteja logado no sistema
    E o usuário acessar a tela de listagem de usuários
    Então o sistema deverá redirecionar a listagem de usuários

  Cenário: Acesso à tela de novo usuário
    Dado o usuário clicar em "Novo"
    Então o sistema deverá redirecionar para o formulário "Usuarios"

  Cenário: Validar presença nos campos
    Quando clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "usuario_nome" com a mensagem "não pode ficar em branco"
    E o sistema deverá indicar erro no campo "usuario_login" com a mensagem "não pode ficar em branco"
    E o sistema deverá indicar erro no campo "usuario_email" com a mensagem "não pode ficar em branco"
    E o sistema deverá indicar erro no campo "usuario_perfil" com a mensagem "não pode ficar em branco"

  Cenário: O campo longin deve ser único
    Dado o usuário preencher o campo "Nome Usuário" com "Jhon Doe"
    E o usuário preencher o campo "Login" com "mobilab"
    E o usuário preencher o campo "Email" com "mobilab@mobilab.com.br"
    E o usuário preencher o campo "Senha" com "123456"
    E o usuário preencher o campo "Confirmação Senha" com "123456"
    E o usuário selecionar o perfil de administrador
    Quando clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "usuario_login" com a mensagem "login já utilizado"

  Cenário: Salvar um usuário
    Dado o usuário preencher o campo "Login" com "mobilab2"
    Quando clicar no botão de salvar
    Então o sistema deverá redirecionar a listagem de usuários
