
drop table Rent cascade constraints;
drop table Vehicle cascade constraints;
drop table Return cascade constraints;
drop table Reservation cascade constraints;
drop table VehicleType cascade constraints;
drop table Customer cascade constraints;
drop table Branch cascade constraints;
drop table TimePeriod cascade constraints;

create table VehicleType
    (vtname varchar(40) not null,
    features char(40),
    wrate float,
    drate float,
    hrate float,
    wirate float,
    dirate float,
    hirate float,
    krate float,
    primary key (vtname)); 

create table Customer
    (cellphone char(14),
    name varchar(40),
    address varchar(40),
    dlicense char(7),
    primary key (dlicense)); 

create table Branch 
    (location varchar(40) not null,
    city varchar(40) not null,
    primary key(location, city));

create table TimePeriod 
    (fromDateTime timestamp,
    toDateTime timestamp,
    primary key(fromDateTime, toDateTime));

create table Reservation
    (confNo char(10) not null,
    vtname varchar(40),
    dlicense char(7),
    fromDateTime timestamp,
    toDateTime timestamp,
    primary key (confNo),
    foreign key (vtname) references VehicleType,
    foreign key (dlicense) references Customer,
    foreign key (fromDateTime, toDateTime) references TimePeriod); 

create table Vehicle
    (vlicense char(7),
    make varchar(40),
    model varchar(40),
    year char(4),
    color varchar(20),
    odometer int,
    status varchar(40),
    vtname varchar(40),
    location varchar(40),
    city varchar(40),
    primary key (vlicense),
    foreign key (location, city) references Branch,
    foreign key (vtname) references VehicleType);

create table Rent
    (rid char(10) not null,
    vlicense char(7),
    dlicense char(7),
    fromDateTime timestamp,
    toDateTime timestamp,
    odometer int,
    cardName varchar(50),
    cardNo char(10),
    ExpDate char(5),
    confNo char(10),
    primary key (rid),
    foreign key (vlicense) references Vehicle,
    foreign key (dlicense) references Customer,
    foreign key (fromDateTime, toDateTime) references TimePeriod,
    foreign key (confNo) references Reservation);  

create table Return
    (rid char(10) not null,
    return_dateTime timestamp,
    odometer int,
    fulltank char(1),
    value float,
    primary key (rid),
    foreign key (rid) references Rent); 

ALTER TABLE Vehicle 
    ADD CONSTRAINT check_status
    CHECK(status IN ('available', 'rented', 'maintenence_shop')); 

insert into VehicleType values ('Economy', 'AC', 20.99, 30.00, 35.40, 35.40, 30.00, 20.99, 60.00);
insert into VehicleType values ('Compact', 'Radio', 30.99, 40.00, 55.40, 65.40, 70.00, 80.99, 90.00);

