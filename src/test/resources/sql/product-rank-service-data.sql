insert into users(user_id , name , current_point)
values (1,"건희",100000000);

insert into orders(order_id ,total_price,user_id)
values
    (1,40000,1),
    (2,25000,1),
    (3,20000,1),
    (4,45000,1);


insert into product(product_id , name, price ,quantity)
values
    (1,"양파",1000,300),
    (2,"감자",2000,300),
    (3,"당근",3000,300),
    (4,"버섯",5000,300),
    (5,"고구마",2000,300);


INSERT INTO order_line (created_date, id, last_modified_date, order_id, price, product_id, quantity)
VALUES
    (CURDATE() - INTERVAL 1 DAY, 1, CURDATE() - INTERVAL 1 DAY, 1, 1000, 1, 5),
    (CURDATE() - INTERVAL 1 DAY, 2, CURDATE() - INTERVAL 1 DAY, 1, 2000, 2, 10),
    (CURDATE() - INTERVAL 1 DAY, 3, CURDATE() - INTERVAL 1 DAY, 1, 3000, 3, 5),
    (CURDATE() - INTERVAL 2 DAY, 4, CURDATE() - INTERVAL 2 DAY, 2, 3000, 3, 5),
    (CURDATE() - INTERVAL 2 DAY, 5, CURDATE() - INTERVAL 2 DAY, 2, 2000, 2, 5),
    (CURDATE() - INTERVAL 3 DAY, 6, CURDATE() - INTERVAL 3 DAY, 3, 3000, 3, 5),
    (CURDATE() - INTERVAL 3 DAY, 7, CURDATE() - INTERVAL 3 DAY, 3, 1000, 1, 5),
    (CURDATE() - INTERVAL 4 DAY, 8, CURDATE() - INTERVAL 4 DAY, 4, 5000, 4, 5),
    (CURDATE() - INTERVAL 4 DAY, 9, CURDATE() - INTERVAL 4 DAY, 4, 1000, 1, 5),
    (CURDATE() - INTERVAL 4 DAY, 10, CURDATE() - INTERVAL 4 DAY, 4, 3000, 3, 5);