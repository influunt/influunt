# Introdução a Comunicação entre a Central e os Controladores

Toda a comunicação entre a central e os controladores (e vice-versa) acontece de forma assíncrona utilizando o protocolo 
aberto [MQTT](http://mqtt.org). O protocolo [MQTT](http://mqtt.org) provê um mecanismo de comunicação para redes de baixa 
confiabilidade utilizado mensagens assíncronas no padrão [publish/subscribe](https://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern).

Todas as mensagens trafegadas entre a central e os controladores são serializadas no formato [JSON](http://json.org) e envelopadas de acordo com as 
regras definidas no [padrão de envelopagem](/comunicacao/envelope).
