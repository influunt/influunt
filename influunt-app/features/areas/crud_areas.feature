# language: pt
@crud @areas @interfaces
Funcionalidade: tela de cadastro de áreas

  Cenário: Listagem de áreas
    Dado que exista ao menos uma área cadastrada no sistema
    Quando o usuário acessar a tela de listagem de áreas
    Então o sistema deverá mostrar "1" items na tabela

  Cenário: Acesso à tela de nova área
    Quando o usuário acessar a tela de listagem de áreas
    E clicar no botão de Nova Área
    Então o sistema deverá redirecionar para o formulário "Areas"

  Cenário: Cadastro de áreas
    Quando o usuário acessar a tela de cadastro de novas áreas
    E o usuário selecionar o valor "São Paulo" no campo "Cidade"
    E o usuário preencher o campo "Área" com "42"
    E clicar no botão de salvar
    Então o registro da área deverá ser salvo com número CTA igual a "42"
    E o sistema deverá retornar à tela de listagem de áreas

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
    E clicar no botão de adicionar limites geográficos
    E o usuário preencher o campo "Latitude" com "-55"
    E o usuário preencher o campo "Longitude" com "55"
    E clicar no botão de salvar
    Então o sistema deverá possuir longitude e latidude com os valores "(-55.0; 55.0)"

  Cenário: Acesso à tela de detalhes de área
    Quando o usuário acessar a tela de listagem de áreas
    E o usuário na tabela clicar em "Editar" do registro "55"
    Então o sistema deverá redirecionar para o show "55"

  Cenário: Acesso à tela de edição de áreas
    Quando o usuário acessar a tela de listagem de áreas
    E clicar no botão de editar área
    Então o sistema deverá redirecionar para o formulário de edição de áreas

  Cenário: Edição de áreas
    Quando o usuário acessar o formulário de edição de áreas
    E o usuário selecionar o valor "Belo Horizonte" no campo "Cidade"
    E que o usuário limpe o campo área
    E o usuário preencher o campo "Área" com "99"
    E clicar no botão de salvar
    Então o registro da área deverá ser salvo com número CTA igual a "99"
    E o sistema deverá retornar à tela de listagem de áreas

  Cenário: Exclusão de áreas sem confirmação do usuário
    Quando o usuário acessar a tela de listagem de áreas
    E clicar no botão de excluir uma área
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a área
    Quando o usuário responde não
    Então o sistema deverá mostrar "2" items na tabela

  Cenário: Exclusão de áreas com confirmação do usuário
    Quando o usuário acessar a tela de listagem de áreas
    E clicar no botão de excluir uma área
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a área
    Quando o usuário confirmar
    Então o sistema deverá mostrar "1" items na tabela
