package com.uyun.cas.server.action;

import com.uyun.cas.server.credential.UyunUserCredential;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.flow.resolver.CasDelegatingWebflowEventResolver;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @author dongxiaoming
 * @date 2022/11/22
 */
public class MyInitializeLoginAction extends AbstractAction {
    private String uyunTokenName="token";
    private String uyunOpenApiUrl="http://10.1.152.172:7600/tenant/openapi/v2/users/get_by_token?apikey=%s&token=%s";
    private String uyunApikey="92529a5655a211ed8174000c29ec1be9";

    @Autowired
    @Qualifier("initialAuthenticationAttemptWebflowEventResolver")
    private  CasDelegatingWebflowEventResolver initialAuthenticationAttemptWebflowEventResolver;


    @Override
    protected Event doExecute(final RequestContext requestContext) throws Exception {
        HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);
        Cookie[] cookies = request.getCookies();//此处采用同域获取cookie的方式实现和userrole同步登陆状态，如有必要可接入userrole的单点登录
        String token=null;
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(),uyunTokenName)) {
                    token=cookie.getValue();
                    break;
                }
            }
        }
        if(token==null){
//            token="3456996de72f1a5ea43fb09499b7979f4b5bbe46c87ad8074331ea8d1f764b32";//临时添加
            return this.error();
        }
        //根据token获取用户
        RestTemplate restTemplate=new RestTemplate();
        String url=String.format(uyunOpenApiUrl,uyunApikey,token);
        Map<String,Map<String,Object>> restResult = null;
        try {
            restResult = restTemplate.getForObject(url, Map.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return this.error();
        }
        Map<String,Object> data =  restResult.get("data");
        if(data==null){
            return this.error();
        }
        String account = (String) data.get("account");
        //存储用户数据
        UyunUserCredential credential=new UyunUserCredential(account,"NoPasswordRequired");
        credential.setUserId((String) data.get("userId"));
        credential.setTenantId((String) data.get("tenantId"));
        credential.setRealname((String) data.get("realname"));
        credential.setAccount(account);
        credential.setEmail((String) data.get("email"));
        credential.setMobile((String) data.get("mobile"));
        requestContext.getRequestScope().put("credential",credential);
        Event event = initialAuthenticationAttemptWebflowEventResolver.resolveSingle(requestContext);


        return this.success();
    }

    public MyInitializeLoginAction() {}

}
