# CustomHandlerToAuthorizeFromCookieNHeader
This is the custom handler that we can put in WSO2 APIM where it will alow you to send the half of access token in the header and other half in the cookie

1.) Build the project using maven

>> mvn clean install


2) Put the jar to the dropins folder in <APIM_HOME>/repository/components/dropins

3) Add the handler class to the <APIM_HOME>/repository/resources/api_templates/velocity_template.xml

      
      
     
     
           <handler class="org.wso2.custom.authenticator.handler.CustomAPIAuthenticationHandler"/>
          ......

4) Restart the APIM. 

5) How to test? - You should now try by sending second part of the access token as a refID header parameter in the request.
