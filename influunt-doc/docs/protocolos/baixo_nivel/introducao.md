# Introdução
O protocolo de baixo nível especifica um protocolo serial para controladores que utilizem o 72c. Esse protocolo constitui uma série de mensagens binárias que são enviadas do 72c para o hardware ou do hardware para o 72c. As mensagens são empacotadas de forma padrão na estrutura chamada Mensagem e são transmitidas em formato binário.

## Mensagem

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
|Header| 3 bytes | <I\> |
| Tamanho      | 8 bits        | Tamanho em bytes da mensagem que está sendo enviada|
|Tipo de Mensagem| 8 bits | Descreve o tipo de mensagem que está sendo enviada conforme a tabela de tipo de mensagem|
|Sequencia | 16 bits | Número sequência da mensagem. Deve ser incrementado a cada nova mensagem enviada |
|Mensagem | Variável | Conteúdo da mensagem que está sendo enviada | 
|Checksum | 8 bits   | _Checksum_ de toda a mensagem utilizando o algorítmo _LRC_ | 
|Trailler| 3 bytes | <F\> |

O conteúdo entre o Header e Trailler deve ser convertido para um _String_ representando cada byte em Hexadecimal com duas casas, no caso de um caracter inserir zero a esquerda. 

### Mensagem de Início
O 72c envia a mensagem de início ao hardware para informar que está pronto para começar o envio de estágios.

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
| Anel G1 | 4 bits | Indica a qual anel pertence o grupo semafórico G1|
| Anel G2 | 4 bits | Indica a qual anel pertence o grupo semafórico G2|
| Anel G3 | 4 bits | Indica a qual anel pertence o grupo semafórico G3|
| Anel G4 | 4 bits | Indica a qual anel pertence o grupo semafórico G4|
| Anel G5 | 4 bits | Indica a qual anel pertence o grupo semafórico G5|
| Anel G6 | 4 bits | Indica a qual anel pertence o grupo semafórico G6|
| Anel G7 | 4 bits | Indica a qual anel pertence o grupo semafórico G7|
| Anel G8 | 4 bits | Indica a qual anel pertence o grupo semafórico G8|
| Anel G9 | 4 bits | Indica a qual anel pertence o grupo semafórico G9|
| Anel G10 | 4 bits | Indica a qual anel pertence o grupo semafórico G10|
| Anel G11 | 4 bits | Indica a qual anel pertence o grupo semafórico G11|
| Anel G12 | 4 bits | Indica a qual anel pertence o grupo semafórico G12|
| Anel G13 | 4 bits | Indica a qual anel pertence o grupo semafórico G13|
| Anel G14 | 4 bits | Indica a qual anel pertence o grupo semafórico G14|
| Anel G15 | 4 bits | Indica a qual anel pertence o grupo semafórico G15|
| Anel G16 | 4 bits | Indica a qual anel pertence o grupo semafórico G16|

O hardware deve responder a mensagem de início com à mensagem de retorno OK e uma mensagem de INFO.

###  Estágio
O 72c informa a configuração dos grupos semafóricos de um determinado anel. Essa configuração deve ser seguida pelo hardware até que uma nova configuração seja enviada.

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
| Flag 1| 1 bit | Reservado para uso futuro|
| Flag 2| 1 bit | Reservado para uso futuro|
| Flag 3| 1 bit | Reservado para uso futuro|
| Quantidade de Grupos Semafóricos| 5 bits | Quantidade de grupos semafóricos que fazem parte dessa configuração de estágio|
| Configuração do Grupo Semafórico| 14 bytes por grupo semafórico | Configuração dos tempos de cada grupo semafórico | 

### Grupo semafórico

Descreve como um grupo semafórico deve se comportar nesse estágio:

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
|Reservado | 4 bits | Reservado|
|Flag pedestre/veicular | 1 bit | Se diferente de 0, então esse é um grupo semafórico de pedestre|
|Flag composição dos tempos| 3 bits| Ver tabela de flag composição dos tempos  |
|Grupo | 8 bits | Número do grupo semafórico|
|Tempo De Atraso de Grupo ou Entreverde | 24 bits| Tempo de atraso de grupo para perda do direito de passagem ou tempo de vermelho no período de entreverdes para o grupo com ganho do direito de passagem.|
|Tempo Amarelo| 24 bits| Tempo de amarelo para veicular ou vermelho intermitente para pedestre|
|Tempo Vermelho Limpeza| 24 bits| Tempo de vermelho de limpeza|
|Tempo do Estágio | 24 bits| Verde para quem tem direito de passagem e vermelho para quem não tem.|

