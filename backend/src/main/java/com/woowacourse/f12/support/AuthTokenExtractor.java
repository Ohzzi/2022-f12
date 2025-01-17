package com.woowacourse.f12.support;

import com.woowacourse.f12.exception.unauthorized.TokenInvalidException;
import com.woowacourse.f12.exception.unauthorized.TokenNotExistsException;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenExtractor {

    public String extractToken(final String authorizationHeader, final String tokenType) {
        if (Objects.isNull(authorizationHeader)) {
            throw new TokenNotExistsException();
        }
        final String[] splitHeaders = authorizationHeader.split(" ");
        if (splitHeaders.length != 2 || !splitHeaders[0].equalsIgnoreCase(tokenType)) {
            throw new TokenInvalidException();
        }
        return splitHeaders[1];
    }
}
