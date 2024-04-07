drop table if exists refresh CASCADE;
CREATE TABLE refresh (
                     ID SERIAL,
                     NAME VARCHAR(255) NOT NULL,
                     REFRESH VARCHAR(255) NOT NULL,
                     EXPIRATION VARCHAR(255) NOT NULL
);
