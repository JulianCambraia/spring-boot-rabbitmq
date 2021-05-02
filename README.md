# spring-boot-rabbitmq

### 1.  Introdução

O RabbitMQ é um message broker que propicia facilidade, onde implantar cenários avançados de roteamento, balanceamento 
de carga ou filas de mensagens persistentes são feitos em poucas linhas de código.

Por padrão o RabbitMQ utiliza o protocolo AMQP 0-9-1 para transição das suas mensagens, mas também tem suporte a outros 
protocolos como: STOMP, MQTT, AMQP 1.0 e HTTP, porém, segundo a documentação é recomendado a utilização do Rabbit com o 
protocolo padrão (AMQP 0-9-1), que é um protocolo binário com uma semântica de mensagens bastante forte.

A criação da fila pode ser feita pela tela de administração do RabbitMQ no endereço: http://localhost:15672/#/queues, no 
item “Add a new queue”, como demonstrado na imagem abaixo:

![alt text](https://github.com/julianCambraia/spring-boot-rabbitmq/blob/main/images/rabbitmq-criando-queue1.png?raw=true)