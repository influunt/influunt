# language: pt
@simulacao
Funcionalidade: Simulação
  Quero realizar a simulação dos meus controladores

  Cenário: Acesso a tela de simulação
    Dado que o usuário esteja logado no sistema
    E que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores

  Cenário: So posso acessar a simulação de controladores em configuração
    Dado o usuário esteja na listagem de controladores
    Então o sistema não deverá mostrar o botão "Simular" do controlador "R. Maria Figueiredo com Av. Paulista"
    E o sistema não deverá mostrar o botão "Simular" do controlador "R. Maria Figueiredo, nº -4"

  Cenário: Entrar no simulador
    Dado o usuário esteja na listagem de controladores
    Quando o usuário clicar em "Simular" do controlador "Av. Paulista com R. Pamplona"
    E o sistema não deverá mostrar o botão "Simular" do controlador "1.000.0002"
    E o sistema não deverá mostrar o botão "Simular" do controlador "1.000.0003"

  Cenário: Não pode simular com um alarme sem data e horário
    Dado usuário clicar em abrir "Programar disparo de alarmes"
    Quando o usuário no campo "plano" selecionar o valor "Abertura da porta principal do controlador" para o label
    E usuário clicar em simular
    Então o sistema deverá apresentar erro no campo "data-alarme"
    E o sistema deverá indicar erro no campo "hora"

  Cenário: Não pode simular com falhar sem informar data e horário
    Dado usuário clicar em abrir "Programar falhas no controlador"
    Quando o usuário no campo "falha" selecionar o valor "Detector pedestre - Falta de acionamento" para o label
    E usuário clicar em simular
    Então o sistema deverá apresentar erro no campo "data-falhas"
    E o sistema deverá indicar erro no campo "hora"

  Cenário: Quero ver o resumo do controlador
    Dado o usuário clicar para visualizar o resumo
    Então o sistema deverá mostrar as informações iniciais do controlador 1 e anel 1

  Cenário: Prencher corretamente os campos para alarme
    Dado o usuário realizar um scroll down no modal
    E o usuário clicar em fechar o modal "myModal"
    E o usuário remova falha da simulação
    E o usuário confirmar
    Quando o usuário digitar no campo "data-alarme" com a informação "20/12/2016"
    E o usuário no campo "hora" selecionar o valor "2"
    E o usuário no campo "minuto" selecionar o valor "0"
    E o usuário no campo "segundo" selecionar o valor "20"

  Cenário: Prencher corretamente os campos para alarme
    Dado usuário clicar em simular
    Então o simulador deverá aparecer
