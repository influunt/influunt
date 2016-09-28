# language: pt
Funcionalidade: Carregamento do Controlador

  Contexto:
    Dado que exista o controlador "suely"

  Cenário: Simulacao
    Dado que o controlador "suely" seja escolhido
    E que o controlador esta em execucao desdes "18/09/2016 22:00:00"
    E que a simulacao vai comecar em "18/09/2016 22:00:00"
    E que a simulacao vai terminar em "25/09/2016 22:00:00"
    E que o detector veicular 1 seja acionado em "19/09/2016 22:00:00"
    E que o detector de pedestre 1 seja acionado em "19/09/2016 22:00:00"
    E que a falha 1 aconteca em "19/09/2016 22:00:00" no "G1"
    E que a falha 1 cesse em "19/09/2016 22:00:00"  no "G1"
    E que o alarme 1 aconteca em "19/09/2016 22:00:00"
    E que o plano 4 seja imposto em "19/09/2016 22:00:00"
    Quando a simulacao terminar
    Então a simulacao tera duracao de 604800 segundos
    E que houveram as seguintes trocas de planos:
    |de|para|quando             |no anel|
    |1 | 1  |19/09/2016 19:30:00| 1     |

    E que os grupos semaforicos estao nos seguintes estados:
    |momento            |G1   | G2     | G3     | G4     | G5     | G6  | G7  | G8     | G9   | G10 | G11    | G12 | G13 |
    |18/09/2016 22:00:00|VERDE|VERMELHO|VERDE|VERMELHO|VERMELHO|VERMELHO|VERDE|VERDE|VERMELHO |VERDE|VERDE|VERMELHO|VERDE|