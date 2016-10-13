# language: pt
Funcionalidade: Carregamento do Controlador

  Contexto:
    Dado que exista o controlador "basico"

  Cenário: Simulacao
    Dado que o controlador "basico" seja escolhido
    E que o controlador esta em execucao desdes "18/09/2016 00:00:00"
    E que a simulacao vai comecar em "18/09/2016 00:00:00"
    E que a simulacao vai terminar em "18/09/2016 02:01:00"
    Quando a simulacao terminar

    E que houveram as seguintes trocas de planos:
      |de|para|quando             |
      |1 | 2  |18/09/2016 02:00:00|

    E que houveram as seguintes trocas de planos reais:
      |de|para|quando             |
      |1 | 2  |18/09/2016 02:00:24|

    E que houveram as seguintes trocas de estados dos grupos semaforicos:
      |momento            |G1              | G2     | G3                  | G4     | G5                  |
      |18/09/2016 02:00:14|VERDE           |VERMELHO|VERMELHO             |VERMELHO|VERDE                |
      |18/09/2016 02:00:24|AMARELO         |VERMELHO|VERMELHO             |VERMELHO|VERMELHO_INTERMITENTE|
      |18/09/2016 02:00:27|VERMELHO_LIMPEZA|VERMELHO|VERMELHO             |VERMELHO|VERMELHO_LIMPEZA     |
      |18/09/2016 02:00:30|VERMELHO        |VERMELHO|VERDE                |VERDE   |VERDE                |
      |18/09/2016 02:00:41|VERMELHO        |VERMELHO|VERMELHO_INTERMITENTE|VERDE   |VERMELHO_INTERMITENTE|

    E que os estagios estao nos seguintes estados:
      |momento            |Anel1  |
      |18/09/2016 02:00:23|E3     |
      |18/09/2016 02:00:24|E1     |
      |18/09/2016 02:00:25|E1     |
      |18/09/2016 02:00:26|E1     |
      |18/09/2016 02:00:27|E1     |
      |18/09/2016 02:00:28|E1     |
      |18/09/2016 02:00:29|E1     |
      |18/09/2016 02:00:30|E2     |
      |18/09/2016 02:00:31|E2     |
      |18/09/2016 02:00:32|E2     |
      |18/09/2016 02:00:33|E2     |
      |18/09/2016 02:00:34|E2     |
      |18/09/2016 02:00:35|E2     |
      |18/09/2016 02:00:36|E2     |
      |18/09/2016 02:00:37|E2     |
      |18/09/2016 02:00:38|E2     |
      |18/09/2016 02:00:39|E2     |
      |18/09/2016 02:00:40|E2     |
      |18/09/2016 02:00:41|E1     |