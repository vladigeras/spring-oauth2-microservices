create table if not exists oauth_client_details (
    client_id VARCHAR(256) PRIMARY KEY,
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256),
    scope VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(256)
);

-- create table if not exists oauth_access_token (
--   token_id VARCHAR(255),
--   token bytea,
--   authentication_id VARCHAR(255) PRIMARY KEY,
--   user_name VARCHAR(255),
--   client_id VARCHAR(255),
--   authentication bytea,
--   refresh_token VARCHAR(255)
-- );
-- create table if not exists oauth_refresh_token (
--   token_id VARCHAR(255),
--   token bytea,
--   authentication bytea
-- );


-- CLIENT ID: client
-- CLIENT SECRET: secret

INSERT INTO oauth_client_details (client_id, client_secret, resource_ids, scope,
    authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('client', '$2a$04$RI7XM2jXjuuuINo1T34nlOsvwEbPATDL5H84CObX957BHTpvWykay','SERVICE', 'read,write',
    'client_credentials,password', null, null, 60 * 60, null, null, true);