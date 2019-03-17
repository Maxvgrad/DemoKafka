package ru.demo.betfair.client;

import com.jbetfairng.BetfairClient;
import com.jbetfairng.enums.Exchange;
import com.jbetfairng.exceptions.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

@Slf4j
public class BetfairClientHelper {

    private BetfairClientHelper() {
    }

    public static BetfairClient login(Properties properties) {

        String appKey = properties.getProperty("application.key");
        String certPath = properties.getProperty("certificate.path");
        String certPassword = ObjectUtils
                .defaultIfNull(properties.getProperty("certificate.password"), StringUtils.EMPTY);
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        BetfairClient client = new BetfairClient(Exchange.UK, appKey);
        try {
            boolean result = client.login(certPath, certPassword, username, password);
            log.debug("#login: result:{}", result);

        } catch (LoginException ex) {
            log.error("#login: ex(msg:{})", ex.getLocalizedMessage());
            throw new IllegalArgumentException(ex);
        }

        return client;
    }
}
