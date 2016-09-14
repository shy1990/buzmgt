package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.Superposition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by joe on 16-9-7.
 */
public interface SuperpositonService {

    public Superposition save(Superposition superposition);

    public void delete(Long id);

    public Superposition checkMember(String memberId);

    public Superposition findById(Long id);

    public Page<Superposition> findAll(Pageable pageable);

    public void find1(Superposition superposition);//计算每个业务员的提货量

}
