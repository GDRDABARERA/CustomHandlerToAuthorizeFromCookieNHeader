# CustomHandlerToAuthorizeFromCookieNHeader
This is the custom handler that we can put in WSO2 APIM where it will alow you to send the half of access token in the header and other half in the cookie

1.) Build the project using maven

>> mvn clean install


2) Put the jar to the dropins folder in <APIM_HOME>/repository/components/dropins

3) Add the handler class to the <APIM_HOME>/repository/resources/api_templates/velocity_template.xml

      
      <handlers xmlns="http://ws.apache.org/ns/synapse">
           <handler class="org.wso2.custom.authenticator.handler.CustomAPIAuthenticationHandler"/>
          ......

4) Restart the APIM. You should now try the API with harlf header and half cookie
