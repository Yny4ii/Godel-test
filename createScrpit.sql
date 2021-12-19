
CREATE TABLE IF NOT EXISTS public.expense
(
    id bigint NOT NULL DEFAULT nextval('expense_id_seq'::regclass),
    amount double precision,
    local_date date,
    name character varying(255) COLLATE pg_catalog."default",
    category_id bigint,
    CONSTRAINT expense_pkey PRIMARY KEY (id),
    CONSTRAINT fkmvjm59reb5i075vu38bwcaqj6 FOREIGN KEY (category_id)
        REFERENCES public.category (category_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)



CREATE TABLE IF NOT EXISTS public.category
(
    category_id bigint NOT NULL DEFAULT nextval('category_category_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT category_pkey PRIMARY KEY (category_id)
)
