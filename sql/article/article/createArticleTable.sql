DROP TABLE IF EXISTS article CASCADE;

CREATE TABLE article (
                         ID SERIAL PRIMARY KEY,
                         WRITER_ID INT NOT NULL,
                         ARTICLE_TYPE VARCHAR(50) NOT NULL,
                         CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (WRITER_ID) REFERENCES user_account(ID)
);
