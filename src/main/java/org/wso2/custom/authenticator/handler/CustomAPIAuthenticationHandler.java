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
    private String PARAM_HEADER = "refID";
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
        String tokenCookie = getParameterHeader(headers);
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

    private String getParameterHeader(Map headers) {
        return (String) headers.get(PARAM_HEADER);
    }



    private Map getTransportHeaders(MessageContext messageContext) {
        return (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext().
                getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
    }

}
