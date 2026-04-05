package com.typeface.aws_wrapper.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.identity-service")
public class IdentityServiceProperties {
    private String publicKeyUri;
}
