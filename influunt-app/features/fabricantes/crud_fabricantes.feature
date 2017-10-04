# language: pt
@crud @fabricantes @interfaces
Funcionalidade: tela de cadastro de fabricantes

  Cenário: Listagem de fabricantes
    Dado que exista ao menos um fabricante cadastrado no sistema
    Quando o usuário acessar a tela de listagem de fabricantes
    Então deve ser exibida uma lista com os fabricantes já cadastrados no sistema

  Cenário: Acesso à tela de novo fabricante
    Quando o usuário acessar a tela de listagem de fabricantes
    E clicar no botão de Novo Fabricante
    Então o sistema deverá redirecionar para o formulário de Cadastro de novo fabricante

  Cenário: Cadastro de fabricante sem modelo de controlador
    Quando o usuário acessar a tela de cadastro de novos fabricantes
    E o usuário preencher o campo "Nome" com "Raro Labs"
    E clicar no botão de salvar
    Então o registro do fabricante deverá ser salvo com "nome" igual a "Raro Labs"
    E o sistema deverá retornar à tela de listagem de fabricantes

  Cenário: Cadastro de fabricante com um modelo de controlador
    Quando o usuário acessar a tela de cadastro de novos fabricantes
    E o usuário preencher o campo "Nome" com "MobiLab"
    E clicar no botão de salvar
    Então o registro do fabricante deverá ser salvo com "nome" igual a "MobiLab"
    E o registro do fabricante deverá ser salvo com "modelo" igual a "Default"
    E o sistema deverá retornar à tela de listagem de fabricantes

  Cenário: Acesso à tela de detalhes de fabricantes
    Quando o usuário acessar a tela de listagem de fabricantes
    E clicar no botão de visualizar fabricante
    Então o sistema deverá redirecionar para a tela de visualização de fabricantes

  Cenário: Acesso à tela de edição de fabricantes
    Quando o usuário acessar a tela de listagem de fabricantes
    E clicar no botão de editar fabricante
    Então o sistema deverá redirecionar para o formulário de edição fabricantes

  Cenário: Edição de fabricantes
    Quando o usuário acessar o formulário de edição de fabricantes
    E o usuário preencher o campo "Nome" com "Fabricante"
    E clicar no botão de salvar
    Então o registro do fabricante deverá ser salvo com "nome" igual a "Fabricante"
    E o sistema deverá retornar à tela de listagem de fabricantes

  Cenário: Exclusão de fabricantes sem confirmação
    Quando o usuário acessar a tela de listagem de fabricantes
    E clicar no botão de excluir um fabricante
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o fabricante
    Quando o usuário responde não
    Então o sistema deverá mostrar "3" items na tabela

  Cenário: Exclusão de fabricantes com confirmação
    Quando o usuário acessar a tela de listagem de fabricantes
    E clicar no botão de excluir um fabricante
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o fabricante
    Quando o usuário confirmar
    Então o sistema deverá mostrar "2" items na tabela

  Cenário: Exclusão de fabricantes com modelos e controladores associados
    Dado que o sistema possui ao menos um controlador cadastrado
    Quando o usuário acessar a tela de listagem de fabricantes
    E clicar no botão de excluir um fabricante
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o fabricante
    Quando o usuário confirmar
    Então o sistema deverá mostrar "1" items na tabela
    E o sistema exibe uma mensagem "Este fabricante possui modelos utilizados em controladores"
