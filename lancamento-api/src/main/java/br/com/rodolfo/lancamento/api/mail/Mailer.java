package br.com.rodolfo.lancamento.api.mail;

import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Mailer
 */
@Component
public class Mailer {

    @Autowired
    private JavaMailSender mailSender;

    @EventListener
    private void teste(ApplicationReadyEvent event) {

        this.enviarEmail("ciencia.rodolfo@gmail.com", Arrays.asList("ciencia.rodolfo@gmail.com", "h-e-r-m-a-n@hotmail.com"), "testando", "Teste do envio de e-mail<br>Teste OK!");

        System.out.println("ENVIO DE EMAIL TERMINADO");
    }

    public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {

        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            
            // Configurar o remetente, destinatário e outras coisas
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
            helper.setSubject(assunto);
            // Se é HTML colocar como TRUE
            helper.setText(mensagem, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            
            throw new RuntimeException("Problemas no envio de e-mails!", e);
        }
        
    }

}