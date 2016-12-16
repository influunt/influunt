# Introdução
A estrutura do controlador é definida pelo objeto [CONTROLADOR](#controlador) que inclui seus dados básicos e listas de objetos associados. Para evitar referências cruzadas e problemas de navegação no grafo de objetos, adotou-se uma estratura de _JSON_ desnormatizada. Sendo assim, todos os objetos são escritos diretamente na raiz do controlador e não internamente de forma hierárquica. Em compensação os objetos internos trazem referências para os objetos externos.


A figura abaixo apresenta os objetos que fazem parte da configuração de um controlador. As setas representam referências entre esses objetos.


![Controlador](/img/controlador.png)

## Controlador

É o equipamento que fica em campo (na rua), sendo responsável pelo gerenciamento e controle dos semáforos. As programações semafóricas são inseridas neste equipamento.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto [UUID] (https://en.wikipedia.org/wiki/Universally_unique_identifier) |S| Identificador do Controlador |
| numeroSMEE | Texto||N| Número SMEE do Controlador |
| nomeEndereco | Texto|N| Descrição da localização do controlador |
| dataCriacao | Data|N| Data de Criação do controlador |
| dataAtualizacao | Data|N| Data da última atualização do controlador |
| CLC | Texto|N| Código de Localização do Controlador |
| aneis | vetor de [anel](#anel)|S| Lista dos anéis do controlador |
| estagios | vetor de [estagio](#estagio)|S| Lista dos estágios do controlador |
| gruposSemaforicos | vetor de [grupos semáforicos](#grupo-semaforico)|S| Lista dos grupos semafóricos do controlador |
| detectores | vetor de [detector](#detector)|S| Lista dos detectores do controlador |
| transicoesProibidas | vetor de [transição proibada](#transicao-proibida)|S| Lista das transições proibidas |
| estagiosGruposSemaforicos | vetor de [estágio grupo semáforico](#estagio-grupo-semaforico)|S| Lista de estágios grupos semafóricos|
| transicoes | vetor de [transição](#transicao)|S| Lista de transições|
| verdesConflitantes | vetor de [verde conflitante](#verde-conflitante)|S| Lista de verdes conflitantes|
| tabelaEntreVerdes | vetor de [tabela entre verdes](#tabela-entre-verdes)|S| Lista de tabelas de entre verdes|
| tabelasEntreVerdesTransicoes | vetor de [tabela entre verdes transição](#tabela-entre-verdes-transicao)|S| Lista de tabelas de entre verdes transições|
| versoesPlanos| vetor de [versão plano](#versao-plano)|S| Lista de versões planos|
| planos| vetor de [plano](#planos)|S| Lista de planos|
| gruposSemaforicosPlanos| vetor de [grupo semafórico plano](#grupo-semaforico-plano)|S| Lista de grupos semafóricos planos|
| estagiosPlanos| vetor de [estágio plano](#estagio-plano)|S| Lista de estágios planos|
| versoesTabelasHorarias| vetor de [versão de tabela horária](#versao-tabela-horaria)|S| Lista de versões de tabelas horárias|
| tabelasHorarias| vetor de [tabela horária](#tabela-horaria)|S| Lista de tabelas horárias|
| eventos| vetor de [eventos](#evento)|S| Lista de eventos|
|limiteEstagio| Inteiro | S | utilizado para validação pelo 72c |
|limiteGrupoSemaforico| Inteiro | S | utilizado para validação pelo 72c |
|limiteAnel| Inteiro | S | utilizado para validação pelo 72c |
|limiteDetectorPedestre| Inteiro | S | utilizado para validação pelo 72c |
|limiteDetectorVeicular| Inteiro | S | utilizado para validação pelo 72c |
|limiteTabelasEntreVerdes| Inteiro | S | utilizado para validação pelo 72c |
|limitePlanos| Inteiro | S | utilizado para validação pelo 72c |
|verdeMin| Inteiro | S | utilizado para validação pelo 72c |
|verdeMax| Inteiro | S | utilizado para validação pelo 72c |
|verdeMinimoMin| Inteiro | S | utilizado para validação pelo 72c |
|verdeMinimoMax| Inteiro | S | utilizado para validação pelo 72c |
|verdeMaximoMin| Inteiro | S | utilizado para validação pelo 72c |
|verdeMaximoMax| Inteiro | S | utilizado para validação pelo 72c |
|extensaoVerdeMin| Inteiro | S | utilizado para validação pelo 72c |
|extensaoVerdeMax| Inteiro | S | utilizado para validação pelo 72c |
|verdeIntermediarioMin| Inteiro | S | utilizado para validação pelo 72c |
|verdeIntermediarioMax| Inteiro | S | utilizado para validação pelo 72c |
|defasagemMin| Inteiro | S | utilizado para validação pelo 72c |
|defasagemMax| Inteiro | S | utilizado para validação pelo 72c |
|amareloMin| Inteiro | S | utilizado para validação pelo 72c |
|amareloMax| Inteiro | S | utilizado para validação pelo 72c |
|vermelhoIntermitenteMin| Inteiro | S | utilizado para validação pelo 72c |
|vermelhoIntermitenteMax| Inteiro | S | utilizado para validação pelo 72c |
|vermelhoLimpezaVeicularMin| Inteiro | S | utilizado para validação pelo 72c |
|vermelhoLimpezaVeicularMax| Inteiro | S | utilizado para validação pelo 72c |
|vermelhoLimpezaPedestreMin| Inteiro | S | utilizado para validação pelo 72c |
|vermelhoLimpezaPedestreMax| Inteiro | S | utilizado para validação pelo 72c |
|atrasoGrupoMin| Inteiro | S | utilizado para validação pelo 72c |
|atrasoGrupoMax| Inteiro | S | utilizado para validação pelo 72c |
|verdeSegurancaVeicularMin| Inteiro | S | utilizado para validação pelo 72c |
|verdeSegurancaVeicularMax| Inteiro | S | utilizado para validação pelo 72c |
|verdeSegurancaPedestreMin| Inteiro | S | utilizado para validação pelo 72c |
|verdeSegurancaPedestreMax| Inteiro | S | utilizado para validação pelo 72c |
|maximoPermanenciaEstagioMin| Inteiro | S | utilizado para validação pelo 72c |
|maximoPermanenciaEstagioMax| Inteiro | S | utilizado para validação pelo 72c |
|defaultMaximoPermanenciaEstagioVeicular| Inteiro | S | utilizado para validação pelo 72c |
|cicloMin| Inteiro | S | utilizado para validação pelo 72c |
|cicloMax| Inteiro | S | utilizado para validação pelo 72c |
|ausenciaDeteccaoMin| Inteiro | S | utilizado para validação pelo 72c |
|ausenciaDeteccaoMax| Inteiro | S | utilizado para validação pelo 72c |
|deteccaoPermanenteMin| Inteiro | S | utilizado para validação pelo 72c |
|deteccaoPermanenteMax| Inteiro | S | utilizado para validação pelo 72c |

### Exemplo JSON
```JSON
{
  "id": "bdf5005e-9aa3-40a4-b0de-2e38a8aef48e",
  "numeroSMEE": "123",
  "numeroSMEEConjugado1": "123",
  "numeroSMEEConjugado2": "123",
  "numeroSMEEConjugado3": "123",
  "nomeEndereco": "R. Dr. Cícero de Castro com R. Carlos Chagas",
  "dataCriacao": "12/08/2016 11:27:34",
  "dataAtualizacao": "15/08/2016 09:44:03",
  "CLC": "1.000.0001",
  "limiteEstagio": 16,
    "limiteGrupoSemaforico": 16,
    "limiteAnel": 4,
    "limiteDetectorPedestre": 4,
    "limiteDetectorVeicular": 8,
    "limiteTabelasEntreVerdes": 2,
    "limitePlanos": 16,
    "verdeMin": "1",
    "verdeMax": "255",
    "verdeMinimoMin": "10",
    "verdeMinimoMax": "255",
    "verdeMaximoMin": "10",
    "verdeMaximoMax": "255",
    "extensaoVerdeMin": "1.0",
    "extensaoVerdeMax": "10.0",
    "verdeIntermediarioMin": "10",
    "verdeIntermediarioMax": "255",
    "defasagemMin": "0",
    "defasagemMax": "255",
    "amareloMin": "3",
    "amareloMax": "5",
    "vermelhoIntermitenteMin": "3",
    "vermelhoIntermitenteMax": "32",
    "vermelhoLimpezaVeicularMin": "0",
    "vermelhoLimpezaVeicularMax": "20",
    "vermelhoLimpezaPedestreMin": "0",
    "vermelhoLimpezaPedestreMax": "5",
    "atrasoGrupoMin": "0",
    "atrasoGrupoMax": "20",
    "verdeSegurancaVeicularMin": "10",
    "verdeSegurancaVeicularMax": "30",
    "verdeSegurancaPedestreMin": "4",
    "verdeSegurancaPedestreMax": "10",
    "maximoPermanenciaEstagioMin": "20",
    "maximoPermanenciaEstagioMax": "255",
    "defaultMaximoPermanenciaEstagioVeicular": 127,
    "cicloMin": "30",
    "cicloMax": "255",
    "ausenciaDeteccaoMin": "0",
    "ausenciaDeteccaoMax": "5800",
    "deteccaoPermanenteMin": "0",
    "deteccaoPermanenteMax": "10",
  "aneis": [],
  "estagios": [],
  "gruposSemaforicos": [],
  "detectores": [],
  "transicoesProibidas": [],
  "estagiosGruposSemaforicos": [],
  "verdesConflitantes": [],
  "transicoes": [],
  "tabelasEntreVerdes": [],
  "tabelasEntreVerdesTransicoes": []
}
```

## Anel

Característica que permite a um controlador operar como se fossem vários controladores independentes. Cada anel é responsável pelo controle de certo número de grupos focais e a programação semafórica que ocorre nesses grupos focais é totalmente independente da programação semafórica dos demais grupos focais do controlador.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador do Anel |
| idJson | Texto _UUID_ |S| Identificador do Anel para referências dentro do _JSON_ |
| numeroSMEE | Texto||N| Número SMEE do Anel |
| CLA | Texto|N| Código de Localização do Anel |
|aceitaModoManual | Booleano|S | Determina se esse anel aceita a operação em modo manual|
| ativo | Booleano | S|Identifica se esse anel está ativo|
| posicao | Inteiro | S|Posição do anel no controlador|
| estagios | vetor de referência aos [estagios](#estagio)|S| Lista com as referências de estágios desse anel |
| gruposSemaforicos | vetor de referência aos [grupos Semaforicos](#grupo-semaforico)|S| Lista com as referências de grupos semafóricos desse anel |
| detectores | vetor de referência aos [detectores](#detector)|S| Lista com as referências de detectores desse anel |
| endereco | referência ao [endereco](#endereco)|S| Endereço do anel |
| versaoPlano | referência à [versaoPlano](#versaoPlano)|S| Versão plano ativa para esse anel |
| planos | vetor de referência aos [planos](#plano)|S| Lista de planos vinculadas à versão plano desse anel |

### Exemplo JSON

```JSON
  {
        "id": "fd9b7112-f87a-4c19-ade4-648b83de954b",
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e",
        "numeroSMEE": "-",
        "ativo": true,
        "aceitaModoManual": true,
        "posicao": 1,
        "CLA": "1.000.0003.1",
        "versaoPlano": {
          "idJson": "f03abf36-4851-4568-9fe4-926dcece2276"
        },
        "estagios": [
          {
            "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
          },
          {
            "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
          },
          {
            "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
          },
          {
            "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
          }
        ],
        "gruposSemaforicos": [
          {
            "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
          },
          {
            "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
          },
          {
            "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
          },
          {
            "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
          },
          {
            "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
          }
        ],
        "detectores": [
          {
            "idJson": "9266bda5-68df-4784-8645-2c8123c99206"
          }
        ],
        "planos": [
          {
            "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
          },
          {
            "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
          }
        ],
        "endereco": {
          "idJson": "363048ec-e138-470b-bbdb-ba053ce6c732"
        }
      }
```





## Grupo Semafórico

 Conjunto de grupos focais com indicações luminosas idênticas às que controlam grupos de movimentos que recebem simultaneamente o direito de passagem e que possuem as mesmas fases. Utiliza-se a notação _Gn_ para identificar tanto nos projetos como nas programações semafóricas o grupo semafórico de número _"n"_. Quanto aos tipos, temos: grupo semafórico veicular composto de 3 fases (vermelha, amarela e verde) e grupo semafórico de pedestre composto de 2 fases (vermelha e verde).

### Especificação 

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id   | Texto _UUID_ |S| Identificador do Grupo Semáforico |
| idJson | Texto _UUID_ |S| Identificador do Grupo Semáforico para referências dentro do _JSON_ |
| tipo | Texto|S| PEDESTRE ou VEICULAR |
| descricao | Texto|N| Descrição do Grupo Semafórico |
| faseVermelhaApagadaAmareloIntermitente | Booleano |S| Define se, caso houver uma falha na fase vermelha do grupo semáforico, o anel entra em amarelo intermitente |
| tempoVerdeSeguranca | Inteiro||S| Tempo de verde de segurança do grupo semáforico |
| posicao | Inteiro | S|Posição do grupo semafórico no controlador|
| anel | referência ao [anel](#anel)|S| Referência ao anel ao qual esse grupo semáforico faz parte |
| verdesConflitantesOrigem | vetor de referência de [verdes conflitantes](#verde-conflitante)|S| Lista com as referências de verdes conflitantes que tem esse grupo semáforico como origem|
| verdesConflitantesDestino | vetor de referência de [verdes conflitantes](#verde-conflitante)|S| Lista com as referências de verdes conflitantes que tem esse grupo semáforico como destino|
| estagiosGruposSemaforicos | vetor de referência de [estágio grupo semafórico](#estagio-grupo-semaforico)|S| Lista com as referências de estágios grupos semafóricos associados a esse grupo semáforico|
| transicoes | vetor de referência de [transições](#transicao)|S| Lista com as referências de transições desse grupo semáforico|
| tabelasEntreVerdes | vetor de referência de [tabelas de entre verdes](#tabela-entre-verdes)|S| Lista com as referências de tabelas de entre verdes desse grupo semáforico|
| transicoesComGanhoDePassagem | vetor de referência de [transições](#transicao)|S| Lista com as referências de transições desse grupo semáforico com ganho de passagem|


### Exemplo JSON

```JSON
{
      "id": "fcfeadf1-d6ad-476b-8b60-a214dfc136ee",
      "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171",
      "tipo": "VEICULAR",
      "posicao": 1,
      "faseVermelhaApagadaAmareloIntermitente": true,
      "tempoVerdeSeguranca": 10,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "verdesConflitantesOrigem": [
        {
          "idJson": "30b011f5-252b-403b-9dd6-0f78c855a527"
        },
        {
          "idJson": "267a6b1c-e080-49fe-a2e3-b2523e8f3d04"
        }
      ],
      "verdesConflitantesDestino": [

      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "3f945224-1773-41a1-bd8e-0b5a306a1196"
        },
        {
          "idJson": "6eaff012-323a-453d-98ff-8fa1459086f9"
        }
      ],
      "transicoes": [
        {
          "idJson": "2e7a6b8e-ac62-409a-bba6-e79eaaa61d35"
        },
        {
          "idJson": "8fcdd78a-0458-465b-932c-b2aff44d215d"
        },
        {
          "idJson": "8536fd3e-92dc-4edb-90e8-9e4593647178"
        },
        {
          "idJson": "665d8334-e9ec-4493-8fed-e56ee4d5dd48"
        }
      ],
      "transicoesComGanhoDePassagem": [
        {
          "idJson": "fe70918a-cad5-4cf0-ac53-198c7425d448"
        },
        {
          "idJson": "8a520f64-3e5c-4fc6-82cb-ff813389f6d2"
        },
        {
          "idJson": "17aeafb5-c255-4bb7-9611-0fc04aef9cd9"
        },
        {
          "idJson": "fd113800-4da3-4e84-9974-1286b2551ef6"
        }
      ],
      "tabelasEntreVerdes": [
        {
          "idJson": "ff9e2246-7495-463c-a8fa-f45016ed8ddf"
        }
      ]
    }
```


## Estágio

Intervalo de tempo em que um ou mais grupos semafóricos recebem simultaneamente o direito de passagem. O estágio compreende o tempo de verde e o entreverdes que o segue.


### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador do Estágio |
| idJson | Texto _UUID_ |S| Identificador do Estágio para referências dentro do _JSON_ |
| tempoMaximoPermanencia | Inteiro||S| O tempo máximo que o estágio pode ter direito de passagem |
| tempoMaximoPermanenciaAtivado | Booleano||S| Define se o tempo máximo de permanência será monitorado |
| demandaPrioritaria | Booleano||S| Define se o estágio é de demanda prioritária |
| tempoVerdeDemandaPrioritaria | Inteiro | N|Tempo de verde do estágio de demanda prioritária|
| posicao | Inteiro | S|Posição do estágio no anel|
| anel | referência ao [anel](#anel)|S| Referência ao anel ao qual essse estágio faz parte |
| origemDeTransicoesProibidas | vetor de referência das [transições proibidas](#transicao-proibida)|S| Lista com as referências de transições proibidas que tem esse estágio como origem|
| destinoDeTransicoesProibidas | vetor de referência das [transições proibidas](#transicao-proibida)|S| Lista com as referências de transições proibidas que tem esse estágio como destino|
| alternativaDeTransicoesProibidas | vetor de referência das [transições proibidas](#transicao-proibida)|S| Lista com as referências de transições proibidas que tem esse estágio como alternativa|
| estagiosGruposSemaforicos | vetor de referência de [estágios grupos semáforicos](#estagio-grupo-semafórico)|S| Lista com as referências de estágios grupos semáforicos que estão associados a esse estágio|
| estagiosPlanos | vetor de referência de [estagioPlano](#estagioPlano)|S| Lista com as referências de estágios planos que estão associados a esse estágio|


### Exemplo JSON

```JSON
{
      "id": "efb8af71-bf56-4d78-b21e-c3dfb4d43e61",
      "idJson": "145240e9-7282-4260-9d39-855bcd811ffc",
      "tempoMaximoPermanencia": 127,
      "tempoMaximoPermanenciaAtivado": true,
      "demandaPrioritaria": false,
      "tempoVerdeDemandaPrioritaria": 1,
      "posicao": 2,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "imagem": {
        "idJson": "247c65f6-9866-4cd3-ae87-4c1adaf77174"
      },
      "origemDeTransicoesProibidas": [

      ],
      "destinoDeTransicoesProibidas": [

      ],
      "alternativaDeTransicoesProibidas": [

      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "3f945224-1773-41a1-bd8e-0b5a306a1196"
        },
        {
          "idJson": "f33e8a3e-e4e2-4e18-aa8d-35b5ac39f0fd"
        }
      ],
      "estagiosPlanos": [
        {
          "idJson": "14f22263-4dff-4ab2-93ce-8e5ddd006fc7"
        },
        {
          "idJson": "9289488d-2986-4850-8ae0-234a7abc3a61"
        }
      ]
    }
```

## Detector
Dispositivo de atuação acoplado ao controlador podendo ser:

1. Detector Veicular
    - Sensor destinado a registrar a presença ou passagem de veículos.
    
2. Botoeira 
    - Dispositivo dotado de um botão que, ao ser acionado, envia um sinal ao controlador solicitando a implementação de um estágio dispensável.

### Especificação


| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id   | Texto _UUID_ |S| Identificador do Detector |
| idJson | Texto _UUID_ |S| Identificador do Detector para referências dentro do _JSON_ |
| tipo | Texto||S| PEDESTRE ou VEICULAR |
| monitorado | Booleano |S| Define se o detector será monitorado |
| posicao | Inteiro | S|Posição do detector no controlador|
| tempoAusenciaDeteccao | Inteiro | S|Tempo para disparar alarme de ausência de detecção|
| tempoDeteccaoPermanente | Inteiro | S|Tempo para disparar alarme de detecção permanente|
| anel | referência ao [anel](#anel)|S| Referência ao anel ao qual esse detector faz parte |
| estagio | referência ao [estágio](#estagio)|S| Referência ao estágio ao qual esse detector faz parte |


### Exemplo JSON

```JSON
{
      "id": "a13f89e2-9e77-416a-9f92-3bbfe100a855",
      "idJson": "9266bda5-68df-4784-8645-2c8123c99206",
      "tipo": "VEICULAR",
      "posicao": 1,
      "monitorado": false,
      "tempoAusenciaDeteccao": 0,
      "tempoDeteccaoPermanente": 0,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "estagio": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      }
    }
```


## Transição
Representa a transição entre o estágio que está perdendo o direito de passagem e o estágio que irá ganhar o direito de passagem.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador da Transição |
| idJson | Texto _UUID_ |S| Identificador da Transição para referências dentro do _JSON_ |
| origem | referência a [estágio](#estagio)|S| Referência ao estágio de origem |
| tipo | PERDA_DE_PASSAGEM ou GANHO_DE_PASSAGEM |S| Tipo da transição |
| modoIntermitenteOuApagado | Booleano |S| Define se essa transição será utilizada quando houver transição para os modos de amarelo intermitente ou apagado |
| tempoAtrasoGrupo | Inteiro |N| Tempo de atraso de grupo quando houver |
| atrasoDeGrupo | referência ao [atraso de grupo](#atraso-de-grupo)|S| Referência ao atraso de grupo |
| destino | referência a [estágio](#estagio)|S| Referência ao estágio de destino |
| tabelaEntreVerdesTransicoes | vetor de referência das [tabela de entre verdes transição proibidas](#tabela-entre-verdes-transicao)|S| Lista com as referências de tabelas de entre verdes transições|
| grupoSemaforico | referência a [grupo semáforico](#grupo-semaforico)|S| Referência ao grupo semafórico |

### Exemplo JSON

```JSON
{
      "id": "328da97a-c3ae-411e-bcee-a45177f3dae2",
      "idJson": "fffcf552-5427-4ef9-9c9d-8a334a9bd61e",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "9752f26d-30fa-488b-8098-895329911d4e"
        }
      ],
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "ebf362ee-29e4-47b7-a974-179fd98e11c0"
      },
      "modoIntermitenteOuApagado": true
    }
```

## Transição com ganho de passagem
Representa a transição entre o estágio que está ganhando o direito de passagem e o estágio que irá perder o direito de passagem.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador da Transição |
| idJson | Texto _UUID_ |S| Identificador da Transição para referências dentro do _JSON_ |
| origem | referência a [estágio](#estagio)|S| Referência ao estágio de origem |
| tipo | PERDA_DE_PASSAGEM ou GANHO_DE_PASSAGEM |S| Tipo da transição |
| modoIntermitenteOuApagado | Booleano |S| Define se essa transição será utilizada quando houver transição para os modos de amarelo intermitente ou apagado |
| tempoAtrasoGrupo | Inteiro |N| Tempo de atraso de grupo quando houver |
| atrasoDeGrupo | referência ao [atraso de grupo](#atraso-de-grupo)|S| Referência ao atraso de grupo |
| destino | referência a [estágio](#estagio)|S| Referência ao estágio de destino |
| tabelaEntreVerdesTransicoes | vetor de referência das [tabela de entre verdes transição proibidas](#tabela-entre-verdes-transicao)|S| Lista com as referências de tabelas de entre verdes transições|
| grupoSemaforico | referência a [grupo semáforico](#grupo-semaforico)|S| Referência ao grupo semafórico |

### Exemplo JSON

```JSON
{
      "id": "328da97a-c3ae-411e-bcee-a45177f3dae2",
      "idJson": "fffcf552-5427-4ef9-9c9d-8a334a9bd61e",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "9752f26d-30fa-488b-8098-895329911d4e"
        }
      ],
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "ebf362ee-29e4-47b7-a974-179fd98e11c0"
      },
      "modoIntermitenteOuApagado": true
    }
```

## Transição Proibida
Representa a proibição de transição entre dois estágios.
### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador da Transição Proibida |
| idJson | Texto _UUID_ |S| Identificador da Transição Proibida para referências dentro do _JSON_ |
| origem | referência a [estágio](#estagio)|S| Referência ao estágio de origem |
| destino | referência a [estágio](#estagio)|S| Referência ao estágio de destino |
| alternativo | referência a [estágio](#estagio)|S| Referência ao estágio alternativo |


### Exemplo JSON

```JSON
    {
      "id": "40253953-a75e-4c2b-8574-c1a2f1d10bc2",
      "idJson": "ac8c11e2-4d05-4624-b199-ddac68f507a1",
      "origem": {
        "idJson": "b6a7553a-bb45-4d49-9cf3-9979f6ffb9a0"
      },
      "destino": {
        "idJson": "0aa7c531-f3bf-4c5e-98b9-75fb02a9b44b"
      },
      "alternativo": {
        "idJson": "810b2ab2-7559-4e86-b3ec-b53e137354b2"
      }
    }
```

## Tabela Entreverdes
Agrupamento de Tabela Entreverdes Transição.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id   | Texto _UUID_ |S| Identificador da Tabela de Entreverdes |
| idJson | Texto _UUID_ |S| Identificador da Tabela de Entreverdes para referências dentro do _JSON_ |
| posicao | Inteiro | S|Posição da Tabela Entreverdes no anel|
| descricao | Texto||S| Descrição da Tabela de Entreverdes |
| grupoSemaforico | referência a [grupo semáforico](#grupo-semaforico)|S| Referência ao grupo semafórico |
| tabelaEntreVerdesTransicoes | vetor de referência de [tabelas de entre verdes transições](#tabela-entre-verdes-transicao)|S| Lista com as referências de Tabelas de Entreverdes transições dessa Tabela de Entreverdes|


### Exemplo JSON

```JSON
    {
      "id": "943934ba-a8b5-4014-8795-84240f21b75b",
      "idJson": "0036f201-af00-4a82-bacf-1f8fe97281c8",
      "descricao": "PADRÃO",
      "posicao": 1,
      "grupoSemaforico": {
        "idJson": "41f04b7e-742f-4722-8e73-689d9c0ef6f1"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "9cc359ee-a47e-44f9-8098-750c114f8d32"
        },
        {
          "idJson": "18a6bd08-9d8c-4fc1-b089-9aa8c390bfe1"
        }
      ]
    }
```


## Tabela Entreverdes Transição
Intervalo de tempo compreendido entre o final do verde de um estágio e o início do verde do estágio subsequente inserido com o propósito de evitar acidentes entre os usuários que estão perdendo seu direito de passagem e aqueles que vão passar a ganhá-lo. No caso de grupos focais veiculares, compõe-se do período de amarelo seguido do período de vermelho geral. No caso de grupos focais de pedestres consiste do período de vermelho intermitente de pedestre seguido do período de vermelho geral.



### Especificação


| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador da Tabela de Entreverdes Transição |
| idJson | Texto _UUID_ |S| Identificador da Tabela de Entreverdes Transição para referências dentro do _JSON_ |
| tempoAmarelo | Inteiro||N| Tempo de amarelo do entreverdes |
| tempoVermelhoIntermitente | Inteiro||N| Tempo de vermelho intermitente |
| tempoVermelhoLimpeza | Inteiro||S| Tempo de vermelho de limpeza do entreverdes |
| tempoAtrasoGrupo | Inteiro||S| Tempo de atraso de grupo |
| tabelaEntreVerdes | referência a [tabela de entre verdes](#tabela-entre-verdes)|S| Referência à Tabela de Entreverdes |
| transicao | referência à [transição](#transicao)|S| Referência à transição |

### Exemplo JSON

```JSON
    {
      "id": "de33f221-f7f0-4118-90d4-49d3dd21ba3a",
      "idJson": "9c912de8-9ca5-411f-af6a-768c172da0a3",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "0",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "3a0bb61c-492f-4727-ac7e-785d270f4b2f"
      },
      "transicao": {
        "idJson": "91882aea-8354-4526-955f-69d3c999ae8d"
      }
    }
```


## Estágio Grupo Semáforico
Objeto que representa a associação entre grupos semafóricos x estágios.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador do Estágio Grupo Semafórico |
| idJson | Texto _UUID_ |S| Identificador do Estágio Grupo Semafórico para referências dentro do _JSON_ |
| estagio | referência a [estágio](#estagio)|S| Referência ao estágio |
| grupoSemaforico | referência a [grupo semáforico](#grupo-semaforico)|S| Referência ao grupo semafórico |


### Exemplo JSON

```JSON
    {
      "id": "eee3b305-7e8e-4c1e-bd46-8f83f462b506",
      "idJson": "4530082c-744e-47ee-b5c2-a0e042d7f0d7",
      "estagio": {
        "idJson": "090768c2-4e9f-4247-9c64-d73425c31a29"
      },
      "grupoSemaforico": {
        "idJson": "873c4c1b-b518-4919-a937-3812f9fe9017"
      }
    }
```



## Verde Conflitante
Representa um conflito entre dois grupos semafóricos que não têm permissão de movimento simultâneo.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador do Verde Conflitante |
| idJson | Texto _UUID_ |S| Identificador do Verde Conflitante para referências dentro do _JSON_ |
| origem | referência a um [grupo semáforico](#grupo-semaforico)|S| Referência ao grupo semáforico de origem |
| destino | referência a um [grupo semáforico](#grupo-semaforico)|S| Referência ao grupo semáforico de destino |

### Exemplo JSON

```JSON
    {
      "id": "6bd31d57-933c-4113-aed4-583b692568aa",
      "idJson": "2b9327f7-d070-45bf-9ce9-7cc1ac5c09ad",
      "origem": {
        "idJson": "41f04b7e-742f-4722-8e73-689d9c0ef6f1"
      },
      "destino": {
        "idJson": "6102fc65-b3fb-48ca-bd5f-35365a6bb284"
      }
    }
```



## Plano
Denomina-se plano semafórico, ou simplesmente plano, o conjunto de parâmetros introduzidos no controlador ou na central de controle que determina a sequência e as durações dos tempos exibidos pelo semáforo. O período de vigência de um plano está estabelecido na tabela horária. 

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador do Plano |
| idJson | Texto _UUID_ |S| Identificador do Plano para referências dentro do _JSON_ |
| posicao | Inteiro |S| Posição do plano no anel |
| descricao | Texto | N| Descrição do plano |
| tempoCiclo | inteiro | S| Tempo de ciclo do plano em segundos |
| defasagem | inteiro | S| Tempo de defasagem no modo coordenado em segundos |
| posicaoTabelaEntreVerde| inteiro | S | Número da tabela entreverde que será utilizada nesse plano 
| modoOperacao | TEMPO_FIXO_ISOLADO, TEMPO_FIXO_COORDENADO, ATUADO, APAGADO, INTERMITENTE, MANUAL | S | Modo de operação do plano|
| anel | referência ao [anel](#anel)|S| Referência ao anel ao qual essse plano faz parte |
| versaoPlano | referência a [versaoPlano](#versaoPlano)|S| Versão desse plano |
| estagiosPlanos | vetor de referência de [estágio plano](#estagio-grupo-semaforico)|S| Lista com as referências de estágios planos associado a esse plano|
| estagiosGruposSemaforicos | vetor de referência de [estágio grupo semafórico](#estagio-grupo-semaforico)|S| Lista com as referências de estágios grupos semafóricos associados a esse plano|


### Exemplo JSON

```JSON
{
      "id": "6604cb8b-f6fa-4d07-95db-4446b5133e21",
      "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec",
      "posicao": 2,
      "descricao": "PLANO 2",
      "tempoCiclo": 60,
      "defasagem": 0,
      "posicaoTabelaEntreVerde": 1,
      "modoOperacao": "TEMPO_FIXO_ISOLADO",
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "versaoPlano": {
        "idJson": "f03abf36-4851-4568-9fe4-926dcece2276"
      },
      "estagiosPlanos": [
        {
          "idJson": "adfad1fc-cd40-45ef-9d61-3fbd21868358"
        },
        {
          "idJson": "a0a9b5dc-0be0-4029-b49f-67d30e95527a"
        },
        {
          "idJson": "9289488d-2986-4850-8ae0-234a7abc3a61"
        }
      ],
      "gruposSemaforicosPlanos": [
        {
          "idJson": "4f08509e-1c83-4997-b31d-cbfa5044f3f8"
        },
        {
          "idJson": "7168bfe5-77d1-4acd-8fa7-9fa71d218fcf"
        },
        {
          "idJson": "9eefb1c0-e1c7-46d8-911f-b57d6c73d25f"
        },
        {
          "idJson": "06604fba-5d00-40f2-b8fd-be97ea0734b3"
        },
        {
          "idJson": "9df48edc-74ec-433b-b5fe-157054b53cd9"
        }
      ]
    }
```

## Estágio Plano
Associação entre um estágio do anel e a sequência de estágios do plano.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador do Estágio Plano |
| idJson | Texto _UUID_ |S| Identificador do Estágio Plano para referências dentro do _JSON_ |
| posicao | Inteiro| S | Posição do estágio na sequência de estágios do plano |
| tempoVerde | Inteiro |S | Tempo de verde em segundos |
| tempoVerdeMinimo | Inteiro |S| Tempo de verde mínimo em caso de modo atuado. Tempo em segundos | 
| tempoVerdeMaximo | Inteiro |S| Tempo de verde máximo em caso de modo atuado. Tempo em segundos | 
| tempoVerdeIntermediario | Inteiro |S| Tempo de verde intermediario em caso de modo atuado. Tempo em segundos | 
| tempoExtensaoVerde | Decimal |S| Tempo de extensão de verde em caso modo atuado. Tempo em segundos | 
| dispensavel | Booleano |S| Indica se o estágio é dispensável|
| estagio | referência ao [estagio](#estagio)|S| Referência ao estagio |
| plano | referência ao [plano](#plano)|S| Referência ao plano |
 
### Exemplo JSON

```JSON
{
      "id": "488e19af-0eb3-4c38-be7e-0a6f1b21799c",
      "idJson": "525d8368-d643-4cb2-b7fe-486a2bdf3c33",
      "posicao": 1,
      "tempoVerde": 20,
      "tempoVerdeMinimo": 0,
      "tempoVerdeMaximo": 0,
      "tempoVerdeIntermediario": 0,
      "tempoExtensaoVerde": 0.0,
      "dispensavel": false,
      "plano": {
        "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
      },
      "estagio": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      }
    }
```

## Grupo Semafórico Plano
Associação entre um grupo semafórico do anel com o plano.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador do Grupo Semafórico Plano |
| idJson | Texto _UUID_ |S| Identificador do Grupo Semafórico Plano para referências dentro do _JSON_ |
| ativado | Booleano| S| Indica se o grupo semafórico está ativo no plano
| grupoSemaforico | referência ao [grupo semafórico](#grupo-semaforico)|S| Referência ao grupo semafórico |
| plano | referência ao [plano](#plano)|S| Referência ao plano |


### Exemplo JSON

```JSON
{
      "id": "94065e0b-c61d-4f7a-bf81-a75a6c12e0db",
      "idJson": "9eefb1c0-e1c7-46d8-911f-b57d6c73d25f",
      "plano": {
        "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
      },
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "ativado": true
    }
```


## Versão Plano
Versão de um conjunto de planos de um anel

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador da Versão Plano |
| idJson | Texto _UUID_ |S| Identificador da Versão Plano para referências dentro do _JSON_ |
| anel | referência ao [anel](#anel)|S| Referência ao anel ao qual essa versão de planos faz parte |
| planos | vetor de referência aos [planos](#plano)|S| Lista de planos vinculadas à versão plano desse anel |

### Exemplo JSON

```JSON
{
      "id": "51f77d2c-5e64-4ced-a2bd-046c883913d8",
      "idJson": "f03abf36-4851-4568-9fe4-926dcece2276",
      "statusVersao": "CONFIGURADO",
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "planos": [
        {
          "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
        },
        {
          "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
        }
      ]
    }
```

## Versão Tabela Horária
Versão de um tabela horária do controlador

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador da Versão Tabela Horária|
| idJson | Texto _UUID_ |S| Identificador da Versão Tabela Horária para referências dentro do _JSON_ |
| tabelaHoraria | referência a [tabelaHoraria](#tabela-horaria)|S| Referência à tabela horária |

### Exemplo JSON

```JSON
{
      "id": "e8f0ce22-60b7-4cb9-bd75-5845108044ee",
      "idJson": "1c888cb9-c4d9-4f51-b642-c9cb3f49b20d",
      "statusVersao": "CONFIGURADO",
      "tabelaHoraria": {
        "idJson": "696ac25e-d49f-464b-a552-fe930de72404"
      },
    }
```

## Tabela Horária
É parte da programação de um controlador semafórico que determina os horários, dias da semana e datas em que se deve efetuar a troca de planos. Os horários, dias da semana e datas são os eventos de ativação de planos

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador da Tabela Horária |
| idJson | Texto _UUID_ |S| Identificador da Tabela Horária para referências dentro do _JSON_ |
| versaoTabelaHoraria | referência a [versão tabela horária](#versao-tabela-horaria)|S| Referência à versão tabela horária|
| eventos | vetor de [eventos](#evento)|S| Lista de eventos da tabela horária|

### Exemplo JSON

```JSON
{
      "id": "0f2eaf6f-9acc-4d19-a861-161ecfcb925a",
      "idJson": "696ac25e-d49f-464b-a552-fe930de72404",
      "versaoTabelaHoraria": {
        "idJson": "1c888cb9-c4d9-4f51-b642-c9cb3f49b20d"
      },
      "eventos": [
        {
          "idJson": "03c024db-e277-49be-88a5-a5db0592ee47"
        },
        {
          "idJson": "b9070f2a-c59f-433d-b0bd-ca0646fc175e"
        }
      ]
    }
```

## Evento

São os horários, dias da semana e datas programados na Tabela de Mudança de Planos para a troca de planos.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador do Evento |
| idJson | Texto _UUID_ |S| Identificador do Evento para referências dentro do _JSON_ |
| posicao | Inteiro | S | Posição do evento na tabela horária |
| tipo | ESPECIAL_NAO_RECORRENTE, ESPECIAL_RECORRENTE, NORMAL | S | Tipo de evento
| diaDaSemana| TODOS_OS_DIAS, SEGUNDA_A_SABADO, SEGUNDA_A_SEXTA, SABADO_A_DOMINGO, DOMINGO, SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO| S | Indica quando o evento será ativado|
| data | Data | S | Indica uma data para ativação quando o evento for do tipo ESPECIAL_RECORRENTE e ESPECIAL_NAO_RECORRENTE|
|horario| Hora | S | Indica o horário de ativação do evento|
|posicaoPlano| Inteiro | S| Indica qual plano será ativado|
| tabelaHoraria | referência a [tabelaHoraria](#tabela-horaria)|S| Referência à tabela horária |



### Exemplo JSON

```JSON
{
      "id": "6fded171-e25a-4f68-9b3c-37445f21c962",
      "idJson": "03c024db-e277-49be-88a5-a5db0592ee47",
      "posicao": "1",
      "tipo": "NORMAL",
      "diaDaSemana": "Todos os dias da semana",
      "data": "02-12-2016",
      "horario": "00:00:00.000",
      "posicaoPlano": "1",
      "tabelaHoraria": {
        "idJson": "696ac25e-d49f-464b-a552-fe930de72404"
      }
    }
```

## Atraso de Grupo
Definições de atraso de grupo para os grupos semafóricos em determinada transição.

### Especificação

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| id | Texto _UUID_ |S| Identificador do Atraso de Grupo |
| idJson | Texto _UUID_ |S| Identificador do Atraso de Grupo para referências dentro do _JSON_ |
| atrasoDeGrupo | Inteiro| S| Tempo de atraso de grupo em segundos|


### Exemplo JSON

```JSON
{
      "id": "3d8027af-c3cf-4103-b339-44f48726d855",
      "idJson": "e5b82afd-f49f-4e52-948c-ad18366276b8",
      "atrasoDeGrupo": 0
}
```

## Exemplo Completo



```JSON
{
  "id": "95a85464-1cc4-4fa4-8008-bc14e99aed4c",
  "versoesTabelasHorarias": [
    {
      "id": "e8f0ce22-60b7-4cb9-bd75-5845108044ee",
      "idJson": "1c888cb9-c4d9-4f51-b642-c9cb3f49b20d",
      "statusVersao": "CONFIGURADO",
      "tabelaHoraria": {
        "idJson": "696ac25e-d49f-464b-a552-fe930de72404"
      },
      "dataCriacao": "02/12/2016 15:52:33",
      "dataAtualizacao": "05/12/2016 13:47:57"
    }
  ],
  "numeroSMEE": "123",
  "sequencia": 3,
  "limiteEstagio": 16,
  "limiteGrupoSemaforico": 16,
  "limiteAnel": 4,
  "limiteDetectorPedestre": 4,
  "limiteDetectorVeicular": 8,
  "limiteTabelasEntreVerdes": 2,
  "limitePlanos": 16,
  "nomeEndereco": "Av. Cruzeiro do Sul, nº 123",
  "dataCriacao": "02/12/2016 15:41:41",
  "dataAtualizacao": "05/12/2016 13:47:37",
  "CLC": "1.000.0003",
  "bloqueado": false,
  "planosBloqueado": false,
  "verdeMin": "1",
  "verdeMax": "255",
  "verdeMinimoMin": "10",
  "verdeMinimoMax": "255",
  "verdeMaximoMin": "10",
  "verdeMaximoMax": "255",
  "extensaoVerdeMin": "1.0",
  "extensaoVerdeMax": "10.0",
  "verdeIntermediarioMin": "10",
  "verdeIntermediarioMax": "255",
  "defasagemMin": "0",
  "defasagemMax": "255",
  "amareloMin": "3",
  "amareloMax": "5",
  "vermelhoIntermitenteMin": "3",
  "vermelhoIntermitenteMax": "32",
  "vermelhoLimpezaVeicularMin": "0",
  "vermelhoLimpezaVeicularMax": "20",
  "vermelhoLimpezaPedestreMin": "0",
  "vermelhoLimpezaPedestreMax": "5",
  "atrasoGrupoMin": "0",
  "atrasoGrupoMax": "20",
  "verdeSegurancaVeicularMin": "10",
  "verdeSegurancaVeicularMax": "30",
  "verdeSegurancaPedestreMin": "4",
  "verdeSegurancaPedestreMax": "10",
  "maximoPermanenciaEstagioMin": "20",
  "maximoPermanenciaEstagioMax": "255",
  "defaultMaximoPermanenciaEstagioVeicular": 127,
  "cicloMin": "30",
  "cicloMax": "255",
  "ausenciaDeteccaoMin": "0",
  "ausenciaDeteccaoMax": "5800",
  "deteccaoPermanenteMin": "0",
  "deteccaoPermanenteMax": "10",
  "statusControlador": "CONFIGURADO",
  "statusControladorReal": "CONFIGURADO",
  "area": {
    "idJson": "011f16c3-b59e-11e6-970d-0401fa9c1b01"
  },
  "endereco": {
    "idJson": "2ad86fb7-ea77-4dbe-b904-2386d561f0ba"
  },
  "modelo": {
    "id": "011f2a13-b59e-11e6-970d-0401fa9c1b01",
    "idJson": "011f2a2c-b59e-11e6-970d-0401fa9c1b01",
    "descricao": "Modelo Básico",
    "fabricante": {
      "id": "011f1c41-b59e-11e6-970d-0401fa9c1b01",
      "nome": "Raro Labs"
    }
  },
  "aneis": [
    {
      "id": "a262b498-accd-4816-ab14-fd77b12ba8bb",
      "idJson": "aa3fbc6f-552c-4fca-ad69-d341dcd95f56",
      "ativo": false,
      "aceitaModoManual": true,
      "posicao": 4,
      "CLA": "1.000.0003.4",
      "estagios": [

      ],
      "gruposSemaforicos": [

      ],
      "detectores": [

      ],
      "planos": [

      ]
    },
    {
      "id": "a91db360-2132-47c8-ade9-71aed4a46c1a",
      "idJson": "e6024b3e-2525-48a2-8fe5-9176ee59189e",
      "ativo": false,
      "aceitaModoManual": true,
      "posicao": 3,
      "CLA": "1.000.0003.3",
      "estagios": [

      ],
      "gruposSemaforicos": [

      ],
      "detectores": [

      ],
      "planos": [

      ]
    },
    {
      "id": "e517deed-9ce9-4ddd-8b33-76b75158af9f",
      "idJson": "17c10350-3f5c-4f61-8106-8d94c9b8b880",
      "ativo": false,
      "aceitaModoManual": true,
      "posicao": 2,
      "CLA": "1.000.0003.2",
      "estagios": [

      ],
      "gruposSemaforicos": [

      ],
      "detectores": [

      ],
      "planos": [

      ]
    },
    {
      "id": "fd9b7112-f87a-4c19-ade4-648b83de954b",
      "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e",
      "numeroSMEE": "-",
      "ativo": true,
      "aceitaModoManual": true,
      "posicao": 1,
      "CLA": "1.000.0003.1",
      "versaoPlano": {
        "idJson": "f03abf36-4851-4568-9fe4-926dcece2276"
      },
      "estagios": [
        {
          "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
        },
        {
          "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
        },
        {
          "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
        },
        {
          "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
        }
      ],
      "gruposSemaforicos": [
        {
          "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
        },
        {
          "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
        },
        {
          "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
        },
        {
          "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
        },
        {
          "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
        }
      ],
      "detectores": [
        {
          "idJson": "9266bda5-68df-4784-8645-2c8123c99206"
        }
      ],
      "planos": [
        {
          "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
        },
        {
          "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
        }
      ],
      "endereco": {
        "idJson": "363048ec-e138-470b-bbdb-ba053ce6c732"
      }
    }
  ],
  "estagios": [
    {
      "id": "9e9726b0-3e37-4875-bdd2-363aed4ccdf9",
      "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f",
      "tempoMaximoPermanencia": 127,
      "tempoMaximoPermanenciaAtivado": true,
      "demandaPrioritaria": true,
      "tempoVerdeDemandaPrioritaria": 30,
      "posicao": 4,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "imagem": {
        "idJson": "333dc024-900c-45a1-b748-9381ccee2e93"
      },
      "detector": {
        "idJson": "9266bda5-68df-4784-8645-2c8123c99206"
      },
      "origemDeTransicoesProibidas": [

      ],
      "destinoDeTransicoesProibidas": [

      ],
      "alternativaDeTransicoesProibidas": [

      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "1308104a-2716-4a5b-a8c8-fde7b38d1576"
        }
      ],
      "estagiosPlanos": [

      ]
    },
    {
      "id": "efb8af71-bf56-4d78-b21e-c3dfb4d43e61",
      "idJson": "145240e9-7282-4260-9d39-855bcd811ffc",
      "tempoMaximoPermanencia": 127,
      "tempoMaximoPermanenciaAtivado": true,
      "demandaPrioritaria": false,
      "tempoVerdeDemandaPrioritaria": 1,
      "posicao": 2,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "imagem": {
        "idJson": "247c65f6-9866-4cd3-ae87-4c1adaf77174"
      },
      "origemDeTransicoesProibidas": [

      ],
      "destinoDeTransicoesProibidas": [

      ],
      "alternativaDeTransicoesProibidas": [

      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "3f945224-1773-41a1-bd8e-0b5a306a1196"
        },
        {
          "idJson": "f33e8a3e-e4e2-4e18-aa8d-35b5ac39f0fd"
        }
      ],
      "estagiosPlanos": [
        {
          "idJson": "14f22263-4dff-4ab2-93ce-8e5ddd006fc7"
        },
        {
          "idJson": "9289488d-2986-4850-8ae0-234a7abc3a61"
        }
      ]
    },
    {
      "id": "b5604c1d-1aa1-4994-b3c1-6f2adfb124f5",
      "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61",
      "tempoMaximoPermanencia": 127,
      "tempoMaximoPermanenciaAtivado": true,
      "demandaPrioritaria": false,
      "tempoVerdeDemandaPrioritaria": 1,
      "posicao": 1,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "imagem": {
        "idJson": "b74c8502-3802-4958-ac77-9fc9a9037a4e"
      },
      "origemDeTransicoesProibidas": [

      ],
      "destinoDeTransicoesProibidas": [

      ],
      "alternativaDeTransicoesProibidas": [

      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "6eaff012-323a-453d-98ff-8fa1459086f9"
        },
        {
          "idJson": "c74422ae-d4d6-4fc6-9a14-45011b92a8ff"
        }
      ],
      "estagiosPlanos": [
        {
          "idJson": "525d8368-d643-4cb2-b7fe-486a2bdf3c33"
        },
        {
          "idJson": "a0a9b5dc-0be0-4029-b49f-67d30e95527a"
        }
      ]
    },
    {
      "id": "b2ac996a-ffa4-4f40-9074-19a1330fd7b5",
      "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371",
      "tempoMaximoPermanencia": 127,
      "tempoMaximoPermanenciaAtivado": true,
      "demandaPrioritaria": false,
      "tempoVerdeDemandaPrioritaria": 1,
      "posicao": 3,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "imagem": {
        "idJson": "c8236221-0b33-4836-b519-5ee7a39aa444"
      },
      "origemDeTransicoesProibidas": [

      ],
      "destinoDeTransicoesProibidas": [

      ],
      "alternativaDeTransicoesProibidas": [

      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "de6d7e7c-5eea-46f2-a8ea-2cfa4e322c80"
        },
        {
          "idJson": "9410153c-71cb-4671-ad01-b7d04d8f1003"
        }
      ],
      "estagiosPlanos": [
        {
          "idJson": "adfad1fc-cd40-45ef-9d61-3fbd21868358"
        },
        {
          "idJson": "a66880b7-f5a1-41d9-8516-0df903822f9b"
        }
      ]
    }
  ],
  "gruposSemaforicos": [
    {
      "id": "58c8cc1c-5af9-42f7-bd86-955dd7f02980",
      "idJson": "a84438d9-8235-44f7-8717-71c2551da441",
      "tipo": "PEDESTRE",
      "posicao": 3,
      "faseVermelhaApagadaAmareloIntermitente": false,
      "tempoVerdeSeguranca": 4,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "verdesConflitantesOrigem": [

      ],
      "verdesConflitantesDestino": [
        {
          "idJson": "081d1852-f164-4160-b957-6650180a419d"
        }
      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "f33e8a3e-e4e2-4e18-aa8d-35b5ac39f0fd"
        }
      ],
      "transicoes": [
        {
          "idJson": "ad3563fa-6030-423e-a5e9-649d3e06b4a8"
        },
        {
          "idJson": "c6798fd4-c483-4f24-b2ee-60e173b3f5f8"
        },
        {
          "idJson": "b176dce2-a26c-47d3-8338-cda039d52274"
        }
      ],
      "transicoesComGanhoDePassagem": [
        {
          "idJson": "1f4292a4-f66b-41eb-9d82-580707362d93"
        },
        {
          "idJson": "c25ca12e-5dc5-40bb-b057-9f3d458eaa86"
        },
        {
          "idJson": "c5299ae7-a0d5-4b19-b23b-68a83817459a"
        }
      ],
      "tabelasEntreVerdes": [
        {
          "idJson": "e1d85886-a591-4f57-b0a7-8d760d5d3757"
        }
      ]
    },
    {
      "id": "fcfeadf1-d6ad-476b-8b60-a214dfc136ee",
      "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171",
      "tipo": "VEICULAR",
      "posicao": 1,
      "faseVermelhaApagadaAmareloIntermitente": true,
      "tempoVerdeSeguranca": 10,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "verdesConflitantesOrigem": [
        {
          "idJson": "30b011f5-252b-403b-9dd6-0f78c855a527"
        },
        {
          "idJson": "267a6b1c-e080-49fe-a2e3-b2523e8f3d04"
        }
      ],
      "verdesConflitantesDestino": [

      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "3f945224-1773-41a1-bd8e-0b5a306a1196"
        },
        {
          "idJson": "6eaff012-323a-453d-98ff-8fa1459086f9"
        }
      ],
      "transicoes": [
        {
          "idJson": "2e7a6b8e-ac62-409a-bba6-e79eaaa61d35"
        },
        {
          "idJson": "8fcdd78a-0458-465b-932c-b2aff44d215d"
        },
        {
          "idJson": "8536fd3e-92dc-4edb-90e8-9e4593647178"
        },
        {
          "idJson": "665d8334-e9ec-4493-8fed-e56ee4d5dd48"
        }
      ],
      "transicoesComGanhoDePassagem": [
        {
          "idJson": "fe70918a-cad5-4cf0-ac53-198c7425d448"
        },
        {
          "idJson": "8a520f64-3e5c-4fc6-82cb-ff813389f6d2"
        },
        {
          "idJson": "17aeafb5-c255-4bb7-9611-0fc04aef9cd9"
        },
        {
          "idJson": "fd113800-4da3-4e84-9974-1286b2551ef6"
        }
      ],
      "tabelasEntreVerdes": [
        {
          "idJson": "ff9e2246-7495-463c-a8fa-f45016ed8ddf"
        }
      ]
    },
    {
      "id": "5aa15474-8207-42f2-b352-b38d2dc5a409",
      "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83",
      "tipo": "PEDESTRE",
      "posicao": 4,
      "faseVermelhaApagadaAmareloIntermitente": false,
      "tempoVerdeSeguranca": 4,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "verdesConflitantesOrigem": [

      ],
      "verdesConflitantesDestino": [
        {
          "idJson": "267a6b1c-e080-49fe-a2e3-b2523e8f3d04"
        }
      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "de6d7e7c-5eea-46f2-a8ea-2cfa4e322c80"
        }
      ],
      "transicoes": [
        {
          "idJson": "13d0ffd3-2c3a-4e39-a4e0-c1f6b93c6d07"
        },
        {
          "idJson": "51f17cdb-5031-4984-a7a3-42f88f9ca74f"
        },
        {
          "idJson": "9e0bd29e-1252-4e76-94e9-2583cd6c83fa"
        }
      ],
      "transicoesComGanhoDePassagem": [
        {
          "idJson": "d9f0d320-f2d4-4ae9-90f5-888bb21f2218"
        },
        {
          "idJson": "3342f580-b30c-41ea-9d09-3dc6bc0a1b3c"
        },
        {
          "idJson": "3631df74-1782-43d9-8d20-0900121ca56b"
        }
      ],
      "tabelasEntreVerdes": [
        {
          "idJson": "c54eaa1a-f4b9-460c-a15a-b3111d7f9caf"
        }
      ]
    },
    {
      "id": "d9862640-c0c7-4be0-aa82-7fc36b8ff3c3",
      "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b",
      "tipo": "VEICULAR",
      "posicao": 5,
      "faseVermelhaApagadaAmareloIntermitente": true,
      "tempoVerdeSeguranca": 10,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "verdesConflitantesOrigem": [

      ],
      "verdesConflitantesDestino": [
        {
          "idJson": "30b011f5-252b-403b-9dd6-0f78c855a527"
        },
        {
          "idJson": "53630e63-9563-4e6f-8d08-28af503eef58"
        }
      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "1308104a-2716-4a5b-a8c8-fde7b38d1576"
        }
      ],
      "transicoes": [
        {
          "idJson": "58653dcd-9edb-4a32-92fa-dff2074f518c"
        },
        {
          "idJson": "fffcf552-5427-4ef9-9c9d-8a334a9bd61e"
        },
        {
          "idJson": "fd0a1df2-1a35-4afe-8e65-455b701819a7"
        }
      ],
      "transicoesComGanhoDePassagem": [
        {
          "idJson": "adb968a3-0f4a-4a3f-ad2d-23ffae4ad37f"
        },
        {
          "idJson": "e4514bc8-eab0-4aa2-8d0d-178124bfbd88"
        },
        {
          "idJson": "dbb34115-9aa5-4186-99e8-a8fadb59409c"
        }
      ],
      "tabelasEntreVerdes": [
        {
          "idJson": "86e6c9e0-5c52-4bcb-8d9d-d98042e116a7"
        }
      ]
    },
    {
      "id": "bdb84414-ff87-46d7-9e19-d7719ac14512",
      "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a",
      "tipo": "VEICULAR",
      "posicao": 2,
      "faseVermelhaApagadaAmareloIntermitente": true,
      "tempoVerdeSeguranca": 10,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "verdesConflitantesOrigem": [
        {
          "idJson": "53630e63-9563-4e6f-8d08-28af503eef58"
        },
        {
          "idJson": "081d1852-f164-4160-b957-6650180a419d"
        }
      ],
      "verdesConflitantesDestino": [

      ],
      "estagiosGruposSemaforicos": [
        {
          "idJson": "9410153c-71cb-4671-ad01-b7d04d8f1003"
        },
        {
          "idJson": "c74422ae-d4d6-4fc6-9a14-45011b92a8ff"
        }
      ],
      "transicoes": [
        {
          "idJson": "7f2f40d2-9ec5-4d69-8460-d0fbd7940851"
        },
        {
          "idJson": "ff770854-4c93-4042-9c02-cd12b77d0214"
        },
        {
          "idJson": "656882f1-6941-461d-97b4-a4d930237e91"
        },
        {
          "idJson": "b9f70f8c-c368-4e27-bedd-24cb6fe3a024"
        }
      ],
      "transicoesComGanhoDePassagem": [
        {
          "idJson": "49e18cd9-e8f4-45d2-9d0d-979105c81175"
        },
        {
          "idJson": "b480a036-0209-41d3-8902-fe4f716e09ac"
        },
        {
          "idJson": "6ee57fe1-37c0-4b14-98e8-47368dd2d2a2"
        },
        {
          "idJson": "4bda3e05-ebb4-4424-8f15-fff4e5145236"
        }
      ],
      "tabelasEntreVerdes": [
        {
          "idJson": "c946dc57-c1a4-4b5c-8652-f013acb741a0"
        }
      ]
    }
  ],
  "detectores": [
    {
      "id": "a13f89e2-9e77-416a-9f92-3bbfe100a855",
      "idJson": "9266bda5-68df-4784-8645-2c8123c99206",
      "tipo": "VEICULAR",
      "posicao": 1,
      "monitorado": false,
      "tempoAusenciaDeteccao": 0,
      "tempoDeteccaoPermanente": 0,
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "estagio": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      }
    }
  ],
  "transicoesProibidas": [

  ],
  "estagiosGruposSemaforicos": [
    {
      "id": "4859fc6f-3e65-4917-9100-864b469fd22b",
      "idJson": "f33e8a3e-e4e2-4e18-aa8d-35b5ac39f0fd",
      "estagio": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      }
    },
    {
      "id": "b94a13c2-1045-4e0a-84f3-da628e0ffd23",
      "idJson": "6eaff012-323a-453d-98ff-8fa1459086f9",
      "estagio": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      }
    },
    {
      "id": "bb2a59af-a1cb-4069-80a6-a57db44c72bf",
      "idJson": "c74422ae-d4d6-4fc6-9a14-45011b92a8ff",
      "estagio": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      }
    },
    {
      "id": "14fde7a4-8275-46b7-b43b-49cb6df813dd",
      "idJson": "de6d7e7c-5eea-46f2-a8ea-2cfa4e322c80",
      "estagio": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      }
    },
    {
      "id": "8d90b844-9c07-49a2-8f44-7f0405a6d7a7",
      "idJson": "9410153c-71cb-4671-ad01-b7d04d8f1003",
      "estagio": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      }
    },
    {
      "id": "cf27d18d-dc1c-4ef2-a4b1-7a82b75b9a1f",
      "idJson": "1308104a-2716-4a5b-a8c8-fde7b38d1576",
      "estagio": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      }
    },
    {
      "id": "17dfac1b-0d0c-496f-adf8-e80993f9c165",
      "idJson": "3f945224-1773-41a1-bd8e-0b5a306a1196",
      "estagio": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      }
    }
  ],
  "verdesConflitantes": [
    {
      "id": "280c37ba-e1d4-4a98-b033-0e731a2f413b",
      "idJson": "30b011f5-252b-403b-9dd6-0f78c855a527",
      "origem": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "destino": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      }
    },
    {
      "id": "81914e35-cce3-4d67-93db-d6ecd9804b72",
      "idJson": "53630e63-9563-4e6f-8d08-28af503eef58",
      "origem": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "destino": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      }
    },
    {
      "id": "63d17845-ec31-43f1-9e74-28b6ad4cba66",
      "idJson": "267a6b1c-e080-49fe-a2e3-b2523e8f3d04",
      "origem": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "destino": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      }
    },
    {
      "id": "90735b07-b95f-4e05-8837-75e905636726",
      "idJson": "081d1852-f164-4160-b957-6650180a419d",
      "origem": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "destino": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      }
    }
  ],
  "transicoes": [
    {
      "id": "328da97a-c3ae-411e-bcee-a45177f3dae2",
      "idJson": "fffcf552-5427-4ef9-9c9d-8a334a9bd61e",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "9752f26d-30fa-488b-8098-895329911d4e"
        }
      ],
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "ebf362ee-29e4-47b7-a974-179fd98e11c0"
      },
      "modoIntermitenteOuApagado": true
    },
    {
      "id": "af8d081f-e50b-45cf-ac8e-1a36c13b4019",
      "idJson": "656882f1-6941-461d-97b4-a4d930237e91",
      "origem": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "destino": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "3ada4d77-e096-47b3-b7c2-22562a2545df"
        }
      ],
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "59cd0a99-e02c-4b7e-80bf-af8f8ed5df89"
      },
      "modoIntermitenteOuApagado": true
    },
    {
      "id": "1f14a018-bcd6-4645-880c-026874404e46",
      "idJson": "58653dcd-9edb-4a32-92fa-dff2074f518c",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "8714db53-7170-4e5e-ae33-bf79616cca01"
        }
      ],
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "2e566b5e-3c17-48dc-9ee8-55adf7106275"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "55fbba60-63b7-44c0-8fbf-bad2fc3493ff",
      "idJson": "51f17cdb-5031-4984-a7a3-42f88f9ca74f",
      "origem": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "destino": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "3adcdab1-0d49-48fe-b188-7bd7b609bbf4"
        }
      ],
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "be73d5c1-6cc0-4e62-b36a-41f6de9b05c7"
      },
      "modoIntermitenteOuApagado": true
    },
    {
      "id": "b2b44dca-be26-4ac7-835b-4221ee408153",
      "idJson": "b9f70f8c-c368-4e27-bedd-24cb6fe3a024",
      "origem": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "destino": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "c28e66aa-f462-4dae-bed1-32b50861733c"
        }
      ],
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "a0188c63-3f49-4b92-aa50-1f3268909969"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "68e94f83-ee0e-4754-ab0a-53b7635f756b",
      "idJson": "7f2f40d2-9ec5-4d69-8460-d0fbd7940851",
      "origem": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "destino": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "eee33794-681b-41b9-ba2c-6f6bac385a25"
        }
      ],
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "d9bb8c6b-299c-4346-a508-2fe4ae5285ab"
      },
      "modoIntermitenteOuApagado": true
    },
    {
      "id": "4c5ce469-ee59-4ad2-8b20-bd3e9cf4475e",
      "idJson": "c6798fd4-c483-4f24-b2ee-60e173b3f5f8",
      "origem": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "destino": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "264b716e-1ce4-47a4-9248-2403fd23cc91"
        }
      ],
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "b1668f4a-1eae-485d-b845-7e66eb9d5535"
      },
      "modoIntermitenteOuApagado": true
    },
    {
      "id": "17c92f27-ad70-4a60-ad8c-f1786f05ff82",
      "idJson": "ad3563fa-6030-423e-a5e9-649d3e06b4a8",
      "origem": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "5c47cf1a-4766-485f-9b07-6789566c4361"
        }
      ],
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "6f24308c-a6d4-4b94-a114-937fc1f4ca5e"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "11f7dceb-cd4c-4918-82f7-69bea7e5a8a2",
      "idJson": "13d0ffd3-2c3a-4e39-a4e0-c1f6b93c6d07",
      "origem": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "destino": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "6ef0603a-0361-43cd-9d18-799eb8c6d04c"
        }
      ],
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "e5b82afd-f49f-4e52-948c-ad18366276b8"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "a7225d9e-5bc9-44b9-beb8-0b3188b91e76",
      "idJson": "ff770854-4c93-4042-9c02-cd12b77d0214",
      "origem": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "destino": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "bcde1192-3a5d-48d9-90ba-a7d5b74d1847"
        }
      ],
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "3b08c18a-daa0-49e0-a1a1-bd8f7433a1b6"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "05edbb2d-f693-4765-86ac-214bcd581a0c",
      "idJson": "2e7a6b8e-ac62-409a-bba6-e79eaaa61d35",
      "origem": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "destino": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "e00b6764-ece2-4b82-89aa-c44e7e451ab8"
        }
      ],
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "9ef0a1f7-e40f-4b0c-818a-3bdc5f90e759"
      },
      "modoIntermitenteOuApagado": true
    },
    {
      "id": "94adc687-5291-45d7-9f39-d9035b64f14e",
      "idJson": "9e0bd29e-1252-4e76-94e9-2583cd6c83fa",
      "origem": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "destino": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "74aff95e-f66e-4b21-af94-b96074b6479f"
        }
      ],
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "2469dbd6-39ff-4ffa-b72c-c3ee2526b749"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "a2d3a73d-afcb-4b73-a9bd-2be1bcd196f5",
      "idJson": "8536fd3e-92dc-4edb-90e8-9e4593647178",
      "origem": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "2473db48-7b61-4fc8-b2a3-822d02854fdd"
        }
      ],
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "1482636f-a03c-4470-b2e5-b8dd9e465179"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "901fa449-f841-4010-a367-63bc2650070f",
      "idJson": "b176dce2-a26c-47d3-8338-cda039d52274",
      "origem": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "destino": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "7b0a7aba-813a-4a3e-a5af-80b5ffb0d8ba"
        }
      ],
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "830193d5-14d3-4c4d-b587-b853f141f95a"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "1c8d8892-3d46-436a-9901-30756ea4428c",
      "idJson": "8fcdd78a-0458-465b-932c-b2aff44d215d",
      "origem": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "617a3c20-7284-43ed-ab8f-9c43a21f54f2"
        }
      ],
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "e6c3a4f2-85d9-47f7-af80-91be361358b2"
      },
      "modoIntermitenteOuApagado": true
    },
    {
      "id": "e5f6a7cc-b809-4e58-b0a8-7e6357968333",
      "idJson": "665d8334-e9ec-4493-8fed-e56ee4d5dd48",
      "origem": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "destino": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "9d5bbd25-180c-4cd3-b75c-a2771e07d8c2"
        }
      ],
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "73982283-f44d-4215-809b-923733d0b409"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "9fe00fee-b6fb-4be2-b815-c9eb985013e4",
      "idJson": "fd0a1df2-1a35-4afe-8e65-455b701819a7",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "2419ea42-ebbd-4104-94d6-7e7fcef6b01b"
        }
      ],
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "tipo": "PERDA_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "6251cbdd-de6f-435f-9e59-b32d7daaef9c"
      },
      "modoIntermitenteOuApagado": false
    }
  ],
  "transicoesComGanhoDePassagem": [
    {
      "id": "d47aa7ed-e4b1-460c-93c6-e2801361b996",
      "idJson": "fd113800-4da3-4e84-9974-1286b2551ef6",
      "origem": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "destino": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "22f4cdd7-5513-438e-becf-103aa2168a16"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "5fdc3600-7868-49ff-a276-10d5ad35230f",
      "idJson": "3342f580-b30c-41ea-9d09-3dc6bc0a1b3c",
      "origem": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "4cabae1a-50db-4fb5-9d8b-26d14cc180da"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "93039553-2b6e-46ae-b0ef-144befbea4d3",
      "idJson": "8a520f64-3e5c-4fc6-82cb-ff813389f6d2",
      "origem": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "destino": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "d3933152-8491-4405-9502-37e85fe6ecb5"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "d8c64e73-fc04-4ea0-bbc6-2bbd60e0e819",
      "idJson": "c5299ae7-a0d5-4b19-b23b-68a83817459a",
      "origem": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "destino": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "acc2bc83-6758-4910-9008-d6ead0cf2bb8"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "b80c2398-46c4-4c92-8d2c-cd6b9532fe4f",
      "idJson": "17aeafb5-c255-4bb7-9611-0fc04aef9cd9",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "08f48a39-1ec3-4125-892e-f0986ccf7351"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "0542f64c-784a-466f-94cf-aef604ae4245",
      "idJson": "49e18cd9-e8f4-45d2-9d0d-979105c81175",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "dccd32e2-a0d8-437c-868c-013468619c23"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "88fa5f75-7ca7-409d-827b-5b19fdc5ac81",
      "idJson": "1f4292a4-f66b-41eb-9d82-580707362d93",
      "origem": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "destino": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "20e6c46b-692f-4dc6-aa63-a78b1f6adb8a"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "cde2a1c7-4211-4c06-9dde-db256fd8e4cc",
      "idJson": "3631df74-1782-43d9-8d20-0900121ca56b",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "32146a51-ac22-4263-ae74-b6d7e36ba37a"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "b168060b-2238-408b-b220-b20ad02b1dce",
      "idJson": "c25ca12e-5dc5-40bb-b057-9f3d458eaa86",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "281d7e4b-b963-433b-84f2-f899bfa3c5f0"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "45a41b94-5bfe-4550-9c10-fddd44f0396e",
      "idJson": "fe70918a-cad5-4cf0-ac53-198c7425d448",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "34f0be96-9c2d-42c4-bd00-4ba6064364e8"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "c2b4c85f-be43-454c-a3f2-6d81180cc902",
      "idJson": "dbb34115-9aa5-4186-99e8-a8fadb59409c",
      "origem": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "destino": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "55c44d33-ac3f-4048-a6b0-7d5011785106"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "c7899e20-2aef-4204-b66b-410c67bfdc7a",
      "idJson": "6ee57fe1-37c0-4b14-98e8-47368dd2d2a2",
      "origem": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "82d1c599-8f58-40d4-b372-637be01cc7e2"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "0018d40c-a15e-4f90-8d3e-a383660f126e",
      "idJson": "adb968a3-0f4a-4a3f-ad2d-23ffae4ad37f",
      "origem": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "destino": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "3deda506-ef44-407e-a333-2dbbd1942922"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "de703411-a1a9-4442-b049-a324d1dff09e",
      "idJson": "4bda3e05-ebb4-4424-8f15-fff4e5145236",
      "origem": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "179f53aa-a192-4801-bbe0-5867d376326e"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "3f2f8708-234b-408f-ba3f-b3476b6499ae",
      "idJson": "d9f0d320-f2d4-4ae9-90f5-888bb21f2218",
      "origem": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "destino": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "024455ff-8750-4b11-80b9-4d8c9a043d08"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "955f0f1f-5618-49ce-8b65-fd64283b2ce0",
      "idJson": "e4514bc8-eab0-4aa2-8d0d-178124bfbd88",
      "origem": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      },
      "destino": {
        "idJson": "cf988bc2-eb8f-4907-9f84-34606dc60f9f"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "ed8dd6ef-d3a8-4f98-befb-7e454201267f"
      },
      "modoIntermitenteOuApagado": false
    },
    {
      "id": "bae3b7c2-26f9-444e-8487-3cced88927f1",
      "idJson": "b480a036-0209-41d3-8902-fe4f716e09ac",
      "origem": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      },
      "destino": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      },
      "tabelaEntreVerdesTransicoes": [

      ],
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "tipo": "GANHO_DE_PASSAGEM",
      "tempoAtrasoGrupo": "0",
      "atrasoDeGrupo": {
        "idJson": "df71a274-69f6-4217-ba99-8dcbb853f190"
      },
      "modoIntermitenteOuApagado": false
    }
  ],
  "tabelasEntreVerdes": [
    {
      "id": "ee2d218a-81aa-449c-a5f5-8d887ed2bd4d",
      "idJson": "ff9e2246-7495-463c-a8fa-f45016ed8ddf",
      "descricao": "PADRÃO",
      "posicao": 1,
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "617a3c20-7284-43ed-ab8f-9c43a21f54f2"
        },
        {
          "idJson": "9d5bbd25-180c-4cd3-b75c-a2771e07d8c2"
        },
        {
          "idJson": "2473db48-7b61-4fc8-b2a3-822d02854fdd"
        },
        {
          "idJson": "e00b6764-ece2-4b82-89aa-c44e7e451ab8"
        }
      ]
    },
    {
      "id": "a7d5c297-9577-41b7-a398-dd4a7c59d827",
      "idJson": "c54eaa1a-f4b9-460c-a15a-b3111d7f9caf",
      "descricao": "PADRÃO",
      "posicao": 1,
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "74aff95e-f66e-4b21-af94-b96074b6479f"
        },
        {
          "idJson": "6ef0603a-0361-43cd-9d18-799eb8c6d04c"
        },
        {
          "idJson": "3adcdab1-0d49-48fe-b188-7bd7b609bbf4"
        }
      ]
    },
    {
      "id": "7e88f59a-e5c9-47fe-abea-7b3bff09323a",
      "idJson": "86e6c9e0-5c52-4bcb-8d9d-d98042e116a7",
      "descricao": "PADRÃO",
      "posicao": 1,
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "8714db53-7170-4e5e-ae33-bf79616cca01"
        },
        {
          "idJson": "2419ea42-ebbd-4104-94d6-7e7fcef6b01b"
        },
        {
          "idJson": "9752f26d-30fa-488b-8098-895329911d4e"
        }
      ]
    },
    {
      "id": "2dca241d-b847-409b-b7ce-5f456e7894aa",
      "idJson": "c946dc57-c1a4-4b5c-8652-f013acb741a0",
      "descricao": "PADRÃO",
      "posicao": 1,
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "3ada4d77-e096-47b3-b7c2-22562a2545df"
        },
        {
          "idJson": "c28e66aa-f462-4dae-bed1-32b50861733c"
        },
        {
          "idJson": "eee33794-681b-41b9-ba2c-6f6bac385a25"
        },
        {
          "idJson": "bcde1192-3a5d-48d9-90ba-a7d5b74d1847"
        }
      ]
    },
    {
      "id": "b6ee456d-f9c4-44fb-a91c-d76b05f9fca8",
      "idJson": "e1d85886-a591-4f57-b0a7-8d760d5d3757",
      "descricao": "PADRÃO",
      "posicao": 1,
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "tabelaEntreVerdesTransicoes": [
        {
          "idJson": "7b0a7aba-813a-4a3e-a5af-80b5ffb0d8ba"
        },
        {
          "idJson": "264b716e-1ce4-47a4-9248-2403fd23cc91"
        },
        {
          "idJson": "5c47cf1a-4766-485f-9b07-6789566c4361"
        }
      ]
    }
  ],
  "tabelasEntreVerdesTransicoes": [
    {
      "id": "9fceba8b-863c-4329-8f3e-cfcd5d1c4169",
      "idJson": "9752f26d-30fa-488b-8098-895329911d4e",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "86e6c9e0-5c52-4bcb-8d9d-d98042e116a7"
      },
      "transicao": {
        "idJson": "fffcf552-5427-4ef9-9c9d-8a334a9bd61e"
      }
    },
    {
      "id": "4cfbc8e5-873a-4190-9b14-7b7d3a0536cf",
      "idJson": "c28e66aa-f462-4dae-bed1-32b50861733c",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "c946dc57-c1a4-4b5c-8652-f013acb741a0"
      },
      "transicao": {
        "idJson": "b9f70f8c-c368-4e27-bedd-24cb6fe3a024"
      }
    },
    {
      "id": "6f302706-b918-49de-b5c0-b48ee77064cb",
      "idJson": "6ef0603a-0361-43cd-9d18-799eb8c6d04c",
      "tempoVermelhoIntermitente": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "c54eaa1a-f4b9-460c-a15a-b3111d7f9caf"
      },
      "transicao": {
        "idJson": "13d0ffd3-2c3a-4e39-a4e0-c1f6b93c6d07"
      }
    },
    {
      "id": "157eb558-3435-4d1a-8c49-b91c2a6af051",
      "idJson": "74aff95e-f66e-4b21-af94-b96074b6479f",
      "tempoVermelhoIntermitente": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "c54eaa1a-f4b9-460c-a15a-b3111d7f9caf"
      },
      "transicao": {
        "idJson": "9e0bd29e-1252-4e76-94e9-2583cd6c83fa"
      }
    },
    {
      "id": "0afb5554-40aa-497d-83a5-1adb1d3a2120",
      "idJson": "617a3c20-7284-43ed-ab8f-9c43a21f54f2",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "ff9e2246-7495-463c-a8fa-f45016ed8ddf"
      },
      "transicao": {
        "idJson": "8fcdd78a-0458-465b-932c-b2aff44d215d"
      }
    },
    {
      "id": "78d42dee-9f77-4529-b590-75e156488738",
      "idJson": "264b716e-1ce4-47a4-9248-2403fd23cc91",
      "tempoVermelhoIntermitente": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "e1d85886-a591-4f57-b0a7-8d760d5d3757"
      },
      "transicao": {
        "idJson": "c6798fd4-c483-4f24-b2ee-60e173b3f5f8"
      }
    },
    {
      "id": "af22114f-92b0-4d5f-a4c8-93ae54c94823",
      "idJson": "2473db48-7b61-4fc8-b2a3-822d02854fdd",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "ff9e2246-7495-463c-a8fa-f45016ed8ddf"
      },
      "transicao": {
        "idJson": "8536fd3e-92dc-4edb-90e8-9e4593647178"
      }
    },
    {
      "id": "9c883be5-32f1-47e4-8d53-83a1bbcc0e08",
      "idJson": "eee33794-681b-41b9-ba2c-6f6bac385a25",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "c946dc57-c1a4-4b5c-8652-f013acb741a0"
      },
      "transicao": {
        "idJson": "7f2f40d2-9ec5-4d69-8460-d0fbd7940851"
      }
    },
    {
      "id": "05de1d0b-de43-4644-a74a-a7ed4970c014",
      "idJson": "3ada4d77-e096-47b3-b7c2-22562a2545df",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "c946dc57-c1a4-4b5c-8652-f013acb741a0"
      },
      "transicao": {
        "idJson": "656882f1-6941-461d-97b4-a4d930237e91"
      }
    },
    {
      "id": "a5f15f6b-a9dd-4c14-871d-7a93c68921b8",
      "idJson": "bcde1192-3a5d-48d9-90ba-a7d5b74d1847",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "c946dc57-c1a4-4b5c-8652-f013acb741a0"
      },
      "transicao": {
        "idJson": "ff770854-4c93-4042-9c02-cd12b77d0214"
      }
    },
    {
      "id": "692d5787-7dbd-482f-b145-5f6712caae0c",
      "idJson": "2419ea42-ebbd-4104-94d6-7e7fcef6b01b",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "86e6c9e0-5c52-4bcb-8d9d-d98042e116a7"
      },
      "transicao": {
        "idJson": "fd0a1df2-1a35-4afe-8e65-455b701819a7"
      }
    },
    {
      "id": "816e2f72-c47f-4645-ae7b-ca1602f63935",
      "idJson": "5c47cf1a-4766-485f-9b07-6789566c4361",
      "tempoVermelhoIntermitente": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "e1d85886-a591-4f57-b0a7-8d760d5d3757"
      },
      "transicao": {
        "idJson": "ad3563fa-6030-423e-a5e9-649d3e06b4a8"
      }
    },
    {
      "id": "1da8941f-e77c-4eca-b09c-ae374178cda9",
      "idJson": "8714db53-7170-4e5e-ae33-bf79616cca01",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "86e6c9e0-5c52-4bcb-8d9d-d98042e116a7"
      },
      "transicao": {
        "idJson": "58653dcd-9edb-4a32-92fa-dff2074f518c"
      }
    },
    {
      "id": "1526acc7-663c-453c-8e97-fd12398ab67f",
      "idJson": "9d5bbd25-180c-4cd3-b75c-a2771e07d8c2",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "ff9e2246-7495-463c-a8fa-f45016ed8ddf"
      },
      "transicao": {
        "idJson": "665d8334-e9ec-4493-8fed-e56ee4d5dd48"
      }
    },
    {
      "id": "e4c4b1dc-8206-4d72-aaa1-cfec322c3e33",
      "idJson": "e00b6764-ece2-4b82-89aa-c44e7e451ab8",
      "tempoAmarelo": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "ff9e2246-7495-463c-a8fa-f45016ed8ddf"
      },
      "transicao": {
        "idJson": "2e7a6b8e-ac62-409a-bba6-e79eaaa61d35"
      }
    },
    {
      "id": "91d455a4-be19-42cc-a049-1f465ab52fed",
      "idJson": "3adcdab1-0d49-48fe-b188-7bd7b609bbf4",
      "tempoVermelhoIntermitente": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "c54eaa1a-f4b9-460c-a15a-b3111d7f9caf"
      },
      "transicao": {
        "idJson": "51f17cdb-5031-4984-a7a3-42f88f9ca74f"
      }
    },
    {
      "id": "43397243-42cc-41d5-bd5a-e03a320451df",
      "idJson": "7b0a7aba-813a-4a3e-a5af-80b5ffb0d8ba",
      "tempoVermelhoIntermitente": "3",
      "tempoVermelhoLimpeza": "3",
      "tempoAtrasoGrupo": "0",
      "tabelaEntreVerdes": {
        "idJson": "e1d85886-a591-4f57-b0a7-8d760d5d3757"
      },
      "transicao": {
        "idJson": "b176dce2-a26c-47d3-8338-cda039d52274"
      }
    }
  ],
  "planos": [
    {
      "id": "6604cb8b-f6fa-4d07-95db-4446b5133e21",
      "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec",
      "posicao": 2,
      "descricao": "PLANO 2",
      "tempoCiclo": 60,
      "defasagem": 0,
      "posicaoTabelaEntreVerde": 1,
      "modoOperacao": "TEMPO_FIXO_ISOLADO",
      "dataCriacao": "02/12/2016 15:51:59",
      "dataAtualizacao": "05/12/2016 13:47:38",
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "versaoPlano": {
        "idJson": "f03abf36-4851-4568-9fe4-926dcece2276"
      },
      "estagiosPlanos": [
        {
          "idJson": "adfad1fc-cd40-45ef-9d61-3fbd21868358"
        },
        {
          "idJson": "a0a9b5dc-0be0-4029-b49f-67d30e95527a"
        },
        {
          "idJson": "9289488d-2986-4850-8ae0-234a7abc3a61"
        }
      ],
      "gruposSemaforicosPlanos": [
        {
          "idJson": "4f08509e-1c83-4997-b31d-cbfa5044f3f8"
        },
        {
          "idJson": "7168bfe5-77d1-4acd-8fa7-9fa71d218fcf"
        },
        {
          "idJson": "9eefb1c0-e1c7-46d8-911f-b57d6c73d25f"
        },
        {
          "idJson": "06604fba-5d00-40f2-b8fd-be97ea0734b3"
        },
        {
          "idJson": "9df48edc-74ec-433b-b5fe-157054b53cd9"
        }
      ]
    },
    {
      "id": "8d0e91d8-37bf-4ce3-a55e-4ff276915471",
      "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17",
      "posicao": 1,
      "descricao": "PLANO 1",
      "tempoCiclo": 66,
      "defasagem": 0,
      "posicaoTabelaEntreVerde": 1,
      "modoOperacao": "TEMPO_FIXO_ISOLADO",
      "dataCriacao": "02/12/2016 15:49:50",
      "dataAtualizacao": "05/12/2016 13:47:38",
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "versaoPlano": {
        "idJson": "f03abf36-4851-4568-9fe4-926dcece2276"
      },
      "estagiosPlanos": [
        {
          "idJson": "525d8368-d643-4cb2-b7fe-486a2bdf3c33"
        },
        {
          "idJson": "14f22263-4dff-4ab2-93ce-8e5ddd006fc7"
        },
        {
          "idJson": "a66880b7-f5a1-41d9-8516-0df903822f9b"
        }
      ],
      "gruposSemaforicosPlanos": [
        {
          "idJson": "061061bd-7fb8-4164-b10c-da13a2afc911"
        },
        {
          "idJson": "b8bb25c8-4a38-4e97-b050-2c62253864e1"
        },
        {
          "idJson": "23805ff4-84bd-452b-89dc-a5a7a10be756"
        },
        {
          "idJson": "0c4d7e5b-24de-4b4f-afd5-ae8043f01f5d"
        },
        {
          "idJson": "d986bd81-ec4f-49f7-ae6d-daa29074e3ed"
        }
      ]
    }
  ],
  "gruposSemaforicosPlanos": [
    {
      "id": "94065e0b-c61d-4f7a-bf81-a75a6c12e0db",
      "idJson": "9eefb1c0-e1c7-46d8-911f-b57d6c73d25f",
      "plano": {
        "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
      },
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "ativado": true
    },
    {
      "id": "bc4e3758-1c52-46e1-8f64-3fb52152015c",
      "idJson": "06604fba-5d00-40f2-b8fd-be97ea0734b3",
      "plano": {
        "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
      },
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "ativado": true
    },
    {
      "id": "70ae77b7-f0c9-4511-87b0-8698eec98ad3",
      "idJson": "7168bfe5-77d1-4acd-8fa7-9fa71d218fcf",
      "plano": {
        "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
      },
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "ativado": true
    },
    {
      "id": "fcf295db-f398-484e-8262-a01357a39ecf",
      "idJson": "9df48edc-74ec-433b-b5fe-157054b53cd9",
      "plano": {
        "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
      },
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "ativado": true
    },
    {
      "id": "3c266d07-1f3d-4b1b-8778-0b529fb8b2d6",
      "idJson": "061061bd-7fb8-4164-b10c-da13a2afc911",
      "plano": {
        "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
      },
      "grupoSemaforico": {
        "idJson": "bf3104fe-53fe-4bb8-b019-052ac6fd6171"
      },
      "ativado": true
    },
    {
      "id": "5bea30d2-6fa5-4825-af0e-5e0629747836",
      "idJson": "b8bb25c8-4a38-4e97-b050-2c62253864e1",
      "plano": {
        "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
      },
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      },
      "ativado": true
    },
    {
      "id": "3a8c52b6-8852-44bf-a335-41d0392c8173",
      "idJson": "4f08509e-1c83-4997-b31d-cbfa5044f3f8",
      "plano": {
        "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
      },
      "grupoSemaforico": {
        "idJson": "9af866e1-5c4d-415f-9165-ee174984eb83"
      },
      "ativado": true
    },
    {
      "id": "9403fe0e-26d5-43bb-9a11-8e0dc8851fa2",
      "idJson": "0c4d7e5b-24de-4b4f-afd5-ae8043f01f5d",
      "plano": {
        "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
      },
      "grupoSemaforico": {
        "idJson": "a84438d9-8235-44f7-8717-71c2551da441"
      },
      "ativado": true
    },
    {
      "id": "7be6a493-bee4-4195-99d6-4597d0385181",
      "idJson": "23805ff4-84bd-452b-89dc-a5a7a10be756",
      "plano": {
        "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
      },
      "grupoSemaforico": {
        "idJson": "1a54dbf4-1fd1-49aa-b30e-bdb23593865a"
      },
      "ativado": true
    },
    {
      "id": "f3f4db5a-9b99-4c42-bb97-1fe060cd44a2",
      "idJson": "d986bd81-ec4f-49f7-ae6d-daa29074e3ed",
      "plano": {
        "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
      },
      "grupoSemaforico": {
        "idJson": "1ab0cca0-5afb-4ef8-9a6e-ff227c89913b"
      },
      "ativado": true
    }
  ],
  "estagiosPlanos": [
    {
      "id": "488e19af-0eb3-4c38-be7e-0a6f1b21799c",
      "idJson": "525d8368-d643-4cb2-b7fe-486a2bdf3c33",
      "posicao": 1,
      "tempoVerde": 20,
      "tempoVerdeMinimo": 0,
      "tempoVerdeMaximo": 0,
      "tempoVerdeIntermediario": 0,
      "tempoExtensaoVerde": 0.0,
      "dispensavel": false,
      "plano": {
        "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
      },
      "estagio": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      }
    },
    {
      "id": "498f02e2-c235-459a-b3e8-f9ccf03919ae",
      "idJson": "14f22263-4dff-4ab2-93ce-8e5ddd006fc7",
      "posicao": 2,
      "tempoVerde": 20,
      "tempoVerdeMinimo": 0,
      "tempoVerdeMaximo": 0,
      "tempoVerdeIntermediario": 0,
      "tempoExtensaoVerde": 0.0,
      "dispensavel": false,
      "plano": {
        "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
      },
      "estagio": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      }
    },
    {
      "id": "67929f35-903d-42f1-bf3f-503cbfd5c392",
      "idJson": "adfad1fc-cd40-45ef-9d61-3fbd21868358",
      "posicao": 3,
      "tempoVerde": 22,
      "tempoVerdeMinimo": 0,
      "tempoVerdeMaximo": 0,
      "tempoVerdeIntermediario": 0,
      "tempoExtensaoVerde": 0.0,
      "dispensavel": false,
      "plano": {
        "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
      },
      "estagio": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      }
    },
    {
      "id": "a47c25ad-1af2-4780-a34e-357cec51202b",
      "idJson": "a66880b7-f5a1-41d9-8516-0df903822f9b",
      "posicao": 3,
      "tempoVerde": 8,
      "tempoVerdeMinimo": 0,
      "tempoVerdeMaximo": 0,
      "tempoVerdeIntermediario": 0,
      "tempoExtensaoVerde": 0.0,
      "dispensavel": false,
      "plano": {
        "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
      },
      "estagio": {
        "idJson": "4c7b3da1-2a97-48cc-a57f-15b94b09a371"
      }
    },
    {
      "id": "f6d9e348-0ee4-4c3a-8fd8-add9fa040390",
      "idJson": "9289488d-2986-4850-8ae0-234a7abc3a61",
      "posicao": 1,
      "tempoVerde": 10,
      "tempoVerdeMinimo": 0,
      "tempoVerdeMaximo": 0,
      "tempoVerdeIntermediario": 0,
      "tempoExtensaoVerde": 0.0,
      "dispensavel": false,
      "plano": {
        "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
      },
      "estagio": {
        "idJson": "145240e9-7282-4260-9d39-855bcd811ffc"
      }
    },
    {
      "id": "a7564113-767b-441c-ad00-dd6ad2dce3e5",
      "idJson": "a0a9b5dc-0be0-4029-b49f-67d30e95527a",
      "posicao": 2,
      "tempoVerde": 10,
      "tempoVerdeMinimo": 0,
      "tempoVerdeMaximo": 0,
      "tempoVerdeIntermediario": 0,
      "tempoExtensaoVerde": 0.0,
      "dispensavel": false,
      "plano": {
        "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
      },
      "estagio": {
        "idJson": "ffc6db20-8ef8-4435-9558-4eb631a17e61"
      }
    }
  ],
  "cidades": [
    {
      "id": "011efdb6-b59e-11e6-970d-0401fa9c1b01",
      "idJson": "011f0dcf-b59e-11e6-970d-0401fa9c1b01",
      "nome": "São Paulo",
      "areas": [
        {
          "idJson": "011f16c3-b59e-11e6-970d-0401fa9c1b01"
        }
      ]
    }
  ],
  "areas": [
    {
      "id": "011effc7-b59e-11e6-970d-0401fa9c1b01",
      "idJson": "011f16c3-b59e-11e6-970d-0401fa9c1b01",
      "descricao": 1,
      "cidade": {
        "idJson": "011f0dcf-b59e-11e6-970d-0401fa9c1b01"
      },
      "limites": [

      ],
      "subareas": [

      ]
    }
  ],
  "limites": [

  ],
  "todosEnderecos": [
    {
      "id": "9d616f04-007b-4afe-9fb6-dfe90042c53b",
      "idJson": "363048ec-e138-470b-bbdb-ba053ce6c732",
      "localizacao": "Av. Cruzeiro do Sul",
      "latitude": -23.5249206,
      "longitude": -46.62531130000002,
      "localizacao2": "",
      "alturaNumerica": 123
    },
    {
      "id": "1230d853-7296-44bf-9342-a610762e9b92",
      "idJson": "2ad86fb7-ea77-4dbe-b904-2386d561f0ba",
      "localizacao": "Av. Cruzeiro do Sul",
      "latitude": -23.5249206,
      "longitude": -46.62531130000002,
      "localizacao2": "",
      "alturaNumerica": 123
    }
  ],
  "imagens": [
    {
      "id": "e169f69c-66df-4aba-9752-f66d8c3142a3",
      "idJson": "c8236221-0b33-4836-b519-5ee7a39aa444",
      "fileName": "Screen Shot 2016-06-19 at 13.13.05.png",
      "contentType": "image/png"
    },
    {
      "id": "fbc68e94-4f6c-41ae-a91d-df9ad446380c",
      "idJson": "333dc024-900c-45a1-b748-9381ccee2e93",
      "fileName": "Screen Shot 2016-06-19 at 13.12.21.png",
      "contentType": "image/png"
    },
    {
      "id": "6c1ad2c7-500f-4bb7-b4a1-6866cc87ea88",
      "idJson": "b74c8502-3802-4958-ac77-9fc9a9037a4e",
      "fileName": "Screen Shot 2016-06-19 at 13.12.55.png",
      "contentType": "image/png"
    },
    {
      "id": "1d09fc3d-bf50-4c08-80ec-1385be74456b",
      "idJson": "247c65f6-9866-4cd3-ae87-4c1adaf77174",
      "fileName": "Screen Shot 2016-06-19 at 13.12.51.png",
      "contentType": "image/png"
    }
  ],
  "atrasosDeGrupo": [
    {
      "id": "3d8027af-c3cf-4103-b339-44f48726d855",
      "idJson": "e5b82afd-f49f-4e52-948c-ad18366276b8",
      "atrasoDeGrupo": 0
    },
    {
      "id": "16fb7060-7211-4816-83d9-e651e294839e",
      "idJson": "55c44d33-ac3f-4048-a6b0-7d5011785106",
      "atrasoDeGrupo": 0
    },
    {
      "id": "d9d1e348-6390-43ed-af0b-33dce1166366",
      "idJson": "20e6c46b-692f-4dc6-aa63-a78b1f6adb8a",
      "atrasoDeGrupo": 0
    },
    {
      "id": "b1563c60-56bb-49d2-925b-2c1571f5c2aa",
      "idJson": "be73d5c1-6cc0-4e62-b36a-41f6de9b05c7",
      "atrasoDeGrupo": 0
    },
    {
      "id": "94366fbb-2506-4e45-af43-2f0a37f19fed",
      "idJson": "08f48a39-1ec3-4125-892e-f0986ccf7351",
      "atrasoDeGrupo": 0
    },
    {
      "id": "25c0ce5c-a594-4e7b-8a77-abdca817b355",
      "idJson": "d9bb8c6b-299c-4346-a508-2fe4ae5285ab",
      "atrasoDeGrupo": 0
    },
    {
      "id": "3b90854e-461c-4bfc-92e0-57b045f516a1",
      "idJson": "9ef0a1f7-e40f-4b0c-818a-3bdc5f90e759",
      "atrasoDeGrupo": 0
    },
    {
      "id": "4803c98f-0470-41d1-85e5-c241945112b4",
      "idJson": "32146a51-ac22-4263-ae74-b6d7e36ba37a",
      "atrasoDeGrupo": 0
    },
    {
      "id": "f2e1bda8-d445-466f-9779-ccd401753990",
      "idJson": "82d1c599-8f58-40d4-b372-637be01cc7e2",
      "atrasoDeGrupo": 0
    },
    {
      "id": "f03f1b26-49a6-4650-87cc-d6360ec4edfc",
      "idJson": "22f4cdd7-5513-438e-becf-103aa2168a16",
      "atrasoDeGrupo": 0
    },
    {
      "id": "4779b510-953f-47c8-89b8-56038a0d51f9",
      "idJson": "3deda506-ef44-407e-a333-2dbbd1942922",
      "atrasoDeGrupo": 0
    },
    {
      "id": "9927290d-9371-4f09-88f4-ad1fa3e42cb8",
      "idJson": "2e566b5e-3c17-48dc-9ee8-55adf7106275",
      "atrasoDeGrupo": 0
    },
    {
      "id": "88c09f96-7467-41bd-9b05-43067941cfb9",
      "idJson": "73982283-f44d-4215-809b-923733d0b409",
      "atrasoDeGrupo": 0
    },
    {
      "id": "7071c6a6-8909-45f6-b88d-e4b8f82d2a6a",
      "idJson": "acc2bc83-6758-4910-9008-d6ead0cf2bb8",
      "atrasoDeGrupo": 0
    },
    {
      "id": "e363a9d3-bf70-4de2-8b52-3c87ddb6f432",
      "idJson": "6f24308c-a6d4-4b94-a114-937fc1f4ca5e",
      "atrasoDeGrupo": 0
    },
    {
      "id": "deae61d3-452b-438d-a9a7-511fdd0a4b98",
      "idJson": "df71a274-69f6-4217-ba99-8dcbb853f190",
      "atrasoDeGrupo": 0
    },
    {
      "id": "d36a34bc-339f-42c2-af49-bec941b73440",
      "idJson": "2469dbd6-39ff-4ffa-b72c-c3ee2526b749",
      "atrasoDeGrupo": 0
    },
    {
      "id": "200bfcaa-83a0-47f4-92a2-3ff35e785ec1",
      "idJson": "1482636f-a03c-4470-b2e5-b8dd9e465179",
      "atrasoDeGrupo": 0
    },
    {
      "id": "de778c42-dee1-41e2-8e3e-2edc0ac10323",
      "idJson": "024455ff-8750-4b11-80b9-4d8c9a043d08",
      "atrasoDeGrupo": 0
    },
    {
      "id": "2b190857-ccad-45a6-a4fb-1b855327b67a",
      "idJson": "b1668f4a-1eae-485d-b845-7e66eb9d5535",
      "atrasoDeGrupo": 0
    },
    {
      "id": "b4a09d50-41de-48da-88a4-87510bf88eea",
      "idJson": "830193d5-14d3-4c4d-b587-b853f141f95a",
      "atrasoDeGrupo": 0
    },
    {
      "id": "67b32786-e600-4697-a4f7-640bb53a2a5c",
      "idJson": "6251cbdd-de6f-435f-9e59-b32d7daaef9c",
      "atrasoDeGrupo": 0
    },
    {
      "id": "e14ed6dc-8a92-47af-bbf1-af87ac71c557",
      "idJson": "ed8dd6ef-d3a8-4f98-befb-7e454201267f",
      "atrasoDeGrupo": 0
    },
    {
      "id": "13880052-1f89-4a73-be7a-5ea2b2d7f9bd",
      "idJson": "ebf362ee-29e4-47b7-a974-179fd98e11c0",
      "atrasoDeGrupo": 0
    },
    {
      "id": "789385cb-0cc0-4183-b558-d8d89a7b9b48",
      "idJson": "281d7e4b-b963-433b-84f2-f899bfa3c5f0",
      "atrasoDeGrupo": 0
    },
    {
      "id": "4cc2bf8b-c405-4ec3-b940-03f0f2a3b7cd",
      "idJson": "3b08c18a-daa0-49e0-a1a1-bd8f7433a1b6",
      "atrasoDeGrupo": 0
    },
    {
      "id": "cd37a109-f50c-4100-80c6-420e5ab49231",
      "idJson": "d3933152-8491-4405-9502-37e85fe6ecb5",
      "atrasoDeGrupo": 0
    },
    {
      "id": "bc85a314-cb0d-4e2f-a326-b5c4826609f8",
      "idJson": "dccd32e2-a0d8-437c-868c-013468619c23",
      "atrasoDeGrupo": 0
    },
    {
      "id": "0ae3ea2a-1294-4a37-afeb-211be453d90d",
      "idJson": "34f0be96-9c2d-42c4-bd00-4ba6064364e8",
      "atrasoDeGrupo": 0
    },
    {
      "id": "cdd917ab-b5f8-4546-8c11-751d3cb1b9b9",
      "idJson": "e6c3a4f2-85d9-47f7-af80-91be361358b2",
      "atrasoDeGrupo": 0
    },
    {
      "id": "d294a0a6-ca78-49db-afff-8c5f2620cfa1",
      "idJson": "59cd0a99-e02c-4b7e-80bf-af8f8ed5df89",
      "atrasoDeGrupo": 0
    },
    {
      "id": "d8a61816-7840-4055-9461-2fe658373157",
      "idJson": "a0188c63-3f49-4b92-aa50-1f3268909969",
      "atrasoDeGrupo": 0
    },
    {
      "id": "13956506-df6b-4a55-9bc4-bcda89b47609",
      "idJson": "179f53aa-a192-4801-bbe0-5867d376326e",
      "atrasoDeGrupo": 0
    },
    {
      "id": "797d87a4-02ba-489f-8622-e21325813711",
      "idJson": "4cabae1a-50db-4fb5-9d8b-26d14cc180da",
      "atrasoDeGrupo": 0
    }
  ],
  "versaoControlador": {
    "id": "5da087a3-bde4-4792-bc7a-72397c30802e",
    "idJson": null,
    "descricao": "Inicial",
    "statusVersao": "CONFIGURADO",
    "controlador": {
      "id": "95a85464-1cc4-4fa4-8008-bc14e99aed4c"
    },
    "controladorFisico": {
      "id": "2a99dfef-ac95-48cf-8d18-0c5b04440b1d"
    },
    "usuario": {
      "id": "011f34ac-b59e-11e6-970d-0401fa9c1b01",
      "nome": "Administrador Geral",
      "login": "root",
      "email": "root@influunt.com.br"
    }
  },
  "statusVersao": "CONFIGURADO",
  "versoesPlanos": [
    {
      "id": "51f77d2c-5e64-4ced-a2bd-046c883913d8",
      "idJson": "f03abf36-4851-4568-9fe4-926dcece2276",
      "statusVersao": "CONFIGURADO",
      "anel": {
        "idJson": "4b0d7379-4273-4f33-a5f3-e39963758b8e"
      },
      "planos": [
        {
          "idJson": "18968fe5-898f-427c-8227-fa2e0019e5ec"
        },
        {
          "idJson": "584e8708-14dc-40cc-8e73-df2dfef26f17"
        }
      ]
    }
  ],
  "tabelasHorarias": [
    {
      "id": "0f2eaf6f-9acc-4d19-a861-161ecfcb925a",
      "idJson": "696ac25e-d49f-464b-a552-fe930de72404",
      "versaoTabelaHoraria": {
        "idJson": "1c888cb9-c4d9-4f51-b642-c9cb3f49b20d"
      },
      "eventos": [
        {
          "idJson": "03c024db-e277-49be-88a5-a5db0592ee47"
        },
        {
          "idJson": "b9070f2a-c59f-433d-b0bd-ca0646fc175e"
        }
      ]
    }
  ],
  "eventos": [
    {
      "id": "6fded171-e25a-4f68-9b3c-37445f21c962",
      "idJson": "03c024db-e277-49be-88a5-a5db0592ee47",
      "posicao": "1",
      "tipo": "NORMAL",
      "diaDaSemana": "Todos os dias da semana",
      "data": "02-12-2016",
      "horario": "00:00:00.000",
      "posicaoPlano": "1",
      "tabelaHoraria": {
        "idJson": "696ac25e-d49f-464b-a552-fe930de72404"
      }
    },
    {
      "id": "c25330e1-dade-46b5-a8a1-efb84397f950",
      "idJson": "b9070f2a-c59f-433d-b0bd-ca0646fc175e",
      "posicao": "2",
      "tipo": "NORMAL",
      "diaDaSemana": "Todos os dias da semana",
      "data": "02-12-2016",
      "horario": "00:00:10.000",
      "posicaoPlano": "2",
      "tabelaHoraria": {
        "idJson": "696ac25e-d49f-464b-a552-fe930de72404"
      }
    }
  ]
}
```
