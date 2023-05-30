package com.example.backend.controller;

import com.example.backend.api.CommonResult;
import com.example.backend.pojo.Customer;
import com.example.backend.pojo.vo.MRCVo;
import com.example.backend.pojo.vo.SummaryVo;
import com.example.backend.service.PythonRPCService;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AI")
@ResponseBody
@Slf4j
//三个python代码接口
public class AIController {
    @Autowired
    PythonRPCService pythonRPCService;

    //摘要抽取
    @PostMapping("/summary")
    public CommonResult<SummaryVo> getSummary(@RequestBody SummaryVo req) throws MalformedURLException, XmlRpcException {
        //System.out.println(req.getArticle());
        Map result=pythonRPCService.getSummary(req.getArticle());
        SummaryVo response=new SummaryVo();
        response.setArticle((String) result.get("article"));
        response.setSummary((String) result.get("summary"));
        return CommonResult.success(response);
    }
    //阅读理解
    @PostMapping("/MRC")
    public CommonResult<String> getanswer(@RequestBody MRCVo req) throws MalformedURLException, XmlRpcException{
        //log.info(req.getQuestion());
        Map result=pythonRPCService.answer_question(req.getQuestion(), req.getContext());
        return CommonResult.success((String)result.get("answer"));
    }
    //上传文件转文字
    @PostMapping("/upload")
    public CommonResult<String> getarticle(MultipartFile file) throws IOException, XmlRpcException {
        //System.out.println("get!");
        String filename=file.getOriginalFilename();
        String prefix=filename.substring(filename.lastIndexOf("."));
        if (prefix.equals(".jpg")||prefix.equals(".png")){
            String path="E:\\software-project-management\\python\\pic\\pic.png";
            File savefile=new File(path);
            file.transferTo(savefile);
            /*OCR算法*/
            Map result=pythonRPCService.get_article(path);
            //返回结果
            return CommonResult.success((String)result.get("article"));
        }
        else if (prefix.equals(".txt")){
            String path="E:\\software-project-management\\python\\text\\text.txt";
            File savefile=new File(path);
            file.transferTo(savefile);
            BufferedReader br = new BufferedReader(new FileReader(savefile));
            String line = br.readLine();
            return CommonResult.success(line);
        }
        else {
            return CommonResult.failed();
        }

    }

}
