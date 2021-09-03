CREATE TABLE IF NOT EXISTS document_files (
    file_id VARCHAR(255) NOT NULL PRIMARY KEY,
    document_id VARCHAR(255) NOT NULL REFERENCES documents(document_id),
    file_url VARCHAR(255) NOT NULL
);

-- create indexes
CREATE INDEX ON document_files (document_id DESC);