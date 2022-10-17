INSERT INTO TRAIN (TRAIN_NAME)
VALUES ('T1'),
       ('T2'),
       ('T3');

ALTER TABLE STATION DROP CONSTRAINT FK_STATION_RIGHT;

ALTER TABLE STATION DROP CONSTRAINT FK_STATION_LEFT;

INSERT INTO STATION (STATION_NAME,
                     STATION_RIGHT,
                     TIME_FROM_STATION_RIGHT,
                     STATION_LEFT,
                     TIME_FROM_STATION_LEFT)
VALUES ('Palma', null, null, 2, 1),
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

ALTER TABLE STATION
    ADD CONSTRAINT FK_STATION_RIGHT FOREIGN KEY (STATION_RIGHT) REFERENCES STATION;

ALTER TABLE STATION
    ADD CONSTRAINT FK_STATION_LEFT FOREIGN KEY (STATION_LEFT) REFERENCES STATION;
