# language: pt
@comandos_central
Funcionalidade: Realizar comandos em controladores

  Cenário: Exibir aneis de um controladro sincronizado
    Dado que o sistema possua controladores cadastrados e configurados
    Quando o usuário acessar a tela de listagem de "impor_config"
    Então o sistema dever possuir o anel "1.003.0002.1" sincronizado
    E o sistema dever possuir o anel "1.003.0002.2" sincronizado

  Cenário: Impor planos
    Dado o usuário selecionar o anel "1.003.0002.1"
    E o usuário clicar no botão "Ações"
