CREATE TABLE IF NOT EXISTS documents (
    document_id VARCHAR(255) NOT NULL PRIMARY KEY,
    submission_id VARCHAR(255) NOT NULL REFERENCES submissions(submission_id),
    document_type VARCHAR(255) NOT NULL,
    document_status VARCHAR(255) NOT NULL
);

-- create indexes
CREATE INDEX ON documents (submission_id DESC);