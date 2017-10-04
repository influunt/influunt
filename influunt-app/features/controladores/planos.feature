# language: pt
@controladores @planos @interfaces
Funcionalidade: Fluxo de cadastro de planos

  Cenário: Acesso à tela de cadastro planos
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar no botão "Planos"
    Então o sistema deverá redirecionar para a tela de planos

  Cenário: Editar os planos
    Quando o usuário clicar em "Editar"
    Então o "PLANO 1" deverá estar ativado

  Cenário: Apresentar erro com estágios proibidos
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Isolado"
    Então o sistema deverá apresentar erro de "transição proibida de E3 para E4"

  Cenário: O sistema deve apresentar erro quando o tempo de ciclo é menor que os estágios
    Dado que o usuário esteja na página de planos
    E que o usuário clicar no plano 1
    Quando que o usuário selecione o modo de operação "Isolado"
    E que o usuário clique no botão apagar o estagio "E4"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então o sistema deverá apresentar erro de "Tempo de Ciclo é diferente da soma dos tempos dos estágios."

  Cenário: Remover estágio com transição proibida
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Apagado"
    Então a quantidade de estagios na lista deverá ser 3

  Cenário: Configurar Plano Isolado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Isolado"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 9 segundos para o "Tempo de Verde"
    Então o sistema deverá mostrar um alerta para verdes segurança menor
    E o usuário responde sim para verde de segurança
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 9 segundos para o "Tempo de Verde"
    Então o sistema deverá mostrar um alerta para verdes segurança menor
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
    E que o usuário marque 10000 segundos para o "Tempo de Verde"
    Então o sistema deverá mostrar um alerta para valor digitado maior que o limite máximo
    E o usuário responde ok
    E que o usuário clique no botão de fechar a caixa de configuração

  Cenário: Remover estágio em modo de operação Isolado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o anel 1
    E que o usuário selecione o modo de operação "Isolado"
    E que o usuário clique no botão apagar o estagio "E1"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então a quantidade de estagios na lista deverá ser 2

  Cenário: Adicionar estágio novamente para o plano
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o modo de operação "Isolado"
    E o usuário decide adicionar o estágio removido "+E1"
    Então a quantidade de estagios na lista deverá ser 3

  Cenário: Reativar o grupo G1
    Dado que o usuário esteja na página de planos
    E o usuário no diagrama selecionar o grupo "G1"

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
    E o usuário preencha o alert com "PLANO 77"
    Quando o usuário confirmar
    Então o sistema deve alterar o nome para "PLANO 77"

  Cenário: Tentar salvar o plano incorreto
    Dado que o usuário esteja na página de planos
    E que o usuário marque 45 segundos para o "TEMPO DE CICLO"
    E o usuário clicar em "Salvar"
    E o sistema deverá apresentar erro de "G2 - O tempo de verde deve ser maior que o tempo de segurança configurado."
    E o sistema deverá mostrar erro no plano 1
    E o sistema deverá mostrar erro no plano 16
    Então o usuário queira limpar o plano 16
    E o usuário confirmar

  Cenário: Reconfigurar um plano modo Isolado de forma correta
    Dado que o usuário esteja na página de planos
    Quando o usuário realize um scroll up
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
    E o usuário realize um scroll up
    E que o usuário selecione o anel 2
    E que o usuário clicar no plano 1
    E que o usuário marque 45 segundos para o "TEMPO DE CICLO"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 18 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 21 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração

  Cenário: Em modo Cordenado o sitema deve apresentar erro ao tentar salvar com tempo entre verde não configurado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o anel 1
    E que o usuário clicar no plano 2
    E que o usuário selecione o modo de operação "Coordenado"
    E que o usuário clique no botão apagar o estagio "E4"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    E que o usuário marque 50 segundos para o "TEMPO DE CICLO"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 9 segundos para o "Tempo de Verde"
    Então o sistema deverá mostrar um alerta para verdes segurança menor
    E o usuário responde sim para verde de segurança
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 9 segundos para o "Tempo de Verde"
    Então o sistema deverá mostrar um alerta para verdes segurança menor
    E o usuário responde sim para verde de segurança
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E3"
    E que o usuário marque 1 segundos para o "Tempo de Verde"
    Então o sistema deverá mostrar um alerta para verdes segurança mínimo de pedestre
    E o usuário responde ok
    E que o usuário clique no botão de fechar a caixa de configuração
    E o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro de "G1 - O tempo de verde deve ser maior que o tempo de segurança configurado."
    Então o sistema deverá apresentar erro de "G2 - O tempo de verde deve ser maior que o tempo de segurança configurado."
    Então o sistema deverá apresentar erro de "A soma dos tempos dos estágios (31s) é diferente do tempo de ciclo (50s)."
    Então o sistema deverá apresentar erro de "Este plano deve ser configurado em todos os aneis."

  Cenário: Em modo Cordenado o sitema deve apresentar erro para defasagem maior que tempo de ciclo
    Dado que o usuário esteja na página de planos
    E o usuário deve ter a opção de marcar a defasagem do ciclo
    E que o usuário marque 80 segundos para o "Defasagem"
    E o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro em defasagem

  Cenário: Em modo Cordenado o sistema deve aceitar editar defasagem para o valor 0
    Dado que o usuário esteja na página de planos
    E o usuário deve ter a opção de marcar a defasagem do ciclo
    E o usuário realizar um scroll down
    E que o usuário marque 0 segundos para o "Defasagem"
    E o usuário clicar em "Salvar"
    Então o valor "DEFASAGEM" deverá ser "0"

  Cenário: Configurar um plano em modo coordenado
    Dado que o usuário esteja na página de planos
    E o usuário deve ter a opção de marcar a defasagem do ciclo
    E o usuário realizar um scroll down
    E que o usuário marque 5 segundos para o "Defasagem"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 14 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 14 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E3"
    E que o usuário marque 13 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-verde" em "14s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-vermelho" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-vermelho" em "20s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-verde" em "14s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-vermelho" em "13s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G3" com "indicacao-vermelho-intermitente" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G3" com "indicacao-vermelho" em "34s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G3" com "indicacao-verde" em "13s" segundos
    E o usuário realize um scroll up
    E que o usuário selecione o anel 2
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 22 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 22 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-verde" em "22s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-vermelho" em "22s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G5" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G5" com "indicacao-vermelho" em "25s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G5" com "indicacao-verde" em "22s" segundos

  Cenário: Configurar um plano temporário em modo coordenado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o anel 1
    E que o usuário clicar no plano 17
    E o usuário realize um scroll up
    E que o usuário clique no botão apagar o estagio "E4"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    E que o usuário marque 50 segundos para o "TEMPO DE CICLO"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 14 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 14 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E3"
    E que o usuário marque 13 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-verde" em "14s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G1" com "indicacao-vermelho" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-vermelho" em "20s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-verde" em "14s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G2" com "indicacao-vermelho" em "13s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G3" com "indicacao-vermelho-intermitente" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G3" com "indicacao-vermelho" em "34s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G3" com "indicacao-verde" em "13s" segundos
    E o usuário realize um scroll up
    E que o usuário selecione o anel 2
    Então o usuário queira limpar o plano 17
    E o usuário confirmar

  Cenário: Configurar um plano modo de operação intermitente
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o anel 1
    E que o usuário clicar no plano 3
    E que o usuário selecione o modo de operação "Intermitente"
    E que o usuário clique no botão apagar o estagio "E4"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então o sistema deve mostrar o diagrama "Intermitente" no grupo "G3" com "indicacao-intermitente" em "30s" segundos
    E o sistema deve mostrar o diagrama "Intermitente" no grupo "G2" com "indicacao-intermitente" em "30s" segundos
    E o sistema deve mostrar o diagrama "Intermitente" no grupo "G3" com "indicacao-intermitente" em "30s" segundos
    E o usuário não deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário não deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário não deve ter a opção de marcar a defasagem do ciclo
    Dado que o usuário selecione o anel 2
    E que o usuário selecione o modo de operação "Intermitente"
    Então o sistema deve mostrar o diagrama "Intermitente" no grupo "G4" com "indicacao-intermitente" em "30s" segundos
    E o sistema deve mostrar o diagrama "Intermitente" no grupo "G5" com "indicacao-intermitente" em "30s" segundos

  Cenário: Configurar um plano modo de operação apagado
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o anel 1
    E que o usuário clicar no plano 4
    E que o usuário selecione o modo de operação "Apagado"
    E que o usuário clique no botão apagar o estagio "E4"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G1" com "indicacao-apagado" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G2" com "indicacao-apagado" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G3" com "indicacao-apagado" em "30s" segundos
    E o usuário não deve ter a opção de selecionar uma tabela entre verdes para o plano
    E o usuário não deve ter a opção de marcar o tempo de ciclo do estágio
    E o usuário não deve ter a opção de marcar a defasagem do ciclo
    Dado que o usuário selecione o anel 2
    E que o usuário selecione o modo de operação "Apagado"
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G4" com "indicacao-apagado" em "30s" segundos
    Então o sistema deve mostrar o diagrama "Apagado" no grupo "G5" com "indicacao-apagado" em "30s" segundos
    Então o usuário não deve ter a opção de selecionar uma tabela entre verdes para o plano
    Então o usuário não deve ter a opção de marcar o tempo de ciclo do estágio
    Então o usuário não deve ter a opção de marcar a defasagem do ciclo

  Cenário: Mostrar erro ao tentar configurar atuado com verde intermediário maior
    Dado que o usuário esteja na página de planos
    E que o usuário selecione o anel 1
    E que o usuário clicar no plano 5
    E que o usuário selecione o modo de operação "Atuado"
    E que o usuário clique no botão apagar o estagio "E4"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então o diagrama de intervalos não deverá aparecer
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 10 segundos para o "TEMPO DE VERDE MIN."
    E que o usuário marque 20 segundos para o "TEMPO DE VERDE MAX."
    E que o usuário marque 100 segundos para o "TEMPO DE VERDE INTERMEDIÁRIO"
    E que o usuário marque 10 segundos para o "EXTENSÃO DE VERDE"
    E que o usuário clique no botão de fechar a caixa de configuração
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no estágio "E1"

  Cenário: Mostrar erro ao tentar configurar atuado com tempo de estágio maior tempo permanência
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 30 segundos para o "TEMPO DE VERDE MIN."
    E que o usuário marque 100 segundos para o "TEMPO DE VERDE MAX."
    E que o usuário marque 50 segundos para o "TEMPO DE VERDE INTERMEDIÁRIO"
    E que o usuário marque 10 segundos para o "EXTENSÃO DE VERDE"
    E que o usuário clique no botão de fechar a caixa de configuração
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no estágio "E2"

  Cenário: Configurar um plano modo de operação atuado correto
    Dado que o usuário esteja na página de planos
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 10 segundos para o "TEMPO DE VERDE MIN."
    E que o usuário marque 20 segundos para o "TEMPO DE VERDE MAX."
    E que o usuário marque 15 segundos para o "TEMPO DE VERDE INTERMEDIÁRIO"
    E que o usuário marque 5 segundos para o "EXTENSÃO DE VERDE"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 13 segundos para o "TEMPO DE VERDE MIN."
    E que o usuário marque 20 segundos para o "TEMPO DE VERDE MAX."
    E que o usuário marque 15 segundos para o "TEMPO DE VERDE INTERMEDIÁRIO"
    E que o usuário marque 2 segundos para o "EXTENSÃO DE VERDE"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E3"
    Quando que o usuário marque 5 segundos para o "TEMPO DE VERDE MIN."
    Entao o sistema deverá mostrar um alerta para valor digitado menor que o limite mínimo
    E o usuário responde ok
    E que o usuário marque 34 segundos para o "TEMPO DE VERDE MAX."
    E que o usuário marque 11 segundos para o "TEMPO DE VERDE INTERMEDIÁRIO"
    E que o usuário marque 5 segundos para o "EXTENSÃO DE VERDE"
    E que o usuário clique no botão de fechar a caixa de configuração
    E o usuário realize um scroll up
    Dado que o usuário selecione o anel 2
    E que o usuário selecione o modo de operação "Atuado"
    Então o diagrama de intervalos não deverá aparecer
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 13 segundos para o "TEMPO DE VERDE MIN."
    E que o usuário marque 20 segundos para o "TEMPO DE VERDE MAX."
    E que o usuário marque 15 segundos para o "TEMPO DE VERDE INTERMEDIÁRIO"
    E que o usuário marque 10 segundos para o "EXTENSÃO DE VERDE"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 10 segundos para o "TEMPO DE VERDE MIN."
    E que o usuário marque 20 segundos para o "TEMPO DE VERDE MAX."
    E que o usuário marque 15 segundos para o "TEMPO DE VERDE INTERMEDIÁRIO"
    E que o usuário marque 2 segundos para o "EXTENSÃO DE VERDE"
    E que o usuário clique no botão de fechar a caixa de configuração

  Cenário: Salvar os Planos configurados corretamente
    Dado que o usuário esteja na página de planos
    E o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
