/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.custom.authenticator.handler.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.rest.AbstractHandler;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.custom.authenticator.handler.CustomAPIAuthenticationHandler;


/**
 * @scr.component name="org.wso2.custom.authenticator.handler.component" immediate="true"
 */
public class CustomAPIAuthenticationHandlerComponent {

    private static Log log = LogFactory.getLog(CustomAPIAuthenticationHandlerComponent.class);

    private static RealmService realmService;

    public static RealmService getRealmService() {
        return realmService;
    }

    protected void activate(ComponentContext ctxt) {
        try {
            CustomAPIAuthenticationHandler customAPIAuthenticationHandler = new CustomAPIAuthenticationHandler();
            ctxt.getBundleContext().registerService(AbstractHandler.class.getName(), customAPIAuthenticationHandler, null);
            log.info("Abstract Custom handler is activated");

        } catch (Throwable e) {
            log.error("Abstract Custom handler activation Failed", e);
        }
    }

    protected void deactivate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.info("BasicCustomAuthenticator bundle is deactivated");
        }
    }

    protected void unsetRealmService(RealmService realmService) {
        log.debug("UnSetting the Realm Service");
        CustomAPIAuthenticationHandlerComponent.realmService = null;
    }

    protected void setRealmService(RealmService realmService) {
        log.debug("Setting the Realm Service");
        CustomAPIAuthenticationHandlerComponent.realmService = realmService;
    }
}
