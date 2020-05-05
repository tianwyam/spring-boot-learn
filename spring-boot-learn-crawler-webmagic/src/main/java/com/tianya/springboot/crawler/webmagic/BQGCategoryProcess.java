package com.tianya.springboot.crawler.webmagic;

import java.util.ArrayList;
import java.util.List;

import com.tianya.springboot.common.entity.crawler.NovelCategory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class BQGCategoryProcess implements PageProcessor{
	
	private static final String DEFAULT_CHARSET = "gbk" ;
	
	private Site site = Site.me()
			.setCharset(DEFAULT_CHARSET)
			.setRetryTimes(3) // 重试的次数
			.setRetrySleepTime(10*1000) // 重试的时间 毫秒
			.setTimeOut(10*1000); // 超时时间

	@Override
	public void process(Page page) {
		
		List<NovelCategory> catesList = new ArrayList<>();
		List<Selectable> nodes = page.getHtml().css("div.nav ul li").nodes();
		for (Selectable node : nodes) {
			
			String url = node.links().get();
			String title = node.css("a", "title").toString();
			NovelCategory cate = NovelCategory.builder().category(title).url(url).build();
			catesList.add(cate);
		}
		
		catesList.forEach(System.out::println);
	}

	@Override
	public Site getSite() {
		return this.site;
	}

	
	
	
	
	
	public static void main(String[] args) {
		
		Spider.create(new BQGCategoryProcess())
			.addUrl("https://www.52bqg.com/")
			.thread(3)
			.run();
		
		
	}
	
	
	
	
	
}
