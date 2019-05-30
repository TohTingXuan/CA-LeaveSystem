DROP SCHEMA IF EXISTS team12;
CREATE SCHEMA team12;

USE team12;

DROP TABLE IF EXISTS HOLIDAY_LIST;
DROP TABLE IF EXISTS DESIGNATION_LEAVE;
DROP TABLE IF EXISTS LEAVE_APPLICATION;
DROP TABLE IF EXISTS LEAVE_BALANCE;
DROP TABLE IF EXISTS STAFF;
DROP TABLE IF EXISTS DESIGNATION;
DROP TABLE IF EXISTS LEAVE_TYPE;


CREATE TABLE DESIGNATION (ID INTEGER  NOT NULL AUTO_INCREMENT,  
						  DESIGNATION_NAME VARCHAR(255), 
						  PRIMARY KEY (ID) );

CREATE TABLE LEAVE_TYPE (ID INTEGER  NOT NULL AUTO_INCREMENT,  
						 NAME VARCHAR(255),
						 PRIMARY KEY (ID) );
                        
CREATE TABLE STAFF (ID INTEGER  NOT NULL AUTO_INCREMENT, 
                    NAME VARCHAR(255), 
                    DESIGNATION_ID INTEGER,
                    PASSWORD VARCHAR(255),
                    BOSS_ID INTEGER,
                    PRIMARY KEY (ID),
                    CONSTRAINT DESIGNATION_FK FOREIGN KEY (DESIGNATION_ID) REFERENCES DESIGNATION(ID),
                    CONSTRAINT MANAGER_FK FOREIGN KEY (BOSS_ID) REFERENCES STAFF(ID));

CREATE TABLE LEAVE_APPLICATION (ID INTEGER  NOT NULL AUTO_INCREMENT,  
								STAFF_ID INTEGER, 
                                START_DATE DATE,
                                END_DATE DATE, 
                                REASON VARCHAR(255),
                                WORK_INSTRUCTION VARCHAR(255),
                                CONTACT INTEGER,
                                LEAVETYPE_ID INTEGER,
                                STATUS VARCHAR(255),
                                COMMENT VARCHAR(255),
                                PRIMARY KEY (ID), 
                                CONSTRAINT STAFF_FK FOREIGN KEY (STAFF_ID) REFERENCES STAFF(ID),
                                CONSTRAINT LEAVETYPE_FK FOREIGN KEY (LEAVETYPE_ID) REFERENCES LEAVE_TYPE(ID) );
                                
CREATE TABLE HOLIDAY_LIST (ID INTEGER  NOT NULL AUTO_INCREMENT,  
							YEAR INTEGER, 
							NAME VARCHAR(255),
							DATE DATE, 
							PRIMARY KEY (ID) );   
                                
CREATE TABLE LEAVE_BALANCE (LEAVETYPE_ID INTEGER  NOT NULL,  
								STAFF_ID INTEGER, 
                                LEAVE_AVAILABLE DOUBLE,
                                PRIMARY KEY (LEAVETYPE_ID,STAFF_ID), 
                                CONSTRAINT LEAVETYPE1_FK FOREIGN KEY (LEAVETYPE_ID) REFERENCES LEAVE_TYPE(ID),
                                CONSTRAINT STAFF2_FK FOREIGN KEY (STAFF_ID) REFERENCES STAFF(ID));
   
CREATE TABLE DESIGNATION_LEAVE (DESIGNATION_ID INTEGER  NOT NULL,  
								LEAVETYPE_ID INTEGER, 
                                MAXIMUM_LEAVE INTEGER,
                                PRIMARY KEY (DESIGNATION_ID,LEAVETYPE_ID), 
                                CONSTRAINT LEAVETYPE2_FK FOREIGN KEY (LEAVETYPE_ID) REFERENCES LEAVE_TYPE(ID),
                                CONSTRAINT DESIGNATION1_FK FOREIGN KEY (DESIGNATION_ID) REFERENCES DESIGNATION(ID));


        


INSERT INTO `team12`.`designation` (`DESIGNATION_NAME`) VALUES ('Manager');
INSERT INTO `team12`.`designation` (`DESIGNATION_NAME`) VALUES ('Employee');
INSERT INTO `team12`.`designation` (`DESIGNATION_NAME`) VALUES ('Admin');

INSERT INTO `team12`.`leave_type` (`NAME`) VALUES ('Annual Leave');
INSERT INTO `team12`.`leave_type` (`NAME`) VALUES ('Medical Leave');
INSERT INTO `team12`.`leave_type` (`NAME`) VALUES ('Compensation Leave AM');
INSERT INTO `team12`.`leave_type` (`NAME`) VALUES ('Compensation Leave PM');
/* INSERT INTO `team12`.`leave_type` (`NAME`) VALUES ('Compensation Leave'); */

