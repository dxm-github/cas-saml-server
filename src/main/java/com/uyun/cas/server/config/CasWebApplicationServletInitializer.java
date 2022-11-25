package com.uyun.cas.server.config;

import com.uyun.cas.server.StartApplication;
import com.uyun.cas.server.util.CasEmbeddedContainerUtils;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.Map;

/**
 * This is {@link CasWebApplicationServletInitializer}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
public class CasWebApplicationServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        final Map<String, Object> properties = CasEmbeddedContainerUtils.getRuntimeProperties(Boolean.FALSE);
        return builder
                .sources(StartApplication.class)
                .properties(properties)
                .banner(CasEmbeddedContainerUtils.getCasBannerInstance());
    }
}