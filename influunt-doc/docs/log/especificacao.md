# Introdução


### Formato
| Campo | Descrição |
| ----- | --------- | 
| Data e Hora | Data e hora no formato dd/mm/yyyy HH:MM:SS|
| espaço | um caracter de espaço usado como separador de campos|
| Nome da classe do tipo de evento do log | Nome da classe de log entre couchetes|
| espaço | um caracter de espaço usado como separador de campos|
| evento do log | Descrição textual do evento conforme especificação dos Eventos do Log|

```
22/02/1982 20:40 [EXECUCAO] Ocorreu uma falha de verdes conflitantes no anel 1 
```

## Classes

### COMUNICAÇÃO
Eventos de log relacionados a comunicação do controlador com a central
### EXECUÇÃO
Eventos de log relacionados a execução dos planos semafóricos
### INICIALIZAÇÃO
Eventos de log relacionados a inicialização do controlador
### FINALIZAÇÃO
Eventos de log relacionados a finalização do controlador
### ERRO
Eventos de log relacionados a erros no controlador


## Níveis
O controlador deve ser capaz de ser configurado para atender 3 nível de log diferentes. As mensagem de log dos níveis normal e detalhado são padranizadas e não podem ser alteradas. As mensagem de log do nível superdetalhado são de livre utilização.

### NORMAL
Eventos normais da execução do controlador.
### DETALHADO
Incluí os eventos do nível normal é adiciona eventos para ajudar a encontrar problemas na comunicação.
###SUPERDETALHADO
Incluí o máximo de informações possíveis no log.

## Padrão das Mensagens para os níveis normal e detalhado

| Nível | Tópico    | Momento | Padrão | Exemplo|
| ----- | --------- | --------|--------|--------|
|DETALHADO | COMUNICACAO  | A cada passo de uma transação | "[%s] %s" tipo da transação, etapa da transação || 
|DETALHADO | COMUNICACAO  | Quando o cliente perde conexão a conexão com o broker MQTT | MQTT perdeu a conexão com o broker. Restartando ator. ||
|DETALHADO | COMUNICACAO  | Quando uma mensagem não pode ser processada | Ocorreceu um erro no processamento de mensagens. a mensagem será desprezada || 
|DETALHADO | EXECUCAO  | Quando a comunicação serial é iniciada | Comunicação serial pronta para iniciar || 
|DETALHADO | EXECUCAO  | Quando a comunicação serial é iniciada | Iniciando a comunicação serial ||
|DETALHADO | INICIALIZACAO  | Ao definir a porta MQTT | "MQTT PORT       :%s", port | 03/01/2017 17:37:48.887 - [INICIALIZACAO] MQTT PORT :1883 |
|DETALHADO | INICIALIZACAO  | Ao definir a senha do MQTT | "MQTT PWD        :%s", senha | 03/01/2017 17:37:48.887 - [INICIALIZACAO] MQTT PWD :PASSWORD |
|DETALHADO | INICIALIZACAO  | Ao definir o host MQTT | "MQTT HOST       :%s", host | 03/01/2017 17:37:48.886 - [INICIALIZACAO] MQTT HOST :mosquitto.influunt.com.br|
|DETALHADO | INICIALIZACAO  | Ao definir o id do controlador | "ID CONTROLADOR  :%s", id | 03/01/2017 17:37:48.886 - [INICIALIZACAO] ID CONTROLADOR :d933e586-b763-4c26-bc5f-ef1a60428003 |
|DETALHADO | INICIALIZACAO  | Ao definir o login do MQTT | "MQTT LOGIN      :%s", login | 03/01/2017 17:37:48.887 - [INICIALIZACAO] MQTT LOGIN :d933e586-b763-4c26-bc5f-ef1a60428003 |
|DETALHADO | INICIALIZACAO  | Ao solicitar configuração à central | Verificando a configuração do controlador | 03/01/2017 17:37:51.430 - [INICIALIZACAO] Verificando a configuração do controlador|
|DETALHADO | INICIALIZACAO  | Quando a central devolve a configuração | Configuração encontrada | 03/01/2017 17:37:51.443 - [INICIALIZACAO] Configuração encontrada |
|DETALHADO | INICIALIZACAO  | Quando o controlador começa a executar a programação semafórica | O controlador foi colocado em execução | |
|NORMAL | ERRO  | Quando não é possível se conectar na porta serial | Falha na comunicação serial. Não foi possivel enviar mensagem | |
|NORMAL | ERRO  | Quando não é possível se conectar na porta serial | Não foi possível abrir comunicação pela porta:  + porta | |
|NORMAL | ERRO  | Quando não é possível se conectar na porta serial | Não foi possível iniciar a comunicação serial | |
|NORMAL | EXECUCAO  | Quando a tabela horária muda | Mudança de tabela horária | |
|NORMAL | INICIALIZACAO  | Ao iniciar o OS | Iniciando O 72C | |
|NORMAL | INICIALIZACAO  | Quando o controlador está no ar, mas ainda não existe uma configuração válida | Não existe configuração para iniciar o motor | |
|NORMAL | INICIALIZACAO  | Quando o controlador está no ar, mas ainda não existe uma configuração válida | O controlador será iniciado quando um configuração for recebida | |
|NORMAL | EXECUCAO  | Quando um evento é gerado | "[%s] %s", Tipo evento - Descricao do evento" | Ver tabela de descrição de tipo de evento |


