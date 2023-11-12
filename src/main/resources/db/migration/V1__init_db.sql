create table if not exists client (
id bigserial primary key,
name varchar (200) not null check (length(name) >= 3)
);

create table if not exists planet (
id varchar (50) primary key check (id similar to '[A-Z,0-9]+'),
name varchar (500) not null
);

create table if not exists ticket (
id bigserial primary key,
created_at timestamp with time zone,
client_id bigint not null,
from_planet_id varchar (50) not null,
to_planet_id varchar (50) not null check (to_planet_id <> from_planet_id),
foreign key (client_id) references client (id)
ON UPDATE CASCADE ON DELETE CASCADE,
foreign key (from_planet_id) references planet (id)
ON UPDATE CASCADE ON DELETE CASCADE,
foreign key (to_planet_id) references planet (id)
ON UPDATE CASCADE ON DELETE CASCADE
);