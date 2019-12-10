--- CREATE TYPE gender AS ENUM ('M', 'K'); ---

DROP TABLE IF EXISTS patrons;

CREATE TABLE patrons(
	patron_id SERIAL PRIMARY KEY,
    patron_mail TEXT NOT NULL,
    patron_pass TEXT NOT NULL,
    patron_firstname TEXT NOT NULL,
    patron_lastname TEXT NOT NULL,
    patron_phone TEXT NOT NULL
);

INSERT INTO patrons(patron_mail, patron_pass, patron_firstname, patron_lastname, patron_phone) VALUES 
('kowalski@gmail.com', '1234', 'Jan', 'Kowalski', '725232123'),
('malecka@outlook.com', 'malecka', 'Joanna', 'Malecka', '582123123'),
('janek@yahoo.mail', 'janek', 'Jan', 'Michalski', '723123123');

DROP TABLE IF EXISTS proteges;

CREATE TABLE proteges(
	protege_id SERIAL PRIMARY KEY,
    protege_proton INTEGER DEFAULT NULL,
    --- protege_age SMALLINT NOT NULL, ---
    --- protege_gender gender NOT NULL, ---
    protege_firstname TEXT NOT NULL,
    protege_lastname TEXT NOT NULL,
    protege_phone TEXT NOT NULL,
    protege_mail TEXT NOT NULL,
    protege_pass TEXT NOT NULL,
    FOREIGNT KEY(protege_proton) REFERENCES patrons(patron_id) ON DELETE CASCADE
);


DROP TABLE IF EXISTS exams;

CREATE TABLE exams(
	exam_id SERIAL PRIMARY KEY,
    exam_protege TEXT NOT NULL,
    exam_weight TEXT NOT NULL,
    exam_glucose TEXT NOT NULL,
    exam_pressure TEXT NOT NULL,
    FOREIGNT KEY(exam_id) REFERENCES proteges(protege_id) ON DELETE CASCADE
);
