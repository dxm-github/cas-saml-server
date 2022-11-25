package com.uyun.cas.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uyun.cas.server.credential.UyunUserCredential;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class CustomUsernamePasswordAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler {


    public CustomUsernamePasswordAuthentication(String name, ServicesManager servicesManager,
                                                PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        UyunUserCredential uyunUserCredential= (UyunUserCredential) credential;
        String username = uyunUserCredential.getUsername();
        String password = uyunUserCredential.getPassword();

        if (!Objects.equals(password, "Admin@123")&&!Objects.equals(password,"NoPasswordRequired")) {
            throw new FailedLoginException("密码错误!");
        } else {
            //可自定义返回给客户端的多个属性信息
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> returnInfo=objectMapper.convertValue(uyunUserCredential, Map.class);
            returnInfo.remove("username");
            returnInfo.remove("password");
            final List<MessageDescriptor> list = new ArrayList<>();
            return createHandlerResult(uyunUserCredential, this.principalFactory.createPrincipal(username, returnInfo), list);
        }
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof UyunUserCredential;
    }
}
