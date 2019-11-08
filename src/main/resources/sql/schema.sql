CREATE TABLE USER (
	ID LONG GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL, 
	USERNAME VARCHAR(50) NOT NULL, 
	PASSWORD VARCHAR(100) NOT NULL, 
	ENABLED BOOLEAN NOT NULL, 
	LASTPASSWORDRESETDATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, 
	MENU VARCHAR(200) NOT NULL, 
	CONSTRAINT pk_USER PRIMARY KEY (ID)
);

CREATE INDEX IDX_USERNAME_USER ON USER (USERNAME);

CREATE TABLE AUTHORITY (
	ID LONG GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL, 
	NAME VARCHAR(50) NOT NULL,
	CONSTRAINT pk_AUTHORITY PRIMARY KEY (ID)
);

CREATE TABLE USER_AUTHORITY (
	USER_ID LONG NOT NULL, 
	AUTHORITY_ID LONG NOT NULL, 
	CONSTRAINT fk_USER_AUTHORITY_USER_ID FOREIGN KEY (USER_ID) REFERENCES USER (ID), 
	CONSTRAINT fk_USER_AUTHORITY_AUTHORITY_ID FOREIGN KEY (AUTHORITY_ID) REFERENCES AUTHORITY (ID)
);

CREATE INDEX IDX_USER_AUTHORITY_USER_ID ON USER_AUTHORITY (USER_ID);
CREATE INDEX IDX_USER_AUTHORITY_AUTHORITY_ID ON USER_AUTHORITY (AUTHORITY_ID);