# language: pt
@controladores @tabelas_horarias @interfaces
Funcionalidade: Fluxo de cadastro de manutenção de Tabelas Horárias

  Cenário: Acesso à tela de tabelas horárias
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o sistema possui planos para o controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar no botão Tabela Horária do controlador
    Então o sistema deverá redirecionar para a tela de tabela horária

  Cenário: A tabela horária deve possuir ao menos um envento configurado
    Quando o usuário clicar em "Editar"
    E o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro de "A tabela horária deve ter pelo menos 1 evento configurado."

  Cenário: Adicionar um evento na tabela no qual o plano não esteja configurado
    E o usuário selecionar o valor "DOMINGO" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "10" no campo "Minuto" para o evento
    E o usuário selecionar o valor "Plano 5" no campo "Plano" para o evento
    E o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no evento

  Cenário: Visualizar o número de eventos adicionados na aba
    E o usuário selecionar o valor "SEGUNDA" no campo "Dias" para o evento
    E o usuário selecionar o valor "10" no campo "Hora" para o evento
    E o usuário selecionar o valor "11" no campo "Minuto" para o evento
    E o usuário selecionar o valor "Plano 6" no campo "Plano" para o evento
    E o usuário selecionar o valor "TERCA" no campo "Dias" para o evento
    E o usuário selecionar o valor "9" no campo "Hora" para o evento
    E o usuário selecionar o valor "59" no campo "Minuto" para o evento
    E o usuário selecionar o valor "Plano 7" no campo "Plano" para o evento
    Então o sistema deverá apresentar a aba com o valor "3"

  Cenário: Apagar eventos da tabela
    E que o usuário remover o ultimo evento
    Quando o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Então o usuário responde sim
    E que o usuário remover o ultimo evento
    Quando o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Então o usuário responde sim
    E que o usuário remover o ultimo evento
    Quando o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário responde sim
    Então não deve mais possuir eventos inseridos

  Cenário: Adicionar eventos na tabela no qual os planos estejam configurados
    E o usuário selecionar o valor "DOMINGO" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "15" no campo "Minuto" para o evento
    E o usuário selecionar o valor "Plano 1" no campo "Plano" para o evento
    E o usuário selecionar o valor "SEGUNDA" no campo "Dias" para o evento
    E o usuário selecionar o valor "14" no campo "Hora" para o evento
    E o usuário selecionar o valor "20" no campo "Minuto" para o evento
    E o usuário selecionar o valor "Plano 2" no campo "Plano" para o evento
    E o usuário selecionar o valor "TERCA" no campo "Dias" para o evento
    E o usuário selecionar o valor "15" no campo "Hora" para o evento
    E o usuário selecionar o valor "23" no campo "Minuto" para o evento
    E o usuário selecionar o valor "Plano 3" no campo "Plano" para o evento
    E o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no evento

