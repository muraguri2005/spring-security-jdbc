INSERT INTO
  oauth_client_details (
    client_id,
    client_secret,
    resource_ids,
    scope,
    authorized_grant_types,
    authorities,
    access_token_validity,
    refresh_token_validity
  )
VALUES
  (
    'spring-security-jdbc-app',
    '$2a$10$N0Sb/T398FKUFsxSzy5eoOiKDZDh.gs.XKqyqWklHrVPbDjHg4X8W',
    'spring-security-jdbc',
    'read,write',
    'authorization_code,check_token,refresh_token,password',
    'ROLE_USER',
    3600,
    250000
  );

  INSERT INTO users (username,password,enabled)
    VALUES ('user@example.com', '$2a$10$.hFW5vRkH5.O520y1hrcGOVdeMZIefPe.hOwNP5hRhezbo33aW4cW',true);