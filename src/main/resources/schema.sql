INSERT INTO
	t_train (train_name)
VALUES
	('T1'),
	('T2'),
	('T3');

ALTER TABLE
	t_station DROP CONSTRAINT fk_sr;

ALTER TABLE
	t_station DROP CONSTRAINT fk_sl;

INSERT INTO
	t_station (
		station_name,
		station_right,
		time_from_station_right,
		station_left,
		time_from_station_left
	)
VALUES
	('Palma', null, null, 2, 1),
	('Jacint Verdaguer', 1, 1, 3, 2),
	('Son Costa/Son Fortesa', 2, 2, 4, 1),
	('Son Fuster', 3, 1, 5, 1),
	('Son Cladera/Es Vivero', 4, 1, 6, 2),
	('Verge de Lluc', 5, 2, 7, 1),
	('Pont dInca', 6, 1, 8, 2),
	('Pont dInca Nou', 7, 2, 9, 2),
	('Poligon de Marratxi', 8, 2, 10, 2),
	('Marratxi', 9, 2, 11, 2),
	('Es Caulls', 10, 2, 12, 5),
	('Santa Maria', 11, 5, 13, 3),
	('Alaro/Consell', 12, 3, 14, 3),
	('Binissalem', 13, 3, 15, 3),
	('Lloseta', 14, 3, 16, 4),
	('Inca', 15, 4, 17, 5),
	('Enllac Sa Pobla', 16, 5, 18, 4),
	('Llubi', 17, 4, 19, 4),
	('Muro', 18, 4, 20, 4),
	('Sa Pobla', 19, 4, null, null),
	('Enllac Manacor', 16, 5, 22, 14),
	('Sineu', 21, 14, 23, 8),
	('Petra', 22, 8, 24, 9),
	('Manacor', 23, 9, null, null);

ALTER TABLE
	t_station
ADD
	CONSTRAINT fk_sr FOREIGN KEY (station_right) REFERENCES t_station;

ALTER TABLE
	t_station
ADD
	CONSTRAINT fk_sl FOREIGN KEY (station_left) REFERENCES t_station;

INSERT INTO
	t_departure (direction, dep_hour)
VALUES
	('LEFT', '5:50');

INSERT INTO
	t_stops (group_id, station, arrival_time)
VALUES
	(1, 1, '0'),
	(1, 2, '1'),
	(1, 3, '3'),
	(1, 4, '4'),
	(1, 5, '5'),
	(1, 6, '7'),
	(1, 7, '8'),
	(1, 8, '10'),
	(1, 9, '12'),
	(1, 10, '14'),
	(1, 11, '16'),
	(1, 12, '21'),
	(1, 13, '24'),
	(1, 14, '27'),
	(1, 15, '30'),
	(1, 16, '35'),
	(1, 17, '40'),
	(1, 18, '44'),
	(1, 19, '48'),
	(1, 20, '52');

INSERT INTO
	t_routes (train, departure)
VALUES
	(2, 1);

INSERT INTO
	route_stops (route_id, group_id)
VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(1, 4),
	(1, 5),
	(1, 6),
	(1, 7),
	(1, 8),
	(1, 9),
	(1, 10),
	(1, 11),
	(1, 12),
	(1, 13),
	(1, 14),
	(1, 15),
	(1, 16),
	(1, 17),
	(1, 18),
	(1, 19),
	(1, 20);