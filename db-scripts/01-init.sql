drop table if exists CAKE;
create table CAKE(
                     id int not null GENERATED BY DEFAULT AS IDENTITY,
                     title varchar(250) not null,
                     description varchar(1000) not null,
                     image_url varchar(1000) not null,
                     CONSTRAINT pk_cake PRIMARY KEY (id)
);