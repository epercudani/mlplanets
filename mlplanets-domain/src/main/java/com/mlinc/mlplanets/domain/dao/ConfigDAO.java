package com.mlinc.mlplanets.domain.dao;

import com.mlinc.mlplanets.domain.model.Config;

public interface ConfigDAO extends DAO<Config> {

    public Config findByKey(String key);
}
