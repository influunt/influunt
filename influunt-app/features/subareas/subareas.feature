# language: pt
@subareas
Funcionalidade: Cadastro de subáreas

  Cenário: Acessar listagem de subáreas
    Dado que exista ao menos uma área cadastrada no sistema
    Quando o usuário acessar a tela de listagem de "subareas"
    Então o sistema deverá mostrar "0" items na tabela

  Cenário: Acesso à tela de nova subárea
    Dado o usuário clicar em "Novo"
    Então o sistema deverá redirecionar para o formulário

  Cenário: Validar campos em branco
    Dado clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "area"
    E o sistema deverá indicar erro no campo "nome"
    E o sistema deverá indicar erro no campo "area" com a mensagem "não pode ficar em branco"
    E o sistema deverá indicar erro no campo "nome" com a mensagem "não pode ficar em branco"

  Cenário: Cadastro de subárea
    Dado o usuário acessar a tela de listagem de "subareas"
    E o usuário clicar em "Novo"
    E o usuário selecionar "São Paulo" para o campo "cidade"
    E o usuário selecionar "51" para o campo "area"
    E o usuário preencher o campo "Nome" com "Subárea Sul Paulista"
    E clicar no botão de salvar
    Então o sistema deverá retornar à tela de listagem de áreas
    E o sistema deverá mostrar na tabela o valor "1"

  Cenário: O sistema deverá apresentar erro cadastrar uma subárea com mesmo nome
    Dado o usuário clicar em "Novo"
    E o usuário selecionar "São Paulo" para o campo "cidade"
    E o usuário selecionar "51" para o campo "area"
    E o usuário preencher o campo "Nome" com "Subárea Sul Paulista"
    E clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "nome" com a mensagem "Já existe uma subárea cadastrada com esse nome."

  Cenário: Exclusão de subáreas com confirmação do usuário
    Dado o usuário acessar a tela de listagem de "subareas"
    E o usuário clicar em "Excluir"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então o sistema deverá mostrar "0" items na tabela

  Cenário: Acesso à tela de detalhes de subárea
    Dado que o sistema possua controladores cadastrados e configurados
    Quando o usuário acessar a tela de listagem de "subareas"
    E o usuário na tabela clicar em "Editar" do registro "AREA SUL PAULISTA"
    Então o sistema deverá redirecionar para o formulário
    E o sistema deverá redirecionar para o show "AREA SUL PAULISTA"

  Cenário: Editar uma subárea
    Dado o usuário acessar a tela de listagem de "subareas"
    E o usuário clicar em "Editar"
    Então o sistema deverá redirecionar para o formulário
    Quando o usuário limpar o campo "nome"
    E o usuário preencher o campo "Nome" com "Subárea Norte Paulista"
    E clicar no botão de salvar
    Então o sistema deverá redirecionar o usuário para a listagem "Subáreas"
    E o sistema deverá mostrar na tabela o valor "Subárea Norte Paulista"
