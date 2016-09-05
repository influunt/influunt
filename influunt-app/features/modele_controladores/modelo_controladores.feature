# language: pt
@crud @modelo_controladores @interfaces
Funcionalidade: tela de cadastro de modelos de controladores

  Cenário: Listagem de modelos
    Dado que o usuário esteja na tela de listagem de modelos
    E que exista ao menos um modelo cadastrado no sistema
    Então deve ser exibida uma lista com o modelo já cadastrado no sistema

  Cenário: Acesso à tela de novo modelo de crontroladores
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão de Novo Modelo
    Então o sistema deverá redirecionar para o formulário de cadastro de novos modelos

  Cenário: Cadastro de modelos não pode ficar em branco
    Quando o usuário acessar a tela de cadastro de novos modelos
    E clicar no botão de salvar
    Então o sistema deverá permanecer no form

  Cenário: Cadastro de modelos
    Quando o usuário acessar a tela de cadastro de novos modelos
    E o usuario preencher o campo "Descrição" com "Modelo 1"
    E o usuario selecionar o valor "Raro Labs" no campo "Fabricante"
    E o usuario preencher o campo "Limite Estágios" com "8"
    E o usuario preencher o campo "Limite Grupos Semafóricos" com "8"
    E o usuario preencher o campo "Limite Aneies" com "8"
    E o usuario preencher o campo "Limite Detectores Pedestre" com "8"
    E o usuario preencher o campo "Limite Detectores Veicular" com "8"
    E o usuario preencher o campo "Limite Tabelas Entre Verdes" com "8"
    E o usuario preencher o campo "Limite Planos" com "16"
    E clicar no botão de salvar
    Então o registro do modelo deverá ser salvo com nome igual a "Modelo 1"
    E o sistema deverá retornar à tela de listagem de modelos

  Cenário: Visualizar
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão de visualizar um modelo
    Então o sistema deverá redirecionar para a tela de visualização de modelos

  Cenário: Acesso à tela de edição de modelos
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão de editar um modelo
    Então o sistema deverá redirecionar para o formulário de edição de modelos

  Cenário: Edição de modelos
    Quando o usuário acessar o formulário de edição de modelos
    E o usuario preencher o campo "Descrição" com "Modelo 2"
    E o usuario preencher o campo "Limite Estágios" com "16"
    E clicar no botão de salvar
    Então o registro do modelo deverá ser salvo com nome igual a "Modelo 2"
    E o sistema deverá retornar à tela de listagem de modelos

  Cenário: Não Exclusão de modelo sem confirmação do usuário
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão de excluir um modelo
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário responde não
    Então nenhum modelo deve ser excluído

  Cenário: Exclusão de modelo com confirmação do usuário
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão de excluir um modelo
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário responde sim
    Então o item deverá ser excluido
