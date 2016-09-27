# language: pt
@crud @areas @interfaces
Funcionalidade: tela de cadastro de areas

  Cenário: Listagem de areas
    Dado que exista ao menos uma area cadastrada no sistema
    Quando o usuário acessar a tela de listagem de areas
    Então deve ser exibida uma lista com as areas já cadastradas no sistema

  Cenário: Acesso à tela de nova area
    Quando o usuário acessar a tela de listagem de areas
    E clicar no botão de Nova Area
    Então o sistema deverá redirecionar para o formulário de Cadastro de novas Areas

  Cenário: Cadastro de areas
    Quando o usuário acessar a tela de cadastro de novas areas
    E o usuário selecionar o valor "São Paulo" no campo "Cidade"
    E o usuário preencher o campo "Área" com "42"
    E clicar no botão de salvar
    Então o registro da área deverá ser salvo com número CTA igual a "42"
    E o sistema deverá retornar à tela de listagem de areas

  Cenário: Validar campos em branco
    Dado que exista ao menos uma area cadastrada no sistema
    Quando o usuário acessar a tela de cadastro de novas areas
    E clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "area"
    Então o sistema deverá indicar erro no campo "cidade"

  Cenário: Cadastro de areas com mesmo nome
    Dado que exista ao menos uma area cadastrada no sistema
    Quando o usuário acessar a tela de cadastro de novas areas
    E o usuário marcar a cidade como "São Paulo"
    E o usuário preencher o campo "Área" com "51"
    E clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "area"

   Cenário: Adicionar limites geográficos
    Dado que exista ao menos uma area cadastrada no sistema
    Quando o usuário acessar a tela de cadastro de novas areas
    E o usuário marcar a cidade como "São Paulo"
    E o usuário preencher o campo "Área" com "55"
    E clicar no botão de adicionar limites geográficos
    E o usuário preencher o campo "Latitude" com "-55"
    E o usuário preencher o campo "Longitude" com "55"
    E clicar no botão de salvar
    Então o sistema deverá possuir longitude e latidude

  Cenário: Acesso à tela de detalhes de area
    Quando o usuário acessar a tela de listagem de areas
    E clicar no botão de visualizar área
    Então o sistema deverá redirecionar para a tela de visualização de areas

  Cenário: Acesso à tela de edição de areas
    Quando o usuário acessar a tela de listagem de areas
    E clicar no botão de editar area
    Então o sistema deverá redirecionar para o formulário de edição de areas

  Cenário: Edição de areas
    Quando o usuário acessar o formulário de edição de areas
    E o usuário selecionar o valor "Belo Horizonte" no campo "Cidade"
    E o usuário preencher o campo "Área" com "99"
    E clicar no botão de salvar
    Então o registro da área deverá ser salvo com número CTA igual a "99"
    E o sistema deverá retornar à tela de listagem de areas

  Cenário: Exclusão de areas sem confirmação do usuário
    Quando o usuário acessar a tela de listagem de areas
    E clicar no botão de excluir uma area
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a area
    Quando o usuário responde não
    Então nenhuma área deve ser excluída

  Cenário: Exclusão de areas com confirmação do usuário
    Quando o usuário acessar a tela de listagem de areas
    E clicar no botão de excluir uma area
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a area
    Quando o usuário responde sim
    Então a area deverá ser excluida
