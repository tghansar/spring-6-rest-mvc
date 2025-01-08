drop table if exists beer;

drop table if exists customer;

create table beer (
    id uuid not null,
    beer_name varchar(50) not null,
    beer_style smallint not null,
    created_date timestamp,
    price numeric(38,2) not null,
    quantity_on_hand integer,
    upc varchar(255) not null,
    update_date timestamp,
    version integer,
    primary key (id)
);

create table customer (
    id uuid not null,
    created_date timestamp,
    name varchar(255),
    surname varchar(255),
    update_date timestamp,
    version integer,
    primary key (id)
);
