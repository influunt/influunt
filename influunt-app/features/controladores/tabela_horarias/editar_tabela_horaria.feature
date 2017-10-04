# language: pt
@editar_tabela_horaria
Funcionalidade: Editar tabelas horárias

  Cenário: Inserir um envento especial recorrente
    Dado que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    E o usuário esteja na listagem de controladores
    E o usuário clicar em "Tabela Horária" do controlador "Av. Paulista com R. Pamplona"
    E o usuário clicar em "Editar"
    Então o sistema deverá apresentar na aba "Eventos especiais recorrentes" com o valor "0"
    Quando o usuário mudar de aba para Eventos "especiais recorrentes"
    E o usuário preencher o campo evento com "Feriado Independência do Brasil"
    E o usuário no campo data preencher com valor "07/09"
    E o usuário selecionar o valor "10" no campo "Hora" para o evento
    E o usuário selecionar o valor "0" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "1" no campo "Plano" para o evento
    Então o sistema deverá apresentar na aba "Eventos especiais recorrentes" com o valor "1"
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores

  Cenário: Inserir um envento especial não recorrente
    Dado o usuário esteja na listagem de controladores
    E o usuário clicar em "Tabela Horária" do controlador "Av. Paulista com R. Pamplona"
    E o usuário clicar em "Editar"
    Então o sistema deverá apresentar na aba "Eventos especiais não recorrentes" com o valor "0"
    Quando o usuário mudar de aba para Eventos "especiais não recorrentes"
    E o usuário preencher o campo evento com "Dia Das Mães"
    E o usuário no campo data preencher com valor "10/05/2016"
    E o usuário selecionar o valor "10" no campo "Hora" para o evento
    E o usuário selecionar o valor "0" no campo "Minuto" para o evento
    Quando o usuário selecionar o valor plano "1" no campo "Plano" para o evento
    Então o sistema deverá apresentar na aba "Eventos especiais não recorrentes" com o valor "1"
    Quando o usuário clicar em "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores
