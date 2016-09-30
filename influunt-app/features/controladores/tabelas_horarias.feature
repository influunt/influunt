# language: pt
@controladores @tabelas_horarias @interfaces
Funcionalidade: Fluxo de cadastro de manutenção de Tabelas Horárias

  Cenário: Acesso à tela de tabelas horárias
    Dado que o sistema possui ao menos um controlador configurado
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar no botão Tabela Horária do controlador
    Então o sistema deverá redirecionar para a tela de tabela horária
