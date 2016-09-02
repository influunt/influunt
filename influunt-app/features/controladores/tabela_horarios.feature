# language: pt
@controladores @tabela_horarios @interfaces

Funcionalidade: Fluxo para setar tabela de horários
  Cenário: Acesso à tela tabela de horários
    Dado que o sistema possui ao menos um controlador configurado
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar no botão Tabela Horários
    Então o sistema deverá redirecionar para tabelas horários

  Cenário: Habitar edição da tabela horáios
    Dado que o usuário esteja na página tabela horários
    Quando o usuário clicar no botão editar
    Então a tabela horários deverá estar editável

  Cenário: Adicionar um envento
    Dado que o usuário esteja na página tabela horários
    E o usuario selecionar "Domingo" no campo "Dias"
    E o usuario selecionar "12" no campo "Hora"
    E o usuario selecionar "0" no campo "Minuto"
    E o usuario selecionar "0" no campo "Segundo"
    E o usuario selecionar "Plano 1" no campo "Plano"
    Então o sistema deverá preencher a tabela horário

