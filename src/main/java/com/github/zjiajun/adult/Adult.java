package com.github.zjiajun.adult;

import com.github.zjiajun.adult.downloader.DefaultDownloader;
import com.github.zjiajun.adult.downloader.Downloader;
import com.github.zjiajun.adult.downloader.RetrofitClient;
import com.github.zjiajun.adult.processor.DefaultProcessor;
import com.github.zjiajun.adult.processor.Processor;
import com.github.zjiajun.adult.response.Response;
import com.github.zjiajun.adult.scheduler.DefaultScheduler;
import com.github.zjiajun.adult.scheduler.Scheduler;
import com.github.zjiajun.adult.request.LoginParamBuild;
import com.github.zjiajun.adult.request.Method;
import com.github.zjiajun.adult.request.Request;
import com.github.zjiajun.adult.store.DefaultStore;
import com.github.zjiajun.adult.store.Store;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhujiajun
 * @since 2017/1/31
 *
 * 主类
 */
public class Adult {

    public Adult () {}

    private Request loginRequest;
    private List<Request> requests = new ArrayList<>();
    private Scheduler scheduler;
    private Downloader downloader;
    private Processor processor;
    private Store store;

    public Adult login(String loginUrl, LoginParamBuild loginParamBuild) {
        Map<String,String> param = new HashMap<>(50);
        loginParamBuild.param(param);
        this.loginRequest = new Request.Builder().url(loginUrl).charset("GBK").login().data(param).method(Method.POST).build();
        return this;
    }


    public Adult url(String url) {
        requests.add(new Request.Builder().url(url).charset("GBK").build());
        return this;
    }


    public void start() {
        init();

        if (null != loginRequest) {
            scheduler.putRequest(loginRequest);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        Request build = new Request.Builder().url("http://67.220.90.4/forum/attachment.php?aid=3105998").build();
        Request build = new Request.Builder().url("http://img588.net/images/2017/06/29/javcc.net_bcdp087pld7e1f.jpg").build();
        try {
            Response execute = RetrofitClient.getInstance().execute(build);
            byte[] bytes = execute.getBytes();
            FileOutputStream fileOutputStream = new FileOutputStream("/Users/zhujiajun/Work/123.jpg");
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null != requests && !requests.isEmpty()) {
            requests.forEach(scheduler::putRequest);
        }

    }


    private void init() {
        scheduler = new DefaultScheduler();
        downloader = new DefaultDownloader(scheduler);
        store = new DefaultStore();
        processor = new DefaultProcessor(scheduler, store);
    }




}
