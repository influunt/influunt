# language: pt
@relatorios @relatorio_tabela_horaria
Funcionalidade: Realizar o relatório da tabela horária

  Cenário: Acessar a tela de relatório tabela horária
    Dado o usuário logue no sistema com usuário "mobilab" e perfil administrador
    Dado que o sistema possua controladores cadastrados e configurados
    Quando o usuário acessar o relatório "tabela_horaria"
    Então o sistema deverá redirecionar para o formulário "RelatorioTabelaHoraria"

   Cenário: Não pode gerar evento sem selecionar um controlador
    Dado o usuário no campo data preencher com valor "04/12/2016"
    Então o botão gerar deverá estar desabilitado

    Cenário: Não pode gerar evento com uma data inválida
    Dado o usuário limpar o campo "Data"
    E o usuário no campo data preencher com valor "99/99/9999"
    Então o botão gerar deverá estar desabilitado

   Cenário: Não pode gerar evento sem selecionar um controlador
    Dado o usuário limpar o campo "Data"
    E o usuário selecionar no campo "controlador" selecionar o label "1.000.0001 - Av. Paulista com R. Pamplona"
    Então o botão gerar deverá estar desabilitado

  Cenário: Visualizar vários eventos
    Dado o usuário no campo data preencher com valor "02/12/2016"
    E o usuário selecionar no campo "controlador" selecionar o label "1.003.0002 - Av. Paulista, nº 1000. ref.: AREA 1"
    Quando o usuário clicar em Gerar
    Então o sistema deverá mostrar na coluna "Horário" com valor "01:00:00.000"
    E o sistema deverá mostrar na coluna "Plano" com valor "PLANO 4"
    E o sistema deverá mostrar na coluna "Modo de Operação" com valor "INTERMITENTE"
    Então o sistema deverá mostrar na coluna "Horário" com valor "03:01:00.000"
    E o sistema deverá mostrar na coluna "Plano" com valor "PLANO 3"
    E o sistema deverá mostrar na coluna "Modo de Operação" com valor "TEMPO_FIXO_ISOLADO"

  Cenário: Visualizar evento especial
    Dado o usuário limpar o campo "Data"
    E o usuário no campo data preencher com valor "08/11/2016"
    E o usuário selecionar no campo "controlador" selecionar o label "1.003.0002 - Av. Paulista, nº 1000. ref.: AREA 1"
    Quando o usuário clicar em Gerar
    Então o sistema deverá mostrar na coluna "Horário" com valor "00:00:00.000"
    E o sistema deverá mostrar na coluna "Plano" com valor "PLANO 4"
    E o sistema deverá mostrar na coluna "Modo de Operação" com valor "INTERMITENTE"
