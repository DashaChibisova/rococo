
create extension if not exists "uuid-ossp";


create table if not exists "painting"
(
    id        UUID unique not null default uuid_generate_v1() primary key,
    museum   UUID        not null,
    artist   UUID        not null,
    title varchar(255) not null,
    description varchar(255) not null,
    content     bytea
);


alter table "painting"
    owner to postgres;


