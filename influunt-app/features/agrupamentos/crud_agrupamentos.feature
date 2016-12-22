# language: pt
@agrupamentos
Funcionalidade: tela de cadastro de agrupamentos

  Cenário: Listagem de agrupamentos
    Dado que o sistema possua controladores cadastrados e configurados
    Quando o usuário acesse a listagem de "agrupamentos"
    Então o sistema deverá mostrar "1" items na tabela

  Cenário: Acesso à tela de novo agrupamento
    Quando o usuário acesse a listagem de "agrupamentos"
    E clicar no botão de Novo Agrupamento
    Então o sistema deverá redirecionar para o formulário de cadastro de novos agrupamentos

  Cenário: Não posso cadastrar um agrupamento em branco
    Quando o usuário acesse a listagem de "agrupamentos"
    E clicar no botão de Novo Agrupamento
    E clicar no botão de salvar
    Então sistema deverá mostar um alerta se deseja atualizar tabela horária
    E o usuário confirmar
    Então o sistema deverá indicar erro no campo "nome"
    Então o sistema deverá indicar erro no campo "tipo"
    Então o sistema deverá indicar erro no campo "agrupamentoPlanoDiaSemana"
    Então o sistema deverá indicar erro no campo "planoHora"

  Cenário: Não posso salvar sem associar a pelo menos uma anel
    Dado o usuário acessar a tela de cadastro de novos agrupamentos
    E o usuário preencher o campo "Nome" com "Corredor da Paulista"
    E o usuário preencher o campo "Descrição" com "Agrupamento 1"
    E o usuário selecionar o valor "Corredor" no campo "Tipo"
    E o usuário selecionar o valor "Av. Paulista, nº 1000. ref.: AREA 1" para o campo "controladores"
    E o usuário em evento selecionar o valor "DOMINGO" no campo "Dias"
    E o usuário em evento selecionar o valor "12" no campo "Hora"
    E o usuário em evento selecionar o valor "10" no campo "Minuto"
    E o usuário em evento selecionar o valor "1" no campo "Segundo"
    E o usuário em evento selecionar o valor plano "1" no campo "Plano"
    Quando clicar no botão de salvar
    Então sistema deverá mostar um alerta se deseja atualizar tabela horária
    Quando o usuário confirmar
    Então o sistema deve mostrar erro no campo controladores avulsos com a mensagem "este agrupamento deve ter pelo menos 1 anel."

  Cenário: Salvar um agrupamento
    Dado o usuário agrupar o controlador "1.003.0002"
    Quando clicar no botão de salvar
    E sistema deverá mostar um alerta se deseja atualizar tabela horária
    E o usuário confirmar
    Então o sistema deverá mostrar "1" items na tabela

  Cenário: Acesso à tela de detalhes de um agrupamento
    Dado o usuário acesse a listagem de "agrupamentos"
    Quando clicar no botão de visualizar um agrupamento
    Então o sistema deverá redirecionar para a tela de visualização de agrupamentos

  Cenário: Acesso à tela de edição de agrupamentos
    Dado o usuário acesse a listagem de "agrupamentos"
    E clicar no botão de editar um agrupamento
    Então o sistema deverá redirecionar para o formulário de edição de agrupamentos

  Cenário: Edição de agrupamentos
    Dado o usuário acesse a listagem de "agrupamentos"
    Quando o usuário acessar o formulário de edição de agrupamentos
    E o usuário preencher o campo "Nome" com "Rota da Cidade"
    E o usuário selecionar o valor "Rota" no campo "Tipo"
    E clicar no botão de salvar
    Então sistema deverá mostar um alerta se deseja atualizar tabela horária
    E o usuário confirmar
    Então o sistema deverá mostrar "1" items na tabela

  Cenário: Validar tempo simétrico ou assimétrico
    Dado o usuário acesse a listagem de "agrupamentos"
    E o usuário acessar o formulário de edição de agrupamentos
    Quando o usuário selecionar o valor "AREA SUL PAULISTA" para o campo "subareas"
    E o usuário selecionar o valor "Av. Paulista com R. Pamplona" para o campo "controladores"
    E o usuário agrupar o controlador "1.000.0001"
    E clicar no botão de salvar
    Então sistema deverá mostar um alerta se deseja atualizar tabela horária
    E o usuário confirmar
    E o sistema deverá apresentar erro de "O Tempo de ciclo deve ser simétrico ou assimétrico ao tempo de ciclo dos planos."

  Cenário: Exclusão de agrupamentos sem confirmação do usuário
    Quando o usuário acesse a listagem de "agrupamentos"
    E clicar no botão de excluir um agrupamento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o agrupamento
    Quando o usuário responde não
    Então o sistema deverá mostrar "1" items na tabela

  Cenário: Exclusão de agrupamentos com confirmação do usuário
    Dado o usuário acesse a listagem de "agrupamentos"
    E clicar no botão de excluir um agrupamento
    Então o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o agrupamento
    Quando o usuário confirmar
    Então o sistema deverá mostrar "1" items na tabela
