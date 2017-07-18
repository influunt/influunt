#Influnnt

![Diagrama de Componentes](/influunt-doc/diagramas/componentes.png?raw=true "Componentes do Sistema")


## Referências

* [Manual do Usuário](/influunt-doc/manual_usuario.pdf)
* [Especificação do Protocolo de Comunicação](http://influunt.github.io)
* [Especificação da CET](influunt-doc/especificacao_cet.pdf)

## Dependências da Central
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


## Ambiente de Desenvolvimento
### Dependências
Para instalar e executar o projeto no ambiente de desevolvimento, é necessário instalar, além das [dependências da central](#dependencias-da-central), os seguintes softwares:

#### NginX
[http://nginx.org/en/download.html](http://nginx.org/en/download.html)

Se estiver em uma máquina Linux ou Mac, o NginX também pode ser instalado das seguintes formas:

Linux:

    apt-get install nginx    # Ubuntu ou Debian
    yum install nginx        # CentOS ou RedHat

Mac (Homebrew):

    brew install nginx

#### NodeJs e Bower
[https://nodejs.org/en/download/](https://nodejs.org/en/download/)

Após a instalação do NodeJs, execute o seguinte comando para instalar o Bower:

    npm install -g bower

#### Git
[https://git-scm.com/downloads](https://git-scm.com/downloads)


### Instalação

1. Baixe o projeto utilizando o git:

        git clone git@github.com:influunt/influunt.git

2. Navegue para a pasta da central, e execute o comando `activator` para baixar as dependências da central:

        cd influunt/influunt-api
        activator
Depois de todas as dependências serem baixadas e instaladas, execute o comando `exit` para sair do activator;

3. Navegue para a pasta da interface do sistema, e execute os seguintes comandos para baixar as dependências da interface:

        cd ../influunt-app
        npm install
        bower install

### Configuração

* **Configure o NginX**: Abra o arquivo `nginx.conf` (a localização depende do modo de instalação), e adicione o seguinte conteúdo no final do bloco `http`. Substitua as ocorrências de `<!CAMINHO PARA INFLUUNT-APP!>` com o caminho completo para chegar na pasta do `influunt-app`. Ex: `/Users/seunome/influunt/influunt-app`

        http{

            # ... (outras configurações)

            server {
                listen 80;
                charset UTF-8;
                server_name     localhost;
                send_timeout    600;
                gzip             on;
                gzip_comp_level  8;
                gzip_types       text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;
                client_max_body_size 100M;
                location /api {
                    try_files $uri @influuntapi;
                }
                location /bower_components {
                    root <!CAMINHO PARA INFLUUNT-APP!>;
                }
                location @influuntapi {
                    proxy_http_version 1.1;
                    proxy_pass  http://localhost:9000;
                    proxy_redirect off;
                    proxy_buffering off;
                    proxy_connect_timeout       600;
                    proxy_send_timeout          600;
                    proxy_read_timeout          600;
                    proxy_set_header        Host               $host;
                    proxy_set_header        X-Real-IP          $remote_addr;
                    proxy_set_header        X-Forwarded-For    $proxy_add_x_forwarded_for;
                }
                location / {
                    root <!CAMINHO PARA INFLUUNT-APP!>/app/;
                    index  index.html;
                }
                location /assets {
                    proxy_pass  http://localhost:9000;
                }
            }
        }

* **Configure o MySQL**: para configurar o mysql é necessário criar um banco de dados chamado `influuntdev`. Qualquer programa gerenciador de banco de dados pode ser usado para isso. Pela linha de comando, entre no _shell_ do MySQL e digite o seuginte comando:

        mysql> create database influuntdev;
OBS: O ambiente de desenvolvimento espera que o MySQL tenha o usuário `root` sem senha.

* **Configure o Mosquitto**: abra o arquivo `mosquitto.conf` (a localização do arquivo depende de como o mosquitto foi instalado), e altere as seguintes propriedades:
    * Na seção "Default Listeners" altere as propriedades `bind_address`, `port` e `protocol`

            bind_address 127.0.0.1
            port 1883
            protocol mqtt

    * Na seção "Extra Listeners" altere as propriedades `listener` e `protocol`

            listener 1884
            protocol websockets

    * Na seção "Persistence" altere a propriedade `persistence`

            persistence false

### Execução

Execute os seguintes comandos para rodar o projeto:

* Inicie o MySQL (se ainda não estiver em execução)
* Inicie o NginX (se ainda não estiver em execução)
* Inicie o MongoDB. No terminal, o comando é (dependendo da instalação pode ser necessário usar `sudo`):

        mongod

* Inicie o mosquitto:

        mosquitto -c caminho/para/mosquitto.conf

* Inicie a central:

    1. Navegue para a pasta do projeto, e depois entre na pasta `influunt-api`
    2. Rode o seguinte comando:

            activator run

* Abra o navegador Chrome e vá para o endereço `http://localhost:9000`. Irá aparecer na tela uma mensagem dizendo que a _database_ necessita de evolução. Clique no botão `Apply this script now!` para aplicar as evoluções. **Somente na primeira vez em que executar o projeto**

* Aplique o arquivo com os dados iniciais do banco de dados. O arquivo chama-se `influunt_seed.sql` e fica no root do projeto. **Somente na primeira vez em que executar o projeto**

        mysql -u root influuntdev < influunt_seed.sql

* Inicie o app web:

    1. Entre na pasta do projeto, e depois entre na pasta `influunt-app`
    2. Rode o seguinte comando:

            gulp serve

* Coloque no navegador o endereço `http://localhost`. O Influunt deve abrir e mostrar a tela de login. Para entrar utiliz o login **root** e a senha **1234**.

## Ambiente de Produção (único servidor)
### Dependências

As dependências do ambiente de produção são as mesmas do ambiente de desenvolvimento. O passo-a-passo abaixo irá mostrar como configurar um servidor linux (CentOS) de produção para rodar a aplicação.

### Configurar servidor de produção

**O passo-a-passo abaixo se refere a um servidor linux CentOS!**

Primeiramente entre no servidor com o usuário root.

#### Java 8

Baixe o arquivo RPM de instalação do Java em [http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) e salve no servidor. Execute o seguinte comando para instalar (substituindo o nome do arquivo pelo o nome do que você baixou):

    rpm -ivh jdk-8uxxx-linux-x64.rpm


#### MySQL

Primeiramente, instale a versão aberta do MySQL:

    yum install mariadb-server

Em seguida rode o comando para deixar a instalação segura (todas as opções sugeridas pelo programa são seguras de serem aceitas):

    mysql_secure_installation

Entre no MySQL com o usuário `root`, utilizando a senha cadastrada no comando anterior, e crie um novo banco de dados:

    CREATE DATABASE influunt;

Crie um novo usuário para acessar o banco de dados da aplicação, alterando o valor `<SENHA>` por uma senha da sua preferência:

    CREATE USER 'influunt'@'localhost' IDENTIFIED BY '<SENHA>';
    GRANT ALL PRIVILEGES ON influunt.* TO 'influunt'@'localhost';


#### MongoDB

Crie o arquivo `/etc/yum.repos.d/mongodb-org-3.4.repo` com o seguinte conteúdo:

    [mongodb-org-3.4]
    name=MongoDB Repository
    baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/3.4/x86_64/
    gpgcheck=1
    enabled=1
    gpgkey=https://www.mongodb.org/static/pgp/server-3.4.asc

Em seguida, instale o mongoDB:

    yum install mongodb-org

Inicie o mongoDB:

    mongod

Configure o mongoDB para iniciar automaticamente:

    systemctl enable mongod


#### eMQTT (MQTT Broker)

Para instalar o eMQTT, entre no site e baixe o arquivo `.zip` de acordo com o servidor: [http://emqtt.io/downloads](http://emqtt.io/downloads). faça o upload do arquivo para o servidor, e descompacte o arquivo zip.

Entre na pasta descompactada, e altere o seguintes valores no arquivo `etc/emq.conf`:

Na seção "Node Args", altere as seguintes configurações:

* `node.name = emqttd@<IP_DO_SERVIDOR>`

Na seção "MQTT Session", altere as seguintes configurações:

* `mqtt.session.max_inflight = 1000`

Na seção "MQTT Listeners", altere as seguintes configurações:

* `mqtt.listener.tcp = 1883`
* `mqtt.listener.tcp.max_clients = 4096`
* `mqtt.listener.http = 1884`

Após alterar as configurações, crie o arquivo `etc/emq_auth_http.conf` com o seguinte conteúdo (substituindo `<IP_DO_SERVIDOR>` pelo IP do servidor):

    ##--------------------------------------------------------------------
    ## HTTP Auth/ACL Plugin
    ##--------------------------------------------------------------------

    ## Variables: %u = username, %c = clientid, %a = ipaddress, %P = password, %t = topic, %A access (2 - publish, 1 - subscribe)

    auth.http.auth_req = http://<IP_DO_SERVIDOR>/api/v1/mqtt/auth
    auth.http.auth_req.method = post
    auth.http.auth_req.params = clientId=%c,username=%u,password=%P

    auth.http.super_req = http://<IP_DO_SERVIDOR>:8080/mqtt/superuser
    auth.http.super_req.method = post
    auth.http.super_req.params = clientid=%c,username=%u

    ## 'access' parameter: sub = 1, pub = 2
    auth.http.acl_req =  http://<IP_DO_SERVIDOR>/api/v1/mqtt/acl
    auth.http.acl_req.method = get
    auth.http.acl_req.params = username=%u,clientId=%c,topic=%t,password=%P,access=%A

    auth.http.acl_nomatch = deny


Rode o eMQTT:

    bin/emqttd

Depois que o eMQTT estiver rodando, execute o seguinte comando para habilitar o plugin de autenticação:

    bin/emqttd_ctl plugins load emq_auth_http

  **OBS:** Depois de instalado, é necessário alterar o arquivo de configuração dos controladores (no seu computador). Esse arquivo está em `influunt/influunt-app/app/resources/controlador.conf`. Basta alterar a chave `host` (endereço) para o endereço onde o eMQTT está instalado.

#### NginX

Instale o NginX:

    yum install nginx

Configure o NginX para servir a aplicação. Abra o arquivo `/etc/nginx/nginx.conf` e adicione o bloco `server` abaixo dentro do bloco `http`:

    http {
        # outras configurações...

        server {
            listen 80;
            charset UTF-8;
            server_name     influunt.com.br;
            send_timeout    600;
            gzip             on;
            gzip_comp_level  8;
            gzip_types       text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;
            client_max_body_size 100M;
            location /api {
                try_files $uri @influuntapi;
            }
            # the notification server
            location @influuntapi {
                proxy_http_version 1.1;
                proxy_pass  http://localhost:9000;
                proxy_redirect off;
                proxy_buffering off;
                proxy_connect_timeout       600;
                proxy_send_timeout          600;
                proxy_read_timeout          600;
                proxy_set_header        Host               $host;
            }
            location /assets {
                proxy_pass  http://localhost:9000;
            }
            location / {
                root   /app/influunt-app/current;
                index  index.html;
            }
        }

    }

Inicie o NginX com o comando

    nginx

#### Usuário para deploy

Crie um novo usuário e desabilite o seu login:

    adduser raro
    passwd -l raro

Em seguida adicione a sua chave SSH pública no servidor, executando os seguintes comandos (substitua `CHAVE_SSH_PUBLICA` pela sua chave SSH pública):

    su - raro
    cd ~
    mkdir .ssh
    echo CHAVE_SSH_PUBLICA >> .ssh/authorized_keys
    chmod 700 .ssh
    chmod 600 .ssh/authorized_keys
    exit

#### Estrutura de pastas do app

**Os nomes de pastas utilizados são muito importantes para a configuração do servidor web, não modifique.**

Execute os seguintes comandos para criar a estrutura de pastas da central e do app web.

Configuração de pastas da central:

    deploy_to=/app/influunt-api
    mkdir -p ${deploy_to}
    chown raro:raro ${deploy_to}
    umask 0002
    chmod g+s ${deploy_to}
    mkdir ${deploy_to}/{releases,shared}
    mkdir ${deploy_to}/shared/{imagens,logs}
    chown -R raro ${deploy_to}/{releases,shared}

Configuração de pastas do app web:

    deploy_to=/app/influunt-app
    mkdir -p ${deploy_to}
    chown raro:raro ${deploy_to}
    umask 0002
    chmod g+s ${deploy_to}
    mkdir ${deploy_to}/{releases,shared}
    chown raro ${deploy_to}/{releases,shared}

#### Configuração do app

Crie o arquivo de configuração da central, e altere os valores necessários. Primeiro copie o arquivo `influunt/influunt-api/conf/application.conf` do seu computador para o servidor, e o coloque na pasta `/app/influunt-api/shared/conf`. Altere os seguintes valores:

    play.evolutions {
      enabled = true
      autoApply = true
      autoApplyDowns = true
    }

    db {
      default.driver=com.mysql.jdbc.Driver
      default.url="jdbc:mysql://localhost/influunt"
      default.username=influunt
      default.password="SENHA"                # <--- substitua SENHA pela senha escolhida para o usuário influunt no banco de dados

      default.logSql=true
      default.jndiName=DefaultDS
    }

    playjongo.uri="mongodb://127.0.0.1:27017/influunt"

    play.mailer {
        host = smtp.gmail.com                 # <--- Substitua pelo seu host do seu serviço de email
        port = 587
        user = USUARIO_DO_EMAIL               # <--- Substitua pelo seu usuário de email
        password = "SENHA_DO_EMAIL"           # <--- Substitua pela senha do seu usuário de email
        from = "naoresponda@rarolabs.com.br"  # <--- Substitua pelo seu email
        tls = yes
        ssl = no
    }

    influuntUrl = "ENDERECO SERVIDOR"         # <--- Substitua pelo endereço do servidor ex: http://influunt.com.br

    central {
        mqtt {
            host = "SERVIDOR MQTT"            # <--- Substitua esse valor pelo endereço onde o Mosquitto foi instalado.
            port = 1883
            login = ""
            senha = "SENHA DE ACESSO DA CENTRAL" # <--- Coloque uma senha para a central
            acl = {
                device = {
                    publish: ["controladores/conn/online", "controladores/conn/offline", "central/transacoes/$USERNAME/+", "central/alarmes_falhas", "central/troca_plano", "central/configuracao", "central/mudanca_status_controlador", "central/info"],
                    subscribe: ["controlador/$USERNAME/+"]
                }
                simulador_web = {
                    publish: ["simulador/$USERNAME/morreu", "simulador/$USERNAME/proxima_pagina", "simulador/$USERNAME/detector", "simulador/$USERNAME/alternar_modo_manual", "simulador/$USERNAME/trocar_estagio"],
                    subscribe: ["simulador/$USERNAME/estado"]
                }
                simulador_api = {
                    publish: ["simulador/$USERNAME/estado", "simulador/$USERNAME/morreu"],
                    subscribe: ["simulador/$USERNAME/proxima_pagina", "simulador/$USERNAME/detector", "simulador/$USERNAME/alternar_modo_manual", "simulador/$USERNAME/trocar_estagio"]
                }
                app = {
                    subscribe: ["app/controlador/+/dados", "app/transacoes/+/status", "app/conn/offline", "app/conn/online", "app/troca_plano", "app/alarmes_falhas", "app/mudanca_status_controlador"],
                    publish: ["central/app/transacoes/+"]
                }
            }
        }
    }

Após as alterações, adicione os seguintes valores no final do arquivo:

    application.mode=PROD
    pidfile.path = "/app/influunt-api/shared/influunt.pid"


## Ambiente de produção (vários servidores)

É possível configurar o app em vários servidores, para uma melhor performance. Para essa configuração, são necessários pelo menos 5 servidores:

* 2 servidores para a API (podem ser adicionados mais servidores)
* 1 servidor para a CENTRAL (a central **deve** funcionar em somente 1 servidor)
* 1 servidor de DADOS
* 1 servidor para servir a aplicação WEB

As dependências são as mesmas do ambiente de produção com somente um servidor, mas com essa configuração os servidores devem ser configurados individualmente.

### Configuração

#### Java 8

O Java 8 deve ser instalado em todos os servidores para a API e no servidor para a CENTRAL. As intruções de instalação são as mesmas do ambiente de produção com somente um servidor.

#### MySQL

O MySQL deve ser instalado no servidor de DADOS. As intruções de instalação são as mesmas do ambiente de produção com somente um servidor, com uma diferença: devem ser rodados dois comandos a mais:

    CREATE USER 'influunt'@'%' IDENTIFIED BY '<SENHA>';
    GRANT ALL PRIVILEGES ON influunt.* TO 'influunt'@'&';

Lembrando que o valor `<SENHA>` deve ser substituído pela mesma senha cadastrada no passo anterior.

#### MongoDB

O MongoDB deve ser instalado no servidor de DADOS. As intruções de instalação são as mesmas do ambiente de produção com somente um servidor.

#### eMQTT

O eMQTT deve ser instalado somente no servidor da CENTRAL. As intruções de instalação são as mesmas do ambiente de produção com somente um servidor. A diferença está na configuração do plugin de autenticação.

Ao criar o arquivo de confiugração do plugin, ao invés de substituir o valor `<IP_DO_SERVIDOR>` pelo IP do servidor onde o eMQTT está instalado, deve ser substituído pelo IP do servidor WEB.

#### NginX

O MongoDB deve ser instalado no servidor WEB. As intruções de instalação são as mesmas do ambiente de produção com somente um servidor, mas a configuração é diferente.

Abra o arquivo `/etc/nginx/nginx.conf` e adicione os seguintes blocos `server` e `upstream` abaixo dentro do bloco `http`

    http {
        # outras configurações...


        upstream influuntapi {
            server <IP API 1>:9000; # API-01
            server <IP API 2>:9000; # API-02
        }

        server {
            listen 80;
            charset UTF-8;
            server_name     influunt.com.br;
            send_timeout    600;
            gzip             on;
            gzip_comp_level  8;
            gzip_types       text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;

            client_max_body_size 100M;

            location /assets {
                proxy_http_version 1.1;
                proxy_redirect off;
                proxy_buffering off;
                proxy_connect_timeout       600;
                proxy_send_timeout          600;
                proxy_read_timeout          600;
                proxy_pass  http://influuntapi;
            }

            location /api {
                proxy_http_version 1.1;
                proxy_redirect off;
                proxy_buffering off;
                proxy_connect_timeout       600;
                proxy_send_timeout          600;
                proxy_read_timeout          600;
                proxy_pass  http://influuntapi;
                proxy_set_header Host "IP DESSE SERVIDOR"; # <--- Substitua pelo IP desse servidor (load balancer)
            }

            location / {
                root   /app/influunt-app/current;
                index  index.html;
            }
        }


    }

#### Usuário para deploy

Deve ser criado um usuário para realizar o deploy em cada um dos servidores. As instruções para criar os usuários são as mesmas do ambiente de produção com somente um servidor.

Há uma diferença, porém: atualmente a aplicação está configurada para o deploy em produção com vários servidores usando o usuário "cet" (nas outras instruções foi criado o usuário "raro"). Para não precisar fazer mais alterações, o usuário deve ser criado como "cet".

#### Estrutura de pasta do app

Execute os seguintes comandos nos servidores para a API:

    deploy_to=/app/influunt-api
    mkdir -p ${deploy_to}
    chown cet:cet ${deploy_to}
    umask 0002
    chmod g+s ${deploy_to}
    mkdir ${deploy_to}/{releases,shared}
    mkdir ${deploy_to}/shared/{imagens,logs}
    chown -R cet ${deploy_to}/{releases,shared}

Execute os seguintes comandos no servidor para a CENTRAL:

    deploy_to=/app/influunt-central
    mkdir -p ${deploy_to}
    chown cet:cet ${deploy_to}
    umask 0002
    chmod g+s ${deploy_to}
    mkdir ${deploy_to}/{releases,shared}
    mkdir ${deploy_to}/shared/{imagens,logs}
    chown -R cet ${deploy_to}/{releases,shared}

Execute os seguintes comandos no servidor WEB:

    deploy_to=/app/influunt-app
    mkdir -p ${deploy_to}
    chown cet:cet ${deploy_to}
    umask 0002
    chmod g+s ${deploy_to}
    mkdir ${deploy_to}/{releases,shared}
    chown cet ${deploy_to}/{releases,shared}

#### Configuração do app

Para a configuração do app é necessário configurar a API e a CENTRAL.
Para configurar a API, primeiramente copie o arquivo `influunt/influunt-api/modules/influunt-api/conf/api.conf` do seu computador para os servidores API, e o coloque na pasta `/app/influunt-api/shared/conf`. Altere os seguintes valores:

    play.evolutions {
      enabled = true
      autoApply = true
      autoApplyDowns = true
    }

    db {
      default.driver=com.mysql.jdbc.Driver
      default.url="jdbc:mysql://IP_SERVIDOR_BD/influunt" # <--- substitua IP_SERVIDOR_BD pelo IP do servidor de banco de dados
      default.username=influunt
      default.password="SENHA"             # <--- substitua SENHA pela senha escolhida para o usuário influunt no banco de dados

      default.logSql=true
      default.jndiName=DefaultDS
    }

    playjongo.uri="mongodb://IP_SERVIDOR_BD:27017/influunt" # <--- substitua IP_SERVIDOR_BD pelo IP do servidor de banco de dados

    play.mailer {
        host = smtp.gmail.com
        port = 587
        user = USUARIO_DO_EMAIL            # <--- Substitua pelo seu usuário no gmail
        password = "SENHA_DO_EMAIL"        # <--- Substitua pela senha do seu usuário no gmail
        from = "naoresponda@rarolabs.com.br"
        tls = yes
        ssl = no
    }

    influuntUrl = "ENDERECO SERVIDOR"      # <--- Substitua pelo endereço do servidor WEB

    central {
        mqtt {
            host = "SERVIDOR MQTT"         # <--- Substitua esse valor pelo endereço onde o EMQTT foi instalado.
            port = 1883
            login = ""
            senha = "<SENHA DE ACESSO DA CENTRAL>" # <--- Coloque uma senha para a central
            acl = {
                device = {
                    publish: ["controladores/conn/online", "controladores/conn/offline", "central/transacoes/$USERNAME/+", "central/alarmes_falhas", "central/troca_plano", "central/configuracao", "central/mudanca_status_controlador", "central/info"],
                    subscribe: ["controlador/$USERNAME/+"]
                }
                simulador_web = {
                    publish: ["simulador/$USERNAME/morreu", "simulador/$USERNAME/proxima_pagina", "simulador/$USERNAME/detector", "simulador/$USERNAME/alternar_modo_manual", "simulador/$USERNAME/trocar_estagio"],
                    subscribe: ["simulador/$USERNAME/estado"]
                }
                simulador_api = {
                    publish: ["simulador/$USERNAME/estado", "simulador/$USERNAME/morreu"],
                    subscribe: ["simulador/$USERNAME/proxima_pagina", "simulador/$USERNAME/detector", "simulador/$USERNAME/alternar_modo_manual", "simulador/$USERNAME/trocar_estagio"]
                }
                app = {
                    subscribe: ["app/controlador/+/dados", "app/transacoes/+/status", "app/conn/offline", "app/conn/online", "app/troca_plano", "app/alarmes_falhas", "app/mudanca_status_controlador"],
                    publish: ["central/app/transacoes/+"]
                }
            }
        }
    }

Após as alterações, adicione os seguintes valores no final do arquivo:
    application.mode=PROD
    pidfile.path = "/app/influunt-api/shared/influunt.pid"
    play.http.router = "api.Routes"


Para configurar a CENTRAL, devem ser seguidos os mesmos passos da API, com as seguintes modificações:

* o arquivo que deve ser copiado para o servidor é o `influunt/influunt-api/modules/influunt-central/conf/central.conf`
* As configurações a serem adicionadas no final do arquivo são:

        application.mode=PROD
        pidfile.path = "/app/influunt-api/shared/influunt.pid"
        play.http.router = "central.Routes"

### Deploy da aplicação

Para realizar o deploy (atualizar o código no servidor), primeiramente instale o ruby no seu computador ([Instruções de Instalação](https://www.ruby-lang.org/en/documentation/installation/)).

Em seguida execute o seguinte comando para instalar a ferramenta de deploy:

```bash
gem install capistrano -v 3.5.0
```

#### Deploy em ambiente de produção com somente um servidor:

Altere os arquivos (no seu computador) `influunt/influunt-api/conf/capistrano/deploy/production.rb` e `influunt/influunt-app/config/deploy/production.rb`, e insira o endereço do servidor de produção no local apropriado de cada arquivo:

```ruby
server 'xxx.xxx.xxx.xxx', user: 'raro', roles: %w{app db web}
```

Para executar o deploy, primeiro entre na pasta `influunt/influunt-api` e execute o comando:

    cap production deploy

Em outra janela do terminal, entre na pasta `influunt/influunt-app` e execute o mesmo comando novamente:

    cap production deploy


Se este for o primeiro deploy feito no servidor, é necessário inserir o arquivo de seed no banco de dados. Para isso, faça o upload do arquivo `influunt/influunt-api/influunt_seed.sql` para o servidor, e execute o arquivo no Mysql:

    mysql -u root -p influunt < influunt_seed.sql

#### Deploy em ambiente de produção com vários servidores

Altere os seguintes arquivos (no seu computador):

`influunt/influunt-api/modules/influunt-api/conf/capistrano/deploy/production.rb`

* insira os endereços dos servidores da API, uma linha para cada um:

        server 'xxx.xxx.xxx.xxx', user: 'cet', roles: %w{app db web}

`influunt/influunt-api/modules/influunt-central/conf/capistrano/deploy/production.rb`

* insira o endereço do servidor da CENTRAL:

        server 'xxx.xxx.xxx.xxx', user: 'cet', roles: %w{app db web}

`influunt/influunt-app/config/deploy/production.rb`

* insira o endereço do servidor WEB:

        server 'xxx.xxx.xxx.xxx', user: 'cet', roles: %w{app db web}


Para executar o deploy em vários servidores, primeiro entre na pasta `influunt/influunt-api` e execute o comando `activator dist`.

Em seguida, entre na pasta `influunt/influunt-api/modules/influunt-api` e execute o comando:

    cap production deploy

Em outra janela do terminal, entre na pasta `influunt/influunt-api/modules/influunt-central` e execute o comando:

    cap production deploy

Por último, em outra janela do terminal, entre na pasta `influunt/influunt-app` e execute o mesmo comando novamente:

    cap production deploy

Se este for o primeiro deploy feito no servidor, é necessário inserir o arquivo de seed no banco de dados. Para isso, faça o upload do arquivo `influunt/influunt-api/influunt_seed.sql` para o servidor DADOS, e execute o arquivo no MySQL:

    mysql -u root -p influunt < influunt_seed.sql




## 72c

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

Após a instalação execute o comando baixo para parar a execução do 72c.

```bash
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

