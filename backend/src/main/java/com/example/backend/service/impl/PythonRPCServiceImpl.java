package com.example.backend.service.impl;



import com.example.backend.service.PythonRPCService;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/*
调用python代码
*/
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




    public Object getSummarybyPython(String article) throws MalformedURLException, XmlRpcException {
        XmlRpcClient client=getRPCConnection();

        Object params[]=new Object[]{article};
        Object res=client.execute("text2rank",params);


        return res;
    }


    public Object get_answer_questionbyPython(String question,String context) throws MalformedURLException, XmlRpcException {
        XmlRpcClient client=getRPCConnection();
        Object params[]=new Object[]{question,context};
        Object res=client.execute("MRC",params);
        return res;
    }

    public Object get_articlebyPython(String path) throws MalformedURLException, XmlRpcException {
        XmlRpcClient client=getRPCConnection();
        Object params[]=new Object[]{path};
        Object res=client.execute("OCR",params);
        return res;
    }

    @Override
    public Map getSummary(String article) throws MalformedURLException, XmlRpcException {
        String answerjson=(String)(getSummarybyPython(article));
        Map maps=(Map)JSON.parse(answerjson);
        return maps;
    }

    @Override
    public Map answer_question(String question, String context) throws MalformedURLException, XmlRpcException {
        String answerjson=(String)(get_answer_questionbyPython(question,context));
        Map maps=(Map)JSON.parse(answerjson);
        return maps;
    }

    @Override
    public Map get_article(String path) throws MalformedURLException, XmlRpcException {
        String answerjson=(String)(get_articlebyPython(path));
        Map maps=(Map)JSON.parse(answerjson);
        return maps;
    }

    //config.setServerURL(new URL("http://127.0.0.1:8000/RPC2"));
}
