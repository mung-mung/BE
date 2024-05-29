DROP TABLE IF EXISTS article CASCADE;

CREATE TABLE article (
                         ID SERIAL PRIMARY KEY,
                         TITLE VARCHAR(256) NOT NULL,
                         OWNER_ID INT NOT NULL,
                         WALKING_LOCATION VARCHAR(256) NOT NULL,
                         WALKING_DATE DATE NOT NULL,
                         WALKING_DATETIME TIMESTAMP NOT NULL,
                         WALKING_DURATION INTERVAL NOT NULL,
                         HOURLY_RATE INT NOT NULL,
                         ADDITIONAL_REQUIREMENTS VARCHAR(256),
                         CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (OWNER_ID) REFERENCES user_account(ID)
);
