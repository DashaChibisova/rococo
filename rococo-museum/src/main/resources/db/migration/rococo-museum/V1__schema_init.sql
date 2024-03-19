
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



