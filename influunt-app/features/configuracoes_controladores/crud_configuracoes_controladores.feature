# language: pt
@crud @configuracoes_controladores @interfaces
Funcionalidade: tela de cadastro de configurações de controladores

  Cenário: Listagem de configurações de controladores
    Dado que exista ao menos uma configuração de controlador cadastrado no sistema
    Quando o usuário acessar a tela de listagem de configurações de controladores
    Então deve ser exibida uma lista com os configurações de controladores já cadastrados no sistema

  Cenário: Acesso à tela de novo configuração controlador
    Quando o usuário acessar a tela de listagem de configurações de controladores
    E clicar no botão de Nova configuração controlador
    Então o sistema deverá redirecionar para o formulário de Cadastro de novo configuração controlador

  Cenário: Cadastro de configuração de controlador
    Quando o usuário acessar a tela de cadastro de configurações de controladores
    E preencher os campos da configuração corretamente com a descricao "Desc Config"
    Então o registro do configuração controlador com a descricao "Desc Config" deverá ter sido salvo com sucesso
    E o sistema deverá retornar à tela de listagem de configurações de controladores

  Cenário: Acesso à tela de detalhes de configurações de controladores
    Quando o usuário acessar a tela de listagem de configurações de controladores
    E clicar no botão de visualizar configuração controlador
    Então o sistema deverá redirecionar para a tela de visualização de configurações de controladores

  Cenário: Acesso à tela de edição de configurações de controladores
    Quando o usuário acessar a tela de listagem de configurações de controladores
    E clicar no botão de editar configuração controlador
    Então o sistema deverá redirecionar para o formulário de edição configurações de controladores

  Cenário: Edição de configurações de controladores
    Quando o usuário acessar o formulário de edição de configurações de controladores
    E preencher os campos da configuração corretamente com a descricao "Desc Atualizacao"
    Então o registro do configuração controlador com a descricao "Desc Atualizacao" deverá ter sido salvo com sucesso
    E o sistema deverá retornar à tela de listagem de configurações de controladores

  Cenário: Exclusão de configurações de controladores com confirmação
    Quando o usuário acessar a tela de listagem de configurações de controladores
    E clicar no botão de excluir um configuração controlador
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o configuração controlador
    Quando o usuário responde sim
    Então a configuração controlador deverá ser excluido

  Cenário: Exclusão de configurações de controladores sem confirmação
    Quando o usuário acessar a tela de listagem de configurações de controladores
    E clicar no botão de excluir um configuração controlador
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o configuração controlador
    Quando o usuário responde não
    Então nenhuma configuração controlador deve ser excluído
