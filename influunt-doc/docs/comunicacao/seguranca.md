# Mantendo a Segurança na Comunicação
Toda a comunicação entre os controladores e a central é criptografada. Para isso são usados dois pares de chaves assimétricas por controlador. Um par na central e o outro no controlador.

Cada mensagem é criptografada com uma chave simétrica que é criptografada pela chave privada assimétrica. 

As chaves assimétricas são geradas automaticamente no cadastro da controlador na central. Cada controlador deve conhecer sua chave privada e a chave pública da central. As chaves simétricas devem ser geradas a cada nova mensagem trafegada.

Dessa forma, toda a mensagem recebida no controlador deve ser descriptografada com a chave assímetrica após essa ser descriptografada pela chave privada assimétrica. O mesmo acontece no sentido contrário, ou seja, quando uma mensagem é recebida no controlador.

O esquema de segurança adotado pela central garante:

### Confiabilidade
Todas as informações são trafegadas criptografadas.

### Autenticidade
A comunicação controlador/central e central/controlador utiliza chaves exclusivas. Dessa forma, não é possível um controlador se passar por outro controlador ao se comunicar com a central e também não é possível que uma outra central se comunique com um controlador que não seja de sua propriedade.

### Integridade
Como o conteúdo é criptografado, qualquer alteração fará que a mensagem não consiga ser aberta.


##Envelope Seguro
Toda a comunicação em ambos os sentidos deve ser empacotada em um envelope seguro. O evelope seguro contém 3 campos: a senha, o conteúdo e o _id_ do controlador. A seguir um exemplo de um envelope seguro:


```JSON
{
  key=469caede37e5336f...adb131458c3, 
  idControlador=1d473b41-7136-4b1d-a077-3dc17828c225, 
  content=f09138a8a9113e57c30994221cb6ed...671ad94f9870d97a69cf41c3c1105189
} 
```

| Campo| Tipo | Descrição |
| ------------ | ------------- | ------------ |
| key | Testo  | Chave simétrica a ser usada para descriptografar os dados do campo _content_. Essa chave deve ser descriptogrfada com a chave privada de quem a recebeu|
| idControlador | Texto _GUUID_ | Identificador do Controlador |
| content | Texto | Um [envelope](/comunicacao/envelope) que foi criptografado com a chave do campo _key_ |

## Criptogrando um envelope
Antes de enviar uma mensagem o envelope deve ser criptografado. Os passos são os seguintes:

1. Obtém-se o _JSON_ do envelope
2. Gera-se uma nova chave simétrica
3. Criptografa-se o _JSON_ do envelope com a chave simétrica
4. Converte-se os _bytes_ resultantes da criptografia em uma _string_ de hexadecimais
5. Adiciona-se a _string_ ao campo _content_ do novo _JSON_ (envelope seguro)
6. Criptografa-se a chave simétrica com a chave pública do destinatário.
7. Converte-se os _bytes_ resultantes da criptografia em uma _string_ de hexadecimais
8. Adiciona-se a _string_ ao campo _key_ do novo _JSON_ (envelope seguro)
9. Adiciona-se o _id_ do controlador no campo _idControlador_

O envelope está seguro é pode ser enviado ao destinatário seja ele a central ou um controlador específico.
## Descriptogrando um envelope
1. Obtem-se o _JSON_ do envelope seguro
2. Converte-se o campo _key_ em um _array de bytes_
3. Descriptografa-se o campo _key_ do envelope seguro com a chave privada do destinatário
4. Converte-se o campo _content_ em um _array de bytes_
3. Utiliza-se a chave para descriptografar o campo _content_ do envelope seguro
4. O resultado conterá o envelope descriptografado com os dados que foram enviados

O envelope recebido é confiável e pode ser processado.

## Especificações das chaves e algoritmos de criptografia
### Chaves Assimétricas
Devem ser utilizadas chaves RSA de 1024 bits.
### Chaves Simétricas
Devem ser utilizadas chaves AES de 128 bits.



