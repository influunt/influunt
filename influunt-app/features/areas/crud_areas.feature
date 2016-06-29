# language: pt
@crud @areas @interfaces
Funcionalidade: tela de cadastro de areas
  # Contexto:
  #   Dado que exista o usuário "admin" com senha "12345678"
  #   E que o usuário "admin" entre no sistema com a senha "12345678"

  Cenário: Listagem de areas
    Dado que exista ao menos uma area cadastrada no sistema
    Quando o usuário acessar a tela de listagem de areas
    Então deve ser exibida uma lista com as areas já cadastradas no sistema

  Cenário: Acesso à tela de nova area
    Quando o usuário acessar a tela de listagem de areas
    E Clica no botão de Nova Area
    Então o sistema deverá redirecionar para o formulário de Cadastro de novas Areas

  Cenário: Cadastro de areas
    Quando o usuário acessar a tela de cadastro de novas areas
    E preenche os campos da area corretamente
    Então o registro da área deverá ser salvo com sucesso
    E o sistema deverá retornar à tela de listagem de areas

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
    E preenche os campos da area corretamente
    Então o registro da área deverá ser salvo com sucesso
    E o sistema deverá retornar à tela de listagem de areas

  Cenário: Exclusão de areas com confirmação do usuário
    Quando o usuário acessar a tela de listagem de areas
    E clicar no botão de excluir uma area
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a area
    Quando o usuário responde sim
    Então a area deverá ser excluida

  Cenário: Exclusão de areas sem confirmação do usuário
    Quando o usuário acessar a tela de listagem de areas
    E clica no botão de excluir uma area
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a area
    Quando o usuário responde não
    Então nenhuma área deve ser excluída
