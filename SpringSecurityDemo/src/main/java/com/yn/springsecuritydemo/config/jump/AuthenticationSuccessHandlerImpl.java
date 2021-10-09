package com.yn.springsecuritydemo.config.jump;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private String url;
    private RequestCache requestCache = new HttpSessionRequestCache();

    public AuthenticationSuccessHandlerImpl(String destUrl) {
        if (!UrlUtils.isValidRedirectUrl(destUrl)) {
            throw new RuntimeException(String.format("'%s' is not a valid URL", destUrl));
        }
        this.url = destUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest == null) {
            response.sendRedirect(this.url);
            return;
        }
        String targetUrl = savedRequest.getRedirectUrl();
        System.out.println("targetUrl = " + targetUrl);
        response.sendRedirect(targetUrl);
    }
}
