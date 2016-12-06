# language: pt
@interfaces @login
Funcionalidade: Usuário fazendo login
  Como um usuário não logado
  Com o objetivo de entrar no sistema Influunt
  O usuário deve informar login e senha para conseguir acesso ao sistema.

  Cenário: Tentativa de acesso com senha não informada
    Quando o usuário acessa a tela de login
    E informa somente o nome "João" no campo usuário
    Então o usuário não deve conseguir acessar o sistema
    E o sistema deve informar "Campo obrigatório" para o campo de senha

  Cenário: Tentativa de acesso com usuário não informado
    Quando o usuário acessa a tela de login
    E informa somente a senha "123" no campo de senha
    Então o usuário não deve conseguir acessar o sistema
    E o sistema deve informar "Campo obrigatório" para o campo de usuário

  Cenário: Tentativa de acesso com senha ou usuário incorretos
    Quando o usuário acessa a tela de login
    E informa usuário ou senha incorretos
    Então o usuário não deve conseguir acessar o sistema
    E o sistema deve informar "usuário ou senha inválidos"

  Cenário: Recuperar senha
    Quando o usuário acessa a tela de login
    E o usuário clicar em "Esqueceu sua senha?"
    E informa o email "mobilab@mobilab.com.br" no campo de email
    E o usuário clicar no botão recuperar
    Então o sistema exibe uma mensagem "Instruções para recuperar sua senha foram enviadas para seu e-mail"

  Cenário: Tentativa de acesso com usuário e senha corretos
   Quando o usuário acessa a tela de login
   E informa usuário e senha corretos
   Então o usuário deve ser enviado para a tela de dashboard
