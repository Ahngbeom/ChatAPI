SELECT USER, HOST FROM USER;

SHOW DATABASES;

SHOW TABLES;

CREATE USER 'ahngbeom'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO 'ahngbeom'@'localhost';
CREATE USER 'ahngbeom'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *.* TO 'ahngbeom'@'%';

SHOW GRANTS FOR 'ahngbeom'@'localhost';
SHOW GRANTS FOR 'ahngbeom'@'%';

