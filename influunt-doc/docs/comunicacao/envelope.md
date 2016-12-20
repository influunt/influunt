# Envelopagem das Mensagens

Todas as mensagens trafegadas na aplicação devem ser envelopadas conforme as definições desse documento.

## Envelope Padrão
O envelope é um padrão _JSON_ que contém informações básicas de rastreamento da mensagem, bem como seu próprio conteúdo. Uma mensagem sempre tem como origem ou destino um controlador específico. Dessa forma, o envelope deve conter sempre o _id_ do controlador.

Os seguintes campos são obrigatórios no envelope:

### Campos Obrigatórios

| Campo| Tipo | Descrição |
| ------------ | ------------- | ------------ |
| tipoMensagem | Texto  | Código único que identifica o tipo de mensagem que está dentro do envelope. As mensagens que podem ser envelopadas estão definidas na seção [mensagens](comunicacao/mensagens.md) |
| idControlador | UUID | Identificador do Controlador |
| idMensagem | UUID  | Identificador único e global no formato _UUID_ que identifica unicamente essa mensagem. Deve ser gerado no momento da construção do envelope |
| destino | Texto  | Tópico _MQTT_ em que a mensagem deve ser publicada. Para lista de tópicos veja a seção [MQTT](comunicacao/mqtt) |
| qos | Inteiro | Qualidade do serviço no protocolo _MQTT_ que deve ser empregado para esse envelope. Deve estar de acordo com os requisitos da [mensagem](comunicacao/mensagens.md) |
| carimboDeTempo | Número Longo | Carimbo de tempo referente ao momento de criação do envelope. Deve estar no formato milissegundo desde 1 de janeiro de 1970 UTC|
| conteudo | Objeto _JSON_ ou Texto Criptografado representando um Objeto _JSON_ | Mensagem a ser entregue pelo envelope. Pode ser diretamente o objeto _JSON_ ou, nos casos onde seja requerido, criptografado e convertido para _BASE64_|
| emResposta | UUID | Identificador único e global no formato _UUID_ da mensagem original para quando essa for uma resposta |



A seguir é apresentado um exemplo de envelope:

### Exemplo de Envelope

```JSON
  {
    "tipoMensagem": "CONEXAO",
    "idControlador":"56074e06-439b-409f-b37d-4e0dc884084f",
    "idMensagem": "56074e06-439b-409f-b37d-4e0dc8840856"
    "destino": "/controladores/56074e06-439b-409f-b37d-4e0dc884084f/atualizarConfiguracoes",
    "qos": 1,
    "carimboDeTempo": "1471272798600",
    "conteudo" : {},
    "emResposta": "56074e06-439b-409f-b37d-4e0dc884084d"
  }
```

