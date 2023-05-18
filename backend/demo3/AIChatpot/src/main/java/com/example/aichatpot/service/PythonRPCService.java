package com.example.aichatpot.service;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

public interface PythonRPCService {
    Object getSummary(String article) throws MalformedURLException, XmlRpcException;
}
