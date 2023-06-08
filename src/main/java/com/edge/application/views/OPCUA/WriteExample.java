package com.edge.application.views.OPCUA;

import com.google.common.collect.ImmutableList;



import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteExample extends Thread implements clientExample {

//    public  void main1(String[] args) throws Exception {
//        WriteExample example = new WriteExample();
//
//        new ClientExampleRunner(example).run();
//    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
	public void run(OpcUaClient client, CompletableFuture<OpcUaClient> future,String url) throws Exception {
}

//	@Override
//	public void run(OpcUaClient client, CompletableFuture<OpcUaClient> future) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void Stop() {
		// TODO Auto-generated method stub
		
	}


}