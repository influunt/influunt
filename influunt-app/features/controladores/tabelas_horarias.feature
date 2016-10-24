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

  Cenário: Tentar salvar uma tabela horário com eventos conflitantes
    Dado o usuário selecionar o valor "DOMINGO" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "10" no campo "Minuto" para o evento
    E o usuário selecionar o valor plano "1" no campo "Plano" para o evento
    Então o usuário selecionar o valor "DOMINGO" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "10" no campo "Minuto" para o evento
    E o usuário selecionar o valor plano "1" no campo "Plano" para o evento
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no evento

  Cenário: Adicionar um evento na tabela no qual o plano não esteja configurado
    E o quadro de horário deverá marcar "Domingo", na hora "12", com a cor "Vermelha"
    E o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no evento

  Cenário: Descartar um plano
    Quando o usuário clicar em "Descartar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores

  Cenário: Adicionar evento para o plano 1 na segunda-feira
    Dado que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar no botão Tabela Horária do controlador
    Então o sistema deverá redirecionar para a tela de tabela horária
    Quando o usuário clicar em "Editar"
    E o usuário selecionar o valor "SEGUNDA" no campo "Dias" para o evento
    E o usuário selecionar o valor "10" no campo "Hora" para o evento
    E o usuário selecionar o valor "11" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "1" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Vermelha" no evento "1"
    E o quadro de horário deverá marcar "Segunda", na hora "10", com a cor "Vermelha"

  Cenário: Adicionar evento para o plano 2 na terça-feira
    Dado o usuário selecionar o valor "TERCA" no campo "Dias" para o evento
    E o usuário selecionar o valor "9" no campo "Hora" para o evento
    E o usuário selecionar o valor "59" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "2" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "VerdeClara" no evento "2"
    E o quadro de horário deverá marcar "Terça", na hora "9", com a cor "VerdeClara"
    E o quadro de horário deverá marcar "Segunda", na hora "9", com a cor "VerdeClara"

  Cenário: Adicionar evento para o plano 3 na quarta-feira
    Dado o usuário selecionar o valor "QUARTA" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "0" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "3" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Roxa" no evento "3"
    E o quadro de horário deverá marcar "Quarta", na hora "12", com a cor "Roxa"
    E o quadro de horário deverá marcar "Segunda", na hora "9", com a cor "Roxa"

  Cenário: Adicionar evento para o plano 4 na quinta-feira
    Dado o usuário selecionar o valor "QUINTA" no campo "Dias" para o evento
    E o usuário selecionar o valor "21" no campo "Hora" para o evento
    E o usuário selecionar o valor "53" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "4" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Azul" no evento "4"
    E o quadro de horário deverá marcar "Quinta", na hora "21", com a cor "Azul"
    E o quadro de horário deverá marcar "Segunda", na hora "9", com a cor "Azul"

  Cenário: Adicionar evento para o plano 5 na sexta-feira
    Dado o usuário selecionar o valor "SEXTA" no campo "Dias" para o evento
    E o usuário selecionar o valor "3" no campo "Hora" para o evento
    E o usuário selecionar o valor "42" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "5" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Laranja" no evento "5"
    E o quadro de horário deverá marcar "Sexta", na hora "3", com a cor "Laranja"
    E o quadro de horário deverá marcar "Segunda", na hora "9", com a cor "Laranja"

  Cenário: Adicionar evento para o plano 6 de segunda a sabado
    Dado o usuário selecionar o valor "SEGUNDA_A_SABADO" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "52" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "6" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Preta" no evento "6"
    E o quadro de horário deverá marcar "Segunda", na hora "12", com a cor "Preta"
    E o quadro de horário deverá marcar "Sabado", na hora "12", com a cor "Preta"
    E o quadro de horário deverá marcar "Quinta", na hora "12", com a cor "Preta"
    E o quadro de horário deverá marcar "Quinta", na hora "20", com a cor "Preta"

  Cenário: Adicionar evento para o plano 7 de sabado a domingo
    Dado o usuário selecionar o valor "SABADO_A_DOMINGO" no campo "Dias" para o evento
    E o usuário selecionar o valor "11" no campo "Hora" para o evento
    E o usuário selecionar o valor "33" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "7" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "VerdeEscuro" no evento "7"
    E o quadro de horário deverá marcar "Domingo", na hora "11", com a cor "VerdeEscuro"
    E o quadro de horário deverá marcar "Segunda", na hora "9", com a cor "VerdeEscuro"
    E o quadro de horário deverá marcar "Sabado", na hora "11", com a cor "VerdeEscuro"

  Cenário: Adicionar evento para o plano 8 de segunda a sexta-feira
    Dado o usuário selecionar o valor "SEGUNDA_A_SEXTA" no campo "Dias" para o evento
    E o usuário selecionar o valor "7" no campo "Hora" para o evento
    E o usuário selecionar o valor "20" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "8" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Branco" no evento "8"
    E o quadro de horário deverá marcar "Segunda", na hora "7", com a cor "Branco"
    E o quadro de horário deverá marcar "Segunda", na hora "9", com a cor "Branco"
    E o quadro de horário deverá marcar "Sexta", na hora "11", com a cor "Branco"
    Então o sistema deverá apresentar a aba com o valor "8"

  Cenário: Apagar eventos da tabela
    Quando que o usuário remover o ultimo evento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    Então o evento "8" deverá ser excluído
    Quando que o usuário remover o ultimo evento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    Então o evento "7" deverá ser excluído
    Quando que o usuário remover o ultimo evento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    Então o evento "6" deverá ser excluído
    Quando que o usuário remover o ultimo evento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    Então o evento "5" deverá ser excluído
    Quando que o usuário remover o ultimo evento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    Então o evento "4" deverá ser excluído
    Quando que o usuário remover o ultimo evento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    Então o evento "3" deverá ser excluído
    Quando que o usuário remover o ultimo evento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    Então o evento "2" deverá ser excluído
    Quando que o usuário remover o ultimo evento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    Então o evento "1" deverá ser excluído
    E não deve mais possuir eventos inseridos
    E o sistema deverá apresentar a aba com o valor "0"

  Cenário: Lançar planos isolado e coordenado que estejam configurados
    Quando o usuário selecionar o valor "DOMINGO" no campo "Dias" para o evento
    E o usuário selecionar o valor "12" no campo "Hora" para o evento
    E o usuário selecionar o valor "15" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "1" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Vermelha" no evento "1"
    E o quadro de horário deverá marcar "Domingo", na hora "12", com a cor "Vermelha"
    E o quadro de horário deverá marcar "Segunda", na hora "13", com a cor "Vermelha"
    Dado o usuário selecionar o valor "SEGUNDA" no campo "Dias" para o evento
    E o usuário selecionar o valor "14" no campo "Hora" para o evento
    E o usuário selecionar o valor "20" no campo "Minuto" para o evento
    E o usuário selecionar o valor plano "2" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "VerdeClara" no evento "2"
    E o quadro de horário deverá marcar "Segunda", na hora "14", com a cor "VerdeClara"
    E o quadro de horário deverá marcar "Domingo", na hora "11", com a cor "VerdeClara"

  Cenário: Lançar plano intermitente que esteja configurado
    Quando o usuário selecionar o valor "TERCA" no campo "Dias" para o evento
    E o usuário selecionar o valor "10" no campo "Hora" para o evento
    E o usuário selecionar o valor "2" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "3" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Roxa" no evento "3"
    E o quadro de horário deverá marcar "Terça", na hora "10", com a cor "Roxa"
    E o quadro de horário deverá marcar "Quarta", na hora "17", com a cor "Roxa"

  Cenário: Lançar plano apagado que esteja configurado
    Dado o usuário selecionar o valor "QUARTA" no campo "Dias" para o evento
    E o usuário selecionar o valor "18" no campo "Hora" para o evento
    E o usuário selecionar o valor "10" no campo "Minuto" para o evento
    E o usuário selecionar o valor plano "4" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Azul" no evento "4"
    E o quadro de horário deverá marcar "Quarta", na hora "18", com a cor "Azul"
    E o quadro de horário deverá marcar "Quinta", na hora "14", com a cor "Azul"

  Cenário: Lançar plano atuado que esteja configurado
    Dado o usuário selecionar o valor "QUINTA" no campo "Dias" para o evento
    E o usuário selecionar o valor "15" no campo "Hora" para o evento
    E o usuário selecionar o valor "20" no campo "Minuto" para o evento
    E o usuário selecionar o valor plano "5" no campo "Plano" para o evento
    Então o sistema deverá adicionar uma cor "Laranja" no evento "5"
    E o quadro de horário deverá marcar "Quinta", na hora "15", com a cor "Laranja"
    E o quadro de horário deverá marcar "Domingo", na hora "11", com a cor "Laranja"

  Cenário: Visulizar um diagrama em modo isolado
    Quando o usuário clicar em visualizar o diagrama do evento "1"
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G1" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G1" com "indicacao-vermelho" em "31s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G1" com "indicacao-verde" em "11s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G2" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G2" com "indicacao-verde" em "12s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G2" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G2" com "indicacao-vermelho" em "27s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G3" com "indicacao-vermelho" em "18s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G3" com "indicacao-verde" em "13s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G3" com "indicacao-vermelho-intermitente" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G3" com "indicacao-vermelho" em "11s" segundos
    E que o usuário selecione o anel 2
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G4" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G4" com "indicacao-verde" em "18s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G4" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G4" com "indicacao-vermelho" em "21s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G5" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G5" com "indicacao-vermelho" em "21s" segundos
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G5" com "indicacao-verde" em "21s" segundos
    Dado o usuário clicar em fechar o diagrama

  Cenário: Visulizar um diagrama em modo Coordenado
    Quando o usuário clicar em visualizar o diagrama do evento "2"
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-verde" em "14s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-vermelho" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-vermelho" em "20s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-verde" em "14s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G3" com "indicacao-vermelho-intermitente" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G3" com "indicacao-vermelho" em "34s" segundos
    E que o usuário selecione o anel 2
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-verde" em "12s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-vermelho" em "12s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G5" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G5" com "indicacao-vermelho" em "15s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G5" com "indicacao-verde" em "12s" segundos
    Dado o usuário clicar em fechar o diagrama

  Cenário: Visulizar um diagrama em modo Intermitente
    Quando o usuário clicar em visualizar o diagrama do evento "3"
    Então o sistema deve mostrar o diagrama "Intermitente" no grupo "G1" com "indicacao-amarelo-intermitente" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Intermitente" no grupo "G2" com "indicacao-amarelo-intermitente" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Intermitente" no grupo "G3" com "indicacao-apagado" em "30s" segundos
    E que o usuário selecione o anel 2
    Então o sistema deve mostrar o diagrama "Intermitente" no grupo "G4" com "indicacao-amarelo-intermitente" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Intermitente" no grupo "G4" com "indicacao-amarelo-intermitente" em "30s" segundos
    Dado o usuário clicar em fechar o diagrama

  Cenário: Visulizar um diagrama em modo Apagado
    Quando o usuário clicar em visualizar o diagrama do evento "4"
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G1" com "indicacao-apagado" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G2" com "indicacao-apagado" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G3" com "indicacao-apagado" em "30s" segundos
    E que o usuário selecione o anel 2
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G4" com "indicacao-apagado" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G4" com "indicacao-apagado" em "30s" segundos
    Dado o usuário clicar em fechar o diagrama

  Cenário: Visulizar um diagrama em modo Atuado
    Quando o usuário clicar em visualizar o diagrama do evento "5"
    Então o sistema deve mostrar um alert com a mensagem atuado não possue diagrama
    E o usuário confirmar
    Então o sistema deverá redirecionar para a tela de tabela horária

  Cenário: Salvar tabela horária
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
