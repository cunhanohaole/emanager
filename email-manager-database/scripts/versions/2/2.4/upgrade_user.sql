CREATE USER 'cunha_emanager' IDENTIFIED BY 'temp@123';

GRANT ALL PRIVILEGES ON *.* TO 'cunha_emanager'@'%' WITH GRANT OPTION;
GRANT PROXY ON ''@'' TO 'cunha_emanager'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;