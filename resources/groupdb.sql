--- Schema of GroupDB
--- We need a new database that stores only group data,
--- and one that actually stores which user is in which database

CREATE TABLE GroupDatabase (
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  groupName VARCHAR(20) NOT NULL,
  adminId INT NOT NULL,
  memberIds VARCHAR(255) NOT NULL
);