DROP TABLE IF EXISTS job_posting_article CASCADE;

CREATE TABLE job_posting_article (
                         ID INT PRIMARY KEY,
                         OWNING_ID INT NOT NULL,
                         WALKING_LOCATION VARCHAR(256) NOT NULL,
                         WALKING_DATETIME TIMESTAMP NOT NULL,
                         WALKING_MIN INT NOT NULL,
                         HOURLY_RATE INT NOT NULL,
                         ADDITIONAL_REQUIREMENTS VARCHAR(256),
                         FOREIGN KEY (ID) REFERENCES article(ID) ON DELETE CASCADE,
                         FOREIGN KEY (OWNING_ID) REFERENCES owning(ID)  ON DELETE CASCADE
);
