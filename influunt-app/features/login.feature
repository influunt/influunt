# language: pt
@interfaces @login
Funcionalidade: Usuário fazendo login
  Como um usuário não logado
  Com o objetivo de entrar no sistema Influunt
  O usuário deve informar login e senha para conseguir acesso ao sistema.

  Cenário: Tentativa de acesso com senha não informada
    # Dado que o usuário não esteja logado
    Quando o usuário acessa a tela de login
    E informa somente o nome "João" no campo usuário
    Então o usuário não deve conseguir acessar o sistema
    E o sistema deve informar "Campo obrigatório" para o campo de senha

  Cenário: Tentativa de acesso com usuário não informado
    # Dado que o usuário não esteja logado
    Quando o usuário acessa a tela de login
    E informa somente a senha "123" no campo de senha
    Então o usuário não deve conseguir acessar o sistema
    E o sistema deve informar "Campo obrigatório" para o campo de usuário

  Cenário: Tentativa de acesso com senha ou usuário incorretos
    # Dado que o usuário não esteja logado
    Quando o usuário acessa a tela de login
    E informa usuário ou senha incorretos
    Então o usuário não deve conseguir acessar o sistema
    E o sistema deve informar "Usuário ou senha incorretos"

  Cenário: Tentativa de acesso com usuário e senha corretos
   # Dado que o usuário não esteja logado
   Quando o usuário acessa a tela de login
   E informa usuário e senha corretos
   Então o usuário deve ser enviado para a tela de dashboard
