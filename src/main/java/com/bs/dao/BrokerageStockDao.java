package com.bs.dao;

import org.springframework.data.repository.CrudRepository;

import com.bs.beans.Stock;

public interface BrokerageStockDao extends CrudRepository<Stock, Integer> {

}
