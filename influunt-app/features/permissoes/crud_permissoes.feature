# language: pt
@crud @permissoes @interfaces
Funcionalidade: tela de cadastro de permissões

  Cenário: Listagem de permissões
    Dado que exista ao menos uma permissão cadastrada no sistema
    Quando o usuário acessar a tela de listagem de permissões
    Então deve ser exibida uma lista com as permissões já cadastradas no sistema

  Cenário: Acesso à tela de nova permissão
    Quando o usuário acessar a tela de listagem de permissões
    E clicar no botão de Nova Permissao
    Então o sistema deverá redirecionar para o formulário de Cadastro de novas Permissões

  Cenário: Cadastro de permissões
    Quando o usuário acessar a tela de cadastro de novas permissões
    E o usuario preencher o campo "Descrição" com "[Cidade] - Listar Cidades"
    E o usuario preencher o campo "Chave" com "GET /api/v1/cidades"
    E clicar no botão de salvar
    Então o registro da permissão deverá ser salvo com descrição igual a "[Cidade] - Listar Cidades"
    E o sistema deverá retornar à tela de listagem de permissões

  Cenário: Acesso à tela de detalhes de uma permissão
    Quando o usuário acessar a tela de listagem de permissões
    E clicar no botão de visualizar permissão
    Então o sistema deverá redirecionar para a tela de visualização de permissões

  Cenário: Acesso à tela de edição de permissões
    Quando o usuário acessar a tela de listagem de permissões
    E clicar no botão de editar permissão
    Então o sistema deverá redirecionar para o formulário de edição de permissões

  Cenário: Edição de permissões
    Quando o usuário acessar o formulário de edição de permissões
    E o usuario preencher o campo "Descrição" com "[Areas] - Listar Areas"
    E o usuario preencher o campo "Chave" com "GET /api/v1/areas"
    E clicar no botão de salvar
    Então o registro da permissão deverá ser salvo com descrição igual a "[Areas] - Listar Areas"
    E o sistema deverá retornar à tela de listagem de permissões

  Cenário: Exclusão de permissão sem confirmação do usuário
    Quando o usuário acessar a tela de listagem de permissões
    E clicar no botão de excluir uma permissão
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a permissão
    Quando o usuário responde não
    Então nenhuma permissão deve ser excluída

  Cenário: Exclusão de permissão sem confirmação do usuário
    Quando o usuário acessar a tela de listagem de permissões
    E clicar no botão de excluir uma permissão
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a permissão
    Quando o usuário responde sim
    Então a permissão deverá ser excluida
