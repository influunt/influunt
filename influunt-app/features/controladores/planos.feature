# language: pt
@controladores @planos @interfaces
Funcionalidade: Fluxo de cadastro de planos

  Cenário: Acesso à tela de cadastro planos
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar no botão "Planos"
    Então o sistema deverá redirecionar para a tela de planos

  Cenário: Cancelar a edição de um plano
    Dado que o usuário esteja na página de planos
    Quando o usuário clicar em "Editar"
    E o usuário clicar em "Cancelar Edição"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário responde sim
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores

  Cenário: Editar os planos
    Quando o usuário clicar no botão "Planos"
    Dado que o usuário esteja na página de planos
    Quando o usuário clicar em "Editar"
    Então o "PLANO 1" deverá estar ativado

  Cenário: Apresentar erro com estágios proibidos
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Apagado"
    Então o sistema deverá apresentar erro de "transição proibida de E3 para E4"

  Cenário: Remover estágio com transição proibida
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Apagado"
    E que o usuário clique no botão apagar o estagio "E4"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário responde sim
    Então a quantidade de estagios na lista deverá ser 3

  Cenário: Selecionar modo de operação coordenado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Coordenado"
    Então o diagrama de ciclos deverá marcar o grupo semafórico "G1" como "Operação Normal"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G2" como "Operação Normal"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G3" como "Operação Normal"
    E o usuário deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário deve ter a opção de marcar a defasagem do ciclo

  Cenário: Selecionar modo de operação isolado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Isolado"
    Então o diagrama de ciclos deverá marcar o grupo semafórico "G1" como "Operação Normal"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G2" como "Operação Normal"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G3" como "Operação Normal"
    E o usuário deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário não deve ter a opção de marcar a defasagem do ciclo

  Cenário: Selecionar modo de operação apagado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Apagado"
    Então o diagrama de ciclos deverá marcar o grupo semafórico "G1" como "Apagado"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G2" como "Apagado"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G3" como "Apagado"
    E o usuário não deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário não deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário não deve ter a opção de marcar a defasagem do ciclo

  Cenário: Selecionar modo de operação intermitente
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Intermitente"
    Então o diagrama de ciclos deverá marcar o grupo semafórico "G1" como "Intermitente"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G2" como "Intermitente"
    E o diagrama de ciclos deverá marcar o grupo semafórico "G3" como "Apagado"
    E o usuário não deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário não deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário não deve ter a opção de marcar a defasagem do ciclo

  Cenário: Selecionar modo de operação atuado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Atuado"
    Então o diagrama de intervalos não deverá aparecer

  Cenário: Configurar Plano Isolado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Isolado"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 9 segundos para o "Tempo de Verde"
    Então o sistema deverá mostar um alerta para verdes segurança menor
    E o usuário responde sim para verde de segurança
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 9 segundos para o "Tempo de Verde"
    Então o sistema deverá mostar um alerta para verdes segurança menor
    Quando o usuário responde sim para verde de segurança
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E3"
    E que o usuário marque 6 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário selecione o anel 2
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 12 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 12 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração

  Cenário: Remover estágio em modo de operação Isolado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o anel 1
    E que o usuário selecione o modo de operação "Isolado"
    E que o usuário clique no botão apagar o estagio "E1"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário responde sim
    Então a quantidade de estagios na lista deverá ser 2

  Cenário: Adicionar estágio novamente para o plano
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Isolado"
    E o usuário decide adicionar o estágio removido "+E1"
    Então a quantidade de estagios na lista deverá ser 3

  Cenário: Copiar um plano
    Dado que o usuário esteja na página de planos
    E que o usuário clicar em copiar o "PLANO 1"
    Então o sistema exibe uma caixa para copiar o plano 1
    E o usuário selecionar o "PLANO 16"
    Quando o usuário clicar no botão copiar
    Então o "PLANO 16" deverá estar ativado

  Cenário: Editar o nome de um Plano
    Dado que o usuário esteja na página de planos
    E que o usuário clicar em editar o "PLANO 1"
    Então o sistema exibe uma caixa para renomear o plano
    E o usuário prenche o campo com "PLANO 77"
    Quando o usuário responde sim
    Então o sistema deve alterar o nome para "PLANO 77"

  Cenário: Tentar salvar o plano incorreto
    Dado que o usuário esteja na página de planos
    Então o sistema deverá apresentar erro de "Tempo de Ciclo é diferente da soma dos tempos dos estágios."
    E que o usuário marque 45 segundos para o "TEMPO DE CICLO"
    E o usuário clicar em "Salvar"
    E o sistema deverá apresentar erro de "G2 - O tempo de verde está menor que o tempo de segurança configurado."
    E o sistema deverá mostrar erro no plano 1
    E o sistema deverá mostrar erro no plano 16
    Então o usuário queira limpar o plano 16
    E o usuário responde sim

  Cenário: Salvar o Plano no modo Isolado
    Dado que o usuário esteja na página de planos
    E o usuário realize um scroll up
    E que o usuário clicar no plano 1
    E que o usuário marque 45 segundos para o "TEMPO DE CICLO"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 11 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 12 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E3"
    E que o usuário marque 13 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
