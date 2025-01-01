create database if not exists identity_service;
-- Use the database
USE identity_service;

-- Create the user and grant privileges
CREATE USER IF NOT EXISTS 'identity_user'@'%' IDENTIFIED BY 'identity_user';

-- Grant all privileges to the user on the database
GRANT ALL PRIVILEGES ON identity_service.* TO 'identity_user'@'%';

-- Apply the privileges
FLUSH PRIVILEGES;