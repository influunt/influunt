# Ciclo de Vida do Controlador
O ciclo de vida do controlador define quais em quais estados um controlador pode estar em um devido momento, bem como as possíveis transições entre esses estados. Toda vez que o controlador mudar de staus a central deve ser notificada. A central também pode impor mudanças nos status do controlador.

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
| INATIVO | O controlador foi inativado. Só será ativado novamente se um comando de ativação dor dado por um operador|
| OPERANDO_COM_FALHAS |O controlador detectou uma falha de menor gravidade, porém continua operando|
| INOPERANTE |O controlador detectou uma falha de maior gravidade e interrompeu sua operação |

## Transições

A tabela baixo lista as possíveis transições entre os estados do controlador. O modo de disparo pode ser __M__ (Manual) ou __A__ (Automático). O campo __mensagem__ descreve qual mensagem deve ser enviada para causar a transição:

| De| Para | Mensagem | Disparo | Descrição |
|---|------|----------|---------|-----------|
|-  |NOVO  | ---      | Automático | Todo novo controlador deve começar com o status NOVO| 
|NOVO|CONFIGURADO|CONFIGURACAO INICIAL|A|O controlador recebeu da central sua configuração inicial e esta pronto para entrar em operação| 
|CONFIGURADO, EM MANUTENCAO, INATIVO|ATIVO|ATIVAR|M|O operador enviou o comando de ativar| 
|OPERANDO COM FALHAS, INOPERANTE|ATIVO|ATIVAR|A|O controlador percebeu que a falha foi sanada e que o controlador voltou a operar normalmente|
|ATIVO, OPERANDO COM FALHAS, INOPERANTE|EM MANUTENCAO|COLOCAR EM MANUTENCAO|M|O operador enviou o comando de colocar em manutenção|
|ATIVO, INOPERANTE|OPERANDO COM FALHAS|REPORTAR FALHA|A|O controlador percebeu um falha leve e continua operando mesmo assim| 
|ATIVO, OPERANDO COM FALHAS|INOPERANTE|REPORTAR FALHA GRAVE|A|O controlador percebeu um falha grave e interrompeu sua operação| 
|ATIVO, EM MANUTENCAO, OPERANDO COM FALHAS, INOPERANTE, CONFIGURADO|INATIVO|INATIVAR| Automático|O operador enviou comando para inativar o controlador| 

## Mensagem do Ciclo de Vida: Controlador / Central

![CONFIGURACAO_CONTROLADOR_CENTRAL](../../img/CONFIGURACAO_CONTROLADOR_CENTRAL.png)

## Mensagem do Ciclo de Vida: Central / Controlador

![CONFIGURACAO_CENTRAL_CONTROLADOR](../../img/CONFIGURACAO_CENTRAL_CONTROLADOR.png)


### CONFIGURACAO INCIAL
Quando um controlador com status NOVO conectar com a internet ele deve solicitar a central sua configuração. Para isso deve enviar a mensagem CONFIGURACAO_INICIAL [CONFIGURACAO_INICIAL](#CONFIGURACAO_INICIAL) para o tópico[/central/configuracao](comunicao/topicos#central_echo).

| Campo| Tipo | Obrigatorio| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```

### CONFIGURACAO
Essa mensagem contém todas as configurações necessárias para a operação de um controlador. Pode ser enviada como resposta a mensagem [CONFIGURACAO_INICIAL] ou quando o controlador está no estado EM_MANUTENÇÃO para atualizar sua configuração.

| Campo| Tipo | Obrigatorio| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```

### ATIVAR
Mensagem enviada para colocar o controlador no modo de operação ATIVO. Pode ser enviada manualmente pelo operador para tirar o controlador dos estados de INATIVO e EM_MANUTENCAO, ou pelo próprio controlador ao se recuperar de uma falaha.

| Campo| Tipo | Obrigatorio| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```
### COLOCAR EM MANUTENCAO
O operador pode enviar a mensagem COLOCAR_EM_MANUTENCAO para realizar manutenções programadas ou de excessão em um controlador.

| Campo| Tipo | Obrigatorio| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```

### REPORTAR FALHA
Quando o controlador detecta uma falha que permita que ele continue operando ele deve enviar a mensagem REPORTAR_FALHA ao entrar no estado OPERANDO_COM_FALHAS.

| Campo| Tipo | Obrigatorio| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```

### REPORTAR FALHA GRAVE
Quando o controlador detecta uma falha grave que não permita que ele continue operando, ele deve enviar a mensagem REPORTAR_FALHA_GRAVE ao entrar no estado INOPERANTE.


| Campo| Tipo | Obrigatorio| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```

### INATIVAR
Um operador pode colocar um controlador como INATIVO. Esse estado deve ser utilizado para um controlador que não fará mais parte da rede.

| Campo| Tipo | Obrigatorio| Descrição |
| ------------ | ------------- | ------------ |


```JSON
```
