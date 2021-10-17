CREATE TABLE IF NOT EXISTS public.employee
(
    id        character varying(64)  NOT NULL,
    email     character varying(128) NOT NULL,
    name      character varying(128) NOT NULL,
    password  character varying(500) NOT NULL,
    createdAt timestamp with time zone,
    CONSTRAINT employee_pk PRIMARY KEY (id),
    CONSTRAINT employee_uk UNIQUE (email)

);

CREATE TABLE IF NOT EXISTS public.admin
(
    employee_id character varying(64) NOT NULL,
    createdAt   timestamp with time zone,
    CONSTRAINT admin_pk PRIMARY KEY (employee_id),
    CONSTRAINT admin_employee_fk FOREIGN KEY (employee_id) REFERENCES employee (id) MATCH simple ON UPDATE cascade ON DELETE NO action

);

CREATE TABLE IF NOT EXISTS public.attendance_broadsheet
(
    id          integer                NOT NULL,
    employee_id character varying(64)  NOT NULL,
    date        character varying(100) NOT NULL,
    time        character varying(15)  NOT NULL,
    CONSTRAINT attendance_broadsheet_pk PRIMARY KEY (id),
    CONSTRAINT attendance_broadsheet_uk UNIQUE (date),
    CONSTRAINT attendance_broadsheet_employee_fk FOREIGN KEY (employee_id) REFERENCES employee (id) MATCH simple ON UPDATE cascade ON DELETE NO action

);

INSERT INTO public.employee (id, email, name, password, createdAt)
VALUES ('TESTGQJJ1',
        'admin@encentral.com',
        'Encentral Admin',
        'admin',
        CURRENT_TIMESTAMP);

INSERT INTO public.admin (employee_id, createdat)
VALUES ('TESTGQJJ1',
        CURRENT_TIMESTAMP)


