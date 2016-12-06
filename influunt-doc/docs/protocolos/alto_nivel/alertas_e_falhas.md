# Alarmes e Falhas
Diversos tipos de eventos podem ser gerados durante a execução do controlador. Esses eventos podem ser transmitidos à central ou serem gravados no log. No caso do 72c, o arquivo de configuração define quais eventos serão enviados e gravados.



A seguir estão detalhados todos os eventos possíveis:

##Alarme
|Nome do Evento | Código| Descrição |
| -------------- |-------|-----------|
|ALARME_ABERTURA_DA_PORTA_PRINCIPAL_DO_CONTROLADOR|	1|	 Abertura da porta principal do controlador|
|ALARME_FECHAMENTO_DA_PORTA_PRINCIPAL_DO_CONTROLADOR|	2|	 Fechamento da porta principal do controlador|
|ALARME_ABERTURA_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR|	3|	 Abertura da porta do painel de facilidades do controlador|
|ALARME_FECHAMENTO_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR|	4|	 Fechamento da porta do painel de facilidades do controlador|
|ALARME_AMARELO_INTERMITENTE|	4|	 Amarelo Intermitente|
|ALARME_SEMAFORO_APAGADO|	5|	 Semáforo apagado|
|ALARME_ACERTO_RELOGIO_GPS|	6|	 Falha acerto relógio GPS|
|ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA|	7|	 Foco vermelho apagado|
|ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_REMOCAO|	8|	 Foco vermelho apagado removida|


### Campos do contéudo
| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| timestamp | Número Longo |S | Carimbo de tempo referente ao momento da ocorrência do evento. Deve estar no formato milissegundo desde 1 de janeiro de 1970|
| tipoEvento | Objeto JSON | S | Objeto que descreve o tipo de evento ocorrido
| descricaoEvento | Texto | S | Descrição completa do evento ocorrido
| params | Lista | N | Lista de parâmetros necessários para identificar o evento ocorrido

### Campos do tipo de evento
| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| tipoEvento | Texto | S | Tipo de evento ocorrido |
| tipoEventoControlador | ALARME, FALHA, REMOCAO_FALHA, DETECTOR_VEICULAR, DETECTOR_PEDESTRE, MODO_MANUAL, IMPOSICAO, TROCA_PLANO | S | Agrupamento que descreve o tipo de evento |
| codigo | Inteiro | S | Código do evento ocorrido |
| descricao | Texto | S | Descrição simplificada do evento ocorrido |
| descricaoParam | Texto | S | Descrição do tipo de paramêtro |
| tipoParam | DETECTOR_VEICULAR, DETECTOR_PEDESTRE, ANEL, GRUPO_SEMAFORICO, PLANO, MODO_OPERACAO_PLANO | S | Descrição do tipo de paramêtro |

### Possíveis paramêtros
DETECTOR_VEICULAR

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| 1 | Inteiro | S | Número do anel ao qual o detector está associado |
| 2 | Inteiro | S | Número do detector veicular |

DETECTOR_PEDESTRE

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| 1 | Inteiro | S | Número do anel ao qual o detector está associado |
| 2 | Inteiro | S | Número do detector pedestre |

ANEL

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| 1 | Inteiro | S | Número do anel |

GRUPO_SEMAFÓRICO

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| 1 | Inteiro | S | Número do anel ao qual o grupo semafórico está associado |
| 2 | Inteiro | S | Número do grupo semafórico |

PLANO

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| 1 | Inteiro | S | Número do anel |
| 2 | Inteiro | S | Número do plano |
| 3 | Inteiro | N | Duração em minutos da imposição do plano |

MODO_OPERAÇÃO_PLANO

| Campo| Tipo | Obrigatório| Descrição |
| -----|----- | ---------- | --------- |
| 1 | Inteiro | S | Número do anel |
| 2 | String | S | Modo de operação |
| 3 | Inteiro | N | Duração em minutos da imposição de modo |

