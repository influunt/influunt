# language: pt
@crud @config @interfaces
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
    E preencher o campo "Descrição" com "Descrição da Configuração"
    E preencher o campo "Limite Aneis" com "2"
    E preencher o campo "Limite Grupos Semafóricos" com "2"
    E preencher o campo "Limite Estágios" com "2"
    E preencher o campo "Limite Detectores Pedestre" com "1"
    E preencher o campo "Limite Detectores Veiculares" com "1"
    E clicar no botão de salvar
    Então o registro da configuração controlador deverá ser salvo com descrição "Descrição da Configuração"
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
    E preencher o campo "Descrição" com "Descrição Atualizada"
    E preencher o campo "Limite Aneis" com "4"
    E preencher o campo "Limite Grupos Semafóricos" com "4"
    E preencher o campo "Limite Estágios" com "4"
    E preencher o campo "Limite Detectores Pedestre" com "2"
    E preencher o campo "Limite Detectores Veiculares" com "2"
    E clicar no botão de salvar
    Então o registro da configuração controlador deverá ser salvo com descrição "Descrição Atualizada"
    E o sistema deverá retornar à tela de listagem de configurações de controladores

  Cenário: Exclusão de configurações de controladores sem confirmação
    Quando o usuário acessar a tela de listagem de configurações de controladores
    E clicar no botão de excluir um configuração controlador
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o configuração controlador
    Quando o usuário responde não
    Então nenhuma configuração do controlador deve ser excluída

  Cenário: Exclusão de configurações de controladores com confirmação
    Quando o usuário acessar a tela de listagem de configurações de controladores
    E clicar no botão de excluir um configuração controlador
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o configuração controlador
    Quando o usuário responde sim
    Então a configuração controlador deverá ser excluido