### Tabela de descrição de tipo de evento

| Tipo Evento | Descrição | Parâmetros|
| ----- | --------- | --------|
| ALARME ABERTURA DA PORTA PRINCIPAL DO CONTROLADOR | Abertura da porta principal do controlador | |
| ALARME FECHAMENTO DA PORTA PRINCIPAL DO CONTROLADOR | Fechamento da porta principal do controlador ||
| ALARME ABERTURA DA PORTA DO PAINEL DE FACILIDADES DO CONTROLADOR | Abertura da porta do painel de facilidades do controlador ||
| ALARME FECHAMENTO DA PORTA DO PAINEL DE FACILIDADES DO CONTROLADOR | Fechamento da porta do painel de facilidades do controlador ||
| ALARME AMARELO INTERMITENTE | Amarelo Intermitente ||
| ALARME SEMAFORO APAGADO | Semafóro apagado | Número do Anel, Número do Detector |
| FALHA DETECTOR PEDESTRE FALTA ACIONAMENTO | Anel %s: Falha no DP%s - Falta de Acionamento | Número do Anel, Número do Detector|
| FALHA DETECTOR PEDESTRE ACIONAMENTO DIRETO | Anel %s: Falha no DP%s - Acionamento Direto | Número do Anel, Número do Detector|
| FALHA DETECTOR VEICULAR FALTA ACIONAMENTO | Anel %s: Falha no DV%s - Falta de Acionamento | Número do Anel, Número do Detector|
| FALHA DETECTOR VEICULAR ACIONAMENTO DIRETO | Anel %s: Falha no DV%s - Acionamento Direto | Número do Anel, Número do Detector|
| FALHA DESRESPEITO AO TEMPO MAXIMO DE PERMANENCIA NO ESTAGIO | Anel %s: Desrespeito ao tempo máximo de permanência no estágio | Número do Anel |
| FALHA FASE VERMELHA DE GRUPO SEMAFORICO APAGADA | Anel %s: Fase vermelha do G%s apagada | Número do Anel |
| FALHA SEQUENCIA DE CORES | Anel %s: Falha sequencia de cores | Número do Anel |
| FALHA VERDES CONFLITANTES | Anel %s: Falha de Verdes conflitantes | Número do Anel |
| FALHA WATCH DOG | Falha CPU | |
| FALHA MEMORIA | Falha Memoria ||
| FALHA FOCO VERMELHO DE GRUPO SEMAFORICO APAGADA | Anel %s: Foco vermelho do G%s apagado | Número do Anel, Número do Grupo Semafórico||
| FALHA COMUNICACAO BAIXO NIVEL | Falha na comunicação do protocolo de baixo nível ||
| FALHA ACERTO RELOGIO GPS | Falha acerto relógio GPS| |
| REMOCAO FALHA DETECTOR PEDESTRE | Anel %s: Falha no DP%s removida | Número do Anel, Número do Detector|
| REMOCAO FALHA DETECTOR VEICULAR | Anel %s: Falha no DV%s removida | Número do Anel, Número do Detector|
| REMOCAO FALHA FASE VERMELHA DE GRUPO SEMAFORICO | %s: Fase vermelha do G%s apagada removida | Número do Anel, Número do Grupo Semafórico|
| REMOCAO FALHA VERDES CONFLITANTES | Anel %s: Falha de Verdes conflitantes removido | Número do Anel |
| REMOCAO FOCO VERMELHO DE GRUPO SEMAFORICO | Anel %s: Foco vermelho do G%s apagado removida | Número do Anel, Número do Grupo Semafórico|
| REMOCAO COMUNICACAO BAIXO NIVEL | Comunicação do protocolo de baixo nível recuperada| |
| ACIONAMENTO DETECTOR VEICULAR | %s foi acionado | Número do Detector |
| ACIONAMENTO DETECTOR PEDESTRE | %s foi acionado | Número do Detector |
| INSERCAO DE PLUG DE CONTROLE MANUAL | Inserção de plug do controle manual| |
| RETIRADA DE PLUG DE CONTROLE MANUAL | Retirada de plug do controle manual| |
| TROCA ESTAGIO MANUAL | Troca estágio no modo manual| |
| MODO MANUAL ATIVADO | Modo manual ativado | Número do Anel, Número do Plano|
| IMPOSICAO PLANO | Anel %s: Plano %s foi imposto com duração de %s minutos | Número do Anel, Número do Plano, minutos|
| IMPOSICAO MODO | Anel %s: Modo de operação %s foi imposto | Número do Anel, Modo de Operação|
| LIBERAR IMPOSICAO | Anel %s: Foi liberado da imposição | Número do Anel |
| TROCA DE PLANO NO ANEL | Plano %s está ativo | Número do Plano |





  
