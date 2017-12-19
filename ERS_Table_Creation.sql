CREATE USER ers_user
IDENTIFIED BY ers_password
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT connect to ers_user;
GRANT resource to ers_user;
GRANT create session TO ers_user;
GRANT create table TO ers_user;
GRANT create view TO ers_user;

conn ers_user/ers_password


create table Employees (
    EmployeeID NUMBER NOT NULL,
    FirstName VARCHAR2(20) NOT NULL,
    LastName VARCHAR2(20) NOT NULL,
    EmployeePassword VARCHAR2(128) NOT NULL,
    CONSTRAINT PK_Employees PRIMARY KEY (EmployeeID)
);

create table Reimbursement (
    ReimbursementID NUMBER NOT NULL,
    RequestedBy NUMBER NOT NULL,
    Amount NUMBER NOT NULL,
    Description VARCHAR2(128),
    Status NUMBER NOT NULL,
    CONSTRAINT PK_Reimbursement PRIMARY KEY (ReimbursementID),
    CONSTRAINT Check_Status CHECK (Status BETWEEN 0 and 2),
    CONSTRAINT FK_RequestedBy FOREIGN KEY (RequestedBy) REFERENCES Employees(EmployeeID) ON DELETE CASCADE
);

INSERT INTO Employees (EmployeeID, FirstName, LastName, EmployeePassword) VALUES (1, 'James', 'Kirk', 'kirkpassword');
INSERT INTO Employees (EmployeeID, FirstName, LastName, EmployeePassword) VALUES (2, 'Spock', 'Grayson', 'spockpassword');
INSERT INTO Employees (EmployeeID, FirstName, LastName, EmployeePassword) VALUES (3, 'Leonard', 'McCoy', 'mccoypassword');
INSERT INTO Employees (EmployeeID, FirstName, LastName, EmployeePassword) VALUES (4, 'Montgomery', 'Scott', 'scottpassword');
INSERT INTO Employees (EmployeeID, FirstName, LastName, EmployeePassword) VALUES (5, 'Nyota', 'Uhura', 'uhurapassword');
INSERT INTO Employees (EmployeeID, FirstName, LastName, EmployeePassword) VALUES (6, 'Hikaru', 'Sulu', 'sulupassword');
INSERT INTO Employees (EmployeeID, FirstName, LastName, EmployeePassword) VALUES (7, 'Pavel', 'Chekov', 'chekovpassword');

INSERT INTO Reimbursement (ReimbursementID, RequestedBy, Amount, Description, Status) VALUES (1, 1, 100, 'Management certification', 2);
INSERT INTO Reimbursement (ReimbursementID, RequestedBy, Amount, Description, Status) VALUES (2, 2, 200, 'Chemistry certification', 2);
INSERT INTO Reimbursement (ReimbursementID, RequestedBy, Amount, Description, Status) VALUES (3, 3, 300, 'Opthamology certification', 2);
INSERT INTO Reimbursement (ReimbursementID, RequestedBy, Amount, Description, Status) VALUES (4, 4, 400, 'Engineering certification', 2);
INSERT INTO Reimbursement (ReimbursementID, RequestedBy, Amount, Description, Status) VALUES (5, 5, 500, 'Mandarin translation certification', 2);
INSERT INTO Reimbursement (ReimbursementID, RequestedBy, Amount, Description, Status) VALUES (6, 6, 600, 'Piloting certification', 2);
INSERT INTO Reimbursement (ReimbursementID, RequestedBy, Amount, Description, Status) VALUES (7, 7, 700, 'Security certification', 2);

create sequence SQ_REIMBURSEMENT_PK start with 8 increment by 1;

create or replace trigger TR_Insert_REIMBURSEMENT before insert on REIMBURSEMENT
for each row begin
select SQ_REIMBURSEMENT_PK.nextval into :new.ReimbursementID from dual;
end;
/