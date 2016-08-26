package com.wangge.buzmgt.goods.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.goods.entity.Category;

public interface  CategoryRepository extends JpaRepository<Category, String> {

}
