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
    E o usuário selecionar o valor plano "7" no campo "Plano" para o evento
    E o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no evento

  Cenário: Visualizar o número de eventos adicionados na aba
    E o usuário selecionar o valor "SEGUNDA" no campo "Dias" para o evento
    E o usuário selecionar o valor "10" no campo "Hora" para o evento
    E o usuário selecionar o valor "11" no campo "Minuto" para o evento
    E o usuário selecionar o valor plano "6" no campo "Plano" para o evento
    E o usuário selecionar o valor "TERCA" no campo "Dias" para o evento
    E o usuário selecionar o valor "9" no campo "Hora" para o evento
    E o usuário selecionar o valor "59" no campo "Minuto" para o evento
    E o usuário selecionar o valor plano "7" no campo "Plano" para o evento
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

  Cenário: Lançar planos isolado e coordenado que estejam configurados
    Quando o usuário selecionar o valor "DOMINGO" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "15" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "1" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Vermelha" no evento "1"
    E o usuário selecionar o valor "SEGUNDA" no campo "Dias" para o evento
    E o usuário selecionar o valor "14" no campo "Hora" para o evento
    E o usuário selecionar o valor "20" no campo "Minuto" para o evento
    E o usuário selecionar o valor plano "2" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "VerdeClara" no evento "2"

  Cenário: Visulizar um diagrama em modo isolado
    Quando o usuário clicar em visualizar o diagrama
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G1" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G1" com "indicacao-vermelho" em "31s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G1" com "indicacao-verde" em "11s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G2" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G2" com "indicacao-verde" em "12s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G2" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G2" com "indicacao-vermelho" em "27s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G3" com "indicacao-vermelho" em "18s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G3" com "indicacao-verde" em "13s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G3" com "indicacao-vermelho-intermitente" em "3s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G3" com "indicacao-vermelho" em "11s" segundos
    E que o usuário selecione o anel 2
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G4" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G4" com "indicacao-verde" em "18s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G4" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G4" com "indicacao-vermelho" em "21s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G5" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G5" com "indicacao-vermelho" em "21s" segundos
    Então o sistema deve mostar o diagrama "Isolado" no grupo "G5" com "indicacao-verde" em "21s" segundos
    Dado o usuário clicar em fechar o diagrama

  Cenário: Salvar tabela horária
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores

