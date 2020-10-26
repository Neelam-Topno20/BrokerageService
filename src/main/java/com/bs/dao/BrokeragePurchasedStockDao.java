package com.bs.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import static com.bs.constants.QueryStrings.SELECT_PURCHASED_STOCK_WHERE_STOCK_AND_USER_QUERY;
import static com.bs.constants.QueryStrings.SELECT_PURCHASED_STOCK_WHERE_USER_QUERY;
import static com.bs.constants.QueryStrings.DELETE_PURCHASED_STOCK_WHERE_ID_QUERY;
import java.util.List;

import javax.transaction.Transactional;

import com.bs.beans.PurchasedStock;

public interface BrokeragePurchasedStockDao extends CrudRepository<PurchasedStock, Integer> {

	@Query(value = SELECT_PURCHASED_STOCK_WHERE_STOCK_AND_USER_QUERY,nativeQuery = true)
	PurchasedStock getStock(int stockId,int userId);
	
	@Query(value = SELECT_PURCHASED_STOCK_WHERE_USER_QUERY,nativeQuery = true)
	List<PurchasedStock> getPurchasedStockForUser(int userId);
	
	@Modifying
	@Transactional
	@Query(value=DELETE_PURCHASED_STOCK_WHERE_ID_QUERY ,nativeQuery = true)
	void deleteFromQuery(int purchaseStockId);

	
}
