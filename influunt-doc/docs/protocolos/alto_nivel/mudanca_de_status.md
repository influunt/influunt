# Mudança de Status
O controlador deve enviar essa mensagem para central sempre que o controlador mudar de status.

![CONFIGURACAO_CONTROLADOR_CENTRAL](../../img/mudanca_status.png)


A seguir estão detalhados todos os eventos possíveis:

## Especificação 
| Campo| Tipo | Obrigatório| Descrição |
| ------|-----------|----------- | ----------------------- |
| statusDevice      | Texto      |S| Status do Controlador:ATIVO, CONFIGURADO, EM_MANUTENCAO, INATIVO, COM_FALHAS|
| statusAnel        | Mapa de Texto para Texto |S| Sendo a chave o número do anel e o valor o status do anel. NORMAL, COM_FALHA, AMARELO_INTERMITENTE_POR_FALHA, APAGADO_POR_FALHA, MANUAL |


## Exemplo


```JSON
{
  "tipoMensagem": "MUDANCA_STATUS_CONTROLADOR",
  "idControlador": "7abfa23d-5646-4b8c-87ae-e68addbabb36",
  "destino": "central/mudanca_status_controlador",
  "qos": 2,
  "carimboDeTempo": 1479929117853,
  "conteudo": {
   "statusDevice": "CONFIGURADO",
   "statusAneis": {
       "1": "NORMAL",
       "2": "NORMAL"
     }
  }
  "emResposta": "null"
}
```






