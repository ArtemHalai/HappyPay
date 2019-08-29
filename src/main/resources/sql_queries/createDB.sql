CREATE SCHEMA `happy_pay` DEFAULT CHARACTER SET UTF8;
use `happy_pay`;

CREATE TABLE users (
user_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
username varchar(255) NOT NULL,
password varchar(255) NOT NULL,
role int NOT NULL
);

ALTER TABLE users ADD CONSTRAINT username_unique UNIQUE (username);

CREATE TABLE user_account (
user_id int NOT NULL PRIMARY KEY,
balance decimal NOT NULL,
currency varchar(10) NOT NULL,
account_number varchar(30) NOT NULL UNIQUE,
validity date NOT NULL,
deposit bool NOT NULL,
credit bool NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE client_details (
user_id int NOT NULL PRIMARY KEY,
name varchar(255) NOT NULL,
lastName varchar(255) NOT NULL,
mobilePhone varchar(20) NOT NULL,
username varchar(255) NOT NULL UNIQUE,
birthday date NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (username) references users(username) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE deposit_accounts (
user_id int NOT NULL PRIMARY KEY,
balance decimal NOT NULL,
currency varchar(10) NOT NULL,
account_number varchar(30) NOT NULL UNIQUE,
rate decimal NOT NULL,
term date NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE credit_accounts (
user_id int NOT NULL PRIMARY KEY,
balance decimal NOT NULL,
currency varchar(10) NOT NULL,
`limit` decimal NOT NULL,
rate decimal NOT NULL,
arrears decimal,
interest_charges decimal NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE credit_approvement_operation (
user_id int NOT NULL PRIMARY KEY,
manager_id int,
amount decimal NOT NULL,
decision bool,
operationDate date,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE transfer_operation (
user_id int NOT NULL PRIMARY KEY,
recipient_account_number VARCHAR(30) NOT NULL,
amount decimal NOT NULL,
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE bill_payment_operation (
user_id int NOT NULL PRIMARY KEY,
bill_number varchar(30) NOT NULL,
purpose varchar(255) NOT NULL,
amount decimal NOT NULL,
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE refill_operation (
user_id int NOT NULL PRIMARY KEY,
amount decimal NOT NULL,
account_number varchar(30) NOT NULL,
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);