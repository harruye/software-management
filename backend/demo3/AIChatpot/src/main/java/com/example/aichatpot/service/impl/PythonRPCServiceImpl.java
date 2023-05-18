package com.example.aichatpot.service.impl;


import com.example.aichatpot.service.PythonRPCService;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class PythonRPCServiceImpl implements PythonRPCService {


    public XmlRpcClient getRPCConnection() throws MalformedURLException {
        XmlRpcClientConfigImpl config=new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://127.0.0.1:8000/RPC2"));
        config.setEnabledForExceptions(true);
        config.setEnabledForExtensions(true);
        //set expire time
        config.setConnectionTimeout(10*1000);
        config.setReplyTimeout(10*1000);
        XmlRpcClient client=new XmlRpcClient();
        client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
        client.setConfig(config);
        return client;
    }



    @Override
    public Object getSummary(String article) throws MalformedURLException, XmlRpcException {
        XmlRpcClient client=getRPCConnection();

        Object params[]=new Object[]{article};
        Object res=client.execute("trans",params);


        return res;
    }

    //config.setServerURL(new URL("http://127.0.0.1:8000/RPC2"));
}