INSERT INTO `team12`.`holiday_list` (YEAR, NAME, DATE) VALUES ('2019', 'Hari Raya Puasa', '2019-06-05');
INSERT INTO `team12`.`holiday_list` (YEAR, NAME, DATE) VALUES ('2019', 'National Day', '2019-08-09');
INSERT INTO `team12`.`holiday_list` (YEAR, NAME, DATE) VALUES ('2019', 'Hari Raya Haji', '2019-08-12');
INSERT INTO `team12`.`holiday_list` (YEAR, NAME, DATE) VALUES ('2019', 'Deepavali', '2019-10-27');
INSERT INTO `team12`.`holiday_list` (YEAR, NAME, DATE) VALUES ('2019', 'Christmas', '2019-12-25');

INSERT INTO team12.designation_leave VALUES ('1','1','18');
INSERT INTO team12.designation_leave VALUES ('2','1','18');
INSERT INTO team12.designation_leave VALUES ('3','1','14');
INSERT INTO team12.designation_leave VALUES ('1','3','10.0');
INSERT INTO team12.designation_leave VALUES ('2','3','10.0');
INSERT INTO team12.designation_leave VALUES ('3','3','10.0');
INSERT INTO team12.designation_leave VALUES ('1','2','60');
INSERT INTO team12.designation_leave VALUES ('2','2','60');
INSERT INTO team12.designation_leave VALUES ('3','2','60');
INSERT INTO team12.designation_leave VALUES ('1','4','10.0');
INSERT INTO team12.designation_leave VALUES ('2','4','10.0');
INSERT INTO team12.designation_leave VALUES ('3','4','10.0');

INSERT INTO team12.staff (ID, NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('1','Bigboss', '1', '777', '1');
INSERT INTO team12.staff (ID, NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('2','BossOne', '1', '111', '1');
INSERT INTO team12.staff (ID, NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('3','BossTwo', '1', '333', '1');
INSERT INTO team12.staff (ID, NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('4','BossAdmin', '1', '666', '1');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Bob', '2', '123', '2');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Becky', '2', '234', '2');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Charles', '2', '456', '2');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Charlotte', '2', '678', '2');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Dave', '2', '789', '2');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Danny', '2', '910', '3');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Tim', '2', '222', '3');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Tilly', '2', '233', '3');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Tom', '2', '564', '3');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Lorraine', '2', '564', '3');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Ahmad', '3', '222', '4');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Weiming', '3', '233', '4');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Muthu', '3', '564', '4');
INSERT INTO team12.staff (NAME, DESIGNATION_ID, PASSWORD, BOSS_ID) VALUES ('Siti', '3', '564', '4');



