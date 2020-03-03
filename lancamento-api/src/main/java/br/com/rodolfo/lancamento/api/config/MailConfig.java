package br.com.rodolfo.lancamento.api.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.com.rodolfo.lancamento.api.config.property.LancamentoProperty;

/**
 * MailConfig
 */
@Configuration
public class MailConfig {

    @Autowired
    private LancamentoProperty property;
    
    @Bean
    public JavaMailSender javaMailSender() {

        Properties properties = new Properties();

        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        //tls -> transport layer security
        properties.put("mail.smtp.starttls.enable", true);
        // timeout -> tempo para esperar por uma conexão para o envio de email (10 segundos)
        properties.put("mail.smtp.connectiotimeout", 10000);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setJavaMailProperties(properties);
        mailSender.setHost(this.property.getMail().getHost());
        mailSender.setPort(this.property.getMail().getPort());
        mailSender.setUsername(this.property.getMail().getUsername());
        mailSender.setPassword(this.property.getMail().getPassword());

        return mailSender;
    }

}