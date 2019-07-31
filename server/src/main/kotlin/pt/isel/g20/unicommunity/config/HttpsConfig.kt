package pt.isel.g20.unicommunity.config

import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.config.annotation.EnableWebMvc

/*
@Configuration
@ControllerAdvice
@EnableWebMvc
class HttpsConfig() : TomcatEmbeddedServletContainerFactory {

}*/
//TODO: add configuration to redirect requests using HTTP to HTTPS so that the connection is secure by default!