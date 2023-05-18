package com.example.backend.controller;

import com.example.backend.api.CommonResult;
import com.example.backend.pojo.Customer;
import com.example.backend.pojo.vo.MRCVo;
import com.example.backend.pojo.vo.SummaryVo;
import com.example.backend.service.PythonRPCService;
import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AI")
@ResponseBody
public class AIController {
    @Autowired
    PythonRPCService pythonRPCService;

    @PostMapping("/summary")
    public CommonResult<SummaryVo> getSummary(@RequestBody SummaryVo req) throws MalformedURLException, XmlRpcException {

        Map result=pythonRPCService.getSummary(req.getArticle());
        SummaryVo response=new SummaryVo();
        response.setArticle((String) result.get("article"));
        response.setSummary((String) result.get("summary"));
        return CommonResult.success(response);
    }

    @PostMapping("/MRC")
    public CommonResult<String> getanswer(@RequestBody MRCVo req) throws MalformedURLException, XmlRpcException{
        Map result=pythonRPCService.answer_question(req.getQuestion(), req.getContext());
        return CommonResult.success((String)result.get("answer"));
    }

    @PostMapping("/OCR")
    public CommonResult<String> getarticle(@RequestBody MultipartFile file) throws IOException, XmlRpcException {
        String path="E:\\software-project-management\\python\\pic";
        File savefile=new File(path);
        file.transferTo(savefile);
        /*OCR算法*/
        Map result=pythonRPCService.get_article(path);
        //返回结果
        return CommonResult.success((String)result.get("article"));
    }

}
