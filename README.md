# Prova de Conceito Springboot 2.5 RabbitMQ 

### 1.  Introdução

O RabbitMQ é um message broker que propicia facilidade, onde implantar cenários avançados de roteamento, balanceamento 
de carga ou filas de mensagens persistentes são feitos em poucas linhas de código.

Por padrão o RabbitMQ utiliza o protocolo AMQP 0-9-1 para transição das suas mensagens, mas também tem suporte a outros 
protocolos como: STOMP, MQTT, AMQP 1.0 e HTTP, porém, segundo a documentação é recomendado a utilização do Rabbit com o 
protocolo padrão (AMQP 0-9-1), que é um protocolo binário com uma semântica de mensagens bastante forte.

A criação da fila pode ser feita pela tela de administração do RabbitMQ no endereço: http://localhost:15672/#/queues, no 
item “Add a new queue”, como demonstrado na imagem abaixo:

![alt text](https://github.com/julianCambraia/spring-boot-rabbitmq/blob/main/images/rabbitmq-criando-queue1.png?raw=true)

#### 1.1 Observação
 
O plugin de administração visual nem sempre está ativo na instalação, utilizaremos um dcoker para subir uma imagem simples
do RabbitMQ. A forma mais simples de se fazer seria esta aqui:

```docker run -d --hostname my-rabbit --name rabbit13 -p 8080:15672 -p 5672:5672 -p 25676:25676 rabbitmq:3-management```

``` 
user:  guest 
senha: guest 
```

![alt text](https://github.com/julianCambraia/spring-boot-rabbitmq/blob/main/images/login-rabitmq.png?raw=true)


#### 1.2 Dependências e Propriedades
 
A dependência do RabbitMQ para o Spring Boot é o spring-boot-starter-amqp, que apesar do nome genérico traz funcionalidades 
do RabbitMQ.

![alt text](https://github.com/julianCambraia/spring-boot-rabbitmq/blob/main/images/dependencia-rabbitmq.png?raw=true)

##### 1.2.1 application.properties

![alt text](https://github.com/julianCambraia/spring-boot-rabbitmq/blob/main/images/application.properties.png?raw=true)

##### 1.2.2 Enviando Dados para Fila do RabbitMQ. 
Após configurado, a Queue pode ser inicializada através de um @Bean na classe de configuração do Spring Boot, assim 
centralizando e possibilitando injeção do objeto que representa a fila.

![alt text](https://github.com/julianCambraia/spring-boot-rabbitmq/blob/main/images/sender-app.png?raw=true)

##### 1.2.3 Enviando e Consumindo dados no RabbitMQ

Realizado os dois passos anteriores, a aplicação está pronta para enviar e consumir dados de filas no RabbitMQ. 
Vamos iniciar pelo envido de dados para fila, criando a classe OrderQueueSender, que tem como objetivo conectar no RabbitMQ 
e enviar mensagens para fila.

![alt text](https://github.com/julianCambraia/spring-boot-rabbitmq/blob/main/images/order-sender.png?raw=true)

* RabbitTemplate: É uma classe helper para acessar e enviar mensagens para RabbitMQ de forma síncrona;
* Queue: É um objeto que representa a fila configurada;
* rabbitTemplate.convertAndSend: Método possui recebe os parâmetro: routingKey e message, onde são o nome da filea e a 
mensagem a ser enviada.

Com isso, a cada chamada do método send, será enviado uma mensagem para fila, no caso do exemplo acima, o pedido(order) 
será enviando no corpo da mensagem para o RabbitMQ na fila OrderQueue.

##### 2. Consumindo Dados da Fila do RabbitMQ.
 
Para consumir a fila, a dependência do spring-boot-starter-amqp, disponibiliza a anotação @RabbitListener que recebe como 
parâmetro um array de String, que são os nomes da filas que serão consumidas, dessa forma, quando inicializado a aplicação 
o método anotado começará a consumir a fila.

![alt text](https://github.com/julianCambraia/spring-boot-rabbitmq/blob/main/images/order-consumer.png?raw=true)

* @Component: O consumer deve ser um bean mapeado no Spring, por isso anotado como componente;
* @RabbitListener: É a anotação que marca o método como um listener;
* @Payload: É a anotação que informa que o parâmetro vai receber o corpo da mensagem. Observação: não é obrigatório quando tem apenas um parâmetro.

##### 2.1. Consumindo Uma única fila de Dados do RabbitMQ.
Por padrão, a anotação @RabbitListener instância apenas um consumer, ou seja, não trabalha com concorrência na leitura da 
fila, caso seja necessário mudar esse comportamento, configurar as propriedades 
``spring.rabbitmq.listener.simple.concurrency`` e ``spring.rabbitmq.listener.simple.max-concurrency`` no application.properties.

E pra fechar esta simples (Poc - Prova de Conceito)
O único pré-requisito para utilizar essa anotação é ativar o Rabbit na aplicação, utilizando a anotação 
```@EnableRabbit na classe de configuração.``` 

![alt text](https://github.com/julianCambraia/spring-boot-rabbitmq/blob/main/images/consumer-enable-rabbitmq.png?raw=true)

Concluindo, o RabbitMQ é uma boa alternativa de message broker, pois além de simples de utilizar e configurar em conjunto 
com o Spring Boot, também apresenta um bom desempenho e com possibilidade de configurações para atender alta escalabilidade.