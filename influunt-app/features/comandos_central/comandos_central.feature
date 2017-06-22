# language: pt
@comandos_central
Funcionalidade: Realizar comandos em controladores

  Cenário: Exibir aneis de um controladro sincronizado
    Dado que o sistema possua controladores cadastrados e configurados
    Quando o usuário acessar a tela de listagem de "impor_config"
    #Então o sistema dever possuir o anel "3.003.0002.1" sincronizado

  Cenário: Realizar pesquisa dos aneis por localização
    Dado o usuário clicar em pesquisar
    E selecionar igual para "Localização"
    Quando preencher o campo "Localização" com "R. Pamplona com Av. Paulista"
    E o usuário clicar no botão pesquisar
    Então deve ser exibido "1" aneis sincronizados na listagem
    Dado o usuário clicar em pesquisar
    Quando o usuário clicar em limpar a pesquisa
    Então deve ser exibido "2" aneis sincronizados na listagem

  Cenário: Verificar validações em Impor planos
    Dado o usuário selecionar o anel "3.003.0002.1" para configuração
    E o usuário clicar no botão ações
    E o usuário no campo "Transação" selecionar o valor de "imposicao-plano"
    Então o botão deverá estar "desabilitado"
    E o usuário no campo "Plano" selecionar o valor de "1"
    E o usuário no campo "Hora" selecionar o valor de "23"
    E o usuário no campo "Minuto" selecionar o valor de "0"
    E o usuário no campo "Segundo" selecionar o valor de "1"
    Quando o usuário digitar no campo "duracao" com a informação "1"
    Então o sistema deverá apresentar erro com a mensagem "O tempo de duração deve estar entre 15 e 600"
    E o usuário digitar no campo "data" com a informação "1"
    Então o sistema deverá apresentar erro com a mensagem "Campo Obrigatório"
    E o botão deverá estar "desabilitado"

  Cenário: Realizar Imposição de Plano
    Dado o usuário limpar o campo "duracao"
    E o usuário digitar no campo "duracao" com a informação "15"
    Dado o usuário limpar o campo "data"
    E o usuário digitar no campo "data" com a informação "20/12/2016"
    Então o botão deverá estar "habilitado"

  Cenário: Verificar validações em Impor modo
    Dado o usuário no campo "Transação" selecionar o valor de "imposicao-modo"
    Então o botão deverá estar "desabilitado"
    E o usuário escolher "APAGADO"
    E o usuário no campo "Hora" selecionar o valor de "23"
    E o usuário no campo "Minuto" selecionar o valor de "0"
    E o usuário no campo "Segundo" selecionar o valor de "1"
    Quando o usuário digitar no campo "duracao" com a informação "1"
    Então o sistema deverá apresentar erro com a mensagem "O tempo de duração deve estar entre 15 e 600"
    E o usuário digitar no campo "data" com a informação "1"
    Então o sistema deverá apresentar erro com a mensagem "Campo Obrigatório"
    E o botão deverá estar "desabilitado"

  Cenário: Realizar Imposição em Impor modo
    Dado o usuário limpar o campo "duracao"
    E o usuário digitar no campo "duracao" com a informação "15"
    Dado o usuário limpar o campo "data"
    E o usuário digitar no campo "data" com a informação "20/12/2016"
    Então o botão deverá estar "habilitado"

  Cenário: Enviar Transação
    Dado o usuário no campo "Transação" selecionar o valor de "sincronizacao"
    Então o botão deverá estar "desabilitado"
    Quando o usuário escolher "tabela_horaria_imediato"
    Então o botão deverá estar "habilitado"

  Cenário: Alterar Status
    Dado o usuário no campo "Transação" selecionar o valor de "alterar-status"
    Então o botão deverá estar "desabilitado"
    Quando o usuário escolher "colocar_manutencao"
    Então o botão deverá estar "habilitado"
    E o usuário fechar o modal clicando nele
