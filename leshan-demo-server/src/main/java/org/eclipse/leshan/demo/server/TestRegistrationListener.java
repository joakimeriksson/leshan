/*******************************************************************************
 * Copyright (c) 2024 RISE Research Institutes of Sweden.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v20.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 *
 * Contributors:
 *     RISE - initial API and implementation
 *******************************************************************************/
package org.eclipse.leshan.demo.server;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.LeshanServer;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;
import org.graalvm.polyglot.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRegistrationListener implements RegistrationListener {

    private static final Logger LOG = LoggerFactory.getLogger(TestRegistrationListener.class);
    LeshanServer server;

    public TestRegistrationListener(LeshanServer server) {
        this.server = server;
    }

    private void startTest(final Registration registration) {
        // TODO Auto-generated method stub
        new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.info("Node registered: " + registration.getEndpoint());
                LOG.info("Starting tests...");
                try {
                    String fname = "pytests/" + registration.getEndpoint() + ".py";
                    File f = new File(fname);
                    // Create a polyglot context for running Python code
                    try (Context context = Context.newBuilder("python").allowAllAccess(true).build()) {
                        // Set "client" variable to be accessible in Python
                        TestClient client = new TestClient(registration, server);
                        context.getBindings("python").putMember("client", client);

                        if (!f.exists()) {
                            fname = "pytests/test-device.py";
                        }
                        context.eval("python", "import sys; print(sys.path)");
                        // Execute the Python script
                        context.eval("python", new String(Files.readAllBytes(Paths.get(fname))));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LOG.info("Ending tests....");
            }
        }).start();
    }

    @Override
    public void registered(Registration reg, Registration previousReg, Collection<Observation> previousObsersations) {
        startTest(reg);
    }

    @Override
    public void updated(RegistrationUpdate update, Registration updatedRegistration,
            Registration previousRegistration) {
        // TODO Auto-generated method stub
        LOG.info("Node updated: " + updatedRegistration.getEndpoint());
    }

    @Override
    public void unregistered(Registration registration, Collection<Observation> observations, boolean expired,
            Registration registration2) {
        // TODO Auto-generated method stub
        LOG.info("Node de-registered: " + registration.getEndpoint());
    }
}
