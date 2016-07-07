# language: pt
@crud @modelos @interfaces
Funcionalidade: tela de cadastro de modelos de controladores

  Cenário: Listagem de modelos de controladores
    Dado que exista ao menos um modelo de controlador cadastrado no sistema
    Quando o usuário acessar a tela de listagem de modelos de controladores
    Então deve ser exibida uma lista com os modelos de controladores já cadastrados no sistema

  Cenário: Acesso à tela de novo modelo de controlador
    Quando o usuário acessar a tela de listagem de modelos de controladores
    E clicar no botão de Novo Modelo de Controlador
    Então o sistema deverá redirecionar para o formulário de cadastro de novo modelo de controlador

  Cenário: Cadastro de modelos de controladores
    Quando o usuário acessar a tela de cadastro de novos modelos de controladores
    E o usuario preencher o campo "Descrição" com "Teste Modelo"
    E o usuario selecionar o valor "Raro Labs" no campo "Fabricante"
    E o usuario selecionar o valor "Mínima" no campo "Configuração Controlador"
    E clicar no botão de salvar
    Então o registro do modelo deverá ser salvo com descricao "Teste Modelo"
    E o sistema deverá retornar à tela de listagem de modelos de controladores

  Cenário: Acesso à tela de detalhes de um modelo de controlador
    Quando o usuário acessar a tela de listagem de modelos de controladores
    E clicar no botão de visualizar um modelo de controlador
    Então o sistema deverá redirecionar para a tela de visualização de modelos de controladores

  Cenário: Acesso à tela de edição de modelos de controladores
    Quando o usuário acessar a tela de listagem de modelos de controladores
    E clicar no botão de editar um modelo de controlador
    Então o sistema deverá redirecionar para o formulário de edição modelos de controladores

  Cenário: Edição de modelos de controladores
    Quando o usuário acessar o formulário de edição de modelos de controladores
    E o usuario preencher o campo "Descrição" com "Novo Teste"
    E o usuario selecionar o valor "Raro Labs" no campo "Fabricante"
    E o usuario selecionar o valor "Mínima" no campo "Configuração Controlador"
    E clicar no botão de salvar
    Então o registro do modelo deverá ser salvo com descricao "Novo Teste"
    E o sistema deverá retornar à tela de listagem de modelos de controladores

  Cenário: Exclusão de modelos de controladores sem confirmação
    Quando o usuário acessar a tela de listagem de modelos de controladores
    E clicar no botão de excluir um modelo de controlador
    Então o sistema exibe uma caixa de confirmação perguntando se o usuário quer mesmo excluir o modelo
    Quando o usuário responde não
    Então nenhum modelo deve ser excluído

  Cenário: Exclusão de modelos de controladores com confirmação
    Quando o usuário acessar a tela de listagem de modelos de controladores
    E clicar no botão de excluir um modelo de controlador
    Então o sistema exibe uma caixa de confirmação perguntando se o usuário quer mesmo excluir o modelo
    Quando o usuário responde sim
    Então o modelo deverá ser excluido
