-- CREATE TABLE Person
-- (
--     id         INTEGER      NOT NULL,
--     name       VARCHAR(255) NOT NULL,
--     location   VARCHAR(255),
--     birth_date TIMESTAMP,
--     PRIMARY KEY (id)
-- );
--
INSERT INTO Person (id, name, location, birth_date)
VALUES (1000, 'Ramin', 'London', TO_TIMESTAMP('2014-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));

INSERT INTO Person (id, name, location, birth_date)
VALUES (1001, 'Mary', 'New york', TO_TIMESTAMP('2012-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));

INSERT INTO Person (id, name, location, birth_date)
VALUES (1002, 'Jack', 'Paris', TO_TIMESTAMP('2000-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));