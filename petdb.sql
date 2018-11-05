drop database if exists petdb;
create database petdb;
use petdb;

create table `order`(
	id int primary key auto_increment,
	petId int,
	quantity int,
	shipDate datetime,
	stratus enum('placed', 'approved', 'delivered'),
	complete bit default(1)
);
insert into `order` values(0,1,1,now(),'placed',1);
insert into `order` values(0,2,2,now(),'placed',1);
insert into `order` values(0,3,1,now(),'approved',1);
insert into `order` values(0,4,3,now(),'placed',1);

create table category(
	id int primary key auto_increment,
	name varchar(20)
);
insert into category values(0,'金毛');
insert into category values(0,'哈士奇');
insert into category values(0,'萨摩耶');
insert into category values(0,'阿拉斯加');

create table `user`(
	id int primary key auto_increment,
	user_name varchar(20),
	first_name varchar(20),
	last_name varchar(20),
	email varchar(50),
	password varchar(50),
	phone char(14),
	user_status int
);
insert into `user` values(0,'张伞','张','伞','asd@163.com','asdfasd','13190902323',0);
insert into `user` values(0,'李思','李','思','dfa@163.com','ahsafad','13191902323',0);
insert into `user` values(0,'王武','王','武','xvh@163.com','wqreasd','13192902323',0);
insert into `user` values(0,'赵柳','赵','柳','dsf@163.com','assdasd','13193902323',0);

create table tag(
	id int primary key auto_increment,
	name varchar(20)
);
insert into tag values(0,'狗狗');
insert into tag values(0,'猫猫');
insert into tag values(0,'鹦鹉');


create table pet(
	id int primary key auto_increment,
	category_id int references category(id),
	name varchar(20),
	photo_urls varchar(20),
	tag_id int references tag(id),
	status enum('available', 'pending', 'sold')
);

insert into pet values(0,1,'大黄','img/1.png',1,'available');
insert into pet values(0,2,'二哈','img/2.png',1,'available');
insert into pet values(0,4,'撕家','img/3.png',1,'available');

