drop table if exists dog CASCADE;
CREATE TABLE dog (
                     ID SERIAL,
                     NAME VARCHAR(20) NOT NULL,
                     BIRTHDAY DATE NOT NULL,
                     BREED VARCHAR(20) NOT NULL,
                     WEIGHT REAL NOT NULL,
                     SEX VARCHAR(20) NOT NULL CHECK (SEX IN ('MALE', 'FEMALE')),
                     PHOTO_URL VARCHAR(255) DEFAULT 'https://cdn.pixabay.com/photo/2018/05/26/18/06/dog-3431913_1280.jpg',
                     CREATED_AT TIMESTAMP,
                     UPDATED_AT TIMESTAMP,
                     PRIMARY KEY (ID)
);
