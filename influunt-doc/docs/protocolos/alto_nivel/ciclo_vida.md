# Ciclo de Vida do Controlador
O ciclo de vida do controlador define em quais estados um controlador pode estar em um devido momento, bem como as possíveis transições entre esses estados. Toda vez que o controlador mudar de status a central deve ser notificada. A central também pode impor mudanças nos status do controlador.

A seguir é apresentado o diagrama de estados do controlador:

![CONTROLADOR_ONLINE](../../img/estados.png)



## Status
A tabela abaixo apresentado o significado de cada estado:

| Campo| Descrição |
| ------------ | ------------- |
| NOVO | O sistema foi instalado em um novo controlador, porém o mesmo ainda não recebeu sua configuração inicial|
| CONFIGURADO | O controlador foi corretamente configurado e aguarda comando para iniciar a operação|
| ATIVO | O controlador está ativo e operando normalmente |
| EM_MANUTENCAO | O controlador foi colocado em manutenção |
| INATIVO | O controlador foi inativado. Só será ativado novamente se um comando de ativação for dado por um operador|
| OPERANDO_COM_FALHAS |O controlador detectou uma falha de menor gravidade, porém continua operando|
| INOPERANTE |O controlador detectou uma falha de maior gravidade e interrompeu sua operação |

## Transições

A tabela abaixo lista as possíveis transições entre os estados do controlador. O modo de disparo pode ser __M__ (Manual) ou __A__ (Automático). O campo __mensagem__ descreve qual mensagem deve ser enviada para causar a transição:

| De| Para | Mensagem | Disparo | Descrição |
|---|------|----------|---------|-----------|
|-  |NOVO  | ---      | Automático | Todo novo controlador deve começar com o status NOVO| 
|NOVO|CONFIGURADO|CONFIGURACAO INICIAL|Automático|O controlador recebeu da central sua configuração inicial e está pronto para entrar em operação| 
|CONFIGURADO, EM MANUTENCAO, INATIVO|ATIVO|ATIVAR|Manual|O operador enviou o comando de ativar| 
|OPERANDO COM FALHAS, INOPERANTE|ATIVO|ATIVAR|Automático|O controlador percebeu que a falha foi sanada e que o controlador voltou a operar normalmente|
|ATIVO, OPERANDO COM FALHAS, INOPERANTE|EM MANUTENCAO|COLOCAR EM MANUTENCAO|Manual|O operador enviou o comando de colocar em manutenção|
|ATIVO, INOPERANTE|OPERANDO COM FALHAS|REPORTAR FALHA|Automático|O controlador percebeu um falha leve e continua operando mesmo assim| 
|ATIVO, OPERANDO COM FALHAS|INOPERANTE|REPORTAR FALHA GRAVE|Automático|O controlador percebeu um falha grave e interrompeu sua operação| 
|ATIVO, EM MANUTENCAO, OPERANDO COM FALHAS, INOPERANTE, CONFIGURADO|INATIVO|INATIVAR| Automático|O operador enviou comando para inativar o controlador| 

## Mensagem do Ciclo de Vida: Controlador / Central

![CONFIGURACAO_CONTROLADOR_CENTRAL](../../img/CONFIGURACAO_CONTROLADOR_CENTRAL.png)

## Mensagem do Ciclo de Vida: Central / Controlador

![CONFIGURACAO_CENTRAL_CONTROLADOR](../../img/CONFIGURACAO_CENTRAL_CONTROLADOR.png)


### CONFIGURAÇÃO INCIAL
Quando um controlador com status NOVO se conectar à internet ele deve solicitar à central sua configuração. Para isso deve enviar a mensagem CONFIGURACAO_INICIAL [CONFIGURACAO_INICIAL](#CONFIGURACAO_INICIAL) para o tópico[/central/configuracao](comunicao/topicos#central_echo).

O conteúdo da mensagem é vazio.

```JSON
{}
```

### CONFIGURAÇÃO
Essa mensagem contém todas as configurações necessárias para a operação de um controlador. Pode ser enviada como resposta à mensagem [CONFIGURACAO_INICIAL] ou quando o controlador está no estado EM_MANUTENÇÃO para atualizar sua configuração.

O conteúdo dessa mensagem está especificado no tópico [Configuração do Controlador](/protocolos/alto_nivel/configuracao/)

### ATIVAR
Mensagem enviada para colocar o controlador no modo de operação ATIVO. Pode ser enviada manualmente pelo operador para tirar o controlador dos estados de INATIVO e EM_MANUTENCAO, ou pelo próprio controlador ao se recuperar de uma falaha.

| Campo| Tipo | Obrigatório| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```
### COLOCAR EM MANUTENÇÃO
O operador pode enviar a mensagem COLOCAR_EM_MANUTENCAO para realizar manutenções programadas ou de exceção em um controlador.

| Campo| Tipo | Obrigatório| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```

### REPORTAR FALHA
Quando o controlador detecta uma falha que permita que ele continue operando ele deve enviar a mensagem REPORTAR_FALHA ao entrar no estado OPERANDO_COM_FALHAS.

| Campo| Tipo | Obrigatório| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```

### REPORTAR FALHA GRAVE
Quando o controlador detecta uma falha grave que não permita que ele continue operando, ele deve enviar a mensagem REPORTAR_FALHA_GRAVE ao entrar no estado INOPERANTE.


| Campo| Tipo | Obrigatório| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```

### INATIVAR
Um operador pode colocar um controlador como INATIVO. Esse estado deve ser utilizado para um controlador que não fará mais parte da rede.

| Campo| Tipo | Obrigatório| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```
