/* DDL Statements*/
CREATE DATABASE IF NOT EXISTS FoodHorse;

CREATE TABLE IF NOT EXISTS FoodHorse.Customer(
    cid int AUTO_INCREMENT PRIMARY KEY ,
    fname varchar(30) NOT NULL,
    lname varchar(30) NOT NULL,
    caddress varchar(30) NOT NULL,
    pnum varchar(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS FoodHorse.Branch(
    bid int AUTO_INCREMENT PRIMARY KEY,
    bname varchar(50) NOT NULL,
    baddress varchar(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS FoodHorse.Product(
    pid int AUTO_INCREMENT PRIMARY KEY,
    pname varchar(50) NOT NULL,
    pdescription varchar(100) NOT NULL,
    price float NOT NULL
);

CREATE TABLE IF NOT EXISTS FoodHorse.Order(
    oid int AUTO_INCREMENT PRIMARY KEY,
    odate varchar(10) NOT NULL,
    cid int,
    pid int,
    bid int,
    FOREIGN KEY (cid) REFERENCES FoodHorse.Customer(cid),
    FOREIGN KEY (pid) REFERENCES FoodHorse.Product(pid),
    FOREIGN KEY (bid) REFERENCES FoodHorse.Branch(bid)
);


/* DML Statements */
INSERT INTO FoodHorse.Customer(fname, lname, caddress, pnum)
VALUES 
    ('Seçkin', 'Ağırbaş', 'Alibeyköy', '05001112233'),
    ('Mahmut', 'Acar', 'Kartal', '05002221133'),
    ('Merve', 'Özkan', 'Beşiktaş', '05003332211'),
    ('Aysel', 'Tekin', 'Kadıköy', '05002223311'),
    ('Orhan', 'Yavuz', 'Ataşehir', '05003331122');

INSERT INTO FoodHorse.Branch(bname, baddress)
VALUES
    ('Alibeyköy Center', 'Alibeyköy'),
    ('Kadıköy Center', 'Kadıköy'),
    ('Kadıköy Pier', 'Kadıköy'),
    ('Beşiktaş Square', 'Beşiktaş'),
    ('Taksim Square', 'Taksim');

INSERT INTO Foodhorse.Product(pname, pdescription, price)
VALUES 
    ('FoodHorse Olive Oil', 'FoodHorse brand olive oil 1L', 11.50),
    ('FoodHorse Rice', 'FoodHorse brand rice 2.5kg', 13.75),
    ('FoodHorse Milk', 'FoodHorse brand whole milk 1L', 3.75),
    ('FoodHorse Kosher Dill Pickles', 'FoodHorse brand pickles 680g', 4.45),
    ('FoodHorse Strawberry Jam', 'FoodHorse Strawberry Jam 380g', 6.45);
