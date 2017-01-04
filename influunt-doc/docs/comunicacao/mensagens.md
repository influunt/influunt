# Mensagens

As seguintes mensagens podem ser trocadas entre a central e o controlador e vice-versa. Cada mensagem pode ter conteúdo diferente e devem ser trafegadas dentro de um envelope.

|Tipo Mensagem       | Descrição |Origem | Destino | Momento do Envio |
|--------------------|-----------|-------| --------|------------------|
|[CONTROLADOR ONLINE](/protocolos/alto_nivel/conectividade/#CONTROLADOR_ONLINE)  | Controlador informa à central que está online |Controlador  | Central| Sempre que o controlador se conectar à internet|
|[CONTROLADOR OFFLINE](/protocolos/alto_nivel/conectividade/#CONTROLADOR_OFFLINE)  | Controlador informa à central que está offline |Controlador | Central| Sempre que o controlador se desconectar da internet|
|[CONFIGURACAO INICIAL](/protocolos/alto_nivel/ciclo_vida/)| Controlador solicita à central sua configuração inicial |Controlador |Central| Sempre que o controlador não estiver conectado à internet e não tiver uma configuração residente válida |
|[CONFIGURACAO](/protocolos/alto_nivel/ciclo_vida/)        | Central envia a configuração incial para o controlador|Central |Controlador| Sempre que um controlador enviar uma mensagem de CONFIGURACAO_INICIAL e a central tiver uma configuração válida para informar ao controlador |
|[CONFIGURACAO OK](/protocolos/alto_nivel/ciclo_vida/)     | O controlador informa que a configuração recebida da central é válida |Controlador | Central| Sempre que a configuração inicial recebida da central for válida|
|[CONFIGURACAO ERRO](/protocolos/alto_nivel/ciclo_vida/) | O controlador reporta que a configuração recebida da central não é válida| Controlador | Central | Quando a configuração recebida não é válida|
|[ALARME FALHA](/protocolos/alto_nivel/alertas_e_falhas/)        | O controlador informa à central a ocorrência de um alarme ou falha|Controlador | Central | Sempre que ocorrer um alarme ou falha no controlador que seja passível de ser reportada à central|
|[REMOCAO FALHA](/protocolos/alto_nivel/alertas_e_falhas/)       | O controlador informa à central que uma falha foi removida  | Controlador | Central | Sempre que uma falha for removida no controlador (dado que a falha foi inicialmente reportada para a central)|
|[TROCA_DE PLANO](/protocolos/alto_nivel/troca_de_planos/)      | O controlador informa à central uma troca de planos | Controlador | Central | Sempre que o controlador trocar de plano |
|[TRANSACAO](/protocolos/alto_nivel/transacoes/)           | A central deseja realizar uma alteração no controlador| Central | Controlador | Sempre que o operador desejar realizar uma alteração no controlador |
|[MUDANCA STATUS CONTROLADOR](/protocolos/alto_nivel/mudanca_de_status/)| O controlador avisa à central da mudança de status  | Controlador | Central | Sempre que o status dos controlador mudar |
|[LER DADOS CONTROLADOR](/protocolos/alto_nivel/ler_dados/) | A central solicita informações ao controlador | Central| Controlador | Sempre que o operador solicitar informações residentes no controlador |
|[INFO](/protocolos/alto_nivel/info/)                 | O controlador informa à central sua versão de firmware | Controlador | Central | Sempre que o controlador obtiver informações da versão do firmware|
