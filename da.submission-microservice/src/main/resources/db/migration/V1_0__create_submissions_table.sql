CREATE TABLE IF NOT EXISTS submissions (
    submission_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    current_working_location VARCHAR(255),
    process_status VARCHAR(255),
    submission_date TIMESTAMP,
    approval_date TIMESTAMP,
    working_date TIMESTAMP
);

-- create indexes
CREATE INDEX ON submissions (submission_id DESC);