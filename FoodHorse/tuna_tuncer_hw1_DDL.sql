CREATE DATABASE IF NOT EXISTS FoodHorse;

CREATE TABLE IF NOT EXISTS FoodHorse.Customer(
    cid int AUTO_INCREMENT PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS FoodHorse.Orders(
    odate varchar(19) NOT NULL,
    cid int,
    pid int,
    bid int,
    FOREIGN KEY (cid) REFERENCES FoodHorse.Customer(cid),
    FOREIGN KEY (pid) REFERENCES FoodHorse.Product(pid),
    FOREIGN KEY (bid) REFERENCES FoodHorse.Branch(bid),
    PRIMARY KEY (odate, cid, pid, bid)
);

CREATE TABLE IF NOT EXISTS FoodHorse.Stock(
    quantity int NOT NULL,
    pid int,
    bid int,
    FOREIGN KEY (pid) REFERENCES FoodHorse.Product(pid),
    FOREIGN KEY (bid) REFERENCES FoodHorse.Branch(bid),
    PRIMARY KEY (pid, bid)
);
