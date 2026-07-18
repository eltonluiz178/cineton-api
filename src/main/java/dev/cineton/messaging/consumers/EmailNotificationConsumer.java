package dev.cineton.messaging.consumers;

import dev.cineton.messaging.config.RabbitMQConfig;
import dev.cineton.messaging.events.UserRegisteredEvent;
import dev.cineton.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailNotificationConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void handleUserRegistered(UserRegisteredEvent event) {
        emailService.sendConfirmationEmail(
                event.email(),
                event.name(),
                event.code(),
                event.expiresAt()
        );
    }
}
