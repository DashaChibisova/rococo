
create extension if not exists "uuid-ossp";


create table if not exists "painting"
(
    id        UUID unique not null default uuid_generate_v1() primary key,
    museum_id   UUID        not null,
    artist_id   UUID        not null,
    title varchar(255) not null,
    description varchar(255) not null,
    content     bytea
      CONSTRAINT fk_painting_museum
        FOREIGN KEY (museum_id)
        REFERENCES "rococo-museum"."museum" (id),

          CONSTRAINT fk_painting_artist
            FOREIGN KEY (artist_id)
            REFERENCES "rococo-artist"."artist" (id)

);
--
--ALTER TABLE "painting" ADD (
--      CONSTRAINT fk_painting_museum
--        FOREIGN KEY (museum_id)
--        REFERENCES "rococo-museum.museum" (id)
--);
--ALTER TABLE "painting" ADD (
--          CONSTRAINT fk_painting_artist
--            FOREIGN KEY (artist_id)
--            REFERENCES "rococo-artist.artist" (id)
--);

alter table "painting"
    owner to postgres;


