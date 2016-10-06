# language: pt
@controladores @resumo @interfaces
Funcionalidade: Vizualizar as informações de um controlador cadastrado

  Cenário: Acessar os controlador
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar em "Configuração"
    Então o sistema deverá redirecionar para a revisão do controlador
