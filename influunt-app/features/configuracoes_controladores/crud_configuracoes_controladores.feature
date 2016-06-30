# language: pt
@crud @configuracoes_controladores @interfaces
Funcionalidade: tela de cadastro de configuracoes_controladores

#  Cenário: Listagem de configuracoes_controladores
#    Dado que exista ao menos um configuracao controlador cadastrado no sistema
#    Quando o usuário acessar a tela de listagem de configuracoes_controladores
#    Então deve ser exibida uma lista com os configuracoes_controladores já cadastrados no sistema
#
#  Cenário: Acesso à tela de novo configuracao controlador
#    Quando o usuário acessar a tela de listagem de configuracoes_controladores
#    E Clica no botão de Novo Fabricante
#    Então o sistema deverá redirecionar para o formulário de Cadastro de novo configuracao controlador

  Cenário: Cadastro de configuração de  controlador
    Quando o usuário acessar a tela de cadastro de configurações de controladores
    E preencher os campos da configuração corretamente
    Então o registro da configuração deverá ser salvo com sucesso
    E o sistema deverá retornar à tela de listagem de configurações de controladores

#  Cenário: Acesso à tela de detalhes de configuracoes_controladores
#    Quando o usuário acessar a tela de listagem de configuracoes_controladores
#    E Clica no botão de visualizar configuracao controlador
#    Então o sistema deverá redirecionar para a tela de visualização de configuracoes_controladores
#
#  Cenário: Acesso à tela de edição de configuracoes_controladores
#    Quando o usuário acessar a tela de listagem de configuracoes_controladores
#    E Clica no botão de editar configuracao controlador
#    Então o sistema deverá redirecionar para o formulário de edição configuracoes_controladores
#
#  Cenário: Edição de configuracoes_controladores
#    Quando o usuário acessar o formulário de edição de configuracoes_controladores
#    E preenche os campos do configuracao controlador corretamente
#    Então o registro do configuracao controlador deverá ser salvo com sucesso
#    E o sistema deverá retornar à tela de listagem de configuracoes_controladores
#
#  Cenário: Exclusão de configuracoes_controladores com confirmação
#    Quando o usuário acessar a tela de listagem de configuracoes_controladores
#    E clica no botão de excluir um configuracao controlador
#    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o configuracao controlador
#    Quando o usuário responde sim
#    Então o configuracao controlador deverá ser excluido
#
#  Cenário: Exclusão de configuracoes_controladores sem confirmação
#    Quando o usuário acessar a tela de listagem de configuracoes_controladores
#    E clica no botão de excluir um configuracao controlador
#    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o configuracao controlador
#    Quando o usuário responde não
#    Então nenhum configuracao controlador deve ser excluído
#
#  Cenário: Cadastro de configuracao controlador com um modelo de controlador
#    Quando o usuário acessar a tela de cadastro de novos configuracoes_controladores
#    E preenche todos os campos do formulário
#    Então o registro do configuracao controlador deverá ser salvo com sucesso
#    E o sistema deverá retornar à tela de listagem de configuracoes_controladores
