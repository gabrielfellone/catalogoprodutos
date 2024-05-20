Documentação:


https://miro.com/app/board/uXjVKFpJcng=/?share_link_id=350458539026


Filas:

https://excalidraw.com/#json=C0xEqibv7LKoaFNSxgLEx,pI3CEPJMHqHz0NPTGa_Euw


Microserviços

Clientes - 
CRUD de clientes

https://github.com/gabrielfellone/clientes


Catalogo de Produtos - Onde ficam os produtos - 
Usado Spring Batch para carga na base via produtos.csv*
*Para encontrar o arquivo no seu computador, por favor, alterar na Class ProdutoBatchConfig linha 48 o diretorio onde esta o arquivo na sua maquina

https://github.com/gabrielfellone/catalogoprodutos


Gestão de pedidos - Todo processamento de pedidos - 
Foi usado junto aos outros serviços o Spring Clooud Stream para menssageria (rabbitmq)

https://github.com/gabrielfellone/pedidos


Logistica de entregas - Logica para receber os pedidos e encontrar um entregador

https://github.com/gabrielfellone/entrega





---------------

Acessar o Swagger

http://localhost:8060/swagger-ui/index.html#/ - Serviço de Entrega

http://localhost:8070/swagger-ui/index.html#/ - Serviço de Pedidos

http://localhost:8080/swagger-ui/index.html#/ - Serviço de Clientes

http://localhost:8090/swagger-ui/index.html#/ - Serviço de Produtos



---------------

Criar o rabbit docker

docker run -d --hostname local-rabbit --name rabbit-mq -p 15672:15672 -p 5672:5672 rabbitmq:3.6.9-management

docker ps

http://localhost:15672

username: guest
password: guest


Queues:

pedido-request-queue
pedido-response-sucesso-queue
pedido-response-erro-queue
pedido-cliente-queue
pedido-produto-queue
pedido-clienteentrega-queue
pedido-entregafinalizada-queue
pedido-entreganaofinalizada-queue
pedido-entregue-sucesso-queue

Exchanges:

pedido-request-exchange
pedido-response-sucesso-exchange
pedido-response-erro-exchange
pedido-cliente-exchange
pedido-produto-exchange
pedido-clienteentrega-exchange
pedido-entregafinalizada-exchange
pedido-entreganaofinalizada-exchange
pedido-entregue-sucesso-exchange



Nas exchanges associar as filas

Fila:  pedido-request-queue --> Routing key: pedido-request-rout-key
Fila:  pedido-response-sucesso-queue --> Routing key: pedido-response-sucesso-rout-key
Fila:  pedido-response-erro-queue --> Routing key: pedido-response-erro-rout-key
Fila:  pedido-cliente-queue --> Routing key: pedido-cliente-rout-key
Fila:  pedido-produto-queue --> Routing key: pedido-produto-rout-key
Fila:  pedido-clienteentrega-queue --> Routing key: pedido-clienteentrega-rout-key
Fila:  pedido-entregafinalizada-queue --> Routing key: pedido-entregafinalizada-rout-key
Fila:  pedido-entreganaofinalizada-queue --> Routing key: pedido-entreganaofinalizada-rout-key
Fila:  pedido-entregue-sucesso-queue --> Routing key: pedido-entregue-sucesso-rout-key




---------------

Criar o Cassandra

docker pull cassandra
docker run -d --name cassandra-docker -p 9042:9042 cassandra

Acessar o Cassandra

docker exec -it cassandra-docker bash

cqlsh

Criar o KeySpace

create keyspace techchallengequatro with replication={'class':'SimpleStrategy', 'replication_factor':1};

Criar as tabelas

use techchallengequatro;

CREATE TABLE cliente ( id UUID, nome text, endereco text, cep text, cpf text, email text, PRIMARY KEY (id) );
CREATE TABLE pedido ( id UUID, idCliente UUID, idProduto bigint, status text, PRIMARY KEY (id) );
CREATE TABLE entrega ( idEntregador UUID, idCliente UUID, idProduto UUID, endereco text, cep text, PRIMARY KEY (idEntregador) );
CREATE TABLE entregador ( idEntregador UUID, nome text, cep text, cpf text, PRIMARY KEY (idEntregador) );





----------------------


H2 DataBase

Acesso:

http://localhost:8090/h2-console

JDBC Name: jdbc:h2:mem:testdb


Senha: vazia 


