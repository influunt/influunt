# language: pt
@crud @agrupamentos @interfaces
Funcionalidade: tela de cadastro de agrupamentos

  @agrupamentos1
  Cenário: Listagem de agrupamentos
    Dado que exista ao menos um agrupamento cadastrado no sistema
    Quando o usuário acessar a tela de listagem de agrupamentos
    Então deve ser exibida uma lista com os agrupamentos já cadastrados no sistema

  Cenário: Acesso à tela de novo agrupamento
    Quando o usuário acessar a tela de listagem de agrupamentos
    E clicar no botão de Novo Agrupamento
    Então o sistema deverá redirecionar para o formulário de cadastro de novos agrupamentos

  Cenário: Cadastro de agrupamentos
    Quando o usuário acessar a tela de cadastro de novos agrupamentos
    E o usuario preencher o campo "Nome" com "Corredor da Paulista"
    E o usuario selecionar o valor "Corredor" no campo "Tipo"
    E o usuario selecionar o valor "Esquina rua A com B" no campo Controladores
    E clicar no botão de salvar
    Então o registro do agrupamento deverá ser salvo com nome igual a "Corredor da Paulista"
    E o sistema deverá retornar à tela de listagem de agrupamentos

  Cenário: Acesso à tela de detalhes de um agrupamento
    Quando o usuário acessar a tela de listagem de agrupamentos
    E clicar no botão de visualizar um agrupamento
    Então o sistema deverá redirecionar para a tela de visualização de agrupamentos

  Cenário: Acesso à tela de edição de agrupamentos
    Quando o usuário acessar a tela de listagem de agrupamentos
    E clicar no botão de editar um agrupamento
    Então o sistema deverá redirecionar para o formulário de edição de agrupamentos

  Cenário: Edição de agrupamentos
    Quando o usuário acessar o formulário de edição de agrupamentos
    E o usuario preencher o campo "Nome" com "Rota da Cidade"
    E o usuario selecionar o valor "Rota" no campo "Tipo"
    # E o usuario selecionar o valor "Esquina rua A com B" no campo Controladores
    E clicar no botão de salvar
    Então o registro do agrupamento deverá ser salvo com nome igual a "Rota da Cidade"
    E o sistema deverá retornar à tela de listagem de agrupamentos

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
    Quando o usuário responde sim
    Então o agrupamento deverá ser excluido
