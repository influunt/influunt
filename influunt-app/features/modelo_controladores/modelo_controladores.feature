# language: pt
@crud @modelo_controladores @interfaces
Funcionalidade: tela de cadastro de modelos de controladores

  Cenário: Acesso à tela de novo modelo de crontroladores
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão de Novo Modelo
    Então o sistema deverá redirecionar para o formulário de cadastro de novos modelos

  Cenário: Verificar valicações em branco
    Dado clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "descricao"
    E o sistema deverá indicar erro no campo "fabricante"

  Cenário: Verificar valicações de valores negativo para os limetes
    Dado o usuário preencher o campo "Limite Estágios" com "-1"
    E o usuário preencher o campo "Limite Grupos Semafóricos" com "-1"
    E o usuário preencher o campo "Limite Aneies" com "-1"
    E o usuário preencher o campo "Limite Detectores Pedestre" com "-1"
    E o usuário preencher o campo "Limite Detectores Veicular" com "-1"
    E o usuário preencher o campo "Limite Tabelas Entre Verdes" com "-1"
    E o usuário preencher o campo "Limite Planos" com "-1"
    Quando clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "limiteEstagio"
    E o sistema deverá indicar erro no campo "limiteGrupoSemaforico"
    E o sistema deverá indicar erro no campo "limiteAnel"
    E o sistema deverá indicar erro no campo "limiteDetectorPedestre"
    E o sistema deverá indicar erro no campo "limiteDetectorVeicular"
    E o sistema deverá indicar erro no campo "limiteTabelasEntreVerdes"
    E o sistema deverá indicar erro no campo "limitePlanos"

  Cenário: Cadastro de modelos
    Dado que tenha um fabricante cadastrado
    Quando o usuário acessar a tela de cadastro de novos modelos
    E o usuário preencher o campo "Descrição" com "Modelo 1"
    E o usuário selecionar o valor "Raro Labs" no campo "Fabricante"
    E o usuário preencher o campo "Limite Estágios" com "8"
    E o usuário preencher o campo "Limite Grupos Semafóricos" com "8"
    E o usuário preencher o campo "Limite Aneies" com "8"
    E o usuário preencher o campo "Limite Detectores Pedestre" com "8"
    E o usuário preencher o campo "Limite Detectores Veicular" com "8"
    E o usuário preencher o campo "Limite Tabelas Entre Verdes" com "8"
    E o usuário preencher o campo "Limite Planos" com "16"
    E clicar no botão de salvar
    Então o registro do modelo deverá ser salvo com nome igual a "Modelo 1"
    E o sistema deverá retornar à tela de listagem de modelos

  Cenário: Visualizar
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão de visualizar um modelo
    Então o sistema deverá redirecionar para a tela de visualização de modelos

  Cenário: Listagem de modelos
    Dado que o usuário esteja na tela de listagem de modelos
    Então deve ser exibida uma lista com o modelo já cadastrado no sistema

  Cenário: Acesso à tela de edição de modelos
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão "Editar" do "Modelo 1"
    Então o sistema deverá redirecionar para o formulário de edição de modelos

  Cenário: Edição de modelos
    Quando o usuário acessar o formulário de edição de modelos
    E o usuário limpe o campo Descrição
    E o usuário preencher o campo "Descrição" com "Modelo 2"
    E o usuário preencher o campo "Limite Estágios" com "16"
    E clicar no botão de salvar
    Então o registro do modelo deverá ser salvo com nome igual a "Modelo 2"
    E o sistema deverá retornar à tela de listagem de modelos

  Cenário: Não Exclusão de modelo sem confirmação do usuário
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão "Excluir" do "Modelo 2"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário responde não
    Então nenhum modelo deve ser excluído

  Cenário: Exclusão de modelo com confirmação do usuário
    Dado que o usuário esteja na tela de listagem de modelos
    E clicar no botão "Excluir" do "Modelo 2"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então o item deverá ser excluido

  Cenário: Deve Salvar um modelo padrão quando usuário não passar os limetes
    Dado o usuário acessar a tela de cadastro de novos modelos
    E o usuário preencher o campo "Descrição" com "Modelo 1"
    E o usuário selecionar o valor "Raro Labs" no campo "Fabricante"
    Quando clicar no botão de salvar
    Então o sistema deverá mostrar na tabela o valor "Modelo 1"
    E o sistema deverá mostrar em linhas com valor "LIMITE ESTÁGIOS: 16" na tabela
    E o sistema deverá mostrar em linhas com valor "LIMITE GRUPOS SEMAFÓRICOS: 16" na tabela
    E o sistema deverá mostrar em linhas com valor "LIMITE ANEIS: 4" na tabela
    E o sistema deverá mostrar em linhas com valor "LIMITE DETECTORES PEDESTRE: 4" na tabela
    E o sistema deverá mostrar em linhas com valor "LIMITE DETECTORES VEICULARES: 8" na tabela
    E o sistema deverá mostrar em linhas com valor "LIMITE TABELAS ENTRE-VERDES: 2" na tabela
    E o sistema deverá mostrar em linhas com valor "LIMITE Planos: 16" na tabela

