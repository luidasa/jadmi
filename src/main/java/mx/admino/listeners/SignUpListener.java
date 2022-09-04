package mx.admino.listeners;

import mx.admino.events.OnUserRegisteredEvent;
import mx.admino.models.entities.Token;
import mx.admino.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SignUpListener implements ApplicationListener<OnUserRegisteredEvent> {

    @Autowired
    UsuarioService userService;

    @Autowired
    MailSender mailSender;

    @Override
    public void onApplicationEvent(OnUserRegisteredEvent event) {

        Token newToken = new Token(event.getUsuario());
        userService.save(newToken);

        String recipientAddress = event.getUsuario().getEmail();

        String subject = "Registro de usuarios.";
        String confirmationUrl
                = event.getUrlBase() + "/commit/" + newToken.getCode();
        /// TODO. Falta a implementación del codigo en base a un template de thymeleaf y tomar los valores de un archivo de propiedades o del ambiente.
        String message = "Copia y pega esta liga " + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("contacto@luidasa.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);

    }
}
