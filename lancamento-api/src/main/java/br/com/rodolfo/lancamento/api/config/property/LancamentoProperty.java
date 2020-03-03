package br.com.rodolfo.lancamento.api.config.property;

import javax.validation.constraints.NotBlank;

// import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * LancamentoProperty
 * Para melhor explicação: https://www.baeldung.com/configuration-properties-in-spring-boot
 */
@Configuration
@ConfigurationProperties(prefix = "lancamento")
public class LancamentoProperty {

    private final Seguranca seguranca = new Seguranca();
    private final Host host = new Host();
    private final Mail mail = new Mail();

    public Seguranca getSeguranca() {

        return this.seguranca;
    }

    public Host getHost() {

        return this.host;
    }

    public Mail getMail() {

        return this.mail;
    }

    // Criar classe para separar os temas. Ex.: tudo a respeito de segurança 
    // criar métodos dentro dessa classe.
    public static class Seguranca {

        private boolean enableHttps;

        public boolean isEnableHttps() {

            return this.enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {

            this.enableHttps = enableHttps;
        }

    }

    public static class Host {

        @NotBlank
        private String originPermitida = "http://localhost:4200";

        public String getOriginPermitida() {

            return this.originPermitida;
        }

        public void setOriginPermitida(String originPermitida) {

            this.originPermitida = originPermitida;
        }

    }

    public static class Mail {

        private String host;

        private Integer port;

        private String username;

        private String password;

        
        public String getHost() {

            return this.host;
        }

        public void setHost(String host) {

            this.host = host;
        }

        public Integer getPort() {

            return this.port;
        }

        public void setPort(Integer port) {

            this.port = port;
        }

        public String getUsername() {

            return this.username;
        }

        public void setUsername(String username) {

            this.username = username;
        }

        public String getPassword() {

            return this.password;
        }

        public void setPassword(String password) {

            this.password = password;
        }


    }
}