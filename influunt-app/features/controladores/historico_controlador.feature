# language: pt
@historico
Funcionalidade: Visualizar histórico do controlador, tabela horária e planos
  Contexto:
    Dado que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores

  Cenário: Visualizar histórico de forma ordenada por data do controlador
    Quando o usuário clicar em "Visualizar Configuração" do controlador "2.000.0001"
    E o usuário clicar em "Histórico"
    Então o sistema deve mostrar um hitórico das alterações
    E o sistema deverá mostrar na "1" posição com a data "13 de Novembro de 2016"
    E o sistema deverá mostrar na "2" posição com a data "12 de Novembro de 2016"
    E o sistema deverá mostrar na "3" posição com a data "8 de Novembro de 2016"
    Então o usuário clicar em fechar o modal "modal-timeline"

  Cenário: Visualizar histórico de forma ordenada por data da tabela horária
    Dado o usuário clicar em "Tabela Horária" do controlador "1.000.0001"
    E o usuário clicar em "Histórico"
    Então o sistema deve mostrar um hitórico das alterações
    E o sistema deverá mostrar na "1" posição com a data "16 de Novembro de 2016"
    E o sistema deverá mostrar na "2" posição com a data "15 de Novembro de 2016"
    Quando o usuário em histórico clicar em ver alterações
    Então o sistema deve redirecionar para tela tabela horárias e suas diferenças
    E o sistema deve possuir alteração no evento de "Terça-feira" como "removido"
    E o sistema deve possuir alteração no evento de "Segunda-feira" como "adicionado"




