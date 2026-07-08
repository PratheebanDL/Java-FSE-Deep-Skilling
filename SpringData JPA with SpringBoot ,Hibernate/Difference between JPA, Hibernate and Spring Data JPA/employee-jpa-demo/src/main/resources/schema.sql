-- Run in MySQL client (mysql -u root -p) before starting the application

create schema jpademo;

use jpademo;

create table employee(
    emp_id     int primary key auto_increment,
    emp_name   varchar(100) not null,
    emp_dept   varchar(50),
    emp_salary decimal(10,2)
);

insert into employee (emp_name, emp_dept, emp_salary) values ('Alice Fernandes', 'Engineering', 75000.00);
insert into employee (emp_name, emp_dept, emp_salary) values ('Ravi Kumar', 'HR', 55000.00);
