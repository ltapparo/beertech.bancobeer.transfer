package com.br.beertech.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableRabbit
public class RabbitConfig {

  @Bean
  public ApplicationRunner runner(
      final RabbitListenerEndpointRegistry registry, final AmqpAdmin admin) {

    return args -> {
      registry.start();
    };
  }

  @Bean
  public TopicExchange operacaoTopicExchange(){
    return new TopicExchange("operacao.exchange",true,false);
  }

  @Bean
  public TopicExchange operacaoDlqTopicExchange(){
    return new TopicExchange("operacao.exchange-dlq",true,false);
  }


  @Bean
  public Declarables declarablesBean(){
    List<Declarable> declarables = createOperacaoQueueDeclarables();
    declarables.addAll(createTransferenciaQueueDeclarables());

    return new Declarables(declarables);
  }

  private List<Declarable> createOperacaoQueueDeclarables() {
    List<Declarable> declarables = new ArrayList<>();

    Queue operacaoQueue = QueueBuilder.durable("operacao").build();
    Binding operacaoBinding = BindingBuilder.bind(operacaoQueue).to(operacaoTopicExchange()).with(".");

    declarables.add(operacaoQueue);
    declarables.add(operacaoBinding);
    return declarables;
  }

  private List<Declarable> createTransferenciaQueueDeclarables() {
    List<Declarable> declarables = new ArrayList<>();

    Queue transferenciaQueue = QueueBuilder.durable("transferencia").build();
    Binding transferenciaBinding = BindingBuilder.bind(transferenciaQueue).to(operacaoTopicExchange()).with(".");

    declarables.add(transferenciaQueue);
    declarables.add(transferenciaBinding);
    return declarables;
  }
  @Bean
  AmqpAdmin admin(@Qualifier("bancoConnectionFactory") final ConnectionFactory connectionFactory) {

    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  @Primary
  public ConnectionFactory bancoConnectionFactory() {

    final CachingConnectionFactory connectionFactory =
        new CachingConnectionFactory("localhost",5672);
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    connectionFactory.setVirtualHost("/");

    return connectionFactory;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory simpleContainerFactory(
      @Qualifier("bancoConnectionFactory") final ConnectionFactory connectionFactory) {
    return createContainerFactory(connectionFactory);
  }
  public SimpleRabbitListenerContainerFactory createContainerFactory(
      final ConnectionFactory connectionFactory) {

    final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(jackson2JsonMessageConverter());

    return factory;
  }

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}
