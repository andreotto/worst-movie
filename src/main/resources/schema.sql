CREATE TABLE IF NOT EXISTS movies
(
    id           INT PRIMARY KEY,
    release_year INT          NOT NULL,
    title        VARCHAR(100) NOT NULL,
    studio       VARCHAR(100) NOT NULL,
    producer     VARCHAR(100) NOT NULL,
    winner       BOOLEAN      NOT NULL
);
