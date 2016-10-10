# language: pt
@controladores @resumo @interfaces
Funcionalidade: Vizualizar as informações de um controlador cadastrado

  Cenário: Acessar os controlador
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar em "Configuração"
    Então o sistema deverá redirecionar para a revisão do controlador

  Cenário: Vizualizar os dados básicos do controlador
  	Então o sistema deverá mostrar o campo "Cidade:" com a seguinte informação "São Paulo"
  	Então o sistema deverá mostrar o campo "Área:" com a seguinte informação "1"
  	Então o sistema deverá mostrar o campo "CLC:" com a seguinte informação "1.000.0001"
  	Então o sistema deverá mostrar o campo "Fabricante:" com a seguinte informação "Raro Labs"
  	Então o sistema deverá mostrar o campo "Modelo:" com a seguinte informação "Modelo Básico"
  	Então o sistema deverá mostrar o campo "Estágios:" com a seguinte informação "6"
  	Então o sistema deverá mostrar o campo "Controladores.aneis:" com a seguinte informação "2"