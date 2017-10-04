# language: pt
@relatorios @relatorio_logs_controlador
Funcionalidade: Visuzalizar os logs de falhar dos controladores

  Cenário: Usuário deverá vizualizar os dados de log
    Dado que o sistema possua controladores cadastrados e configurados
    Quando o usuário acessar o relatório "log_controladores"
    Então o sistema deverá mostrar "2" items na tabela
    E o sistema deverá apresentar na listagem controlador "Alarme e Falhas"
    E o sistema deverá apresentar na listagem controlador "Status Conexão"

  Cenário: Filtrar os logs por subárea
    Dado o usuário clicar em pesquisar
    E o usuário selecionar o status "Subarea"
    E preencher o campo "Subárea / Agrupamento" com "AREA SUL PAULISTA"
    E o usuário clicar no botão pesquisar
    Então o sistema deverá mostrar "1" controladores cadastrados
    E o sistema deverá apresentar na listagem controlador "3.003.0002"
