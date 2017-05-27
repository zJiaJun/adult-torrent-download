package com.github.zjiajun.adult.processor;

import com.github.zjiajun.adult.scheduler.Scheduler;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author zhujiajun
 * @since 2017/4/19
 */
public class DefaultProcessor extends AbstractProcessor {

    public DefaultProcessor(Scheduler scheduler) {
        super(scheduler);
    }

    @Override
    protected void handler(Document document) {

        //如何区分帖子列表页面 和 帖子详情页

        Elements elements = document.select("table[id^=forum]:contains(推荐主题) span a");

        elements.forEach(e -> {
            String text = e.text();
            String href = e.absUrl("href");

        });

    }


}