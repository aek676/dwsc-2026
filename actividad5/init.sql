CREATE TABLE IF NOT EXISTS public.student (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1),
    name text COLLATE pg_catalog."default",
    surnames text COLLATE pg_catalog."default",
    dni character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT student_pkey PRIMARY KEY (id)
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.student OWNER TO estudiante;

CREATE TABLE IF NOT EXISTS public.degree (
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1),
    code text COLLATE pg_catalog."default",
    name text COLLATE pg_catalog."default",
    programme text COLLATE pg_catalog."default",
    CONSTRAINT degree_pkey PRIMARY KEY (id)
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.degree OWNER TO estudiante;

CREATE TABLE IF NOT EXISTS public.student_degree (
    student_id bigint NOT NULL,
    degree_id bigint NOT NULL,
    CONSTRAINT student_degree_pkey PRIMARY KEY (student_id, degree_id),
    CONSTRAINT student_degree_ibfk_1 FOREIGN KEY (student_id) REFERENCES public.student (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT student_degree_ibfk_2 FOREIGN KEY (degree_id) REFERENCES public.degree (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.student_degree OWNER TO estudiante;

INSERT INTO public.student (dni, name, surnames) VALUES
    ('12345678V', 'Carlos', 'López Jiménez'),
    ('12345678G', 'César', 'González García');

INSERT INTO public.degree (code, name, programme) VALUES
    ('GRAD04015', 'Grado en Ingeniería Informática', 'Plan 2015'),
    ('MASTER7109', 'Máster en Tecnologías y Aplicaciones en Ingeniería Informática', NULL);

INSERT INTO public.student_degree (student_id, degree_id)
    SELECT s.id, d.id FROM public.student s, public.degree d
    WHERE s.dni = '12345678V' AND d.code = 'GRAD04015';
INSERT INTO public.student_degree (student_id, degree_id)
    SELECT s.id, d.id FROM public.student s, public.degree d
    WHERE s.dni = '12345678G' AND d.code = 'MASTER7109';
