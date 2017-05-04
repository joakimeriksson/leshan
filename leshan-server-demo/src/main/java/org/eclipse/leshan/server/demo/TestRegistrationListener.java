package org.eclipse.leshan.server.demo;

import java.util.Collection;

import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.LwM2mServer;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;

import org.python.util.PythonInterpreter; 
import org.python.core.*;

public class TestRegistrationListener implements RegistrationListener {

    
    LwM2mServer server;
    
    public TestRegistrationListener(LwM2mServer server) {
        this.server = server;
    }

    private void startTest(final Registration registration) {
        // TODO Auto-generated method stub
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Node registered: " + registration.getEndpoint());
                System.out.println("Starting tests...");
                try {
                    PythonInterpreter interp = new PythonInterpreter();
                    interp.exec("import sys");
                    interp.set("client", new TestClient(registration, server));
                    interp.execfile("test.py");                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Ending tests....");
            }
        }).start();
    }
    
    @Override
    public void registered(Registration registration) {
        startTest(registration);
    }

    @Override
    public void updated(RegistrationUpdate update,
            Registration updatedRegistration,
            Registration previousRegistration) {
        // TODO Auto-generated method stub
        System.out.println("Node updated: " + updatedRegistration.getEndpoint());
    }

    @Override
    public void unregistered(Registration registration,
            Collection<Observation> observations, boolean expired) {
        // TODO Auto-generated method stub
        System.out.println("Node de-registered: " + registration.getEndpoint());        
    }

}
