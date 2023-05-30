package com.example.backend;

import com.example.backend.api.CommonResult;
import com.example.backend.api.ResultCode;
import com.example.backend.controller.AIController;
import com.example.backend.pojo.vo.MRCVo;
import com.example.backend.pojo.vo.OCRVo;
import com.example.backend.pojo.vo.SummaryVo;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.XmlRpcException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class PythonFunctionsTests {
    @Autowired
    AIController aiController;

    static String text_pic_path="E:\\software-project-management\\backend\\text_resources\\pic";
    static String txt_path="E:\\software-project-management\\backend\\text_resources\\测试用例";
    @Test
    public void TestSummary_1() throws MalformedURLException, XmlRpcException {
        String text="该研究主持者之一、波士顿大学地球与环境科学系博士陈池（音）表示，“尽管中国和印度国土面积仅占全球陆地的9%，但两国为这一绿化过程贡献超过三分之一。考虑到人口过多的国家一般存在对土地过度利用的问题，这个发现令人吃惊。” NASA埃姆斯研究中心的科学家拉玛·内曼尼（Rama Nemani）说，“这一长期数据能让我们深入分析地表绿化背后的影响因素。我们一开始以为，植被增加是由于更多二氧化碳排放，导致气候更加温暖、潮湿，适宜生长。”“MODIS的数据让我们能在非常小的尺度上理解这一现象，我们发现人类活动也作出了贡献。”NASA文章介绍，在中国为全球绿化进程做出的贡献中，有42%来源于植树造林工程，对于减少土壤侵蚀、空气污染与气候变化发挥了作用。 据观察者网过往报道，2017年我国全国共完成造林736.2万公顷、森林抚育830.2万公顷。其中，天然林资源保护工程完成造林26万公顷，退耕还林工程完成造林91.2万公顷。京津风沙源治理工程完成造林18.5万公顷。三北及长江流域等重点防护林体系工程完成造林99.1万公顷。完成国家储备林建设任务68万公顷";
        SummaryVo summaryVo=new SummaryVo(text,null);
        CommonResult result=aiController.getSummary(summaryVo);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }

    @Test
    public void TestSummary_2() throws MalformedURLException, XmlRpcException {
        String text="在中国传统文化中，曲艺是一种具有广泛影响力的艺术形式。它包括评书、相声、小品、快板等多种艺术形式。曲艺的特点是幽默风趣、语言生动、表现力强，深受人们的喜爱。在中国南方的一些地区，曲艺演出还被视为传统文化的一种体现。近年来，曲艺在电视和网络等新媒体平台上得到了更广泛的传播，受到了更多年轻观众的关注。";
        SummaryVo summaryVo=new SummaryVo(text,text);
        CommonResult result=aiController.getSummary(summaryVo);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }

    @Test
    public void TestSummary_3() throws IOException, XmlRpcException {
        Path path= Paths.get("E:\\software-project-management\\backend\\text_resources\\测试用例\\example.txt");
        String text= Files.readString(path);
        SummaryVo summaryVo=new SummaryVo(text,null);
        CommonResult result=aiController.getSummary(summaryVo);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }

    public static List<String> getFileList(File file) {

        List<String> result = new ArrayList<String>();

        if (!file.isDirectory()) {
            System.out.println(file.getAbsolutePath());
            result.add(file.getAbsolutePath());
        } else {
            File[] directoryList = file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().indexOf("txt") > -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (int i = 0; i < directoryList.length; i++) {
                result.add(directoryList[i].getPath());
            }
        }

        return result;
    }

    @Test
    public void TestSummary_4() throws IOException, XmlRpcException {
        String FILE_IN = txt_path;
        File f = new File(FILE_IN);
        List<String> list = new ArrayList<String>();
        list = getFileList(f);
        String text="";
        for (String l : list) {
            BufferedReader br = new BufferedReader(new FileReader(new File(l)));
            String line = br.readLine();
            text+=line;

        }
        SummaryVo summaryVo=new SummaryVo(text,null);
        CommonResult result=aiController.getSummary(summaryVo);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }

    @Test
    public void TestOCR_1() throws IOException, XmlRpcException {

        File file=new File(text_pic_path,"001.png");
        MultipartFile multipartFile=new MockMultipartFile("001.png","001.png", ContentType.class.toString(),new FileInputStream(file));
        //System.out.println(aiController.getarticle(multipartFile));
        CommonResult result=aiController.getarticle(multipartFile);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }
    @Test
    public void TestOCR_2() throws IOException, XmlRpcException {
        File file=new File(text_pic_path,"003.png");
        MultipartFile multipartFile=new MockMultipartFile("003.png","003.png", ContentType.class.toString(),new FileInputStream(file));
        CommonResult result=aiController.getarticle(multipartFile);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }
    @Test
    public void TestOCR_3() throws IOException, XmlRpcException {
        File file=new File(text_pic_path,"006.png");
        MultipartFile multipartFile=new MockMultipartFile("006.png","006.png", ContentType.class.toString(),new FileInputStream(file));
        CommonResult result=aiController.getarticle(multipartFile);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }

    @Test
    public void TestUpload_1() throws IOException, XmlRpcException {
        File file=new File(txt_path,"example.txt");
        MultipartFile multipartFile=new MockMultipartFile("example.txt","example.txt", ContentType.class.toString(),new FileInputStream(file));
        CommonResult result=aiController.getarticle(multipartFile);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }

    @Test
    public void TestUpload_2() throws IOException, XmlRpcException {
        File file=new File("E:\\software-project-management\\软件项目管理开题ppt.pptx");
        MultipartFile multipartFile=new MockMultipartFile("软件项目管理开题ppt.pptx","软件项目管理开题ppt.pptx", ContentType.class.toString(),new FileInputStream(file));
        CommonResult result=aiController.getarticle(multipartFile);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.FAILED.getCode(),result.getCode());
    }

    @Test
    public void TestMRC_1() throws MalformedURLException, XmlRpcException {
        MRCVo mrcVo=new MRCVo("苹果公司成立于哪一年?","苹果公司成立于1976年，总部位于美国加利福尼亚州的库比蒂诺市。这家公司最初生产的是个人电脑。如今，苹果公司生产和销售各种电子产品，如iPhone, iPad, 和Macintosh电脑。");
        //System.out.println(aiController.getanswer(mrcVo));;
        CommonResult result=aiController.getanswer(mrcVo);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }
    @Test
    public void TestMRC_2() throws MalformedURLException, XmlRpcException {
        MRCVo mrcVo=new MRCVo("李四喜欢看什么类型的电影？","张三和李四一起去了电影院。张三看了惊悚片，但李四看的是喜剧片。电影结束后，他们一起去了餐厅吃晚饭。他们点了披萨和意大利面。");
        CommonResult result=aiController.getanswer(mrcVo);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }
    @Test
    public void TestMRC_3() throws MalformedURLException, XmlRpcException {
        MRCVo mrcVo=new MRCVo("张三和李四点了什么菜？","张三和李四一起去了电影院。张三看了惊悚片，但李四看的是喜剧片。电影结束后，他们一起去了餐厅吃晚饭。他们点了披萨和意大利面。");
        CommonResult result=aiController.getanswer(mrcVo);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }

    @Test
    public void TestMRC_4() throws MalformedURLException, XmlRpcException {
        MRCVo mrcVo=new MRCVo("张三和李四去了哪里？","张三和李四一起去了电影院。张三看了惊悚片，但李四看的是喜剧片。电影结束后，他们一起去了餐厅吃晚饭。他们点了披萨和意大利面。");
        CommonResult result=aiController.getanswer(mrcVo);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }

    @Test
    public void TestMRC_5() throws MalformedURLException, XmlRpcException {
        MRCVo mrcVo=new MRCVo("张三和李四干了什么？","张三和李四一起去了电影院。张三看了惊悚片，但李四看的是喜剧片。电影结束后，他们一起去了餐厅吃晚饭。他们点了披萨和意大利面。");
        CommonResult result=aiController.getanswer(mrcVo);
        System.out.println(result.toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),result.getCode());
    }


}
