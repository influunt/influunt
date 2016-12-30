# language: pt
@crud @areas @interfaces
Funcionalidade: tela de cadastro de áreas

  Cenário: Listagem de áreas
    Dado que exista ao menos uma área cadastrada no sistema
    Quando o usuário acessar a tela de listagem de "areas"
    Então o sistema deverá mostrar "1" items na tabela

  Cenário: Acesso à tela de nova área
    Dado o usuário acessar a tela de listagem de "areas"
    E o usuário clicar em "Novo"
    Então o sistema deverá redirecionar para o formulário "Areas"

  Cenário: Cadastro de áreas
    Dado o usuário acessar a tela de cadastro de novas áreas
    E o usuário selecionar o valor "São Paulo" no campo "Cidade"
    E o usuário preencher o campo "Área" com "42"
    E clicar no botão de salvar
    Então o sistema exibe uma mensagem "Salvo com sucesso"

  Cenário: Validar campos em branco
    Dado que exista ao menos uma área cadastrada no sistema
    Quando o usuário acessar a tela de cadastro de novas áreas
    E clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "area"
    Então o sistema deverá indicar erro no campo "cidade"

  Cenário: Cadastro de áreas com mesmo nome
    Dado que exista ao menos uma área cadastrada no sistema
    Quando o usuário acessar a tela de cadastro de novas áreas
    E o usuário marcar a cidade como "São Paulo"
    E o usuário preencher o campo "Área" com "51"
    E clicar no botão de salvar
    Então o sistema deverá indicar erro no campo "area"

   Cenário: Adicionar limites geográficos
    Dado que exista ao menos uma área cadastrada no sistema
    Quando o usuário acessar a tela de cadastro de novas áreas
    E o usuário marcar a cidade como "São Paulo"
    E o usuário preencher o campo "Área" com "55"
    E o usuário clicar em "adicionar limites geográficos"
    E o usuário preencher o campo "Latitude" com "-55"
    E o usuário preencher o campo "Longitude" com "55"
    E clicar no botão de salvar
    Então o sistema deverá possuir longitude e latidude com os valores "(-55.0; 55.0)"

  Cenário: Acesso à tela de detalhes de área
    Quando o usuário acessar a tela de listagem de "areas"
    E o usuário na tabela clicar em "Visualizar" do registro "51"
    Então o sistema deverá redirecionar para o show "51"

  Cenário: Acesso à tela de edição de áreas
    Dado o usuário acessar a tela de listagem de "areas"
    E o usuário clicar em "Editar"
    Então o sistema deverá redirecionar para o formulário "Areas"

  Cenário: Edição de áreas
    Dado o usuário selecionar o valor "Belo Horizonte" no campo "Cidade"
    E o usuário limpar o campo "area"
    E o usuário preencher o campo "Área" com "99"
    E clicar no botão de salvar
    Então o sistema exibe uma mensagem "Salvo com sucesso"
    E o usuário acessar a tela de listagem de "areas"

  Cenário: Exclusão de áreas sem confirmação do usuário
    Dado o usuário clicar em "Excluir"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário responde não
    Então o sistema deverá mostrar "2" items na tabela

  Cenário: Exclusão de áreas com confirmação do usuário
    Dado o usuário clicar em "Excluir"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então o sistema exibe uma mensagem "Removido com sucesso"
    E o sistema deverá mostrar "1" items na tabela

  Cenário: Validar a não exclusão de modelos que possuam associação
    Dado que o sistema possui ao menos um controlador cadastrado
    Quando o usuário acessar a tela de listagem de "areas"
    E o usuário clicar em "Excluir"
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir
    Quando o usuário confirmar
    Então o sistema deverá mostrar "1" items na tabela
    E o sistema exibe uma mensagem "Essa área não pode ser removida, pois existe(m) controlador(es) ou subárea(s) vinculado(s) à mesma."
