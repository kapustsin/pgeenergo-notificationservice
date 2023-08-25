CREATE DATABASE notificationservice;

\connect notificationservice

CREATE SCHEMA notificationservice;

CREATE TABLE notificationservice.user (
  id                SERIAL        PRIMARY KEY,
  name              VARCHAR(45)   NOT NULL
);


CREATE TABLE notificationservice.notification_period (
  user_id           BIGINT          NOT NULL,
  day_of_week       SMALLINT        NOT NULL,
  begin_period      TIME (0)        NOT NULL,
  end_period        TIME (0)        NOT NULL
);

CREATE TABLE notificationservice.event (
  id                SERIAL       NOT NULL UNIQUE,
  message           VARCHAR(70)  NOT NULL UNIQUE,
  time              TIMESTAMP    NOT NULL,
  PRIMARY KEY (id)
);


INSERT INTO notificationservice.user (name) VALUES ('Капустин Александр Павлович');
INSERT INTO notificationservice.user (name) VALUES ('Садовский Евгений Григорьевич');
INSERT INTO notificationservice.user (name) VALUES ('Быстров Денис Александрович');


INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (1, 1, '08:05:00', '13:30:00');

INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (1, 1, '14:30:00', '18:30:00');

INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (1, 3, '12:00:00', '18:00:00');

INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (1, 3, '20:00:00', '23:00:00');

INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (1, 4, '18:00:00', '23:59:00');

INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (1, 0, '00:00:00', '15:00:00');

INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (2, 0, '00:10:00', '18:30:00');

INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (2, 2, '08:00:00', '17:00:00');

INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (3, 0, '08:45:00', '18:00:00');

INSERT INTO notificationservice.notification_period (user_id, day_of_week, begin_period, end_period)
VALUES (3, 1, '15:00:00', '23:59:00');


INSERT INTO notificationservice.event (message, time)
VALUES ('Утечка охлаждающей жидкости в первом редукторе', '2023-08-22 16:45:30');
INSERT INTO notificationservice.event (message, time)
VALUES ('Критическое повышение температуры во втором редукторе', '2023-08-23 10:25:15');
INSERT INTO notificationservice.event (message, time)
VALUES ('Утечка масла в третьем редукторе', '2023-08-24 23:58:01');

