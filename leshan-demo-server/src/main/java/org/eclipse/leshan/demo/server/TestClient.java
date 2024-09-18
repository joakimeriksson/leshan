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

import org.eclipse.leshan.core.link.Link;
import org.eclipse.leshan.core.node.codec.CodecException;
import org.eclipse.leshan.core.request.ContentFormat;
import org.eclipse.leshan.core.request.DownlinkDeviceManagementRequest;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.WriteRequest;
import org.eclipse.leshan.core.request.exception.InvalidResponseException;
import org.eclipse.leshan.core.request.exception.RequestCanceledException;
import org.eclipse.leshan.core.request.exception.RequestRejectedException;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.eclipse.leshan.server.LeshanServer;
import org.eclipse.leshan.server.registration.Registration;

public class TestClient {
    LeshanServer server;
    public Link[] links;
    public Registration reg;
    public String endpoint;

    TestClient(Registration r, LeshanServer server) {
        links = r.getSortedObjectLinks();
        reg = r;
        endpoint = r.getEndpoint();
        this.server = server;
    }

    public LwM2mResponse read(String path) {
        DownlinkDeviceManagementRequest<ReadResponse> request = new ReadRequest(path);
        try {
            LwM2mResponse response = server.send(reg, request);
            return response;
        } catch (CodecException | InvalidResponseException | RequestCanceledException | RequestRejectedException
                | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public LwM2mResponse readTLV(String path) {
        DownlinkDeviceManagementRequest<ReadResponse> request = new ReadRequest(ContentFormat.TLV, path);
        try {
            LwM2mResponse response = server.send(reg, request);
            return response;
        } catch (CodecException | InvalidResponseException | RequestCanceledException | RequestRejectedException
                | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public LwM2mResponse readJSON(String path) {
        DownlinkDeviceManagementRequest<ReadResponse> request = new ReadRequest(ContentFormat.JSON, path);
        try {
            LwM2mResponse response = server.send(reg, request);
            return response;
        } catch (CodecException | InvalidResponseException | RequestCanceledException | RequestRejectedException
                | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public LwM2mResponse execute(String path) {
        DownlinkDeviceManagementRequest<ExecuteResponse> request = new ExecuteRequest(path);
        try {
            LwM2mResponse response = server.send(reg, request);
            return response;
        } catch (CodecException | InvalidResponseException | RequestCanceledException | RequestRejectedException
                | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public LwM2mResponse write(int oid, int iid, int rid, String value) {
        DownlinkDeviceManagementRequest<WriteResponse> request = new WriteRequest(oid, iid, rid, value);
        try {
            LwM2mResponse response = server.send(reg, request);
            return response;
        } catch (CodecException | InvalidResponseException | RequestCanceledException | RequestRejectedException
                | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public LwM2mResponse write(int oid, int iid, int rid, long value) {
        DownlinkDeviceManagementRequest<WriteResponse> request = new WriteRequest(oid, iid, rid, value);
        try {
            LwM2mResponse response = server.send(reg, request);
            return response;
        } catch (CodecException | InvalidResponseException | RequestCanceledException | RequestRejectedException
                | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
