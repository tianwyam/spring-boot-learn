package com.tianya.springboot.learntest.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tianya.springboot.learntest.entity.UserBean;

@Repository
public interface UserRepository extends CrudRepository<UserBean, Integer>, PagingAndSortingRepository<UserBean, Integer>{

	List<UserBean> findAll();
	
}
