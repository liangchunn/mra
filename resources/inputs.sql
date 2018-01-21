INSERT INTO Users (userName, userId) VALUES ('dbuser', 1 );
INSERT INTO Users (userName, userId) VALUES ('tom', 2);
INSERT INTO Users (userName, userId) VALUES ('jerry', 3);
INSERT INTO Users (userName, userId) VALUES ('jim', 4);


INSERT INTO GroupDatabase (groupName, adminId) VALUES ('group1', 1);
INSERT INTO GroupMembers (groupName, memberId) VALUES ('group1', 1);
INSERT INTO GroupMembers (groupName, memberId) VALUES ('group1', 2);

INSERT INTO GroupDatabase (groupName, adminId) VALUES ('group2', 2);
INSERT INTO GroupMembers (groupName, memberId) VALUES ('group2', 2);
INSERT INTO GroupMembers (groupName, memberId) VALUES ('group2', 3);
INSERT INTO GroupMembers (groupName, memberId) VALUES ('group2', 4);

INSERT INTO GroupDatabase (groupName, adminId) VALUES ('DELETE_ME_1', 3);
INSERT INTO GroupMembers (groupName, memberId) VALUES ('DELETE_ME_1', 3);

INSERT INTO GroupDatabase (groupName, adminId) VALUES ('DELETE_ME_2', 4);
INSERT INTO GroupMembers (groupName, memberId) VALUES ('DELETE_ME_2', 4);