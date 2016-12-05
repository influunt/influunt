# Introdução
O protocolo de alto nível especifica todos as mensagens que são trocadas entre o controlador e a central. O 72c implementa esse protoloco. Controladores que não utilizem o 72c devem realizar sua própria implementação do mesmo.

Esse protocolo funciona via o envio de envolopes para tópicos do broker MQTT. O envelope contém todas as informações sobre a mensagem trocada, bem como informações de origem, destino e qualidade do serviço. Os envelopes são enviados criptografados.

## Publish/Subscribe
Um tópico é um fila de mensagens onde seus assinantes são notificados a cada nova mensagem recebida. A central é assinante dos tópicos referentes a todos os seus controladores. Cada controlador é assinante de tópicos relacionados a sua informação.

## Tópicos assinados pela central

### controladores/conn/online
Os controladores publicam mensagens nesse tópico para informar a central que estão on-line. Ver tópico [conectividade](/protocolos/alto_nivel/conectividade).

### controladores/conn/offline
Os controladores publicam mensagens nesse tópico para informar a central que estão on-offline. Ver tópico [conectividade](/protocolos/alto_nivel/conectividade).

### central/configuracao
Os controladores publicam nesse tópico para solicitar sua configuração inicial a central. Ver tópico [ciclo de vida do controlador](/protocolos/alto_nivel/ciclo_vida/)


### central/transacoes/+
Gerencia transações entre de envio de dadods para o controlador. O + é substituido pelo id da transação que é gerado na central e informado ao controlador.

### central/alarmes_falhas/
Os controladores publicam alarmes e falhas nesse tópico para que a central tenham conhecimento.

### central/troca_plano/
Os controladores publicam nesse tópico para informar que houve uma troca de plano.


### central/mudanca_status_controlador
Os controladores publicam para informar a central a mudança de planos.


## Tópicos assinados pela controlador

### controlador/#ID-DO-CONTROLADOR/+
A central publica nesse tópico quaisquer mensagens que deseja enviar ao controlador.




  