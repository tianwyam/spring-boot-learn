package com.tianya.springboot.es.test;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import com.alibaba.fastjson.JSON;
import com.tianya.springboot.es.dao.PeopleEsDao;
import com.tianya.springboot.es.entity.PeopleBean;

@SpringBootTest
public class PeopleTest {
	
	@Autowired
	private PeopleEsDao peopleEsDao ;
	
	@Test
	public void insert() {
		
		List<PeopleBean> peopleBeanList = new ArrayList<>();
		peopleBeanList.add(PeopleBean.builder().uid(1L).name("吴彦祖").age(45).birthDay("1977-01-01").addr("香港").professional("演员").interest("赛车").build());
		peopleBeanList.add(PeopleBean.builder().uid(2L).name("吴奇隆").age(55).birthDay("1967-01-01").addr("大陆").professional("演员").interest("唱歌").build());
		peopleBeanList.add(PeopleBean.builder().uid(3L).name("吴京").age(45).birthDay("1977-01-01").addr("大陆").professional("演员").interest("武术").build());
		peopleBeanList.add(PeopleBean.builder().uid(4L).name("古天乐").age(55).birthDay("1967-01-01").addr("香港").professional("演员").interest("唱歌").build());
		peopleBeanList.add(PeopleBean.builder().uid(5L).name("苏炳添").age(35).birthDay("1987-01-01").addr("大陆").professional("运动员").interest("跑步").build());
		peopleBeanList.add(PeopleBean.builder().uid(6L).name("刘亦菲").age(30).birthDay("1992-01-01").addr("大陆").professional("歌手").interest("演戏").build());
		peopleBeanList.add(PeopleBean.builder().uid(7L).name("张杰").age(30).birthDay("1992-01-01").addr("大陆").professional("歌手").interest("健身").build());
		peopleBeanList.add(PeopleBean.builder().uid(8L).name("张家辉").age(45).birthDay("1977-01-01").addr("香港").professional("演员").interest("唱歌").build());
		
		Iterable<PeopleBean> saveResult = peopleEsDao.saveAll(peopleBeanList);
		System.out.println(JSON.toJSONString(saveResult, true));
	}
	
	@Test
	public void get() {
		
		Iterable<PeopleBean> peopleList = peopleEsDao.findAll();
		System.out.println(JSON.toJSONString(peopleList, true));
	}
	
	
	@Test
	public void order4Get() {
		
		Sort orderSort = Sort.by(Order.asc("uid"), Order.desc("age"));
		Iterable<PeopleBean> sortPeopleList = peopleEsDao.findAll(orderSort);
		System.out.println(JSON.toJSONString(sortPeopleList, true));
		
	}
	
	
	@Test
	public void page4Get() {
		
		PageRequest page = PageRequest.of(0, 5, Sort.by(Order.asc("uid")));
		Page<PeopleBean> pagePeopleList = peopleEsDao.findAll(page);
		System.out.println(JSON.toJSONString(pagePeopleList, true));
	}
	
	
	@Test
	public void search() {
		
		TermQueryBuilder ageQuery = QueryBuilders.termQuery("age", 45);
		
		Iterable<PeopleBean> search = peopleEsDao.search(ageQuery);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(search, true));
	}
	
	
	@Test
	public void searchChinese() {
		
		// 添加 keyword 的目的是 以后面查询的值整体查询，不要分词，不然查询不到
		TermQueryBuilder termQuery = QueryBuilders.termQuery("addr.keyword", "大陆");
		
		System.out.println("参数：");
		System.out.println(termQuery.toString(true));
		
		Iterable<PeopleBean> search = peopleEsDao.search(termQuery);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(search, true));
	}
	
	
	@Test
	public void searchMore() {
		
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
				.filter(QueryBuilders.termQuery("addr.keyword", "大陆"))
				.filter(QueryBuilders.termQuery("age", 45));
		
		System.out.println("参数：");
		System.out.println(boolQuery.toString(true));
		
		Iterable<PeopleBean> search = peopleEsDao.search(boolQuery);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(search, true));
	}
	
	
	@Test
	public void searchBetweenAnd() {
		
		
		// >= 
//		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age").gte(45);
		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age").from(55).to(66);
		
		System.out.println("参数：");
		System.out.println(rangeQuery.toString(true));
		
		Iterable<PeopleBean> search = peopleEsDao.search(rangeQuery);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(search, true));
		
	}
	
	
	@Test
	public void searchSort() {
		
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.filter(QueryBuilders.termQuery("professional.keyword", "演员"))
				.filter(QueryBuilders.termQuery("age", 55));
		System.out.println("参数：");
		System.out.println(queryBuilder.toString(true));
		// 分页排序
		PageRequest page = PageRequest.of(0, 5, Sort.by(Order.desc("age"), Order.asc("uid")));
		// 搜索分页查询
		Iterable<PeopleBean> search = peopleEsDao.search(queryBuilder, page);
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(search, true));
		
	}
	
	
	
	@Test
	public void searchQuery() {
		
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
				.withFields("uid","name","age","professional")
				.withFilter(QueryBuilders.termQuery("professional.keyword", "演员"))
				.withQuery(QueryBuilders.rangeQuery("age").gte(45))
				.withSort(SortBuilders.fieldSort("uid"))
				.withPageable(PageRequest.of(0, 3));
		
		Iterable<PeopleBean> search = peopleEsDao.search(queryBuilder.build());
		
		System.out.println("结果：");
		System.out.println(JSON.toJSONString(search, true));
		
	}
	
	
	
	@Test
	public void searchAggr() {
		
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
				.withFields("uid,age,professional,name,birthDay")
				.addAggregation(AggregationBuilders.terms("ageCount")
						.field("age")
						.size(100)
						.subAggregation(AggregationBuilders.terms("professionalCount").field("professional.keyword")
								.subAggregation(AggregationBuilders.topHits("fieldNms")
										.fetchSource(new String[] {"uid","age","professional","name"}, null))));
		
		Iterable<PeopleBean> search = peopleEsDao.search(queryBuilder.build());
		
		AggregatedPageImpl<PeopleBean> peopleAggrPage = (AggregatedPageImpl<PeopleBean>)search ;
		
		System.out.println("结果：");
		Aggregations aggregations = peopleAggrPage.getAggregations();
		aggregations.asMap().forEach((k,v)->{
			
			ParsedLongTerms terms = (ParsedLongTerms)v;
			System.out.println(k);
			List<? extends Bucket> buckets = terms.getBuckets();
			buckets.forEach(b->{
				Aggregations aggrP = b.getAggregations();
				for (Aggregation p : aggrP) {
					ParsedStringTerms termsP = (ParsedStringTerms)p;
					System.out.println(termsP.getName());
					System.out.println(termsP.getDocCountError());
				}
			});
		});
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