Exemplo:
```JSON
{
  "tipoMensagem": "ALARME_FALHA",
  "idControlador": "7abfa23d-5646-4b8c-87ae-e68addbabb36",
  "destino": "app/alarmes_falhas/9b22cf91-dadf-40eb-8601-2d98bf642da8",
  "qos": 2,
  "carimboDeTempo": 1479929117853,
  "conteudo": {
    "timestamp":"16/11/2016 16:14:33",
    "tipoEvento":{
      "tipo":"ALARME_AMARELO_INTERMITENTE",
      "tipoEventoControlador":"ALARME",
      "codigo": 29,
      "descricao": "Amarelo Intermitente",
      "descricaoParam": "",
      "tipoParam": ""
    },
    "descricaoEvento": "Amarelo Intermitente",
    "params":[1]
  },
  "emResposta": "null"
}
```

## Falha
|Nome do Evento | Código| Descrição |
| -------------- |-------|-----------|
|FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO|	1|	 Detector pedestre - Falta de acionamento|
|FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO|	2|	 Detector pedestre - Acionamento direto|
|FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO|	3|	 Detector veicular - Falta de acionamento|
|FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO|	4|	 Detector veicular - Acionamento direto|
|FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO|	5|	 Desrespeito ao tempo máximo de permanência no estágio|
|FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA|	6|	 Fase vermelha do grupo semafórico apagada|
|FALHA_SEQUENCIA_DE_CORES|	7|	 Falha sequência de cores|
|FALHA_VERDES_CONFLITANTES|	8|	 Verdes conflitantes|
|FALHA_WATCH_DOG|	9|	 Falha CPU|
|FALHA_MEMORIA|	10|	 Falha Memória|

```JSON
{
  "tipoMensagem": "ALARME_FALHA",
  "idControlador": "7abfa23d-5646-4b8c-87ae-e68addbabb36",
  "destino": "app/alarmes_falhas/9b22cf91-dadf-40eb-8601-2d98bf642da8",
  "qos": 2,
  "carimboDeTempo": 1479929117853,
  "conteudo": {
    "timestamp":"16/11/2016 16:14:33",
    "tipoEvento":{
      "tipo":"FALHA_VERDES_CONFLITANTES",
      "tipoEventoControlador":"FALHA",
      "codigo":11,
      "descricao": "Falha do MQTT - anel 1",
      "descricaoParam":"Anel",
      "tipoParam":"ANEL"
    },
    "descricaoEvento": "Falha do MQTT - anel 1",
    "params":[1]
  },
  "emResposta": "null"
}
```

## Remoção de Falhas
|Nome do Evento | Código| Descrição |
| -------------- |-------|-----------|
|REMOCAO_FALHA_DETECTOR_PEDESTRE|	1|	 Detector pedestre - Remoção de falha|
|REMOCAO_FALHA_DETECTOR_VEICULAR|	2|	 Detector veicular - Remoção de falha|
|REMOCAO_FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO|	3|	 Fase vermelha do grupo semafórico apagada removida|
|REMOCAO_FALHA_VERDES_CONFLITANTES|	4|	 Verdes conflitantes removido|

```JSON
{
  "tipoMensagem": "REMOCAO_FALHA",
  "idControlador" : "7abfa23d-5646-4b8c-87ae-e68addbabb36",
  "idAnel" : "7edd7d2a-0bb2-49b3-995c-b1d741b394af",
  "destino": "app/alarmes_falhas/9b22cf91-dadf-40eb-8601-2d98bf642da8",
  "qos": 2,
  "carimboDeTempo": 1479929117853,
  "conteudo": {
    "timestamp":"16/11/2016 16:14:33",
    "tipoEvento":{
      "tipo":"REMOCAO_FALHA_VERDES_CONFLITANTES",
      "tipoEventoControlador":"REMOCAO_FALHA",
      "codigo":11,
      "descricao": "Falha do MQTT - anel 1",
      "descricaoParam":"Anel",
      "tipoParam":"ANEL"
    },
    "descricaoEvento": "Falha do MQTT - anel 1",
    "params":[1]
  },
  "emResposta": "null"
}
```







