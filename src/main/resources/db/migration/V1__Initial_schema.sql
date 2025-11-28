CREATE TABLE notification (
    id BIGSERIAL PRIMARY KEY,
    message_id varchar(255) NOT NULL,
    user_id varchar(255) NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    subject varchar(255) NOT NULL,
    html_content varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS newsletter (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    scheduled_at TIMESTAMP,
    sent_at TIMESTAMP,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255),
    version INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS subscriber (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    subscribed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    unsubscribed_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS newsletter_subscription (
    id BIGSERIAL PRIMARY KEY,
    subscriber_id BIGINT NOT NULL REFERENCES subscriber(id),
    newsletter_id BIGINT NOT NULL REFERENCES newsletter(id),
    sent_at TIMESTAMP NOT NULL DEFAULT NOW(),
    delivered BOOLEAN NOT NULL DEFAULT FALSE,
    opened BOOLEAN NOT NULL DEFAULT FALSE,
    opened_at TIMESTAMP
);

CREATE INDEX idx_newsletter_status ON newsletter(status);
CREATE INDEX idx_newsletter_scheduled_at ON newsletter(scheduled_at);
CREATE INDEX idx_subscriber_active ON subscriber(active);
CREATE INDEX idx_newsletter_subscription_sent_at ON newsletter_subscription(sent_at);