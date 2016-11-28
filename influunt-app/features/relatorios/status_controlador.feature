# language: pt
@relatorios @relatorio_controlador_status
Funcionalidade: Realizar o relatório de status dos controladores

  Cenário: Acessar a tela de relatório por de status do controlador
    Dado que o sistema possua controladores cadastrados e configurados
    E o usuário acessar o relatório "controladores_status"
    Então o sistema deverá apresentar o controlador com status em falha

  Cenário: Fazer o download do arquivo csv
    Dado o usuário clicar em "CSV"
    Então o sistema deverá apresentar o controlador com status em falha