insert into Branch values ('Location A', 'Vancouver');
insert into Branch values ('Location B', 'Vancouver');
insert into Branch values ('Location A', 'Richmond');
insert into Branch values ('Location B', 'Richmond');

    insert into Vehicle values ('7456045', 'Honda', 'Fit', '2017', 'orange', 10, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('2834344', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('9729239', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('9677675', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('3126039', 'Honda', 'Fit', '2009', 'purple', 30, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('9107066', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('7908733', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('7851114', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('1799982', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('6053561', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location A', 'Vancouver');

    insert into Vehicle values ('5943592', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('9031900', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('7012022', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('6544043', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('9385477', 'Honda', 'Fit', '2009', 'black', 30, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('8261494', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('6649209', 'Honda', 'Fit', '2012', 'blue', 30, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('0292059', 'Honda', 'Fit', '2012', 'blue', 30, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('1362600', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('6087874', 'Honda', 'Fit', '2012', 'purple', 30, 'available', 'Compact', 'Location A', 'Vancouver');

    insert into Vehicle values ('0957670', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('2620166', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('8606763', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('2816021', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('5184774', 'Honda', 'Fit', '2009', 'black', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('9943317', 'Honda', 'Fit', '2012', 'purple', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('4498238', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('3180862', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('5346589', 'Honda', 'Fit', '2012', 'pink', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('7180672', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location B', 'Vancouver');

    insert into Vehicle values ('7118623', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('2938322', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('1799270', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('9979910', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('5427124', 'Honda', 'Fit', '2009', 'black', 30, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('5737177', 'Honda', 'Fit', '2012', 'blue', 30, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('7898672', 'Honda', 'Fit', '2012', 'purple', 30, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('8636874', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('7238706', 'Honda', 'Fit', '2012', 'purple', 30, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('9782499', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Compact', 'Location B', 'Vancouver');

    insert into Vehicle values ('5453091', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('4545469', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('7616189', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('0042176', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('3733433', 'Honda', 'Fit', '2009', 'black', 30, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('3036746', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('8177843', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('0501095', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('1820809', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('8377445', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location A', 'Richmond');

    insert into Vehicle values ('1195790', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('1784509', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('7472442', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('0412688', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('2974944', 'Honda', 'Fit', '2009', 'black', 30, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('5248184', 'Honda', 'Fit', '2012', 'blue', 30, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('7333070', 'Honda', 'Fit', '2012', 'gray', 30, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('0760928', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('1447169', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('2959656', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Compact', 'Location A', 'Richmond');

    insert into Vehicle values ('3537841', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('8948967', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('4658801', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('7072560', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('0063263', 'Honda', 'Fit', '2009', 'pink', 30, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('3004244', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('7201925', 'Honda', 'Fit', '2012', 'purple', 30, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('5038713', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('6614909', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('5271908', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location B', 'Richmond');

    insert into Vehicle values ('5693007', 'Honda', 'Fit', '2017', 'pink', 10, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('3315958', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('4290904', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('1645772', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('2284616', 'Honda', 'Fit', '2009', 'black', 30, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('4417149', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('4197770', 'Honda', 'Fit', '2012', 'gray', 30, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('9696710', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('9830213', 'Honda', 'Fit', '2012', 'red', 30, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('5324094', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Compact', 'Location B', 'Richmond');

insert into Customer values('(435) 382-7385', 'Ross Geller', '827 Elgin Street', '3262337');
insert into Customer values('(232) 732-9709', 'Rachel Green', '1137 Runnymede Rd', '6541761');
insert into Customer values('(875) 677-0226', 'Phoebe Buffay', '411 Eglinton Avenue', '9146347');
insert into Customer values('(678) 423-6311', 'Monica Geller', '3535 Alness Street', '1045507');
insert into Customer values('(573) 463-4274', 'Chandler Bing', '3933 Jade St', '5017504');
insert into Customer values('(604) 360-2436', 'Joey Tribbiani', '210 Victoria Park Ave', '1564210');
insert into Customer values('(931) 316-5736', 'Christian Stephens', '3894 2nd Avenue', '2117530');
insert into Customer values('(896) 232-9055', 'Gilberto Paul', '4917 René-Lévesque Blvd', '2314973');

insert into TimePeriod values(TO_TIMESTAMP('2000-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2000-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2012-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2016-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2016-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2017-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2017-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2018-10-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2018-10-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2019-11-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-11-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2019-11-21 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-12-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));

insert into Reservation values('9305832196', 'Economy', '3262337', TO_TIMESTAMP('2000-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2000-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('1245758654', 'Economy', '6541761', TO_TIMESTAMP('2012-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('6103768285', 'Economy', '3262337', TO_TIMESTAMP('2012-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('0085903164', 'Compact', '6541761', TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('2727221526', 'Compact', '3262337', TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('9216187883', 'Economy', '9146347', TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('7769812761', 'Economy', '2117530', TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('5073968130', 'Compact', '1045507', TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('1700718576', 'Economy', '1564210', TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('2329952316', 'Economy', '5017504', TO_TIMESTAMP('2016-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2016-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('8823572224', 'Compact', '1564210', TO_TIMESTAMP('2017-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2017-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('2213387480', 'Economy', '2117530', TO_TIMESTAMP('2018-10-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2018-10-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('3015397447', 'Economy', '1045507', TO_TIMESTAMP('2019-11-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-11-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('6582562957', 'Compact', '2314973', TO_TIMESTAMP('2019-11-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-11-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into Reservation values('5539381414', 'Compact', '1045507', TO_TIMESTAMP('2019-11-21 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-12-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));

insert into Rent values('6MCDIX93QI', '7456045', '3262337', TO_TIMESTAMP('2000-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2000-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Ross Geller', '8650089309', '11/21', '9305832196');
insert into Rent values('98AY5DLETU', '2834344', '3262337', TO_TIMESTAMP('2012-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Ross Geller', '8650089309', '11/21', '6103768285');
insert into Rent values('T08IWJS15F', '9729239', '6541761', TO_TIMESTAMP('2012-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Rachel Green', '8194168926', '11/21', '1245758654');
insert into Rent values('FDTDIXMZGZ', '9677675', '6541761', TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Rachel Green', '8194168926', '11/21', '0085903164');
insert into Rent values('4XTOGXCGSN', '3126039', '3262337', TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Ross Geller', '8650089309', '11/21', '2727221526');
insert into Rent values('8DV72856PQ', '9107066', '9146347', TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Phoebe Buffay', '8141789885', '11/21', '9216187883');
insert into Rent values('HZOGBIMJI4', '7908733', '2117530', TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Christian Stephens', '0650562184', '11/21', '7769812761');
insert into Rent values('G5NYRKLK0F', '7851114', '1045507', TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Monica Geller', '8954331356', '11/21', '5073968130');
insert into Rent values('7EUT0KIRK1', '1799982', '1564210', TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Joey Tribbiani', '1605606152', '11/21', '1700718576');
insert into Rent values('27SDXBVQCM', '6053561', '5017504', TO_TIMESTAMP('2016-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2016-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Chandler Bing', '8272642169', '11/21', '2329952316');
insert into Rent values('4CWDBOYUKE', '7118623', '1564210', TO_TIMESTAMP('2017-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2017-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Joey Tribbiani', '1605606152', '11/21', '8823572224');
insert into Rent values('FBEFE403B4', '2938322', '2117530', TO_TIMESTAMP('2018-10-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2018-10-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Christian Stephens', '0650562184', '11/21', '2213387480');
insert into Rent values('CFXE0XPUB8', '1799270', '1045507', TO_TIMESTAMP('2019-11-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-11-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Monica Geller', '8954331356', '11/21', '3015397447');
insert into Rent values('VGQ8ZS0DRB', '9979910', '2314973', TO_TIMESTAMP('2019-11-21 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-12-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Gilberto Paul', '1561197457', '11/21', '6582562957');
insert into Rent values('L18PASC4WX', '5427124', '1045507', TO_TIMESTAMP('2019-11-21 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-12-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Monica Geller', '8954331356', '11/21', '5539381414');

insert into Return values('6MCDIX93QI', TO_TIMESTAMP('2000-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 200.99);
insert into Return values('98AY5DLETU', TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 250.99);
insert into Return values('T08IWJS15F', TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 320.99);
insert into Return values('FDTDIXMZGZ', TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 310.99);
insert into Return values('4XTOGXCGSN', TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 300.99);
insert into Return values('8DV72856PQ', TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 210.99);
insert into Return values('HZOGBIMJI4', TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 220.99);
insert into Return values('G5NYRKLK0F', TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 230.99);
insert into Return values('7EUT0KIRK1', TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 240.99);
insert into Return values('27SDXBVQCM', TO_TIMESTAMP('2016-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 250.99);

insert into Return values('4CWDBOYUKE', TO_TIMESTAMP('2017-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 260.99);
insert into Return values('FBEFE403B4', TO_TIMESTAMP('2018-10-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 210.99);
insert into Return values('CFXE0XPUB8', TO_TIMESTAMP('2019-06-20 11:56:18', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 270.99);

-- commit;