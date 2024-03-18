drop table if exists users CASCADE;
CREATE TABLE users (
                       ID SERIAL,
                       USER_TYPE VARCHAR(20) NOT NULL,
                       EMAIL VARCHAR(255) UNIQUE NOT NULL,
                       PW VARCHAR(255) NOT NULL,
                       AVATAR_URL VARCHAR(255) DEFAULT 'https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png',
                       CONTACT VARCHAR(255) NOT NULL,
                       GENDER VARCHAR(20) NOT NULL CHECK (GENDER IN ('MALE', 'FEMALE', 'PREFER_NOT_TO_DISCLOSE')),
                       BIRTHDAY TIMESTAMP NOT NULL,
                       CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       PRIMARY KEY (ID)
);
