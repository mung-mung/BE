DROP TABLE IF EXISTS refresh CASCADE;

CREATE TABLE refresh (
                         ID SERIAL PRIMARY KEY,
                         NAME VARCHAR(255) NOT NULL,
                         REFRESH VARCHAR(255) NOT NULL,
                         EXPIRATION TIMESTAMP NOT NULL
);
