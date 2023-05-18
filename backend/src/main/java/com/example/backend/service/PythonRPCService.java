package com.example.backend.service;

import org.apache.xmlrpc.XmlRpcException;

import java.net.MalformedURLException;
import java.util.Map;

public interface PythonRPCService {
    Map getSummary(String article) throws MalformedURLException, XmlRpcException;
    Map answer_question(String question,String context) throws MalformedURLException, XmlRpcException;
    Map get_article(String path) throws MalformedURLException, XmlRpcException;
}
