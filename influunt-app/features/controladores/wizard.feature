# language: pt
@controladores @wizard @interfaces
Funcionalidade: Fluxo de cadastro de controladores

  Cenário: Acesso à tela de cadastro de controladores
    Dado que o usuário acesse a página de listagem de controladores
    E que o sistema possui os dados necessários cadastrados
    Quando o usuário clicar no botão Novo Controlador
    Então o sistema deverá redirecionar para o formulário de Cadastro de Controladores

  Cenário: Tentar salvar controlador com dados básicos em branco
    Dado que o usuário esteja no wizard no passo "Dados Básicos"
    E que o usuário deixe os campos em branco
    Quando o usuário clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar erro nos campos do passo "Dados Básicos"
    E o sistema irá continuar no passo "Dados Básicos"

  Cenário: Salvar dados básicos do controlador
    Dado que o usuário esteja no wizard no passo "Dados Básicos"
    E o usuario selecionar o valor "São Paulo" no campo "Cidade"
    E o usuario selecionar o valor "1" no campo "Área"
    E o usuario preencher o campo "Localização" com "Localização do Controlador"
    E o usuario preencher o campo "Latitude" com "-19.951047"
    E o usuario preencher o campo "Longitude" com "-43.921569799999986"
    E o usuario selecionar o valor "Raro Labs" no campo "Fabricante"
    E o usuario selecionar o valor "Mínima" no campo "Modelo"
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Anéis"

  Cenário: Tentar salvar anéis em branco
    Dado que o usuário esteja no wizard no passo "Anéis"
    E que o usuário deixe os campos em branco
    Quando o usuário clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar erro nos campos do passo "Anéis"
    E o sistema irá continuar no passo "Anéis"

  Cenário: Salvar anéis do controlador
    Dado que o usuário esteja no wizard no passo "Anéis"
    E o usuario preencher o campo "Grupos Semafóricos de pedestre" com "1"
    E o usuario preencher o campo "Grupos Semafóricos veiculares" com "1"
    E o usuario preencher o campo "Número de detectores veiculares" com "0"
    E o usuario preencher o campo "Número de detectores pedestres" com "0"
    E o usuario preencher o campo "Latitude" com "-19.951047"
    E o usuario preencher o campo "Longitude" com "-43.921569799999986"
    # E o usuario adicionar duas imagens para os estágios do anel corrente
    E o usuario adicionar 3 imagens para os estágios do anel corrente
    E o usuario marcar o segundo anel como ativo
    E o usuario selecionar o segundo anel
    E o usuario preencher o campo "Grupos Semafóricos de pedestre" com "1"
    E o usuario preencher o campo "Grupos Semafóricos veiculares" com "1"
    E o usuario preencher o campo "Número de detectores veiculares" com "0"
    E o usuario preencher o campo "Número de detectores pedestres" com "0"
    E o usuario preencher o campo "Latitude" com "-19.951047"
    E o usuario preencher o campo "Longitude" com "-43.921569799999986"
    E o usuario adicionar 2 imagens para os estágios do anel corrente
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Associação"

  Cenário: Tentar salvar associação em branco
    Dado que o usuário esteja no wizard no passo "Associação"
    E que o usuário deixe os campos em branco
    Quando o usuário clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar erro nos campos do passo "Associação"
    E o sistema irá continuar no passo "Associação"

  Cenário: Salvar associação do controlador
    Dado que o usuário esteja no wizard no passo "Associação"
    E o usuario associar o grupo semafórico "G1" com o estágio "E1"
    E o usuario marcar o grupo semafórico como "Veicular"
    E o usuário selecionar o estágio "E2"
    E o usuario associar o grupo semafórico "G2" com o estágio "E2"
    E o usuario marcar o grupo semafórico como "Pedestre"
    E o usuário selecionar o estágio "E3"
    E o usuario associar o grupo semafórico "G1" com o estágio "E3"
    E o usuario selecionar o segundo anel
    E o usuário selecionar o estágio "E1"
    E o usuario associar o grupo semafórico "G3" com o estágio "E1"
    E o usuario marcar o grupo semafórico como "Veicular"
    E o usuário selecionar o estágio "G2"
    E o usuario associar o grupo semafórico "G4" com o estágio "E2"
    E o usuario marcar o grupo semafórico como "Pedestre"
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Verdes Conflitantes"

  Cenário: Tentar salvar verdes conflitantes em branco
    Dado que o usuário esteja no wizard no passo "Verdes Conflitantes"
    E que a tabela de conflitos esteja em branco
    Quando o usuário clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar erro nos campos do passo "Verdes Conflitantes"
    E o sistema irá continuar no passo "Verdes Conflitantes"

  Cenário: Tentar salvar verdes conflitantes com conflito
    Dado que o usuário esteja no wizard no passo "Verdes Conflitantes"
    E que a tabela de conflitos esteja em branco
    E marcar conflito entre os estágios "G2" e "G3"
    E marcar conflito entre os estágios "G1" e "G4"
    Quando o usuário clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar conflito
    E o sistema irá continuar no passo "Verdes Conflitantes"

  Cenário: Salvar verdes conflitantes do controlador
    Dado que o usuário esteja no wizard no passo "Verdes Conflitantes"
    E que a tabela de conflitos esteja em branco
    E marcar conflito entre os estágios "G1" e "G2"
    E marcar conflito entre os estágios "G3" e "G4"
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Transições Proibidas"

  Cenário: Tentar salvar uma transição proibida sem informar estágio alternativo
    Dado que o usuário esteja no wizard no passo "Transições Proibidas"
    E que a tabela de estágios alternativos esteja em branco
    Quando o usuário marcar a transição de "E1" para "E2" como proibida
    E clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar que o campo de estágio alternativo para a transição "E1-E2" é obrigatório
    E o sistema irá continuar no passo "Transições Proibidas"

  Cenário: Tentar salvar uma transição proibida informando um estágio alternativo
    Dado que o usuário esteja no wizard no passo "Transições Proibidas"
    E que a tabela de estágios alternativos esteja em branco
    Quando o usuário marcar a transição de "E1" para "E2" como proibida
    E preencher o campo de alternativa para a transição "E1-E2" com o estágio "E3"
    E clicar no botão para ir pro próximo passo
    Então o sistema irá redirecionar o usuário para a página de listagem de controladores
