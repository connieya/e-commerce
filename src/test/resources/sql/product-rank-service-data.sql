insert into users(user_id , name , point)
values (1,'건희',100000000);

insert into orders(order_id ,total_price,user_id)
values
    (1,40000,1),
    (2,25000,1),
    (3,20000,1),
    (4,45000,1);


insert into product(product_id , name, price ,quantity)
values
    (1,'양파',1000,300),
    (2,'감자',2000,300),
    (3,'당근',3000,300),
    (4,'버섯',5000,300),
    (5,'고구마',2000,300);


INSERT INTO order_line (created_date, id, last_modified_date, order_id, price, product_id, quantity)
VALUES
    (CURRENT_DATE() - INTERVAL '1' DAY, 1, CURRENT_DATE() - INTERVAL '1' DAY, 1, 1000, 1, 5),
    (CURRENT_DATE() - INTERVAL '1' DAY, 2, CURRENT_DATE() - INTERVAL '1' DAY, 1, 2000, 2, 10),
    (CURRENT_DATE() - INTERVAL '1' DAY, 3, CURRENT_DATE() - INTERVAL '1' DAY, 1, 3000, 3, 5),
    (CURRENT_DATE() - INTERVAL '2' DAY, 4, CURRENT_DATE() - INTERVAL '2' DAY, 2, 3000, 3, 5),
    (CURRENT_DATE() - INTERVAL '2' DAY, 5, CURRENT_DATE() - INTERVAL '2' DAY, 2, 2000, 2, 5),
    (CURRENT_DATE() - INTERVAL '3' DAY, 6, CURRENT_DATE() - INTERVAL '3' DAY, 3, 3000, 3, 5),
    (CURRENT_DATE() - INTERVAL '3' DAY, 7, CURRENT_DATE() - INTERVAL '3' DAY, 3, 1000, 1, 5),
    (CURRENT_DATE() - INTERVAL '4' DAY, 8, CURRENT_DATE() - INTERVAL '4' DAY, 4, 5000, 4, 5),
    (CURRENT_DATE() - INTERVAL '4' DAY, 9, CURRENT_DATE() - INTERVAL '4' DAY, 4, 1000, 1, 5),
    (CURRENT_DATE() - INTERVAL '4' DAY, 10, CURRENT_DATE() - INTERVAL '4' DAY, 4, 3000, 3, 5);