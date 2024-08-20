CREATE TABLE IF NOT EXISTS spring_session (
    primary_id CHAR(36) NOT NULL,
    session_id CHAR(36) NOT NULL,
    creation_time BIGINT NOT NULL,
    last_access_time BIGINT NOT NULL,
    max_inactive_interval INTEGER NOT NULL,
    expiry_time BIGINT NOT NULL,
    principal_name VARCHAR(300),
    PRIMARY KEY (primary_id)
    );

CREATE UNIQUE INDEX IF NOT EXISTS spring_session_ix1
    ON spring_session (session_id);

CREATE INDEX IF NOT EXISTS spring_session_ix2
    ON spring_session (expiry_time);

CREATE INDEX IF NOT EXISTS spring_session_ix3
    ON spring_session (principal_name);


CREATE TABLE IF NOT EXISTS spring_session_attributes (
                                                         session_primary_id CHAR(36) NOT NULL,
    attribute_name VARCHAR(200) NOT NULL,
    attribute_bytes BINARY NOT NULL,
    PRIMARY KEY (session_primary_id, attribute_name),
    FOREIGN KEY (session_primary_id) REFERENCES spring_session(primary_id) ON DELETE CASCADE
    );
