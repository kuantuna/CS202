CREATE DATABASE IF NOT EXISTS food_horse;

CREATE TABLE IF NOT EXISTS food_horse.customer(
    cid int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fname varchar(30) NOT NULL,
    lname varchar(30) NOT NULL,
    caddress varchar(30) NOT NULL,
    pnum varchar(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS food_horse.branch(
    bid int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bname varchar(50) NOT NULL,
    baddress varchar(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS food_horse.product(
    pid int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pname varchar(50) NOT NULL,
    pdescription varchar(100) NOT NULL,
    price varchar(7) NOT NULL
);
