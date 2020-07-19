create table shipped_order(order_id int,first_name varchar(50),last_name varchar(50),email varchar(50),cost varchar(50),item_id varchar(40),
item_name varchar(15),ship_date date); 

CREATE TABLE `shipped_order_output` (
  `order_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `cost` varchar(50) DEFAULT NULL,
  `item_id` varchar(40) DEFAULT NULL,
  `item_name` varchar(15) DEFAULT NULL,
  `ship_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
