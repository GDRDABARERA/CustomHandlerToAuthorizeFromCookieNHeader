package org.wso2.custom.authenticator.handler;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.rest.AbstractHandler;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.apimgt.gateway.handlers.security.APISecurityException;
import java.util.Map;

public class CustomAPIAuthenticationHandler extends AbstractHandler {


    private static Log log = LogFactory.getLog(CustomAPIAuthenticationHandler.class);
    public boolean handleRequest(MessageContext messageContext) {
        try {
            if (authenticate(messageContext)) {
                return true;
            }
        } catch (APISecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean handleResponse(MessageContext messageContext) {
        return true;
    }

    public boolean authenticate(MessageContext synCtx) throws APISecurityException {
        Map headers = getTransportHeaders(synCtx);
        String accessToken;
        String tokenCookie = getAccessTokenFromCookie(getCookieHeader(headers));
        String authorizationHeader = getAuthorizationHeader(headers);

        if (authorizationHeader != null) {
            if (log.isDebugEnabled()) {
                log.info("Authorization  header is present, not an open API");
            }
            if (tokenCookie != null) {
                if (log.isDebugEnabled()) {
                    log.info("Cookie header is present, hence assuming that cookie is enabled for the browser");
                }

                String tokenPart1 = authorizationHeader;
                String tokenPart2 = tokenCookie;
                accessToken = tokenPart1.concat(tokenPart2);


                headers.remove("Authorization");
                headers.put("Authorization", accessToken);
                ((Axis2MessageContext) synCtx).getAxis2MessageContext().setProperty(
                        org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS, headers);

            }
        }
        return true;

    }

    private String getAuthorizationHeader(Map headers) {
        return (String) headers.get("Authorization");
    }

    private String getCookieHeader(Map headers) {
        return (String) headers.get("Cookie");
    }

    private String getAccessTokenFromCookie(String CookieHeader) {
        if (log.isDebugEnabled()) {
            log.info("The Cookie header " + CookieHeader);
        }
        if (CookieHeader != null) {
            String CookieName = "access_token";
            String[] ca = CookieHeader.split(";");
            for (int i = 0; i < ca.length; i++) {
                String[] c = ca[i].split("=");
                if (c.length == 2) {
                    if (c[0].contains(CookieName)) {
                        return c[1];
                    }
                }
            }
        }

        return  null;
    }

    private Map getTransportHeaders(MessageContext messageContext) {
        return (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext().
                getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
    }

}
