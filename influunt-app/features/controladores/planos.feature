# language: pt
@controladores @planos @interfaces
Funcionalidade: Fluxo de cadastro de planos

  Cenário: Acesso à tela de cadastro planos
    Dado que o sistema possui ao menos um controlador configurado
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar no botão Planos do controlador
    Então o sistema deverá redirecionar para a tela de planos

  # Cenário: Tentar salvar planos em branco
  #   Dado que o usuário esteja na página de planos
  #   E que o usuário deixe os campos em branco
  #   Quando o usuário clicar no botão de Salvar
  #   Então o sistema deverá indicar erro nos campos obrigatórios
  #   E o sistema irá continuar na página de planos

  Cenário: Selecionar modo de operação apagado
    # Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Apagado"
    Então o diagrama de ciclos deverá marcar o grupo semafórico "G1" como "Apagado"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G2" como "Apagado"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G3" como "Apagado"
    E o usuário não deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário não deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário não deve ter a opção de marcar a defasagem do ciclo

  Cenário: Selecionar modo de operação intermitente
    # Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Intermitente"
    Então o diagrama de ciclos deverá marcar o grupo semafórico "G1" como "Intermitente"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G2" como "Intermitente"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G3" como "Apagado"
    E o usuário não deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário não deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário não deve ter a opção de marcar a defasagem do ciclo

  Cenário: Selecionar modo de operação isolado
    # Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Isolado"
    Então o diagrama de ciclos deverá marcar o grupo semafórico "G1" como "Operação Normal"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G2" como "Operação Normal"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G3" como "Operação Normal"
    E o usuário deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário não deve ter a opção de marcar a defasagem do ciclo

  Cenário: Selecionar modo de operação coordenado
    # Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Coordenado"
    Então o diagrama de ciclos deverá marcar o grupo semafórico "G1" como "Operação Normal"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G2" como "Operação Normal"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G3" como "Operação Normal"
    E o usuário deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário deve ter a opção de marcar a defasagem do ciclo

  Cenário: Selecionar modo de operação atuado
    # Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Atuado"
    # Então o diagrama de ciclos deverá marcar o grupo semafórico "G1" como "Operação Normal"
    # E o diagrama de ciclos deverá marcar o grupo semafórico "G2" como "Operação Normal"
    # E o diagrama de ciclos deverá marcar o grupo semafórico "G3" como "Operação Normal"
    # E o usuário deve ter a opção de selecionar uma tabela entre verdes para o plano
    # E o usuário não deve ter a opção de marcar o tempo de ciclo do estágio
    # E o usuário não deve ter a opção de marcar a defasagem do ciclo

  Cenário: Configurar Plano no modo Isolado
    # Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Isolado"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 4 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 15 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    # Então o Tempo de Ciclo deverá mudar para "34" segundos

