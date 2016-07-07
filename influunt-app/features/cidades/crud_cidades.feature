# language: pt
@crud @cidades @interfaces
Funcionalidade: tela de cadastro de cidades

  Cenário: Listagem de cidades
    Dado que exista ao menos uma cidade cadastrada no sistema
    Quando o usuário acessar a tela de listagem de cidades
    Então deve ser exibida uma lista com as cidades já cadastradas no sistema

  Cenário: Acesso à tela de nova cidade
    Quando o usuário acessar a tela de listagem de cidades
    E clicar no botão de Nova Cidade
    Então o sistema deverá redirecionar para o formulário de cadastro de novas cidades

  Cenário: Cadastro de cidades
    Quando o usuário acessar a tela de cadastro de novas cidades
    E o usuario preencher o campo "Nome" com "São Paulo"
    E clicar no botão de salvar
    Então o registro da cidade deverá ser salvo com nome igual a "São Paulo"
    E o sistema deverá retornar à tela de listagem de cidades

  Cenário: Acesso à tela de detalhes de cidades
    Quando o usuário acessar a tela de listagem de cidades
    E clicar no botão de visualizar cidade
    Então o sistema deverá redirecionar para a tela de visualização de cidades

  Cenário: Acesso à tela de edição de cidades
    Quando o usuário acessar a tela de listagem de cidades
    E clicar no botão de editar cidade
    Então o sistema deverá redirecionar para o formulário de edição cidades

  Cenário: Edição de cidades
    Quando o usuário acessar o formulário de edição de cidades
    E o usuario preencher o campo "Nome" com "Belo Horizonte"
    E clicar no botão de salvar
    Então o registro da cidade deverá ser salvo com nome igual a "Belo Horizonte"
    E o sistema deverá retornar à tela de listagem de cidades

  Cenário: Exclusão de cidades sem confirmação
    Quando o usuário acessar a tela de listagem de cidades
    E clicar no botão de excluir uma cidade
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a cidade
    Quando o usuário responde não
    Então nenhuma cidade deve ser excluída

  Cenário: Exclusão de cidades com confirmação
    Quando o usuário acessar a tela de listagem de cidades
    E clicar no botão de excluir uma cidade
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a cidade
    Quando o usuário responde sim
    Então a cidade deverá ser excluida
