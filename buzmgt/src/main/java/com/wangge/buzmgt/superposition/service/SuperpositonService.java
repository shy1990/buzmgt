package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.Superposition;

/**
 * Created by joe on 16-9-7.
 */
public interface SuperpositonService {

    public Superposition save(Superposition superposition);

    public void delete(Long id);

    public Superposition checkMember(String memberId);
}
