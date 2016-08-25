package com.wangge.buzmgt.pushmoney.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.pushmoney.entity.Category;

public interface  CategoryRepository extends JpaRepository<Category, String> {

}
