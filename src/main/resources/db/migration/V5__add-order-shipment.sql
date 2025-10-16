drop table if exists beer_order_shipment;

create table beer_order_shipment
(
    id                 uuid not null,
    version            integer default null,
    created_date       timestamp(6) default null,
    last_modified_date timestamp(6) default null,
    tracking_number    varchar(255) default null,
    beer_order_id      uuid not null,
    shipment_date      timestamp(6) default null,
    carrier            varchar(255) default null,
    status             varchar(50) default null,
    primary key (id),
    constraint fk_beer_order_shipment_beer_order foreign key (beer_order_id) references beer_order (id)
);

alter table beer_order
    add column beer_order_shipment_id uuid;

alter table beer_order
    add constraint fk_beer_order_shipment
        foreign key (beer_order_shipment_id) references beer_order_shipment (id)

