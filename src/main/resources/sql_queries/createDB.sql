CREATE SCHEMA `happy_pay` DEFAULT CHARACTER SET UTF8;
use `happy_pay`;

CREATE TABLE users (
user_id int AUTO_INCREMENT PRIMARY KEY,
username varchar(255) NOT NULL,
password varchar(255) NOT NULL,
role int NOT NULL
);

ALTER TABLE users ADD CONSTRAINT username_unique UNIQUE (username);

CREATE TABLE user_account (
user_id int NOT NULL PRIMARY KEY,
account_number bigint AUTO_INCREMENT,
validity date NOT NULL,
deposit bool NOT NULL,
credit bool NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE client_details (
user_id int NOT NULL PRIMARY KEY,
name varchar(255) NOT NULL,
surname varchar(255) NOT NULL,
phone_number varchar(20) NOT NULL,
username varchar(255) NOT NULL UNIQUE,
password varchar(255) NOT NULL,
birthday date NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (username) references users(username) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (password) references users(password) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE deposit_accounts (
user_id int NOT NULL PRIMARY KEY,
balance decimal NOT NULL,
currency varchar(10) NOT NULL,
account_number bigint AUTO_INCREMENT,
rate decimal NOT NULL,
term date NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE credit_accounts (
user_id int NOT NULL PRIMARY KEY,
balance decimal NOT NULL,
currency varchar(10) NOT NULL,
credit_limit decimal NOT NULL,
account_number bigint AUTO_INCREMENT,
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
operation_date date,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE transfer_operation (
user_id int NOT NULL PRIMARY KEY,
recipient_account_number bigint NOT NULL,
amount decimal NOT NULL,
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE bill_payment_operation (
user_id int NOT NULL PRIMARY KEY,
bill_number bigint NOT NULL,
purpose varchar(255) NOT NULL,
amount decimal NOT NULL,
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE refill_operation (
user_id int NOT NULL PRIMARY KEY,
amount decimal NOT NULL,
account_number bigint NOT NULL,
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
