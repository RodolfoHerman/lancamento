package br.com.rodolfo.lancamento.api.mail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.models.Usuario;
import br.com.rodolfo.lancamento.api.repositories.LancamentoRepository;

/**
 * Mailer
 */
@Component
public class Mailer {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine thymeleaf;

    // @Autowired
    // private LancamentoRepository lancamentoRepository;

    // Envio de E-mail simples
    // Método que executa quando a aplicação Spring está totalmente em deply, graças a anotação EventListener com parâmetro ApplicationReadyEvent
    // @EventListener
    // private void teste(ApplicationReadyEvent event) {

    //     this.enviarEmail("ciencia.rodolfo@gmail.com", Arrays.asList("ciencia.rodolfo@gmail.com", "h-e-r-m-a-n@hotmail.com"), "testando", "Teste do envio de e-mail<br>Teste OK!");

    //     System.out.println("ENVIO DE EMAIL TERMINADO");
    // }

    // Envio de e-mail com template
    // @EventListener
    // private void teste(ApplicationReadyEvent event) {

    //     // Local do template em resources
    //     String template = "mail/aviso-lancamentos-vencidos";

    //     List<Lancamento> lancamentos = this.lancamentoRepository.findAll();

    //     Map<String,Object> variaveis = new HashMap<>();
    //     variaveis.put("lancamentos", lancamentos);

    //     this.enviarEmail("ciencia.rodolfo@gmail.com", 
    //         Arrays.asList("ciencia.rodolfo@gmail.com", "h-e-r-m-a-n@hotmail.com"), 
    //         "testando", template, variaveis);

    //     System.out.println("ENVIO DE EMAIL TERMINADO");
    // }

    public void avisarSoberLancamentosVencidos(List<Lancamento> vencidos, List<Usuario> destinatarios) {

        Map<String,Object> variaveis = new HashMap<>();
        variaveis.put("lancamentos", vencidos);

        List<String> emails = destinatarios.stream().map(usuario -> usuario.getEmail()).collect(Collectors.toList());

        // Local do template em resources
        String template = "mail/aviso-lancamentos-vencidos";

        this.enviarEmail("ciencia.rodolfo@gmail.com", emails, "Lançamentos Vencidos", template, variaveis);
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

    public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String template, Map<String,Object> variaveis) { 

        // Contexto do template onde serão colocadas as variáveis
        Context context = new Context(new Locale("pt", "BR"));

        variaveis.entrySet().forEach(var -> context.setVariable(var.getKey(), var.getValue()));

        String mensagem = this.thymeleaf.process(template, context);

        this.enviarEmail(remetente, destinatarios, assunto, mensagem);
    }

}