DROP TABLE IF EXISTS article CASCADE;

CREATE TABLE article (
                         ID SERIAL PRIMARY KEY,
                         OWNING_ID INT NOT NULL,
                         WALKING_LOCATION VARCHAR(256) NOT NULL,
                         WALKING_DATETIME TIMESTAMP NOT NULL,
                         WALKING_MIN INT NOT NULL,
                         HOURLY_RATE INT NOT NULL,
                         ADDITIONAL_REQUIREMENTS VARCHAR(256),
                         CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (OWNING_ID) REFERENCES owning(ID)
);
