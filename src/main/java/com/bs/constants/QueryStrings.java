package com.bs.constants;

public class QueryStrings {
	
	public static final String INSERT_TRADE_QUERY = "INSERT INTO TRADE  ( trade_id, type , user_id , symbol , shares , price , timestamp, stock_id) VALUES (trade_sequence.nextval,?1,?2,?3,?4,?5,?6,?7) ";
	
	public static final String UPDATE_TRADE_QUERY = "UPDATE TRADE SET TYPE =?1,USER_ID=?2,SYMBOL=?3,SHARES=?4,PRICE =?5,TIMESTAMP= ?6,STOCK_ID=?7 WHERE TRADE_ID=?8";
	
	//public static final String UPDATE_TRADE_QUERY_HIBERNATE = "update Trade t set t.type =:type,t.userId=:userId,t.symbol=:symbol,t.shares=:shares,t.price =:price,t.timestamp=:timestamp,t.stock_id=:stockId WHERE t.trade_id=:tradeId";
	
	public static final String SELECT_ALL_TRADES_WHERE_STOCK_QUERY="SELECT T.TRADE_ID,T.TYPE,T.SYMBOL,T.SHARES,T.PRICE,T.TIMESTAMP FROM TRADE T JOIN STOCK S ON T.STOCK_ID = S.ID WHERE STOCK_ID = ?1";
	
	public static final String SELECT_ALL_TRADES_WHERE_STOCK_AND_USER_QUERY="SELECT T.TRADE_ID,T.TYPE,T.SYMBOL,T.SHARES,T.PRICE,T.TIMESTAMP,U.NAME as USER_NAME,U.EMAIL_ID,U.ACTIVE,U.ROLES,S.NAME as STOCK_NAME,S.PPU FROM TRADE T JOIN STOCK S ON T.STOCK_ID = S.ID JOIN USER U ON U.USER_ID = T.TRADE_ID WHERE T.STOCK_ID = ?1 AND T.USER_ID= ?2";

	public static final String SELECT_PURCHASED_STOCK_WHERE_STOCK_AND_USER_QUERY="SELECT * FROM PURCHASED_STOCK WHERE STOCK_ID = ?1 AND USER_USER_ID = ?2 ";
	
	public static final String SELECT_PURCHASED_STOCK_WHERE_USER_QUERY="SELECT * FROM PURCHASED_STOCK WHERE USER_USER_ID = ?1";
	
	public static final String DELETE_PURCHASED_STOCK_WHERE_ID_QUERY="DELETE FROM PURCHASED_STOCK WHERE ID=?1";
	
	public static final String SELECT_STOCK_ID_FROM_TRADE_QUERY ="SELECT STOCK_ID FROM TRADE WHERE TRADE_ID=?1";
}
