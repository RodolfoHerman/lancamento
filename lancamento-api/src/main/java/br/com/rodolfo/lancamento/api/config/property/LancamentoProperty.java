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

    public Seguranca getSeguranca() {

        return this.seguranca;
    }

    public Host getHost() {

        return this.host;
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
}