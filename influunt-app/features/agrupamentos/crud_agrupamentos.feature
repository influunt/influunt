# language: pt
@crud @agrupamentos @interfaces
Funcionalidade: tela de cadastro de agrupamentos

  @agrupamentos1
  Cenário: Listagem de agrupamentos
    Dado que o sistema possua controladores cadastrados e configurados
    Quando o usuário acessar a tela de listagem de agrupamentos
    Então deve ser exibida uma lista com os agrupamentos já cadastrados no sistema

  Cenário: Acesso à tela de novo agrupamento
    Quando o usuário acessar a tela de listagem de agrupamentos
    E clicar no botão de Novo Agrupamento
    Então o sistema deverá redirecionar para o formulário de cadastro de novos agrupamentos

  Cenário: Não posso cadastrar um agrupamento em branco
    Quando o usuário acessar a tela de listagem de agrupamentos
    E clicar no botão de Novo Agrupamento
    E clicar no botão de salvar
    Então sistema deverá mostar um alerta se deseja atualizar tabela horária
    E o usuário confirmar
    Então o sistema deverá indicar erro no campo "nome"
    Então o sistema deverá indicar erro no campo "tipo"
    Então o sistema deverá indicar erro no campo "agrupamentoPlanoDiaSemana"
    Então o sistema deverá indicar erro no campo "planoHora"

  Cenário: Cadastro de agrupamentos
    Dado o usuário acessar a tela de cadastro de novos agrupamentos
    E o usuário preencher o campo "Nome" com "Corredor da Paulista"
    E o usuário preencher o campo "Descrição" com "Agrupamento 1"
    E o usuário selecionar o valor "Corredor" no campo "Tipo"
    E o usuário selecionar o valor "Av. Paulista, nº 1000. ref.: AREA 1" para o campo "controladores"
    E o usuário em evento selecionar o valor "DOMINGO" no campo "Dias"
    E o usuário em evento selecionar o valor "12" no campo "Hora"
    E o usuário em evento selecionar o valor "10" no campo "Minuto"
    E o usuário em evento selecionar o valor "1" no campo "Segundo"
    E o usuário em evento selecionar o valor plano "1" no campo "Plano"
    E clicar no botão de salvar
    Então sistema deverá mostar um alerta se deseja atualizar tabela horária
    E o usuário confirmar
    Então deve ser exibida uma lista com os agrupamentos já cadastrados no sistema

  Cenário: Acesso à tela de detalhes de um agrupamento
    Dado o usuário acessar a tela de listagem de agrupamentos
    Quando clicar no botão de visualizar um agrupamento
    Então o sistema deverá redirecionar para a tela de visualização de agrupamentos

  Cenário: Acesso à tela de edição de agrupamentos
    Quando o usuário acessar a tela de listagem de agrupamentos
    E clicar no botão de editar um agrupamento
    Então o sistema deverá redirecionar para o formulário de edição de agrupamentos

  Cenário: Edição de agrupamentos
    Quando o usuário acessar o formulário de edição de agrupamentos
    E o usuário preencher o campo "Nome" com "Rota da Cidade"
    E o usuário selecionar o valor "Rota" no campo "Tipo"
    E clicar no botão de salvar
    Então sistema deverá mostar um alerta se deseja atualizar tabela horária
    E o usuário confirmar

  Cenário: Exclusão de agrupamentos sem confirmação do usuário
    Quando o usuário acessar a tela de listagem de agrupamentos
    E clicar no botão de excluir um agrupamento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o agrupamento
    Quando o usuário responde não
    Então nenhum agrupamento deve ser excluído

  Cenário: Exclusão de agrupamentos com confirmação do usuário
    Quando o usuário acessar a tela de listagem de agrupamentos
    E clicar no botão de excluir um agrupamento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o agrupamento
    Quando o usuário confirmar
    Então o item deverá ser excluido
