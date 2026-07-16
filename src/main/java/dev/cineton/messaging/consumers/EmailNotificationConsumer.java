package dev.cineton.messaging.consumers;

import dev.cineton.messaging.config.RabbitMQConfig;
import dev.cineton.messaging.events.UserRegisteredEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationConsumer {
    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void handleUserRegistered(UserRegisteredEvent event) {
        // processa o evento
    }
}
