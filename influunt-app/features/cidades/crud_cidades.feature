# language: pt
@crud @cidades @interfaces
Funcionalidade: tela de cadastro de cidades
  Contexto:
    Dado que exista o usuário "admin" com senha "12345678"
    E que o usuário "admin" entre no sistema com a senha "12345678"

  Cenário: Listagem de cidades
    Dado que exista ao menos uma cidade cadastrada no sistema
    Quando o usuário acessar a tela de cidades
    Então deve ser exibida uma lista com as cidades já cadastradas no sistema

  Cenário: Acesso à tela de nova cidade
    Quando o usuário acessa a tela de listagem de cidades
    E Clica no botão de Nova Cidade
    Então o sistema deverá redirecionar para o formulário de Cadastro de nova Cidades

  Cenário: Cadastro de cidades
    Quando o usuário acessa a tela de cadastro de novas cidades
    E preenche os campos corretamente
    Então o registro deverá ser salvo com sucesso
    E o sistema deverá retornar à tela de listagem de cidades

  Cenário: Acesso à tela de detalhes de cidades
    Quando o usuário acessa a tela de listagem de cidades
    E Clica no botão de visualizar cidade
    Então o sistema deverá redirecionar para o formulário de visualização cidades
    E esta tela deve exibir o formulário de cidades sem permissão de alteração para os campos

  Cenário: Acesso à tela de edição de cidades
    Quando o usuário acessa a tela de listagem de cidades
    E Clica no botão de editar cidade
    Então o sistema deverá redirecionar para o formulário de edição cidades

  Cenário: Edição de cidades
    Quando o usuário acessa o formulário de edição de cidades
    E preenche os campos corretamente
    Então o registro deverá ser salvo com sucesso
    E o sistema deverá retornar à tela de listagem de cidades

  Cenário: Exclusão de cidades
    Quando o usuário acessa a tela de listagem de cidades
    E clica no botão de excluir uma cidade
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a cidade
    Quando o usuário responde sim
    Então a cidade deverá ser excluida
    Quando o usuário responde não
    Então nenhuma cidade deve ser excluída
