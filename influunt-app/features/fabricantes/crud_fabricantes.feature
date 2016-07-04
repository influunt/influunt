# language: pt
@crud @fabricantes @interfaces
Funcionalidade: tela de cadastro de fabricantes

  Cenário: Listagem de fabricantes
    Dado que exista ao menos um fabricante cadastrado no sistema
    Quando o usuário acessar a tela de listagem de fabricantes
    Então deve ser exibida uma lista com os fabricantes já cadastrados no sistema

  Cenário: Acesso à tela de novo fabricante
    Quando o usuário acessar a tela de listagem de fabricantes
    E Clica no botão de Novo Fabricante
    Então o sistema deverá redirecionar para o formulário de Cadastro de novo fabricante

  Cenário: Cadastro de fabricantes
    Quando o usuário acessar a tela de cadastro de novos fabricantes
    E preenche os campos do fabricante corretamente
    Então o registro do fabricante deverá ser salvo com sucesso
    E o sistema deverá retornar à tela de listagem de fabricantes

  Cenário: Acesso à tela de detalhes de fabricantes
    Quando o usuário acessar a tela de listagem de fabricantes
    E Clica no botão de visualizar fabricante
    Então o sistema deverá redirecionar para a tela de visualização de fabricantes

  Cenário: Acesso à tela de edição de fabricantes
    Quando o usuário acessar a tela de listagem de fabricantes
    E Clica no botão de editar fabricante
    Então o sistema deverá redirecionar para o formulário de edição fabricantes

  Cenário: Edição de fabricantes
    Quando o usuário acessar o formulário de edição de fabricantes
    E preenche os campos do fabricante corretamente
    Então o registro do fabricante deverá ser salvo com sucesso
    E o sistema deverá retornar à tela de listagem de fabricantes

  Cenário: Exclusão de fabricantes com confirmação
    Quando o usuário acessar a tela de listagem de fabricantes
    E clica no botão de excluir um fabricante
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o fabricante
    Quando o usuário responde sim
    Então o fabricante deverá ser excluido

  Cenário: Exclusão de fabricantes sem confirmação
    Quando o usuário acessar a tela de listagem de fabricantes
    E clica no botão de excluir um fabricante
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o fabricante
    Quando o usuário responde não
    Então nenhum fabricante deve ser excluído

  Cenário: Cadastro de fabricante com um modelo de controlador
    Quando o usuário acessar a tela de cadastro de novos fabricantes
    E preenche todos os campos do formulário
    Então o registro do fabricante deverá ser salvo com sucesso
    E o sistema deverá retornar à tela de listagem de fabricantes
