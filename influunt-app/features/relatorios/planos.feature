# language: pt
@relatorios @relatorio_planos
Funcionalidade: Visualizar relatório dos planos

  Cenário: Visualizar os informações do planos
    Dado que o sistema possua controladores cadastrados e configurados
    Quando o usuário acessar o relatório "planos"
    Então o sistema deverá mostrar "16" items na tabela
    E o sistema deverá mostrar na coluna "Anel" com valor "3.003.0002.2"
    E o sistema deverá mostrar na coluna "Dispensável" "Marcada"

  Cenário: Realizar o filtro por CLA
    Dado o usuário clicar em pesquisar
    E o usuário selecionar o status "Subarea"
    E preencher o campo "CLA" com "3.003.0002.2"
    E o usuário clicar no botão pesquisar
    Então o sistema deverá mostrar "4" items na tabela
    E o sistema deverá apresentar na listagem controlador "3.003.0002"
