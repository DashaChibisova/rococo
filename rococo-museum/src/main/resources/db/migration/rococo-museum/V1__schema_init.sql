
create extension if not exists "uuid-ossp";

create table if not exists "country"
(
    id        UUID unique        not null default uuid_generate_v1(),
    name varchar(50) not null,
    primary key (id)

);

alter table "country"
    owner to postgres;
delete from country;


insert into country(name) values('Австралия');
insert into country(name) values('Австрия');
insert into country(name) values('Азербайджан');
insert into country(name) values('Албания');
insert into country(name) values('Алжир');


create table if not exists "geo"
(
    id        UUID unique not null default uuid_generate_v1() primary key,
    country_id   UUID        not null,
    city varchar(50) not null,
    constraint fk_geo_country foreign key (country_id) references "country" (id)
);

alter table "geo"
    owner to postgres;

create table if not exists "museum"
(
    id        UUID unique not null default uuid_generate_v1() primary key,
    geo_id   UUID        not null,
    title varchar(255) not null,
    description varchar(255) not null,
    photo     bytea,
    constraint fk_museum_geo foreign key (geo_id) references "geo" (id)
);

alter table "museum"
    owner to postgres;


