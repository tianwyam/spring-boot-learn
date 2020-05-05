package com.tianya.springboot.crawler.webmagic;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.tianya.springboot.common.entity.crawler.NovelChapter;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

public class BQGNovelPageProcess implements PageProcessor{
	
	
	private static final String DEFAULT_CHARSET = "gbk" ;
	
	private Site site = Site.me()
			.setCharset(DEFAULT_CHARSET)
			.setRetryTimes(3) // 重试的次数
			.setRetrySleepTime(10*1000) // 重试的时间 毫秒
			.setTimeOut(10*1000); // 超时时间
	

	@Override
	public void process(Page page) {
		
		List<NovelChapter> chapters = new ArrayList<>();
		List<Selectable> nodes = page.getHtml().xpath("//*[@id=\"list\"]/dl/dd").nodes();
		for (Selectable node : nodes) {
			
			String url = node.links().toString();
			String chapterEl = node.css("a").toString();
			Document parse = Jsoup.parse(chapterEl);
			String chapter = parse.text();
			
			NovelChapter novelChapter = NovelChapter.builder().url(url).chapter(chapter).build();
			System.out.println(novelChapter);
//			chapters.add(novelChapter);
		}
		
		
		chapters.forEach(System.out::println);
		
	}

	@Override
	public Site getSite() {
		return this.site;
	}

	
	
	
	
	
public static void main(String[] args) {
		
		Spider.create(new BQGNovelPageProcess())
			.addUrl("https://www.52bqg.com/book_45912/")
			.thread(10)
			.setScheduler(new QueueScheduler())
			.run();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
