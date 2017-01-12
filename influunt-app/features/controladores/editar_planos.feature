# language: pt
@editar_planos
Funcionalidade: Editar planos onde o controlador já foi finalizado

Cenário: Estágio que recebe o tempo dispensável não pode ser o primeiro nem o último
  Dado que o sistema possua controladores cadastrados e configurados
  E o usuário acessar a tela de listagem de "controladores"
  Quando o usuário clicar em "Planos" do controlador "Av. Paulista com R. Pamplona"
  E o usuário clicar em "Editar"
  Então o "PLANO 1" deverá estar ativado
  E que o usuário clicar no plano 1
  E que o usuário clique no botão de configurar o estágio "E1"
  E e o usuário clicar em estágio dispensável
  E o usuário não consiga selecionar o valor "E3" para o campo "tipoEstagio"
  E o usuário selecionar o estágio que recebe o estágio dispensável "E2"
  E que o usuário clique no botão de fechar a caixa de configuração
  E que o usuário clique no botão de configurar o estágio "E2"
  E e o usuário clicar em estágio dispensável
  E o usuário selecionar o estágio que recebe o estágio dispensável "E3"
  E que o usuário clique no botão de fechar a caixa de configuração
  E que o usuário clique no botão de configurar o estágio "E3"
  E e o usuário clicar em estágio dispensável
  E o usuário não consiga selecionar o valor "E1" para o campo "tipoEstagio"
  E o usuário selecionar o estágio que recebe o estágio dispensável "E2"
  E que o usuário clique no botão de fechar a caixa de configuração
  E que o usuário clicar no plano 0
  E que o usuário clique no botão apagar o estagio "E3"
  Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
  Quando o usuário confirmar
  Então a quantidade de estagios na lista deverá ser 2
  E o usuário clicar em "Salvar"
  Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
  E o sistema deverá mostrar o status do controlador como "Em revisão"

