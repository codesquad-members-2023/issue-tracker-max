package com.codesquad.issuetracker.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis")
@Getter
@Setter
public class RedisProperties {

    private int port;
    private String host;

    public int getPort() {
        return this.port;
    }

    public String getHost() {
        return this.host;
    }
}
