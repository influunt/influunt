# language: pt
@editar_controlador
Funcionalidade: Editar um controlador
  Contexto:
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar no botão "Configuração"

  Cenário: Editar transicões proibidas utilizando botão Salvar
    Dado em resumo clicar em "Editar Transições Proibidas"
    Quando o usuário desmarcar a transição de "E4" para "E3" como proibida
    E o usuário desmarcar a transição de "E3" para "E4" como proibida
    E o usuário marcar a transição de "E1" para "E2" como proibida
    E o usuário selecionar o valor "E3" no campo "Alternativa"
    E o usuário selecionar o anel 2
    E que o usuário confirme que não há configurações a serem feitas nesse anel
    E o usuário clicar em "Salvar"
    Então o sistema irá continuar no passo "Transições Proibidas"
    Dado o usuário selecionar o anel 1
    Quando o usuário desmarcar a transição de "E1" para "E2" como proibida
    E o usuário marcar a transição de "E1" para "E3" como proibida
    E o usuário selecionar o valor "E2" no campo "Alternativa"
    Quando clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Tabela Entre Verdes"
