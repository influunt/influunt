# language: pt
Funcionalidade: Carregamento do Controlador

  Contexto:
    Dado que exista o controlador "suely"

  Cenário: Simulacao
    Dado que o controlador "basico" seja escolhido
    Dado que o instante seja 10/10/2016 01:59:59
    Então o plano vigente deve ser o plano 1
    Então a sequencia de estagio deve ser E1, E2, E3

    Dado que o instante seja 10/10/2016 02:00:01
    Então o plano vigente deve ser o plano 1
    Então deve existir uma tentantiva de troca de plano agendada
    Então a troca de plano deve estar agendada para 10/10/2016 02:00:07

    Dado que o instante seja 10/10/2016 02:00:08
    Então o plano vigente deve ser o plano 1
    Então a sequencia de estagio deve ser E1, E3, E2

    Então o historico do grupo Semaforico deve ser:
    | Grupo | Estados                                                                                          |
    | 1     | VERDE, AMARELO, AMARELO, AMARELO, VERMELHO_LIMPEZA, VERMELHO_LIMPEZA, VERMELHO_LIMPEZA, VERMELHO
