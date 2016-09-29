package com.wangge.buzmgt.superposition.repository;

import com.wangge.buzmgt.superposition.entity.Superposition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by joe on 16-9-7.
 */
public interface SuperpositionRepository extends JpaRepository<Superposition,Long> {

    public Page<Superposition> findAll(Specification specification,Pageable pageable);

}
