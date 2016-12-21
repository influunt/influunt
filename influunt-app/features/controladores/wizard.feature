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
    Então o sistema deverá indicar erro no campo "endereco"
    E o sistema deverá indicar erro no campo "area"
    E o sistema deverá indicar erro no campo "endereco2" com a mensagem "não pode ficar em branco, caso não seja preenchido a altura numérica."
    E o sistema deverá indicar erro no campo "modelo"
    E o sistema irá continuar no passo "Dados Básicos"

  Cenário: Salvar dados básicos do controlador
    Dado que o usuário esteja no wizard no passo "Dados Básicos"
    E o usuário selecionar o valor "São Paulo" no campo "Cidade"
    E o usuário adicionar imagem ao "Croqui"
    E o usuário selecionar o valor "1" no campo "Área"
    E o usuário buscar o endereço "Av Paulista" no primeiro endereço
    E o usuário buscar o endereço "Rua Bela Cintra" para o endereço 2
    E o usuário preencher o campo "Altura Numérica" com "123"
    E o usuário selecionar o valor "Raro Labs" no campo "Fabricante"
    E o usuário selecionar o valor "Mínima" no campo "Modelo"

  Cenário: Validar o número do SMEE buscando da base do SMEE
    Dado que o usuário esteja no wizard no passo "Dados Básicos"
    E o usuário preencher o campo NÚMERO SMEE com "0"
    E o usuário realizar um scroll down
    Então o sistema exibe um alerta com a mensagem "O número informado não é reconhecido na base do SMEE."
    E o usuário confirmar

  Cenário: Preencher o valor SMEE correto
    Dado que o usuário esteja no wizard no passo "Dados Básicos"
    E o usuário limpar o campo "SMEE"
    E o usuário preencher o campo NÚMERO SMEE com "1234"
    Então o sistema exibe um alerta com a mensagem "O endereço pesquisado é RIO BONITO, AV DO x NEUCHATEL, R x GUIDO BONI, PC - (**)?"
    E o usuário confirmar
    Quando clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Anéis"

  Cenário: Salvar anéis do controlador
    Dado que o usuário esteja no wizard no passo "Anéis"
    Quando o usuário adicionar imagem ao "Anel 1 DWG"
    Então o sistema deverá apresentar erro com a frase "Não foi possível adicionar este arquivo. Exclua-o e tente novamente."

  Cenário: Salvar anéis do controlador
    Dado que o usuário esteja no wizard no passo "Anéis"
    E o usuário limpar o campo endereço 1
    E o usuário buscar o endereço "Av Paulista" no primeiro endereço
    E o usuário limpar o campo endereço 2
    E o usuário buscar o endereço "Rua Bela Cintra" para o endereço 2
    E o usuário adicionar imagem ao "Anel 1 como Estágio1"
    E o usuário adicionar imagem ao "Anel 1 como Estágio2"
    E o usuário adicionar imagem ao "Anel 1 como Estágio3"
    E o usuário adicionar um novo anel ativo
    E o usuário adicionar imagem ao "Anel 2 como Estágio1"
    E o usuário adicionar imagem ao "Anel 2 como Estágio2"
    E o usuário buscar o endereço "Av Paulista" no primeiro endereço
    E o usuário buscar o endereço "Rua Augusta" para o endereço 2
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Grupos Semafóricos"

  Cenário: Tentar salvar grupos semafóricos em branco
    Dado que o usuário esteja no wizard no passo "Grupos Semafóricos"
    E que o usuário deixe os campos em branco
    Quando o usuário clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar erro nas quantidades de grupos semaforicos dos anéis
    E o sistema irá continuar no passo "Grupos Semafóricos"

  Cenário: Salvar grupos semafóricos do controlador
    Dado que o usuário esteja no wizard no passo "Grupos Semafóricos"
    E que o usuário adicione 3 grupos semafóricos ao anel
    Quando o usuário marcar o grupo semafórico "G1" como "Veicular"
    E o usuário marcar o grupo semafórico "G2" como "Veicular"
    E o usuário marcar o grupo semafórico "G3" como "Pedestre"
    E o usuário selecionar o anel 2
    E que o usuário adicione 2 grupos semafóricos ao anel
    Quando o usuário marcar o grupo semafórico "G4" como "Veicular"
    E o usuário marcar o grupo semafórico "G5" como "Pedestre"
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Verdes Conflitantes"

  Cenário: Tentar salvar verdes conflitantes em branco
    Dado que o usuário esteja no wizard no passo "Verdes Conflitantes"
    E que a tabela de conflitos esteja em branco
    Quando o usuário clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar erro nos campos do passo "Verdes Conflitantes"
    E o sistema irá continuar no passo "Verdes Conflitantes"

  Cenário: Tentar salvar verdes conflitantes incompleto
    Dado que o usuário esteja no wizard no passo "Verdes Conflitantes"
    E o usuário selecionar o anel 1
    E que a tabela de conflitos esteja em branco
    Quando marcar conflito entre os grupos "G2" e "G3"
    E o usuário clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar tabela incompleta para o grupo "G1"
    Quando o usuário selecionar o anel 2
    Então o sistema deverá indicar tabela incompleta para o grupo "G4"
    E o sistema deverá indicar tabela incompleta para o grupo "G5"
    E o sistema irá continuar no passo "Verdes Conflitantes"

  Cenário: Salvar verdes conflitantes do controlador
    Dado que o usuário esteja no wizard no passo "Verdes Conflitantes"
    E o usuário selecionar o anel 1
    E que a tabela de conflitos esteja em branco
    Quando marcar conflito entre os grupos "G1" e "G2"
    E marcar conflito entre os grupos "G2" e "G3"
    E o usuário selecionar o anel 2
    E marcar conflito entre os grupos "G4" e "G5"
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
    E o usuário selecionar o anel 1
    E o usuário associar o grupo semafórico "G1" com o estágio "E1"
    E o usuário associar o grupo semafórico "G2" com o estágio "E2"
    E o usuário associar o grupo semafórico "G3" com o estágio "E3"
    E o usuário selecionar o anel 2
    E o usuário associar o grupo semafórico "G4" com o estágio "E1"
    E o usuário associar o grupo semafórico "G5" com o estágio "E2"
    E clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Transições Proibidas"

  Cenário: Deve mostrar o tooltip com uma mensagem
    Dado que o usuário esteja no wizard no passo "Transições Proibidas"
    Então o botão salvar e avançar deve ter a mensagem "É necessário preencher os dados ou confirmar que não há nada a ser feito em todos os anéis."

  Cenário: Tentar salvar uma transição proibida sem informar estágio alternativo
    Dado que o usuário esteja no wizard no passo "Transições Proibidas"
    E que a tabela de estágios alternativos esteja em branco
    E o usuário marcar a transição de "E1" para "E2" como proibida
    E o usuário selecionar o anel 2
    Quando o usuário marcar a transição de "E1" para "E2" como proibida
    E clicar no botão para ir pro próximo passo
    Então o sistema deverá indicar que o campo de estágio alternativo para a transição "E1-E2" é obrigatório
    E o sistema irá continuar no passo "Transições Proibidas"

  Cenário: Tentar salvar uma transição inválida para o estágio
    Dado que o usuário esteja no wizard no passo "Transições Proibidas"
    E o usuário selecionar o anel 1
    E o usuário selecionar o valor "E1" no campo "Alternativa"
    E o usuário selecionar o anel 2
    E o usuário selecionar o valor "E1" no campo "Alternativa"
    Quando clicar no botão para ir pro próximo passo
    Então o sistema deverá conter erro no estágio "E1" do "Anel 2"
    E o sistema irá continuar no passo "Transições Proibidas"

  Cenário: Tentar salvar uma transição proibida informando um estágio alternativo
    Dado que o usuário esteja no wizard no passo "Transições Proibidas"
    E o usuário selecionar o anel 1
    E o usuário selecionar o valor "E1" no campo "Alternativa"
    E o usuário selecionar o anel 2
    Quando o usuário desmarcar a transição de "E1" para "E2" como proibida
    E que o usuário confirme que não há configurações a serem feitas nesse anel
    Quando clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Tabela Entre Verdes"

  Cenário: Deve mostrar o tooltip com uma mensagem diferente para entre verder
    Dado que o usuário esteja no wizard no passo "Tabela Entre Verdes"
    Então o botão salvar e avançar deve ter a mensagem "É necessário preencher os dados ou confirmar que não há nada a ser feito em cada grupo semafórico. Verifique todos os grupos "

  Cenário: Salvar tabela entre verdes
    Dado que o usuário esteja no wizard no passo "Tabela Entre Verdes"
    E que o usuário marque 4 no tempo "Amarelo" da transição "E1-E3"
    E que o usuário marque 2 no tempo "Vermelho de Limpeza" da transição "E1-E3"
    E que o usuário clique no grupo "G2"
    E confirma que será utilizada a configuração padrão para esse grupo semafórico
    E que o usuário clique no grupo "G3"
    E confirma que será utilizada a configuração padrão para esse grupo semafórico
    E o usuário selecionar o anel 2
    E que o usuário clique no grupo "G4"
    E confirma que será utilizada a configuração padrão para esse grupo semafórico
    E que o usuário clique no grupo "G5"
    E que o usuário marque 2 no tempo "Vermelho Intermitente" da transição "E2-E1"
    Entao o sistema deverá mostrar um alerta para valor digitado menor que o limite mínimo
    E o usuário responde ok
    E que o usuário marque 2 no tempo "Vermelho de Limpeza" da transição "E2-E1"
    Quando o usuário clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Atraso de Grupo"

  Cenário: Tentar salvar um atraso de grupo
    Dado que o usuário esteja no wizard no passo "Atraso de Grupo"
    E que o usuário marque 15 no campo 1 para transições com perda de direito de passagem
    E que o usuário marque 20 no campo 1 para transições com ganho de direito de passagem
    E que o usuário marque 15 no campo 2 para transições com ganho de direito de passagem
    E que o usuário clique no grupo "G2"
    E que o usuário marque 23 no campo 1 para transições com perda de direito de passagem
    Então o sistema deverá mostrar um alerta para valor digitado maior que o limite máximo
    E o usuário responde ok
    E que o usuário marque 15 no campo 2 para transições com perda de direito de passagem
    E que o usuário marque 10 no campo 1 para transições com ganho de direito de passagem
    E que o usuário clique no grupo "G3"
    E que o usuário marque 4 no campo 1 para transições com perda de direito de passagem
    E que o usuário marque 12 no campo 2 para transições com perda de direito de passagem
    E que o usuário marque 11 no campo 1 para transições com ganho de direito de passagem
    E que o usuário marque 8 no campo 2 para transições com ganho de direito de passagem
    Quando clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Detectores"

  Cenário: Tentar salvar detectores sem configurar
    Dado que o usuário esteja no wizard no passo "Detectores"
    E que o usuário adicione um detector do tipo "Veicular"
    E que o usuário adicione um detector do tipo "Veicular"
    E o usuário realizar um scroll down
    E que o usuário adicione um detector do tipo "Pedestre"
    Quando o usuário clicar no botão para ir pro próximo passo
    E o sistema irá continuar no passo "Detectores"

  Cenário: Salvar detectores
    Dado que o usuário esteja no wizard no passo "Detectores"
    E que o usuário associe o detector "DP1" com o estágio "E3"
    E que o usuário marque 40 no tempo "Ausência de Detecção"
    E o usuário clicar para fechar o modal
    E que o usuário associe o detector "DV1" com o estágio "E1"
    E que o usuário marque 40 no tempo "Ausência de Detecção"
    E o usuário clicar para fechar o modal
    E que o usuário associe o detector "DV2" com o estágio "E2"
    E que o usuário marque 40 no tempo "Ausência de Detecção"
    E o usuário clicar para fechar o modal
    E o usuário selecionar o anel 2
    E que o usuário adicione um detector do tipo "Veicular"
    E que o usuário adicione um detector do tipo "Pedestre"
    E que o usuário associe o detector "DV3" com o estágio "E1"
    E que o usuário marque 40 no tempo "Ausência de Detecção"
    E o usuário clicar para fechar o modal
    E que o usuário associe o detector "DP2" com o estágio "E2"
    E que o usuário marque 40 no tempo "Ausência de Detecção"
    E o usuário clicar para fechar o modal
    Quando o usuário clicar no botão para ir pro próximo passo
    Então o sistema irá avançar para o passo "Revisão"

  Cenário: Salvar revisão
    Dado que o usuário esteja no wizard no passo "Revisão"
    Quando o usuário clicar no botão "Salvar"
    Então o sistema deverá redirecionar o usuário para a página de listagem de controladores

