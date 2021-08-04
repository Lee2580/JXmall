package com.lee.jxmall.thirdpaty;

import com.aliyun.oss.OSSClient;
import com.lee.jxmall.thirdpaty.component.SmsComponent;
import com.lee.jxmall.thirdpaty.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class JXmallThirdPatyApplicationTests {

    @Autowired
    OSSClient ossClient;

    @Autowired
    SmsComponent smsComponent;

    @Test
    void testSmsComponent(){
        smsComponent.sendSmsCode("18621343864","147852");
    }

    //测试阿里云文件上传
    @Test
    void testAliyun() throws FileNotFoundException {
/*

        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-qingdao.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5t6wJkdRcNLopJhjiLpZ";
        String accessKeySecret = "KwltBcojsoKvPvR93k1aM0VpjGbkyu";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
*/

        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = new FileInputStream("E:\\下载\\三体智子高清4k动漫壁纸_彼岸图网.jpg");
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject("jxmall-lee", "智子1.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        System.out.println("update success");
    }

    /**
     * 短信验证码测试
     */
    @Test
    void sendSms() {
        String host = "https://cdcxdxjk.market.alicloudapi.com";
        String path = "/chuangxin/dxjk";
        String method = "POST";
        String appcode = "3cf8597ab6c94f10803e9ac4525b0ac5";//开通服务后 买家中心-查看AppCode
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("content", "【JX商城】你的验证码是：123456，3分钟内有效，请勿透露给他人");
        querys.put("mobile", "18621343864");
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
