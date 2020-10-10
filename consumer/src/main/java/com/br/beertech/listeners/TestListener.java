package com.br.beertech.listeners;

import com.br.beertech.messages.OperacaoMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TestListener {

  @RabbitListener(queues = "operacao",containerFactory = "simpleContainerFactory")
  public void receive(@Payload OperacaoMessage operacaoMessage){
    System.out.println(operacaoMessage);

  }
}
