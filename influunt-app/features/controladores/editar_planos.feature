# language: pt
@editar_planos
Funcionalidade: Editar planos onde o controlador já foi finalizado

  Cenário: Validar ao inserir um estágio dispensável sem o estágio que recebe o tempo do estágio dispensável
    Dado que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    E o usuário esteja na listagem de controladores
    Então o sistema não deverá mostrar o botão "Finalizar" do controlador "1.000.0002"
    Quando o usuário clicar em "Planos" do controlador "1.003.0002"
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
    Dado o usuário selecionar o estágio que recebe o estágio dispensável "E2"
    E que o usuário marque 12 segundos para o "Tempo de Verde"
    E que o usuário clique no botão de fechar a caixa de configuração
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
    E o sistema deverá mostrar o status do controlador como "Em Edição"

  Cenário: Finalizar o controlador com o estágio dispensável
    Dado o usuário clicar em "Finalizar" do controlador "1.003.0002"
    Então o sistema deverá mostar um modal para salvar o histórico
    Dado o usuário preencha o alert com "Crontrolador principal"
    E o usuário confirmar
    Então o sistema deverá mostrar o status do controlador como "Configurado"
    E o sistema não deverá mostrar o botão "Finalizar" do controlador "1.000.0002"

  Cenário: Checar se o estágio que recebe estágio dispensável foi salvo corretamente
    Dado o usuário clicar em "Planos" do controlador "1.003.0002"
    E o usuário clicar em "Editar"
    Quando o "PLANO 1" deverá estar ativado
    E que o usuário clicar no plano 1
    E que o usuário clique no botão de configurar o estágio "E1"
    Então o estágio "E2" deve estar selecionado para estágio dispensável
    E que o usuário clique no botão de fechar a caixa de configuração

  Cenário: Validar plano manual exclusivo deve possuir dois quantidade de estágios iguais
    Dado que o usuário acesse a página de listagem de controladores
    E o usuário clicar em "Planos" do controlador "1.000.0001"
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
