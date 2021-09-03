CREATE TABLE IF NOT EXISTS comments (
    comment_id VARCHAR(255) NOT NULL PRIMARY KEY,
    submission_id VARCHAR(255) NOT NULL REFERENCES submissions(submission_id),
    comment TEXT NOT NULL
);

-- create indexes
CREATE INDEX ON comments (submission_id DESC);