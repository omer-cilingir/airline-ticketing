CREATE TABLE public.airline_business
(
    id bigint NOT NULL,
    created_date timestamp without time zone,
    deleted boolean,
    modified_date timestamp without time zone,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT airline_business_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.airline_business
    OWNER to postgres;

CREATE TABLE public.airport
(
    id bigint NOT NULL,
    created_date timestamp without time zone,
    deleted boolean,
    modified_date timestamp without time zone,
    code character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT airport_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.airport
    OWNER to postgres;


CREATE TABLE public.fly_route
(
    id bigint NOT NULL,
    created_date timestamp without time zone,
    deleted boolean,
    modified_date timestamp without time zone,
    arrival timestamp without time zone,
    departure timestamp without time zone,
    from_id bigint,
    to_id bigint,
    CONSTRAINT fly_route_pkey PRIMARY KEY (id),
    CONSTRAINT fk1b0g6gegwlxam0ob767e45ljb FOREIGN KEY (to_id)
        REFERENCES public.airport (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk3ra0d3ii2xoauukunuetiiu2l FOREIGN KEY (from_id)
        REFERENCES public.airport (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.fly_route
    OWNER to postgres;

CREATE TABLE public.flight
(
    id bigint NOT NULL,
    created_date timestamp without time zone,
    deleted boolean,
    modified_date timestamp without time zone,
    flight_duration character varying(255) COLLATE pg_catalog."default",
    price numeric(20,5),
    remaining_ticket_count bigint,
    total_ticket_count bigint,
    airline_business_id bigint,
    fly_route_id bigint,
    initial_price numeric(20,5),
    CONSTRAINT flight_pkey PRIMARY KEY (id),
    CONSTRAINT fk5ngj71ubi3n2sscps301pkg6k FOREIGN KEY (fly_route_id)
        REFERENCES public.fly_route (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fknphsvnhiaxpswbde47kamkdv9 FOREIGN KEY (airline_business_id)
        REFERENCES public.airline_business (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.flight
    OWNER to postgres;

CREATE TABLE public.book_flight
(
    id bigint NOT NULL,
    created_date timestamp without time zone,
    deleted boolean,
    modified_date timestamp without time zone,
    status character varying(255) COLLATE pg_catalog."default",
    flight_entity_id bigint,
    price numeric(20,5),
    CONSTRAINT book_flight_pkey PRIMARY KEY (id),
    CONSTRAINT fk2k0viqwpi5krimxo2f0cevmlu FOREIGN KEY (flight_entity_id)
        REFERENCES public.flight (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.book_flight
    OWNER to postgres;

CREATE SEQUENCE public.hibernate_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.hibernate_sequence
    OWNER TO postgres;
