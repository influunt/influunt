# language: pt
@relatorios @relatorio_controlador
Funcionalidade: Imprimir relatório do controlador
  Contexto:
    Dado que o usuário esteja logado no sistema
    E que o usuário deslogue no sistema
    E o usuário confirmar

  Cenário: Imprimir um controlador
    Dado o usuário logue no sistema com usuário "mobilab" e perfil administrador
    E que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar em "Configuração" do controlador "Av. Paulista, nº 1000. ref.: AREA 1"
    Então o usuário clicar em "Imprimir"

  Cenário: O usuário que não tem permissão para imprimir controlador não pode visualizar botão imprimir
    Dado o usuário logue no sistema com usuário "mobilab" e perfil "programador"
    E que o sistema possua controladores cadastrados e configurados
    E que o usuário acesse a página de listagem de controladores
    Quando o usuário clicar em "Configuração" do controlador "Av. Paulista, nº 1000. ref.: AREA 1"
    Então o sistema não deverá mostrar o botão "Imprimir"
