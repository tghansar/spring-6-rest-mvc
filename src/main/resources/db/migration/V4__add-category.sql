drop table if exists category;

drop table if exists beer_category;

create table category
(
    id                 uuid not null,
    description        varchar(255) default null,
    created_date       timestamp(6) default null,
    last_modified_date timestamp(6) default null,
    version            integer default null,
    primary key (id)
);

create table beer_category
(
    beer_id     uuid not null,
    category_id uuid not null,
    primary key (beer_id, category_id),
    constraint fk_beer_cat_beer foreign key (beer_id) references beer (id),
    constraint fk_beer_cat_cat foreign key (category_id) references category (id)
);