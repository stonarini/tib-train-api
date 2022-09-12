INSERT INTO
	TRAIN (TRAIN_NAME)
VALUES
	('T1'),
	('T2'),
	('T3');

ALTER TABLE
	STATION DROP CONSTRAINT FK_STATION_RIGHT;

ALTER TABLE
	STATION DROP CONSTRAINT FK_STATION_LEFT;

INSERT INTO
	STATION (
		STATION_NAME,
		STATION_RIGHT,
		TIME_FROM_STATION_RIGHT,
		STATION_LEFT,
	    TIME_FROM_STATION_LEFT
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
    ('Inca (T1)', 15, 4, null, null),
	('Inca (T2)', 15, 4, 18, 5),
	('Enllac Sa Pobla', 17, 5, 19, 4),
	('Llubi', 18, 4, 20, 4),
	('Muro', 19, 4, 21, 4),
	('Sa Pobla', 20, 4, null, null),
    ('Inca (T3)', 15, 4, 23, 13),
	('Sineu', 22, 13, 24, 8),
	('Petra', 23, 8, 25, 9),
	('Manacor', 24, 9, null, null);

ALTER TABLE
    STATION
ADD
	CONSTRAINT FK_STATION_RIGHT FOREIGN KEY (STATION_RIGHT) REFERENCES STATION;

ALTER TABLE
    STATION
ADD
	CONSTRAINT FK_STATION_LEFT FOREIGN KEY (STATION_LEFT) REFERENCES STATION;

INSERT INTO
	ROUTE (TRAIN_ID, DIRECTION, DEPARTURE_HOUR)
VALUES
	(2, 'LEFT', '5:50'),
	(3, 'RIGHT', '6:17');

INSERT INTO
    STOP (ROUTE_ID, STATION_ID)
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
    (1, 20),
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 7),
    (2, 8),
    (2, 9),
    (2, 10),
    (2, 11),
    (2, 12),
    (2, 13),
    (2, 14),
    (2, 15),
    (2, 22),
    (2, 23),
    (2, 24),
    (2, 25);

