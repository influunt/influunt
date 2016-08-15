# Envolpagem das Mensagens

Todas as mensagens trafegadas na aplicação devem ser envolapadas conforme as definições desse documento.

## Envelope Padrão
O envelope é um padrão JSON que contém informações básicas de rastreamento da mensagem, bem como o próprio contedo da mesma. Uma mensagem sempre tem como origem ou destino um controlador específico. Dessa forma, no envelope deve conter sempre o id do controlador.

Os seguintes campos são obrigatórios no envelope:

### Campos Obrigatórios

| Campo| Tipo | Descrição |
| ------------ | ------------- | ------------ |
| tipoMensagem | string  | Código único que identifica o tipo de mensagem que está dentro do envelope. As mensagen que podem ser envelopadas estao definidas na seção [mensagens](comunicacao/mensagens.md) |
| idControlador | String GUUID | Identificador do Controlador |
| idMensagem | String GUUID  | Identificador único e global no formado GUUID que identifica unicamente essa mensagem. Deve ser gerado no momento da construção do envelope |
| destino | String  | Tópico MQTT em que a mensagem deve ser publicada. Para a lista de tópicos veja a seção [MQTT](comunicacao/mqtt) |
| qos | Inteiro | Qualidade do serviço no protocolo MQTT que deve ser empregado para esse envelope. Deve estar de acordo com os requisitos da [mensagem](comunicacao/mensagens.md) |
| carimboDeTempo | Número Longo | Carimbo de tempo referente ao momento de criação do envelope. Deve estar no formato milisegundo desde 1 de janeiro de 1970|
| conteudo | Objeto JSON ou String Criptografada representado um Objeto JSON | Mensagem a ser entregue pelo envelope. Pode ser direntamente o objeto JSON, ou nos casos onde seja requirido o criptografado e convertido para BASE64|
| emResposta | String GUUID  | Identificador único e global no formado GUUID da mensagem original para quando essa for uma resposta |



A seguir é apresentado um exemplo de envelope:

### Exemplo de Envelope

```JSON
  {
    'tipoMensagem': 'CONEXAO',
    'idControlador':'56074e06-439b-409f-b37d-4e0dc884084f',
    'idMensagem': '56074e06-439b-409f-b37d-4e0dc8840856'
    'destino': '/controladores/56074e06-439b-409f-b37d-4e0dc884084f/atualizarConfiguracoes',
    'qos': 1,
    'carimboDeTempo': '1471272798600',
    'conteudo' : {},
    'emResposta': '56074e06-439b-409f-b37d-4e0dc884084d'
  }
```

