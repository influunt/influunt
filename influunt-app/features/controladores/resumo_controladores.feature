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

  Cenário: Vizualizar as informações dos aneis iniciais do anel 1
    Então o sistema deverá mostrar as informações iniciais do anel 1

  Cenário: Vizualizar as informações do grupos semafóricos do anel 1
    Então o sistema deverá mostrar as informações dos grupos semafóricos

  Cenário: Vizualizar as informações das Associação Estágio x Grupo Semafórico do anel 1
    Então o sistema deverá mostrar as informações para Associação Estágio x Grupo Semafórico

  Cenário: Vizualizar as informações de Verdes Conflitantes do anel 1
    Então o sistema deverá mostrar as informações para Verdes Conflitantes

  Cenário: Vizualizar as informações de Transições Proibidas do anel 1
    Então o sistema deverá mostrar as informações para Transições Proibidas

  Cenário: Vizualizar as informações de Tabela Entreverdes do anel 1
    Então o sistema deverá mostrar as informações para Tabela Entreverdes do grupo G1
    Quando o usuário realizar um scroll down
    E que o usuário mudar o grupo semafórico em Tabela Entreverdes clicando no grupo "G2"
    Então o sistema deverá mostrar as informações para Tabela Entreverdes do grupo G2
    E que o usuário mudar o grupo semafórico em Tabela Entreverdes clicando no grupo "G3"
    Então o sistema deverá mostrar as informações para Tabela Entreverdes do grupo G3
    Então o sistema deverá mostrar as informações para Atraso de grupo do grupo G1
  Cenário: Vizualizar as informações de Atraso de Grupo do anel 1
    E que o usuário mudar o grupo semafórico em Atraso de Grupo clicando no grupo "G2"
    Então o sistema deverá mostrar as informações para Atraso de grupo do grupo G2
    E que o usuário mudar o grupo semafórico em Atraso de Grupo clicando no grupo "G3"
    Então o sistema deverá mostrar as informações para Atraso de grupo do grupo G3
  Cenário: Vizualizar as informações dos Detectores do anel 1
    Então o sistema deverá mostrar as informações para Detectores

  Cenário: Vizualizar as informações dos aneis iniciais do anel 1
    Dado o usuário realize um scroll up
    Dado que o usuário selecione o anel 2
    Então o sistema deverá mostrar as informações iniciais do anel 2
    E que transições proibidas não exista dados a serem exibidos
