INSERT INTO USER ( user_id , name , password , active , roles )VALUES ( 1 ,'Neelam','password',true,'ROLE_USER') , ( 2 ,'Ashav','password',true,'ROLE_ADMIN') ,( 3 , 'Sushant','password',true,'ROLE_USER');

INSERT INTO TRADE (trade_id , type , user_id , symbol , shares , price , timestamp)
VALUES
(1 , 'buy' , 1 , '$' , 20 , 145.0 , '2019-05-22 12:05:05'),
(2 , 'sell' , 2 , '$' , 20 , 145.0 , '2019-05-22 12:05:05'),
(3 , 'buy' , 3 , '&' , 25 , 150.0 , '2019-06-22 12:05:05'),
(4 , 'sell' , 2 , '&' , 25 , 150.0 , '2019-06-22 12:05:05');