CREATE TABLE IF NOT EXISTS international_languages (
    language_id VARCHAR(255) NOT NULL PRIMARY KEY,
    submission_id VARCHAR(255) NOT NULL REFERENCES submissions(submission_id),
    language VARCHAR(255) NOT NULL,
    language_level VARCHAR(255) NOT NULL
);

-- create indexes
CREATE INDEX ON international_languages (submission_id DESC);