### Info

Obtém do hardware qual é o fabricante, o modelo e a versão do firmware.

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
|Fabricante | Livre | String com o nome do fabricante, seguida pela caractere ";". Em formato ASCII convertida para HEX|
|Modelo | Livre | String com o modelo do equipamento, seguida pela caractere ";". Em formato ASCII convertida para HEX|
|Fabricante | Livre | String com o versão do firmware do equipamento. Em formato ASCII convertida para HEX|


| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
|Reservado | 4 bits | Reservado|
|Flag pedestre/veicular | 1 bit | Se verdadeiro, esse é um grupo semafórico de pedestre|
|Flag composição dos tempos| 3 bits| Ver tabela de flag composição dos tempos  |
|Grupo | 8 bits | Número do grupo semafórico|
|Tempo De Atraso de Grupo ou Entreverde | 24 bits| Tempo de atraso de grupo para perda do direito de passagem ou tempo de vermelho no período de entreverdes para o grupo com ganho do direito passagem.|
|Tempo Amarelo| 24 bits| Tempo de amarelo para veicular ou vermelho intermitente para pedestre|
|Tempo Vermelho Limpeza| 24 bits| Tempo de vermelho de limpeza|
|Tempo do Estágio | 24 bits| Verde para quem tem direito de passagem e vermelho para quem não tem.|



#### Flag de composição de tempo

| Valor| Descrição |
| -----|-----------|
|0     | O grupo semafórico estará desligado durante o tempo de estágio |
|1     | O grupo semafórico estará verde durante o tempo de estágio | 
|2     | O grupo semafórico estará vermelho durante o tempo de estágio |
|3     | O grupo semafórico estará em amarelo intermitente para veícular ou desligado para pedestre durante o tempo de estágio |
|4     | O grupo semafórico está executando à sequencia de partida |


### Detector
O hardware informa ao 72c que um detector foi acionado.

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
| Flag 1| 1 bit | Reservado para uso futuro|
| Flag 2| 1 bit | Reservado para uso futuro|
| Flag 3| 1 bit | Verdadeiro, se o detector for de pedestre|
| Posição| 5 bits | Posição do grupo semafórico|

### Falha Anel
O hardware informa ao 72c uma falha em um anel

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
| anel| 8 bits | Número do anel|
| Código da falha| 8 bits | Código da falha|

### Falha Anel
O hardware informa ao 72c uma falha em um grupo semafórico

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
| grupo | 8 bits | Número do grupo semafórico|
| Código da falha| 8 bits | Código da falha|


### Falha Genérica
O hardware informa ao 72c uma falha genérica

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
| Código da falha| 8 bits | Código da falha|

### Falha Detector
O hardware informa ao 72c uma falha no Detector

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
| Flag 1| 1 bit | Reservado para uso futuro|
| Flag 2| 1 bit | Reservado para uso futuro|
| Flag 3| 1 bit | Verdadeiro, se o detector for de pedestre|
| Posição| 5 bits | Posição do grupo detector|
| Código da Falha| 8 bits | Código da falha|

### Alarme
O hardware informa ao 72c um alarme

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
| Código do alarme| 8 bits | Código do alarme|

### Retorno
Mensagem de confirmação de recebimento

| Campo| Tamanho| Descrição |
| ------------ | ------------- | ------------ |
| Tipo de retorno| 8 bits | Tipo do retorno|


### Tabela de Tipos de Mensagem
|Codigo| Descrição|
|------|----------|
|0|	Retorno |
|1|	Início |
|2|	Estágio |
|3|	Detector |
|4|	Falha anel |
|5|	Falha detector |
|6|	Falha grupo semafórico |
|7|	Falha genérica |
|8|	Alarme |
|9|	Remoção de falha |
|10|	Inserção de plug |
|11|	Remoção de plug |
|12|	Troca de estágio modo manual |
|13|	Modo manual ativado |
|14|	Modo manual desativado |

### Tabela de Tipos de Retorno
|Codigo| Descrição|
|------|----------|
|0|	OK|
|1|	INVALID_CHECKSUM|
|2|	Tipo de Mensagem Inválido|

  
