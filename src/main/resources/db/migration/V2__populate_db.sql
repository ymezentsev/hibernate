insert into client (name)
values ('Denis Petrenko'),
('Andriy Nikolaenko'),
('Andriy Semenov'),
('Maksim Kravchenko'),
('Oleksandr Ivanenko'),
('Ivan Petrenko'),
('Dmitrii Dmitriev'),
('Fedor Zaytsev'),
('Alex Zagorulko'),
('Yurii Polunin');

insert into planet (id, name)
values ('MAR', 'Mars'),
('EAR', 'Earth'),
('VEN', 'Venus'),
('JUP', 'Jupiter'),
('MER', 'Mercury'),
('SAT', 'Saturn'),
('URA', 'Uranus'),
('NEP', 'Neptune'),
('PLU', 'Pluto');

insert into ticket (created_at, client_id, from_planet_id, to_planet_id)
values ('2023-01-16 UTC', 1, 'EAR', 'JUP'),
('2022-03-07 UTC', 1, 'EAR', 'MER'),
('2021-10-20 UTC+2', 2, 'EAR', 'SAT'),
('2018-04-04 UTC+4', 2, 'SAT', 'MAR'),
('2022-10-12 UTC-1', 3, 'SAT', 'VEN'),
('2020-12-14 UTC-1', 4, 'MAR', 'PLU'),
('2021-07-17 UTC', 5, 'URA', 'EAR'),
('2022-11-30 UTC+2', 6, 'MAR', 'MER'),
('2017-03-18 UTC+4', 7, 'JUP', 'SAT'),
('2016-08-04 UTC+5', 1, 'VEN', 'EAR');
