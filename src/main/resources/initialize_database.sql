drop database nfz;

create database nfz;

use nfz;

create table users (
    userID int primary key,
    login varchar(20),
    password varchar(20),
    name varchar(20),
    surname varchar(20)
);

create table patients (
    userID int,
    adres varchar(20),
    foreign key (userID) references users(userID)
);

create table doctors (
    userID int,
    specialization varchar(20),
    foreign key (userID) references users(userID)
);

create table visits (
    visitId int primary key auto_increment,
    date date,
    time time,
    doctorId int not null,
    patientId int,
    foreign key (doctorId) references users(userID),
    foreign key (patientId) references users(userID)
);

insert into users(userID, login, password, name, surname) VALUES (1, 'kowal', 'dupa', 'Jan', 'Kowalski');
insert into patients(userID, adres) VALUES (1, 'Dupna 123');
insert into users(userID, login, password, name, surname) VALUES (2, 'malina', 'dupa', 'Adam', 'Malina');
insert into doctors(userID, specialization) VALUES (2, 'kardiolog');
insert into users(userID, login, password, name, surname) VALUES (3, 'gorzala', 'dupa', 'Marian', 'Gorzala');
insert into doctors(userID, specialization) VALUES (3, 'chirurg');

insert into users(userID, login, password, name, surname) VALUES (4, 'aniaderm', 'dupa', 'Anna', 'Kowalska');
insert into doctors(userID, specialization) VALUES (4, 'dermatolog');

insert into visits(visitId, date, time_from, time_to, type, doctorId, patientId) VALUES (1, '2023-12-12', '15:00', '15:15', 'PORADA', 4, 1)