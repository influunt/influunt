#Influnnt

![Diagrama de Componentes](/influunt-doc/diagramas/componentes.png?raw=true "Componentes do Sistema")

## Referências

* [Manual do Usuário](/influunt-doc/manuais/manual_do_usuario.pdf)
* [Especificação do Protocolo de Comunicação](http://influunt.github.io)
* [Especificação da CET](influunt-doc/manuais/especificacao_cet.pdf)

## Dependências
Para instar a central é necessário instalar as seguintes dependências:
### JDK 1.8
[http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
### MongoDB
[https://www.mongodb.com/download-center#community](https://www.mongodb.com/download-center#community)
### MySQL
[http://dev.mysql.com/downloads/mysql/](http://dev.mysql.com/downloads/mysql/)

### Mosquitto
[https://mosquitto.org/download/](https://mosquitto.org/download/)

### Activator
[https://www.lightbend.com/activator/download](https://www.lightbend.com/activator/download)


### Ambiente de Desenvolvimento
#### Dependências
### Ambiente de Produção
#### Dependências
### 72c

O 72c pode ser executado em qualquer sistema operacional que suporte uma máquina virtual java (JVM) standard edition (SE) 1.8 ou superior.
Dessa forma, antes de instalar o 72c certifique-se que a JVM está instalada e a versão é compatível:
```bash
java -version
java version "1.8.0_102"
Java(TM) SE Runtime Environment (build 1.8.0_102-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.102-b14, mixed mode)
```

Se você estiver utilizando uma versão de linux compatível com gerenciador de pacotes do debian ou rpm, você poderá instalar diretamente os binários:

Baixe o pacote binário correspondente à versão do influunt que deseja instalar. [Download](https://github.com/influunt/influunt/releases)

No caso do debian, instale o pacote no sistema operacional utilizando o comando *dpkg -i*:
```bash
sudo dpkg -i 72c_linux_1_0.deb
```

Para instalar o pacote RPM utilize o seguinte comando:
```bash
rpm -ivh 72c_linux_1_0.rpm
```
Após a instalação execute o comando baixo para parar a execução do 72c.

```
sudo /etc/init.d/72c stop
```

Edite o arquivo de configurações de acordo com sua necessidade:
```bash
sudo vim /opt/72c/conf/application.conf`
```

Execute o 72c em foreground para verificar se sua configuração é válida.
```bash
sudo /etc/init.d/72c run
```
Se o 72c executou com sucesso. Pare o processo com o comando ctrl+c e inicie como um serviço do sistema operacional:
```bash
sudo /etc/init.d/72c start
```
Nesse caso, o 72c será iniciado automaticamente quando o sistema operacional for reinciado.

#### Protótipo 72c com Raspberry PI + Arduino
Para montar um prótipo de controlador utilizando o Raspberry PI em conjunto com uma placa Arduino siga as seguintes instruções:

##### Preparando o Arduino
1. Monte seu protótipo de acordo com esse [schema](https://github.com/influunt/influunt/blob/staging/influunt-arduino/influunt_schema.pdf)
2. Instale o [Arduino IDE](https://www.arduino.cc/en/Main/Software)
3. Abra o projeto influunt.ino no Arduino IDE. O projeto encontra-se na pasta /influunt-arduino
4. Conecte o ardunino com o cabo USB e faça o upload do arquivo binário.

##### Preparando o Raspberry PI
1. Instale o [raspbian](https://www.raspbian.org/) no cartão de memória
2. Configure o acesso a internet

##### Conectando o PI + Arduino
1. Conecte a porta USB do PI no Arduino
2. Verifique qual porta serial foi criada no sistema operacional (```ls /dev/tty*```)
3. Instale o 72c conforme orientação anterior. Utilize o pacote .deb
4. No arquivo de configuração do arquivo configure a porta serial com o valor obtido no item 2 e o baud rate para 9600.
5. Inicie o 72c

##Status
##### Master
[![Build Status](https://travis-ci.org/influunt/influunt.svg?branch=master)](https://travis-ci.org/influunt/influunt)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9da5998d9e6545d49783688b9af4ad75?branch=master)](https://www.codacy.com/app/Influunt/influunt?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=influunt/influunt&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/9da5998d9e6545d49783688b9af4ad75?branch=master)](https://www.codacy.com/app/Influunt/influunt?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=influunt/influunt&amp;utm_campaign=Badge_Coverage)

##### Staging
[![Build Status](https://travis-ci.org/influunt/influunt.svg?branch=staging)](https://travis-ci.org/influunt/influunt)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9da5998d9e6545d49783688b9af4ad75?branch=staging)](https://www.codacy.com/app/Influunt/influunt?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=influunt/influunt&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/9da5998d9e6545d49783688b9af4ad75?branch=staging)](https://www.codacy.com/app/Influunt/influunt?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=influunt/influunt&amp;utm_campaign=Badge_Coverage)

