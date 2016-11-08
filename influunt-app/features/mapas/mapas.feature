# language: pt
@mapas
Funcionalidade: Vizualisar Controladores em um Mapa

  Cenário: Acessar o mapa dos meus controladores
    Dado que o sistema possui ao menos um controlador cadastrado
    E que o sistema possua planos para o controlador cadastrado
    E que o sistema possua tabela horária para o controlador cadastrado
    Quando que o usuário acesse a página de listagem de controladores
    E o usuário clicar no botão "Ver no mapa"
    Então o sistema deve redirecionar para o mapa

  Cenário: Ver as informações do anel
    Dado o usuário clicar no anel "2" no mapa
    Então painel com opções deverá aparecer
    E painel ação deve conter "1.000.0001.2"
    E painel ação deve conter "5 Planos"

  Cenário: Ver os diagramas do plano 1 no anel
    Dado o painel com opções esteja aberto
    E o usuário clicar para abrir os planos
    E o usuário clicar no plano "Plano 1 - PLANO 1"
    Então o sistema deve mostrar o diagrama "Isolado" no grupo "G4" com "indicacao-vermelho" em "3s" segundos
    E o sistema deve mostrar o diagrama "Isolado" no grupo "G4" com "indicacao-verde" em "18s" segundos
    E o sistema deve mostrar o diagrama "Isolado" no grupo "G4" com "indicacao-amarelo" em "3s" segundos
    E o sistema deve mostrar o diagrama "Isolado" no grupo "G4" com "indicacao-vermelho" em "21s" segundos
    E o sistema deve mostrar o diagrama "Isolado" no grupo "G5" com "indicacao-amarelo" em "3s" segundos
    E o sistema deve mostrar o diagrama "Isolado" no grupo "G5" com "indicacao-vermelho" em "21s" segundos
    E o sistema deve mostrar o diagrama "Isolado" no grupo "G5" com "indicacao-verde" em "21s" segundos
    Dado o usuário clicar em fechar o diagrama

  Cenário: Ver os diagramas do plano 2 no anel
    Dado o painel com opções esteja aberto
    E o usuário clicar no plano "Plano 2 - PLANO 2"
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-vermelho" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-verde" em "12s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G4" com "indicacao-vermelho" em "12s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G5" com "indicacao-amarelo" em "3s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G5" com "indicacao-vermelho" em "15s" segundos
    Então o sistema deve mostrar o diagrama "Coordenado" no grupo "G5" com "indicacao-verde" em "12s" segundos
    Dado o usuário clicar em fechar o diagrama

  #TODO - Aguardando a issue 1371
  # Cenário: Ver os diagramas do plano 3 no anel
  #   Dado o painel com opções esteja aberto
  #   E o usuário clicar no plano "Plano 3 - PLANO 3"
  #   Então o sistema deve mostrar o diagrama "Intermitente" no grupo "G4" com "indicacao-amarelo-intermitente" em "30s" segundos
  #   Então o sistema deve mostrar o diagrama "Intermitente" no grupo "G4" com "indicacao-amarelo-intermitente" em "30s" segundos
  #   Dado o usuário clicar em fechar o diagrama

  # Cenário: Ver os diagramas do plano 4 no anel
  #   Dado o painel com opções esteja aberto
  #   E o usuário clicar no plano "Plano 4 - PLANO 4"
  #   Então o sistema deve mostrar o diagrama "Apagado" no grupo "G4" com "indicacao-apagado" em "30s" segundos
  #   Então o sistema deve mostrar o diagrama "Apagado" no grupo "G4" com "indicacao-apagado" em "30s" segundos
  #   Dado o usuário clicar em fechar o diagrama

  # Cenário: Ver os diagramas do plano 5 no anel
  #   Dado o painel com opções esteja aberto
  #   E o usuário clicar para abrir os planos
  #   E o usuário clicar no plano "Plano 5 - PLANO 5"
  #   Então o sistema deve mostrar um alert com a mensagem atuado não possue diagrama
  #   E o usuário confirmar

  Cenário: Enviar plano
    Dado o painel com opções esteja aberto
    Quando no painel clicar no botão "Enviar plano"
    Então o sistema deve mostrar um alert para o usuário com a mensagem "Tem certeza que você deseja enviar o PLANO 1 para o anel da Alameda Campinas com Av. Paulista?"
    E o usuário confirmar
    Quando o usuário preencha o alert com "Plano Enviado para ativação"
    E o usuário confirmar
    Então o sistema deve mostrar um alert para o usuário com a mensagem "PLANO 1 enviado com sucesso ao anel Alameda Campinas com Av. Paulista"
    Quando o usuário confirmar
    Então o sistema deve redirecionar para o mapa
