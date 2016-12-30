# language: pt
@tabela_horaria_subarea
Funcionalidade: Posso criar ou atualizar as tabelas horárias pela subárea

  Cenário: Criar uma tabela horária pela subárea
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o sistema possua planos para o controlador cadastrado
    E que o controlador possua subárea
    E o usuário acessar a tela de listagem de "subareas"
    Quando o usuário clicar em "Tabela Horária"
    Então o sistema deverá redirecionar para a tela de tabela horária
    Dado o usuário selecionar o valor "TERCA" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "15" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "1" no campo "Plano" para o evento
    Dado o usuário selecionar o valor "QUARTA" no campo "Dias" para o evento
    E o usuário selecionar o valor "10" no campo "Hora" para o evento
    E o usuário selecionar o valor "10" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "2" no campo "Plano" para o evento
    E o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a listagem "Subáreas"

  Cenário: Tabela horária deve ser alterada no controlador que possue a subárea
    Dado que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar em "Tabela Horária" do controlador "Av. Paulista com R. Bela Cintra"
    E o quadro de horário deverá marcar "Terça", na hora "12", com a cor "Vermelha"
    E o quadro de horário deverá marcar "Quarta", na hora "9", com a cor "Vermelha"
    E o quadro de horário deverá marcar "Quarta", na hora "10", com a cor "VerdeClara"
    E o quadro de horário deverá marcar "Terça", na hora "11", com a cor "VerdeClara"

  Cenário: Deletar um evento da tabela
    Dado que o sistema possua controladores cadastrados e configurados
    E o usuário acessar a tela de listagem de "subareas"
    Quando o usuário clicar em "Tabela Horária"
    Então o sistema deverá redirecionar para a tela de tabela horária
    Quando que o usuário remover o ultimo evento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    Então o evento "6" deverá ser excluído

  Cenário: Adicionar um evento na tabela horária
    Dado o usuário selecionar o valor "TERCA" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "15" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "2" no campo "Plano" para o evento
    Então o quadro de horário deverá marcar "Terça", na hora "12", com a cor "Preta"
    E o quadro de horário deverá marcar "Quarta", na hora "1", com a cor "Roxa"
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a listagem "Subáreas"

  Cenário: Verificar alteração na tabela horária do controlador
    Dado que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar em "Tabela Horária" do controlador "Av. Paulista, nº 1000. ref.: AREA 1"
    E o quadro de horário deverá marcar "Sexta", na hora "3", com a cor "Vermelha"
    E o quadro de horário deverá marcar "Sexta", na hora "11", com a cor "Vermelha"
    E o quadro de horário deverá marcar "Sexta", na hora "12", com a cor "VerdeClara"
    E o quadro de horário deverá marcar "Domingo", na hora "0", com a cor "VerdeClara"
    E o quadro de horário deverá marcar "Domingo", na hora "1", com a cor "Azul"
    E o quadro de horário deverá marcar "Segunda", na hora "0", com a cor "Azul"
    E o quadro de horário deverá marcar "Segunda", na hora "1", com a cor "Roxa"
    E o quadro de horário deverá marcar "Terça", na hora "11", com a cor "Roxa"
    E o quadro de horário deverá marcar "Terça", na hora "12", com a cor "Preta"
    E o quadro de horário deverá marcar "Quarta", na hora "0", com a cor "Preta"
    E o quadro de horário deverá marcar "Quarta", na hora "1", com a cor "Roxa"
    E o quadro de horário deverá marcar "Sexta", na hora "2", com a cor "Roxa"

  Cenário: Editar controlador alterando a subárea
    Dado que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar no botão "Configuração"
    E o usuário clicar em "Editar"
    E o usuário selecionar "3" para o campo "area"
    E o usuário selecionar "3 - AREA SUL PAULISTA" para o campo "subarea"
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Anéis"

  Cenário: Ao editar uma tabela horária pela subárea deve apresentar erro caso controlador não tenha planos
    Dado o usuário acessar a tela de listagem de "subareas"
    E o usuário clicar em "Tabela Horária"
    Então o sistema deverá redirecionar para a tela de tabela horária
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá mostrar um erro com a mensagem "Controlador (Av. Paulista, nº 0 com R. Pamplona): O plano selecionado não está configurado em todos os anéis."
