package com.yunbaek.barogodelivery.auth.infrastructure;

import com.yunbaek.barogodelivery.common.exception.AuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e
    ) throws IOException {
        log.error("UnAuthorized -- message : " + e.getMessage());
        throw new AuthorizationException("인증에 실패하였습니다.");
    }
}
