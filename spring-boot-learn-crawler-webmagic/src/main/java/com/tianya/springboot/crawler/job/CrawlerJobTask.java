package com.tianya.springboot.crawler.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CrawlerJobTask {

	
	@Scheduled(cron = "0 0 1 * * ?")
	public void getNovelInfo() {
		
		
		
	}
	
	
	
}
