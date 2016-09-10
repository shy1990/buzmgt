package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.Superposition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by joe on 16-9-7.
 */
public interface SuperpositonService {

    public Superposition save(Superposition superposition);

    public void delete(Long id);

    public Superposition checkMember(String memberId);

    public Superposition findById(Long id);

    public Page<Superposition> findAll(Pageable pageable);
}
