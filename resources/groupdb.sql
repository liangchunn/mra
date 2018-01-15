--- Schema of GroupDB
--- We need a new database that stores only group data,
--- and one that actually stores which user is in which database

-- CREATE TABLE GroupDatabase (
--   id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
--   groupName VARCHAR(20) NOT NULL,
--   adminId INT NOT NULL,
--   memberIds VARCHAR(255) NOT NULL
-- );
--
-- CREATE TABLE GroupMembers (
--
-- );

CREATE TABLE Users (
  userId INT AUTO_INCREMENT NOT NULL,
  userName VARCHAR(20) NOT NULL,
  PRIMARY KEY (userId),
  UNIQUE (userName),
);

CREATE TABLE GroupDatabase (
  groupName VARCHAR(20) NOT NULL,
  adminId INT NOT NULL,
  PRIMARY KEY (groupName),
  UNIQUE (groupName),
  FOREIGN  KEY (adminId)
    REFERENCES Users(userId)
    ON UPDATE CASCADE
);

CREATE TABLE GroupMembers (
  groupName VARCHAR(20) NOT NULL,
  memberId INT NOT NULL,
  PRIMARY KEY (groupName),
  FOREIGN KEY (groupName)
    REFERENCES GroupDatabase(groupName)
    ON UPDATE CASCADE,
  FOREIGN KEY (memberId)
    REFERENCES Users(userId)
    ON UPDATE CASCADE
);