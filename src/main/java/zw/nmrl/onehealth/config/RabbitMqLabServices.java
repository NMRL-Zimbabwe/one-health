package zw.nmrl.onehealth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

//@EnableRabbit
//@Configuration
public class RabbitMqLabServices {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqLabServices.class);

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private int port;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Bean
    public org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        logger.info("Creating connection factory with: " + username + "@" + host + ":" + port);

        return connectionFactory;
    }

    /**
     * Required for executing adminstration functions against an AMQP Broker
     */
    @Bean(name = "labServices")
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    /**
     * This queue will be declared. This means it will be created if it does not exist. Once declared, you can do something
     * like the following:
     *
     * @RabbitListener(queues = "#{@myDurableQueue}")
     * @Transactional
     * public void handleMyDurableQueueMessage(CustomDurableDto myMessage) {
     *    // Anything you want! This can also return a non-void which will queue it back in to the queue attached to @RabbitListener
     * }
     */
    @Bean
    public Queue myDurableQueue() {
        // This queue has the following properties:
        // name: my_durable
        // durable: true
        // exclusive: false
        // auto_delete: false
        return new Queue("my_durable", true, false, false);
    }

    /**
     * The following is a complete declaration of an exchange, a queue and a exchange-queue binding
     */
    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange("ehr-lims", true, false);
    }

    @Bean
    public Queue inboundEmailQueue() {
        return new Queue("BRIDH", true, false, false);
    }

    @Bean
    public org.springframework.amqp.core.Binding inboundEmailExchangeBinding() {
        // Important part is the routing key -- this is just an example
        return BindingBuilder.bind(inboundEmailQueue()).to(emailExchange()).with("health.*");
    }

    @Bean
    public Queue inboundEmailQueue2() {
        return new Queue("MPILO", true, false, false);
    }

    @Bean
    public org.springframework.amqp.core.Binding inboundEmailExchangeBinding2() {
        // Important part is the routing key -- this is just an example
        return BindingBuilder.bind(inboundEmailQueue2()).to(emailExchange()).with("health.*");
    }
}
