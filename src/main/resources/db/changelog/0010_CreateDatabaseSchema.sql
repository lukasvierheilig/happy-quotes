--liquibase formatted sql

--changeset lukas.vierheilig:0010_create_schema

create schema if not exists happy_quotes;

create table if not exists happy_quotes.quotes
(
    id      uuid default gen_random_uuid() primary key,
    text    text,
    qr_code bytea
);
