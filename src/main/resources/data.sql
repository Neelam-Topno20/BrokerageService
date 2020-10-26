INSERT INTO USER ( user_id ,email_id, name , password , active , roles ) VALUES 
( 1 ,'neelam@gmail.com','Neelam','password',true,'ROLE_ADMIN') 
,( 2 ,'ashav@gmail.com','Ashav','password',true,'ROLE_USER') 
,( 3 ,'sushant@gmail.com', 'Sushant','password',true,'ROLE_USER')
,( 4 ,'somnath@gmail.com', 'Somnath','password',true,'ROLE_USER')
,( 5 ,'keshav@gmail.com', 'Keshav','password',true,'ROLE_USER')
,( 6 ,'rafet@gmail.com', 'Rafe','password',true,'ROLE_USER');

INSERT INTO STOCK ( id , name , ppu , symbol )
VALUES
(1,'State Bank Of India',23.0,'SBI'),
(2,'Aditya Birla  Mutual Fund ELSS',24.0,'ABMF ELSS'),
(3,'Reliance Industries',15.0,'RIL'),
(4,'HDFC Mutual Fund',30.0,'HDFC MF'),
(5,'Hindustan Uniliever Limited',18.0,'HUL'),
(6,'Indian Tobacco Company',26.0,'ITC');

INSERT INTO TRADE (trade_id , type , user_id , symbol , shares , price , timestamp,stock_id)
VALUES
(1 , 'buy' , 1 , 'SBI' , 20 , 145.0 , '2019-05-22 12:05:05',1),
(2 , 'sell' , 2 , 'ABMF ELSS' , 20 , 145.0 , '2019-05-22 12:05:05',2),
(3 , 'buy' , 3 , 'RIL' , 25 , 150.0 , '2019-06-22 12:05:05',3),
(4 , 'sell' , 2 , 'HDFC MF' , 25 , 150.0 , '2019-06-22 12:05:05',4),
(5 , 'buy' , 1 , 'HUL' , 20 , 145.0 , '2019-05-22 12:05:05',5),
(6 , 'sell' , 2 , 'ITC' , 20 , 145.0 , '2019-05-22 12:05:05',6),
(7 , 'buy' , 3 , 'SBI' , 25 , 150.0 , '2019-06-22 12:05:05',1),
(8 , 'sell' , 2 , 'ABMF ELSS' , 25 , 150.0 , '2019-06-22 12:05:05',2);


INSERT INTO PURCHASED_STOCK ( ID , STOCK_ID , TOTAL_PRICE , TOTAL_UNITS , USER_USER_ID)
VALUES
(1, 1 ,690.00 ,30.0,1),
(2,2,480.00,20.0,1),
(3,1,1150.00,50.0,2),
(4,3,150.00,10.0,1),
(5,2,240.00,10.0,2);
