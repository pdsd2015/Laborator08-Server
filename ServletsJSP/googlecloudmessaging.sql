CREATE DATABASE googlecloudmessaging;
 
USE googlecloudmessaging;
 
CREATE TABLE registered_devices (
  id              INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
  registration_id TEXT NOT NULL,
  username        VARCHAR(255),			
  email	          VARCHAR(255),
  timestamp       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);