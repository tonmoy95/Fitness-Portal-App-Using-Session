create database fitness;

use fitness;

/*Registration Table*/

create table registration(
reg_id int not null auto_increment,
first_name varchar(30) not null,
last_name varchar(30) not null,
email_Id varchar(30) not null unique,
password varchar(200) not null,
salt_value varchar(200) not null,
primary Key (reg_id)
);

alter table registration auto_increment=101;                    /*auto-increment for Reg_Id.*/
alter table registration add updated_dt_tm datetime default now();  /*automatic the Date and Time is given by the system.*/



/*Profile Table*/

create table profile(
profile_id int not null auto_increment,
reg_id int,
gender_ind int,
birth_date date not null,
weight float,
height float,
step_size int,
primary Key (profile_id),
foreign key (reg_id) references registration(reg_id)
);

alter table profile auto_increment=201;                    /*auto-increment for Profile_Id*/
alter table profile add updated_dt_tm datetime default now();  /*automatic the Date and Time is given by the system.*/



/*Activity Table*/

create table activity(
activity_id int not null auto_increment,
reg_id int,
title varchar(150) not null,
activity_type varchar(30) not null,
distance float not null,
duration_hr float not null,
energy_expended float not null,
activity_date date not null,
primary Key (activity_Id),
foreign key (reg_id) references registration(reg_id)
);

alter table activity auto_increment=301;                    /*auto-increment for Activity_Id*/
alter table activity add updated_dt_tm datetime default now();  /*automatic the Date and Time is given by the system.*/






