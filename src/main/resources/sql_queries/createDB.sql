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
user_id int NOT NULL,
account_number bigint AUTO_INCREMENT PRIMARY KEY,
balance decimal(16,2),
validity date NOT NULL,
deposit bool NOT NULL,
credit bool NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
ALTER TABLE user_account AUTO_INCREMENT = 1000000;

CREATE TABLE client_details (
user_id int NOT NULL,
name varchar(255) NOT NULL,
surname varchar(255) NOT NULL,
phone_number varchar(20) NOT NULL,
username varchar(255) NOT NULL,
password varchar(255) NOT NULL,
birthday date NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (username) references users(username) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE deposit_accounts (
user_id int NOT NULL,
balance decimal(16,2) NOT NULL,
account_number bigint,
currency varchar(10) NOT NULL,
deposit_term varchar(20) NOT NULL,
rate decimal(16,2) NOT NULL,
term date NOT NULL,
start_date date NOT NULL,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (account_number) references user_account(account_number) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE credit_accounts (
user_id int NOT NULL,
currency varchar(10) NOT NULL,
account_number bigint,
credit_limit decimal(16,2) NOT NULL,
rate decimal(16,2) NOT NULL,
arrears decimal(16,2),
interest_charges decimal(16,2),
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (account_number) references user_account(account_number) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE credit_approvement_operation (
user_id int NOT NULL,
amount decimal(16,2) NOT NULL,
decision bool,
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
operation_type varchar(30),
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE limit_request (
user_id int NOT NULL,
amount decimal(16,2) NOT NULL,
decision bool,
operation_date date,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE transfer_operation (
user_id int NOT NULL,
recipient_account_number bigint NOT NULL,
amount decimal(16,2) NOT NULL,
operation_type varchar(30),
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE bill_payment_operation (
user_id int NOT NULL,
bill_number bigint NOT NULL,
purpose varchar(255) NOT NULL,
amount decimal(16,2) NOT NULL,
operation_type varchar(30),
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE refill_operation (
user_id int NOT NULL,
amount decimal(16,2) NOT NULL,
sender_account_type varchar(30) NOT NULL,
operation_type varchar(30),
date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) references users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into users(username,password,role) values ("artemadmin","12345678",1);