package com.uyun.cas.server.config;

import com.uyun.cas.server.action.MyInitializeLoginAction;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.action.AbstractAction;
//import com.uyun.cas.server.credential.UyunUserCredential;

@Configuration("CustomAuthenticationConfiguration")
@EnableConfigurationProperties({CasConfigurationProperties.class,})
public class CustomAuthenticationConfiguration implements AuthenticationEventExecutionPlanConfigurer {


    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Bean
    public AuthenticationHandler myAuthenticationHandler() {
        return new CustomUsernamePasswordAuthentication(CustomUsernamePasswordAuthentication.class.getName(), servicesManager, new DefaultPrincipalFactory(), 1);
    }

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(myAuthenticationHandler());
    }

    @Bean
    public AbstractAction myInitializeLoginAction(){
        AbstractAction action=new MyInitializeLoginAction();
        return action;
    }

}
