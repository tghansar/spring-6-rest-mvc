drop table if exists beer_order_line;

drop table if exists beer_order;

create table beer_order
(
    id                 uuid not null,
    created_date       timestamp(6) default null,
    customer_ref       varchar(255) default null,
    last_modified_date timestamp(6) default null,
    version            bigint default null,
    customer_id        uuid default null,
    primary key (id),
    constraint fk_customer foreign key (customer_id) references customer (id)
);

create table beer_order_line
(
    id                 uuid not null,
    beer_id            uuid default null,
    created_date       timestamp(6) default null,
    last_modified_date timestamp(6) default null,
    order_quantity     int default null,
    quantity_allocated int default null,
    version            bigint default null,
    beer_order_id      uuid default null,
    primary key (id),
    constraint fk_beer_order foreign key (beer_order_id) references beer_order (id),
    constraint fk_beer foreign key (beer_id) references beer (id)
);