Cenário: Copiar um plano que possua estágios dispensáveis
  Dado o usuário clicar em "Planos" do controlador "Av. Paulista com R. Pamplona"
  E o usuário clicar em "Editar"
  Então o "PLANO 1" deverá estar ativado
  E que o usuário clicar no plano 1
  E que o usuário clicar em copiar o "PLANO 1"
  Então o sistema exibe uma caixa para copiar o plano 1
  E o usuário selecionar o "PLANO 2"
  Quando o usuário clicar no botão copiar
  Então o "PLANO 2" deverá estar ativado
  Quando o usuário clicar em "Salvar"
  Então o sistema deverá redirecionar o usuário para a página de listagem de controladores


  Cenário: Estágio que recebe o tempo dispensável ao apaga-lo o sistema deverá apresentar erro
    Dado o usuário clicar em "Planos" do controlador "Av. Paulista com R. Pamplona"
    E o usuário clicar em "Editar"
    Então o "PLANO 1" deverá estar ativado
    E que o usuário clicar no plano 1
    E que o usuário clique no botão apagar o estagio "E2"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então a quantidade de estagios na lista deverá ser 2
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 30 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no estágio "E1"
    Então o sistema deverá apresentar erro no estágio "E3"

  Cenário: Ao adicionar um plano com ciclo duplo o tempo deve ser maior ou igual ao tempo de ciclo
    Dado que o sistema possua controladores cadastrados e configurados
    E o usuário acessar a tela de listagem de "controladores"
    Quando o usuário clicar em "Planos" do controlador "Avenida Nove de Julho com Av. Paulista"
    E o usuário clicar em "Editar"
    Então o "PLANO 1" deverá estar ativado
    E que o usuário clicar no plano 3
    E que o usuário selecione o modo de operação "Coordenado"
    E o usuário ativar o ciclo duplo
    E que o usuário marque 60 segundos para o "TEMPO DE CICLO"
    E que o usuário marque 40 segundos para o "TEMPO DE CICLO DUPLO"
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá mostrar erro no plano 3
    E o sistema deverá apresentar erro de "O Tempo do ciclo duplo deve ser maior ou igual ao tempo de ciclo."

  Cenário: Salvar um plano coordenado utiliznado o ciclo duplo
    Dado que o usuário marque 61 segundos para o "TEMPO DE CICLO DUPLO"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 44 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E que o usuário selecione o anel 2
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 44 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    Quando o usuário clicar em "Salvar"
    Então o usuário acessar a tela de listagem de "controladores"
    E o sistema deverá mostrar o status do controlador como "Em revisão"

  Cenário: O ciclo duplo pode ser configurado somente no modo coordenado
    Dado o usuário esteja na listagem de controladores
    E o usuário clicar em "Planos" do controlador "Avenida Nove de Julho com Av. Paulista"
    E o usuário clicar em "Editar"
    Então o "PLANO 1" deverá estar ativado
    Quando que o usuário clicar no plano 3
    E o usuário cancelar
    E que o usuário selecione o modo de operação "Isolado"
    E o usuário clicar em "Salvar"
    Então o sistema deverá mostrar erro no plano 3
    E o sistema deverá apresentar erro de "O ciclo duplo pode ser configurado somente no modo coordenado."

  Cenário: Copiar um plano para um que já esteja salvo
    Dado que o sistema possua controladores cadastrados e configurados
    E o usuário acessar a tela de listagem de "controladores"
    E o usuário clicar em "Planos" do controlador "Avenida Nove de Julho com Av. Paulista"
    E o usuário clicar em "Editar"
    Então o "PLANO 1" deverá estar ativado
    E que o usuário clicar no plano 1
    E que o usuário clicar em copiar o "PLANO 1"
    Então o sistema exibe uma caixa para copiar o plano 1
    E o usuário selecionar o "PLANO 2"
    Quando o usuário clicar no botão copiar
    Então o "PLANO 2" deverá estar ativado
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
    E o usuário clicar em "Planos" do controlador "Avenida Nove de Julho com Av. Paulista"
    Quando que o usuário clicar no plano 2
    Então a quantidade de estagios na lista deverá ser 2

  Cenário: Planos em modo coordenado da mesma subárea deverão ser simétricos
    Dado que o sistema possua controladores cadastrados e configurados
    E que o controlador Avenida Nove de Julho com Av. Paulista possua subárea 3
    E o usuário acessar a tela de listagem de "controladores"
    Quando o usuário clicar em "Planos" do controlador "Av. Paulista, nº 1000. ref.: AREA 1"
    E o usuário clicar em "Editar"
    Então o "PLANO 1" deverá estar ativado
    E que o usuário clicar no plano 1
    E que o usuário selecione o anel 2
    E que o usuário clicar no plano 1
    E que o usuário marque 40 segundos para o "TEMPO DE CICLO"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 22 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá mostrar erro no plano 1
    E o sistema deverá apresentar erro de "O Tempo de ciclo deve ser simétrico ou assimétrico nessa subárea para todos os planos de mesma numeração."



  Cenário: Validar ao inserir um estágio dispensável sem o estágio que recebe o tempo do estágio dispensável
    Dado que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    E o usuário esteja na listagem de controladores
    Então o sistema não deverá mostrar o botão "Finalizar" do controlador "1.000.0002"
    Quando o usuário clicar em "Planos" do controlador "Av. Paulista, nº 1000. ref.: AREA 1"
    E o usuário clicar em "Editar"
    Então o "PLANO 1" deverá estar ativado
    E que o usuário clicar no plano 1
    E que o usuário clique no botão de configurar o estágio "E1"
    E e o usuário clicar em estágio dispensável
    E que o usuário clique no botão de fechar a caixa de configuração
    E o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no estágio "E1"

  Cenário: Validar verde de segurança mínimo para estágio dispensável
    Dado que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 6 segundos para o "Tempo de Verde"
    Quando o sistema deverá mostrar um alerta para verdes segurança menor
    Então o usuário responde sim para verde de segurança
    E que o usuário clique no botão de fechar a caixa de configuração

  Cenário: Salva o estágio dispensável
    Dado que o usuário clique no botão de configurar o estágio "E1"
    E o usuário selecionar o estágio que recebe o estágio dispensável "E2"
    E que o usuário marque 12 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
    E o sistema deverá mostrar o status do controlador como "Em revisão"

  Cenário: Finalizar o controlador com o estágio dispensável
    Dado o usuário clicar em "Finalizar" do controlador "Av. Paulista, nº 1000. ref.: AREA 1"
    Então o sistema deverá mostar um modal para salvar o histórico
    Dado o usuário preencha o alert com "Crontrolador principal"
    E o usuário confirmar
    Então o sistema deverá mostrar o status do controlador como "Configurado"
    E o sistema não deverá mostrar o botão "Finalizar" do controlador "1.000.0002"

  Cenário: Checar se o estágio que recebe estágio dispensável foi salvo corretamente
    Dado o usuário clicar em "Planos" do controlador "Av. Paulista, nº 1000. ref.: AREA 1"
    E o usuário clicar em "Editar"
    Quando o "PLANO 1" deverá estar ativado
    E que o usuário clicar no plano 1
    E que o usuário clique no botão de configurar o estágio "E1"
    Então o estágio "E2" deve estar selecionado para estágio dispensável
    E que o usuário clique no botão de fechar a caixa de configuração

  Cenário: Validar plano manual exclusivo deve possuir dois quantidade de estágios iguais
    Dado que o usuário acesse a página de listagem de controladores
    E o usuário clicar em "Planos" do controlador "Av. Paulista com R. Pamplona"
    E o usuário clicar em "Editar"
    E que o usuário clicar no plano 0
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro de "Este plano deve ter a mesma quantidade de estágios que os outros planos em modo manual exclusivo."
    E que o usuário clique no botão apagar o estagio "E3"
    E o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    E o usuário realizar um scroll down
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores

  Cenário: Não pode salvar um estágio dispensável ao apagar o estágio que recebe o tempo
    Dado que o usuário acesse a página de listagem de controladores
    E o usuário clicar em "Planos" do controlador "Av. Paulista com R. Pamplona"
    E o usuário clicar em "Editar"
    Quando o "PLANO 1" deverá estar ativado
    E que o usuário clicar no plano 1
    E que o usuário clique no botão de configurar o estágio "E2"
    E e o usuário clicar em estágio dispensável
    E o usuário selecionar o estágio que recebe o estágio dispensável "E3"
    E que o usuário clique no botão de fechar a caixa de configuração
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
    Dado o usuário clicar em "Planos" do controlador "Av. Paulista com R. Pamplona"
    E o usuário clicar em "Editar"
    Quando o "PLANO 1" deverá estar ativado
    E que o usuário clicar no plano 1
    E que o usuário clique no botão apagar o estagio "E3"
    E o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    E o usuário confirmar
    E que o usuário clique no botão de configurar o estágio "E2"
    E que o usuário marque 21 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no estágio "E2"

  Cenário: Não pode salva um estágio com o tempo maior que de permanência
    Dado que o usuário acesse a página de listagem de controladores
    E que o usuário acesse a página de listagem de controladores
    E o usuário clicar em "Planos" do controlador "Av. Paulista, nº 1000. ref.: AREA 1"
    E o usuário clicar em "Editar"
    E que o usuário clicar no plano 1
    E que o usuário marque 200 segundos para o "TEMPO DE CICLO"
    E que o usuário clique no botão de configurar o estágio "E1"
    E que o usuário marque 128 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    E o usuário realizar um scroll down
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá apresentar erro no estágio "E1"
    E o sistema deverá apresentar erro de "A soma dos tempos dos estágios (146s) é diferente do tempo de ciclo (200s)."
    E o sistema deverá apresentar erro de "O Tempo de ciclo deve ser simétrico ou assimétrico nessa subárea para todos os planos de mesma numeração."


