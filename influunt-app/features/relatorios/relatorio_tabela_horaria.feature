# language: pt
@relatorios @relatorio_tabela_horaria
Funcionalidade: Realizar o relatório da tabela horária

  Cenário: Acessar a tela de relatório tabela horária
    Dado que o sistema possua controladores cadastrados e configurados
    Quando o usuário acessar o relatório "tabela_horaria"
    Então o sistema deverá redirecionar para o formulário "RelatorioTabelaHoraria"

  Cenário: Gerar um relatório do dia sabado
    Dado o usuário selecionar no campo "controlador" selecionar o label "1.000.0001 - Av. Paulista com R. Pamplona"
    E o usuário no campo data preencher com valor "10/12/2016"