INSERT INTO team12.leave_balance VALUES ('1','1','18');
INSERT INTO team12.leave_balance VALUES ('2','1','60');
INSERT INTO team12.leave_balance VALUES ('3','1','10');
INSERT INTO team12.leave_balance VALUES ('4','1','10');
INSERT INTO team12.leave_balance VALUES ('1','2','16');
INSERT INTO team12.leave_balance VALUES ('2','2','60');
INSERT INTO team12.leave_balance VALUES ('1','3','18');
INSERT INTO team12.leave_balance VALUES ('2','3','60');
INSERT INTO team12.leave_balance VALUES ('1','4','18');
INSERT INTO team12.leave_balance VALUES ('2','4','60');
INSERT INTO team12.leave_balance VALUES ('1','5','10');
INSERT INTO team12.leave_balance VALUES ('2','5','60');
INSERT INTO team12.leave_balance VALUES ('1','6','15');
INSERT INTO team12.leave_balance VALUES ('2','6','60');
INSERT INTO team12.leave_balance VALUES ('1','7','12');
INSERT INTO team12.leave_balance VALUES ('2','7','60');
INSERT INTO team12.leave_balance VALUES ('1','8','18');
INSERT INTO team12.leave_balance VALUES ('2','8','60');
INSERT INTO team12.leave_balance VALUES ('1','9','18');
INSERT INTO team12.leave_balance VALUES ('2','9','60');
INSERT INTO team12.leave_balance VALUES ('1','10','15');
INSERT INTO team12.leave_balance VALUES ('2','10','60');
INSERT INTO team12.leave_balance VALUES ('1','11','18');
INSERT INTO team12.leave_balance VALUES ('2','11','60');
INSERT INTO team12.leave_balance VALUES ('1','12','18');
INSERT INTO team12.leave_balance VALUES ('2','12','60');
INSERT INTO team12.leave_balance VALUES ('1','13','18');
INSERT INTO team12.leave_balance VALUES ('2','13','60');
INSERT INTO team12.leave_balance VALUES ('1','14','18');
INSERT INTO team12.leave_balance VALUES ('2','14','60');
INSERT INTO team12.leave_balance VALUES ('1','15','14');
INSERT INTO team12.leave_balance VALUES ('2','15','60');
INSERT INTO team12.leave_balance VALUES ('1','16','14');
INSERT INTO team12.leave_balance VALUES ('2','16','60');
INSERT INTO team12.leave_balance VALUES ('1','17','14');
INSERT INTO team12.leave_balance VALUES ('2','17','60');
INSERT INTO team12.leave_balance VALUES ('1','18','14');
INSERT INTO team12.leave_balance VALUES ('2','18','60');
INSERT INTO team12.leave_balance VALUES ('3','2','10');
INSERT INTO team12.leave_balance VALUES ('4','2','10');
INSERT INTO team12.leave_balance VALUES ('3','3','10');
INSERT INTO team12.leave_balance VALUES ('4','3','10');
INSERT INTO team12.leave_balance VALUES ('3','4','10');
INSERT INTO team12.leave_balance VALUES ('4','4','10');
INSERT INTO team12.leave_balance VALUES ('3','5','10');
INSERT INTO team12.leave_balance VALUES ('4','5','10');
INSERT INTO team12.leave_balance VALUES ('3','6','10');
INSERT INTO team12.leave_balance VALUES ('4','6','10');
INSERT INTO team12.leave_balance VALUES ('3','7','10');
INSERT INTO team12.leave_balance VALUES ('4','7','10');
INSERT INTO team12.leave_balance VALUES ('3','8','10');
INSERT INTO team12.leave_balance VALUES ('4','8','10');
INSERT INTO team12.leave_balance VALUES ('3','9','10');
INSERT INTO team12.leave_balance VALUES ('4','9','10');
INSERT INTO team12.leave_balance VALUES ('3','10','10');
INSERT INTO team12.leave_balance VALUES ('4','10','10');
INSERT INTO team12.leave_balance VALUES ('3','11','10');
INSERT INTO team12.leave_balance VALUES ('4','11','10');
INSERT INTO team12.leave_balance VALUES ('3','12','10');
INSERT INTO team12.leave_balance VALUES ('4','12','10');
INSERT INTO team12.leave_balance VALUES ('3','13','10');
INSERT INTO team12.leave_balance VALUES ('4','13','10');
INSERT INTO team12.leave_balance VALUES ('3','14','10');
INSERT INTO team12.leave_balance VALUES ('4','14','10');
INSERT INTO team12.leave_balance VALUES ('3','15','10');
INSERT INTO team12.leave_balance VALUES ('4','15','10');
INSERT INTO team12.leave_balance VALUES ('3','16','10');
INSERT INTO team12.leave_balance VALUES ('4','16','10');
INSERT INTO team12.leave_balance VALUES ('3','17','10');
INSERT INTO team12.leave_balance VALUES ('4','17','10');
INSERT INTO team12.leave_balance VALUES ('3','18','10');
INSERT INTO team12.leave_balance VALUES ('4','18','10');

INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('2', '2019-01-09','2019-01-10', 'holiday','NIL','98765432','1','APPROVED','ENJOY your Holiday.');
INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('5', '2019-02-22','2019-02-25', 'holiday','NIL','98765432','1','APPROVED','ENJOY your Holiday.');
INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('5', '2019-03-13','2019-03-15', 'holiday','NIL','98765432','1','APPLIED','too much holiday already');
INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('5', '2019-04-09','2019-04-09', 'PTM','NIL','98765432','1','CANCELLED','pending');
INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('5', '2019-05-02','2019-05-03', 'tired from working','NIL','98765432','1','DELETED','pending');
INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('5', '2019-07-22','2019-07-24', 'tired from working','NIL','98765432','1','APPLIED','pending');
INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('6', '2019-07-24','2019-07-26', 'take care of mom','Sandy to take over','98765431','1','APPLIED', 'pending');
INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('7', '2019-07-25','2019-07-29', 'holiday','NIL','98765455','1','APPROVED','ENJOY your Holiday.');
INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('7', '2019-09-11','2019-09-13', 'holiday','NIL','98765333','1','APPLIED','pending');
INSERT INTO team12.leave_application (STAFF_ID, START_DATE, END_DATE, REASON, WORK_INSTRUCTION, CONTACT, LEAVETYPE_ID, STATUS, COMMENT)
VALUES ('10', '2019-09-11','2019-09-13', 'holiday','NIL','98765333','1','APPLIED','pending');



ALTER TABLE `team12`.`staff` 
ADD COLUMN `EMAIL` VARCHAR(255) NULL AFTER `BOSS_ID`;
UPDATE `team12`.`staff` SET `EMAIL` = 'fairytukpan@gmail.com' WHERE (`ID` = '5');
UPDATE `team12`.`staff` SET `EMAIL` = 'elfie.me@gmail.com' WHERE (`ID` = '2');