package com.tianya.springboot.es.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.tianya.springboot.es.entity.PeopleBean;

@Repository
public interface PeopleEsDao extends ElasticsearchRepository<PeopleBean, Long>{

}
