# language: pt
@controladores @resumo @interfaces
Funcionalidade: Vizualizar as informações de um controlador cadastrado

  Cenário: Acessar os controlador
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar em "Configuração"
    Então o sistema deverá redirecionar para a revisão do controlador

  Cenário: Vizualizar os dados básicos do controlador
  	Então o sistema deverá mostrar as informações do dados básicos

  Cenário: Vizualizar as informações dos aneis
    # Dado que o usuário selecione o anel 1
    Então o sistema deverá mostrar as informações íniciais do anel 1
    Então o sistema deverá mostrar as informações dos grupos semáforicos
    Então o sistema deverá mostrar as informações para Associação Estágio x Grupo Semafórico
    Então o sistema deverá mostrar as informações para Verdes Conflitantes
    Dado que o usuário selecione o anel 2
    Então o sistema deverá mostrar as informações íniciais do anel 2

