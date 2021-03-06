/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.sasl;

import static org.wildfly.sasl.anonymous.AbstractAnonymousFactory.ANONYMOUS;
import static org.wildfly.sasl.localuser.LocalUserSaslFactory.JBOSS_LOCAL_USER;
import static org.wildfly.sasl.plain.PlainServerFactory.PLAIN;

import java.security.Provider;

import javax.security.sasl.SaslClientFactory;
import javax.security.sasl.SaslServerFactory;

import org.kohsuke.MetaInfServices;
import org.wildfly.sasl.anonymous.AnonymousClientFactory;
import org.wildfly.sasl.anonymous.AnonymousServerFactory;
import org.wildfly.sasl.localuser.LocalUserClientFactory;
import org.wildfly.sasl.localuser.LocalUserServerFactory;
import org.wildfly.sasl.plain.PlainServerFactory;
import org.wildfly.sasl.scram.Scram;
import org.wildfly.sasl.scram.ScramPasswordFactorySpiImpl;
import org.wildfly.sasl.scram.ScramSha1ClientFactory;
import org.wildfly.sasl.scram.ScramSha1PlusClientFactory;
import org.wildfly.sasl.scram.ScramSha256ClientFactory;
import org.wildfly.sasl.scram.ScramSha256PlusClientFactory;
import org.wildfly.sasl.scram.ScramSha384ClientFactory;
import org.wildfly.sasl.scram.ScramSha384PlusClientFactory;
import org.wildfly.sasl.scram.ScramSha512ClientFactory;
import org.wildfly.sasl.scram.ScramSha512PlusClientFactory;
import org.wildfly.security.password.Password;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 * @author <a href="mailto:darran.lofthouse@jboss.com">Darran Lofthouse</a>
 */
@MetaInfServices(Provider.class)
public class WildFlySaslProvider extends Provider {

    private static final long serialVersionUID = -6687682039965913189L;

    private static final String INFO = "JBoss SASL Provider " + getVersionString();

    private static final String SASL_CLIENT_FACTORY = SaslClientFactory.class.getSimpleName();
    private static final String SASL_SERVER_FACTORY = SaslServerFactory.class.getSimpleName();
    private static final String PASSWORD = Password.class.getSimpleName();

    private static final String DOT = ".";

    /**
     * Construct a new instance.
     */
    public WildFlySaslProvider() {
        super("wildfly-sasl", 1.0, INFO);
        // NOTE: make sure that all client and server factories listed here also end up in the META-INF/services files.
        put(SASL_CLIENT_FACTORY + DOT + ANONYMOUS, AnonymousClientFactory.class.getName());
        put(SASL_SERVER_FACTORY + DOT + ANONYMOUS, AnonymousServerFactory.class.getName());
        put(SASL_SERVER_FACTORY + DOT + PLAIN, PlainServerFactory.class.getName());
        put(SASL_SERVER_FACTORY + DOT + JBOSS_LOCAL_USER, LocalUserServerFactory.class.getName());
        put(SASL_CLIENT_FACTORY + DOT + JBOSS_LOCAL_USER, LocalUserClientFactory.class.getName());

        putService(new Service(this, SASL_CLIENT_FACTORY, Scram.SCRAM_SHA_1, ScramSha1ClientFactory.class.getName(), null, null));
        putService(new Service(this, SASL_CLIENT_FACTORY, Scram.SCRAM_SHA_1_PLUS, ScramSha1PlusClientFactory.class.getName(), null, null));
        putService(new Service(this, SASL_CLIENT_FACTORY, Scram.SCRAM_SHA_256, ScramSha256ClientFactory.class.getName(), null, null));
        putService(new Service(this, SASL_CLIENT_FACTORY, Scram.SCRAM_SHA_256_PLUS, ScramSha256PlusClientFactory.class.getName(), null, null));
        putService(new Service(this, SASL_CLIENT_FACTORY, Scram.SCRAM_SHA_384, ScramSha384ClientFactory.class.getName(), null, null));
        putService(new Service(this, SASL_CLIENT_FACTORY, Scram.SCRAM_SHA_384_PLUS, ScramSha384PlusClientFactory.class.getName(), null, null));
        putService(new Service(this, SASL_CLIENT_FACTORY, Scram.SCRAM_SHA_512, ScramSha512ClientFactory.class.getName(), null, null));
        putService(new Service(this, SASL_CLIENT_FACTORY, Scram.SCRAM_SHA_512_PLUS, ScramSha512PlusClientFactory.class.getName(), null, null));

        putService(new Service(this, SASL_SERVER_FACTORY, Scram.SCRAM_SHA_1, ScramSha1ServerFactory.class.getName(), null, null));
        putService(new Service(this, SASL_SERVER_FACTORY, Scram.SCRAM_SHA_1_PLUS, ScramSha1PlusServerFactory.class.getName(), null, null));
        putService(new Service(this, SASL_SERVER_FACTORY, Scram.SCRAM_SHA_256, ScramSha256ServerFactory.class.getName(), null, null));
        putService(new Service(this, SASL_SERVER_FACTORY, Scram.SCRAM_SHA_256_PLUS, ScramSha256PlusServerFactory.class.getName(), null, null));
        putService(new Service(this, SASL_SERVER_FACTORY, Scram.SCRAM_SHA_384, ScramSha384ServerFactory.class.getName(), null, null));
        putService(new Service(this, SASL_SERVER_FACTORY, Scram.SCRAM_SHA_384_PLUS, ScramSha384PlusServerFactory.class.getName(), null, null));
        putService(new Service(this, SASL_SERVER_FACTORY, Scram.SCRAM_SHA_512, ScramSha512ServerFactory.class.getName(), null, null));
        putService(new Service(this, SASL_SERVER_FACTORY, Scram.SCRAM_SHA_512_PLUS, ScramSha512PlusServerFactory.class.getName(), null, null));

        putService(new Service(this, PASSWORD, Scram.SCRAM_SHA_1,   ScramPasswordFactorySpiImpl.class.getName(), null, null));
        putService(new Service(this, PASSWORD, Scram.SCRAM_SHA_256, ScramPasswordFactorySpiImpl.class.getName(), null, null));
        putService(new Service(this, PASSWORD, Scram.SCRAM_SHA_384, ScramPasswordFactorySpiImpl.class.getName(), null, null));
        putService(new Service(this, PASSWORD, Scram.SCRAM_SHA_512, ScramPasswordFactorySpiImpl.class.getName(), null, null));
    }

    /**
     * Get the version string of the WildFly SASL provider.
     *
     * @return the version string.
     */
    public static String getVersionString() {
        return "NOT SET";
    }

}
