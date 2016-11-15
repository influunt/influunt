# language: pt
@historico_controlador
Funcionalidade: Visualizar histórico do controlador
  Contexto:
    Dado que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar em "Visualizar Configuração" do controlador "2.000.0001"

  Cenário: Visualizar informações de forma ordenada por data
    Quando o usuário clicar em "Histórico"
    Então o sistema deve mostrar um hitórico das alterações
    E o sistema deverá mostrar na "1" posição com a data "13 de Novembro de 2016"
    E o sistema deverá mostrar na "2" posição com a data "12 de Novembro de 2016"
    E o sistema deverá mostrar na "3" posição com a data "8 de Novembro de 2016"


