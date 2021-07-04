create database tests character set utf8;

create user 'test-user'@'localhost' identified by 'test-user-password';
create user 'test-user'@'%' identified by 'test-user-password';

grant all privileges on tests.* to 'test-user'@'localhost';
grant all privileges on tests.* to 'test-user'@'%';

use tests;

create table TESTS(
  id bigint UNSIGNED auto_increment,
  value bigint,

  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO TESTS
(
  value
)
VALUES
(
  0
);