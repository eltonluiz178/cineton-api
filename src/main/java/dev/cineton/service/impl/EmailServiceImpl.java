package dev.cineton.service.impl;

import dev.cineton.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendConfirmationEmail(String email, String name, String code, String expiresAt) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // O parâmetro 'true' indica que faremos um envio multipart (suporta HTML e anexos se necessário)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Confirmação de Cadastro - Cineton");

            // Geramos o HTML dinâmico
            String htmlContent = getHtmlTemplate(name, code, expiresAt);

            // O segundo parâmetro 'true' define que o conteúdo enviado é HTML
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            // Trate o erro de acordo com a arquitetura da sua API (ex: lançar uma runtime exception)
            throw new RuntimeException("Falha ao enviar o e-mail de confirmação", e);
        }
    }

    private String getHtmlTemplate(String name, String code, String expiresAt) {
        return """
        <!DOCTYPE html>
        <html lang="pt-BR">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Confirmação de Cadastro</title>
        </head>
        <body style="margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f5f7; color: #333333;">
            <table role="presentation" width="100%%" cellspacing="0" cellpadding="0" border="0" style="background-color: #f4f5f7; padding: 40px 0;">
                <tr>
                    <td align="center">
                        <table role="presentation" width="600" cellspacing="0" cellpadding="0" border="0" style="background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 12px rgba(0,0,0,0.05);">
                            <!-- Header / Banner -->
                            <tr>
                                <td align="center" style="background: linear-gradient(135deg, #1e3c72 0%%, #2a5298 100%%); padding: 40px 20px;">
                                    <h1 style="margin: 0; color: #ffffff; font-size: 28px; font-weight: 700; letter-spacing: -0.5px;">Cineton</h1>
                                    <p style="margin: 5px 0 0 0; color: #e0e6ed; font-size: 14px;">Quase lá! Confirme seu cadastro</p>
                                </td>
                            </tr>
                            <!-- Conteúdo Principal -->
                            <tr>
                                <td style="padding: 40px 30px;">
                                    <h2 style="margin: 0 0 15px 0; color: #1e3c72; font-size: 20px; font-weight: 600;">Olá, %s!</h2>
                                    <p style="margin: 0 0 25px 0; font-size: 16px; line-height: 1.6; color: #555555;">
                                        Obrigado por se cadastrar no Cineton. Para ativar sua conta e garantir a segurança do seu acesso, utilize o código de confirmação abaixo:
                                    </p>
                                    
                                    <!-- Bloco do Código -->
                                    <table role="presentation" width="100%%" cellspacing="0" cellpadding="0" border="0" style="margin: 30px 0;">
                                        <tr>
                                            <td align="center" style="background-color: #f0f4f8; border-radius: 8px; padding: 20px;">
                                                <span style="display: block; font-size: 12px; text-transform: uppercase; letter-spacing: 1.5px; color: #718096; margin-bottom: 8px; font-weight: bold;">Seu código de acesso</span>
                                                <span style="display: block; font-size: 36px; font-weight: 800; letter-spacing: 6px; color: #1e3c72; font-family: monospace;">%s</span>
                                            </td>
                                        </tr>
                                    </table>

                                    <!-- Expiração -->
                                    <p style="margin: 25px 0 0 0; font-size: 13px; color: #e53e3e; text-align: center; font-weight: 500;">
                                        ⚠️ Este código expira em: <strong>%s</strong>.
                                    </p>
                                </td>
                            </tr>
                            <!-- Rodapé -->
                            <tr>
                                <td style="background-color: #fafbfc; border-top: 1px solid #edf2f7; padding: 25px 30px; text-align: center;">
                                    <p style="margin: 0; font-size: 12px; color: #a0aec0; line-height: 1.5;">
                                        Se você não realizou este cadastro, ignore este e-mail.<br>
                                        &copy; 2026 Cineton. Todos os direitos reservados.
                                    </p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(name, code, expiresAt);
    }
}