
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
    primary key(fromDateTime, toDateTime),
    CHECK (toDateTime > fromDateTime));

create table Reservation
    (confNo char(36) not null,
    vtname varchar(40),
    dlicense char(7),
    fromDateTime timestamp,
    toDateTime timestamp,
    primary key (confNo),
    location varchar(40),
    city varchar(40),
    foreign key (vtname) references VehicleType,
    foreign key (dlicense) references Customer,
    foreign key (fromDateTime, toDateTime) references TimePeriod,
    foreign key (location, city) references Branch,
    CHECK (toDateTime > fromDateTime)); 

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
    (rid char(36) not null,
    vlicense char(7),
    dlicense char(7),
    fromDateTime timestamp,
    toDateTime timestamp,
    odometer int,
    cardName varchar(50),
    cardNo char(16),
    ExpDate char(5),
    confNo char(36),
    primary key (rid),
    foreign key (vlicense) references Vehicle,
    foreign key (dlicense) references Customer,
    foreign key (fromDateTime, toDateTime) references TimePeriod,
    foreign key (confNo) references Reservation,
    CHECK (toDateTime > fromDateTime));  

create table Return
    (rid char(36) not null,
    return_dateTime timestamp,
    odometer int,
    fulltank char(1),
    value float,
    primary key (rid),
    foreign key (rid) references Rent); 

ALTER TABLE Vehicle 
    ADD CONSTRAINT check_status
    CHECK(status IN ('available', 'rented', 'maintenence_shop')); 

insert into VehicleType values ('Economy', 'AC', 20.99, 15.00, 3.40, 5.40, 3.00, 2.99, 0.50);
insert into VehicleType values ('Compact', 'Radio', 21.99, 16.00, 4.40, 6.00, 3.50, 3.50, 1.00);
insert into VehicleType values ('Mid-size', 'Radio', 31.99, 17.00, 5.40, 6.25, 3.75, 3.75, 1.25);
insert into VehicleType values ('SUV', 'AC', 32.99, 18.00, 6.40, 6.50, 4.00, 4.00, 1.50);
insert into VehicleType values ('Truck', 'Radio', 33.99, 19.00, 7.40, 7.00, 4.40, 4.50, 2.00);


insert into Branch values ('Location A', 'Vancouver');
insert into Branch values ('Location B', 'Vancouver');

insert into Branch values ('Location A', 'Richmond');
insert into Branch values ('Location B', 'Richmond');


    insert into Vehicle values ('7456045', 'Honda', 'Fit', '2017', 'orange', 10, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('2834344', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('9729239', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location A', 'Vancouver');
    insert into Vehicle values ('9677675', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'SUV', 'Location A', 'Vancouver');
    insert into Vehicle values ('3126039', 'Honda', 'Fit', '2009', 'pink', 30, 'available', 'Truck', 'Location A', 'Vancouver');
    insert into Vehicle values ('9107066', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('7908733', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('7851114', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location A', 'Vancouver');
    insert into Vehicle values ('1799982', 'Honda', 'Fit', '2012', 'purple', 30, 'available', 'SUV', 'Location A', 'Vancouver');
    insert into Vehicle values ('6053561', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Truck', 'Location A', 'Vancouver');

    insert into Vehicle values ('5943592', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('9031900', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('7012022', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location A', 'Vancouver');
    insert into Vehicle values ('6544043', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'SUV', 'Location A', 'Vancouver');
    insert into Vehicle values ('9385477', 'Honda', 'Fit', '2009', 'purple', 30, 'available', 'Truck', 'Location A', 'Vancouver');
    insert into Vehicle values ('8261494', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location A', 'Vancouver');
    insert into Vehicle values ('6649209', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'Compact', 'Location A', 'Vancouver');
    insert into Vehicle values ('0292059', 'Honda', 'Fit', '2012', 'blue', 30, 'available', 'Mid-size', 'Location A', 'Vancouver');
    insert into Vehicle values ('1362600', 'Honda', 'Fit', '2012', 'red', 30, 'available', 'SUV', 'Location A', 'Vancouver');
    insert into Vehicle values ('6087874', 'Honda', 'Fit', '2012', 'gray', 30, 'available', 'Truck', 'Location A', 'Vancouver');

    insert into Vehicle values ('0957670', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('2620166', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('8606763', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location B', 'Vancouver');
    insert into Vehicle values ('2816021', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'SUV', 'Location B', 'Vancouver');
    insert into Vehicle values ('5184774', 'Honda', 'Fit', '2009', 'green', 30, 'available', 'Truck', 'Location B', 'Vancouver');
    insert into Vehicle values ('9943317', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('4498238', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('3180862', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location B', 'Vancouver');
    insert into Vehicle values ('5346589', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'SUV', 'Location B', 'Vancouver');
    insert into Vehicle values ('7180672', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Truck', 'Location B', 'Vancouver');

    insert into Vehicle values ('7118623', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('2938322', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('1799270', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location B', 'Vancouver');
    insert into Vehicle values ('9979910', 'Toyota', 'Camry', '2010', 'red', 30, 'rented', 'SUV', 'Location B', 'Vancouver');
    insert into Vehicle values ('5427124', 'Honda', 'Fit', '2009', 'black', 30, 'rented', 'Truck', 'Location B', 'Vancouver');
    insert into Vehicle values ('5737177', 'Honda', 'Fit', '2012', 'blue', 30, 'available', 'Economy', 'Location B', 'Vancouver');
    insert into Vehicle values ('7898672', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Compact', 'Location B', 'Vancouver');
    insert into Vehicle values ('8636874', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location B', 'Vancouver');
    insert into Vehicle values ('7238706', 'Honda', 'Fit', '2012', 'gray', 30, 'available', 'SUV', 'Location B', 'Vancouver');
    insert into Vehicle values ('9782499', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Truck', 'Location B', 'Vancouver');

    insert into Vehicle values ('5453091', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('4545469', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('7616189', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location A', 'Richmond');
    insert into Vehicle values ('0042176', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'SUV', 'Location A', 'Richmond');
    insert into Vehicle values ('3733433', 'Honda', 'Fit', '2009', 'purple', 30, 'available', 'Truck', 'Location A', 'Richmond');
    insert into Vehicle values ('3036746', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('8177843', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('0501095', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Mid-size', 'Location A', 'Richmond');
    insert into Vehicle values ('1820809', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'SUV', 'Location A', 'Richmond');
    insert into Vehicle values ('8377445', 'Honda', 'Fit', '2012', 'pink', 30, 'available', 'Truck', 'Location A', 'Richmond');

    insert into Vehicle values ('1195790', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('1784509', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('7472442', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location A', 'Richmond');
    insert into Vehicle values ('0412688', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'SUV', 'Location A', 'Richmond');
    insert into Vehicle values ('2974944', 'Honda', 'Fit', '2009', 'black', 30, 'available', 'Truck', 'Location A', 'Richmond');
    insert into Vehicle values ('5248184', 'Honda', 'Fit', '2012', 'blue', 30, 'available', 'Economy', 'Location A', 'Richmond');
    insert into Vehicle values ('7333070', 'Honda', 'Fit', '2012', 'gray', 30, 'available', 'Compact', 'Location A', 'Richmond');
    insert into Vehicle values ('0760928', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location A', 'Richmond');
    insert into Vehicle values ('1447169', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'SUV', 'Location A', 'Richmond');
    insert into Vehicle values ('2959656', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Truck', 'Location A', 'Richmond');

    insert into Vehicle values ('3537841', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('8948967', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('4658801', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location B', 'Richmond');
    insert into Vehicle values ('7072560', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'SUV', 'Location B', 'Richmond');
    insert into Vehicle values ('0063263', 'Honda', 'Fit', '2009', 'pink', 30, 'available', 'Truck', 'Location B', 'Richmond');
    insert into Vehicle values ('3004244', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('7201925', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('5038713', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location B', 'Richmond');
    insert into Vehicle values ('6614909', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'SUV', 'Location B', 'Richmond');
    insert into Vehicle values ('5271908', 'Honda', 'Fit', '2012', 'red', 30, 'available', 'Truck', 'Location B', 'Richmond');

    insert into Vehicle values ('5693007', 'Honda', 'Fit', '2017', 'black', 10, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('3315958', 'Toyota', 'Camry', '2011', 'red', 40, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('4290904', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location B', 'Richmond');
    insert into Vehicle values ('1645772', 'Toyota', 'Camry', '2010', 'red', 30, 'available', 'SUV', 'Location B', 'Richmond');
    insert into Vehicle values ('2284616', 'Honda', 'Fit', '2009', 'black', 30, 'available', 'Truck', 'Location B', 'Richmond');
    insert into Vehicle values ('4417149', 'Honda', 'Fit', '2012', 'green', 30, 'available', 'Economy', 'Location B', 'Richmond');
    insert into Vehicle values ('4197770', 'Honda', 'Fit', '2012', 'gray', 30, 'available', 'Compact', 'Location B', 'Richmond');
    insert into Vehicle values ('9696710', 'Honda', 'Fit', '2012', 'black', 30, 'available', 'Mid-size', 'Location B', 'Richmond');
    insert into Vehicle values ('9830213', 'Honda', 'Fit', '2012', 'pink', 30, 'available', 'SUV', 'Location B', 'Richmond');
    insert into Vehicle values ('5324094', 'Honda', 'Fit', '2012', 'orange', 30, 'available', 'Truck', 'Location B', 'Richmond');

insert into Customer values('(435) 382-7385', 'Ross Geller', '827 Elgin Street', '3262337');
insert into Customer values('(232) 732-9709', 'Rachel Green', '1137 Runnymede Rd', '6541761');
insert into Customer values('(875) 677-0226', 'Phoebe Buffay', '411 Eglinton Avenue', '9146347');
insert into Customer values('(678) 423-6311', 'Monica Geller', '3535 Alness Street', '1045507');
insert into Customer values('(573) 463-4274', 'Chandler Bing', '3933 Jade St', '5017504');
insert into Customer values('(604) 360-2436', 'Joey Tribbiani', '210 Victoria Park Ave', '1564210');
insert into Customer values('(931) 316-5736', 'Christian Stephens', '3894 2nd Avenue', '2117530');
insert into Customer values('(896) 232-9055', 'Gilberto Paul', '4917 Bobby Brown Blvd', '2314973');

insert into TimePeriod values(TO_TIMESTAMP('2010-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2010-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2012-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2016-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2016-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2017-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2017-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2018-10-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2018-10-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2019-11-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-11-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));
insert into TimePeriod values(TO_TIMESTAMP('2019-11-21 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-12-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'));

insert into Reservation values('a7088112-0ee5-11ea-8d71-362b9e155667', 'Economy', '3262337', TO_TIMESTAMP('2010-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2010-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Vancouver');
insert into Reservation values('a7088234-0ee5-11ea-8d71-362b9e155667', 'Economy', '6541761', TO_TIMESTAMP('2012-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Vancouver');
insert into Reservation values('a7088388-0ee5-11ea-8d71-362b9e155667', 'Economy', '3262337', TO_TIMESTAMP('2012-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Richmond');
insert into Reservation values('a7088694-0ee5-11ea-8d71-362b9e155667', 'Compact', '6541761', TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Vancouver');
insert into Reservation values('a70887e8-0ee5-11ea-8d71-362b9e155667', 'Compact', '3262337', TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Vancouver');
insert into Reservation values('a708891e-0ee5-11ea-8d71-362b9e155667', 'Economy', '9146347', TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Richmond');
insert into Reservation values('a7088a40-0ee5-11ea-8d71-362b9e155667', 'Economy', '2117530', TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Vancouver');
insert into Reservation values('a7088b76-0ee5-11ea-8d71-362b9e155667', 'Compact', '1045507', TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Richmond');
insert into Reservation values('a7088ca2-0ee5-11ea-8d71-362b9e155667', 'Economy', '1564210', TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Vancouver');
insert into Reservation values('a7088dce-0ee5-11ea-8d71-362b9e155667', 'Economy', '5017504', TO_TIMESTAMP('2016-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2016-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Richmond');
insert into Reservation values('a708909e-0ee5-11ea-8d71-362b9e155667', 'Compact', '1564210', TO_TIMESTAMP('2017-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2017-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Richmond');
insert into Reservation values('a70891d4-0ee5-11ea-8d71-362b9e155667', 'Economy', '2117530', TO_TIMESTAMP('2018-10-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2018-10-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Vancouver');

insert into Reservation values('a70893a0-0ee5-11ea-8d71-362b9e155667', 'Economy', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Vancouver');
insert into Reservation values('a70894ea-0ee5-11ea-8d71-362b9e155667', 'Compact', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Richmond');
insert into Reservation values('0362627371', 'Mid-size', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Richmond');

insert into Reservation values('a7089616-0ee5-11ea-8d71-362b9e155667', 'SUV', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Richmond');
insert into Reservation values('a7089990-0ee5-11ea-8d71-362b9e155667', 'Truck', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Vancouver');
insert into Reservation values('a7089aee-0ee5-11ea-8d71-362b9e155667', 'Economy', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Vancouver');
insert into Reservation values('a7089c1a-0ee5-11ea-8d71-362b9e155667', 'Compact', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Richmond');
insert into Reservation values('a7089d46-0ee5-11ea-8d71-362b9e155667', 'Mid-size', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Richmond');
insert into Reservation values('a7089e72-0ee5-11ea-8d71-362b9e155667', 'SUV', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Vancouver');
insert into Reservation values('a7089f94-0ee5-11ea-8d71-362b9e155667', 'Truck', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Vancouver');
insert into Reservation values('ed10754e-0ee9-11ea-8d71-362b9e155667', 'Economy', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Richmond');
insert into Reservation values('ed1077f6-0ee9-11ea-8d71-362b9e155667', 'Compact', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Richmond');
insert into Reservation values('ed107954-0ee9-11ea-8d71-362b9e155667', 'Mid-size', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Richmond');
insert into Reservation values('ed107a8a-0ee9-11ea-8d71-362b9e155667', 'SUV', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Richmond');
insert into Reservation values('ed107bb6-0ee9-11ea-8d71-362b9e155667', 'Truck', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location A', 'Vancouver');

insert into Reservation values('ed107ce2-0ee9-11ea-8d71-362b9e155667', 'Economy', '1045507', TO_TIMESTAMP('2019-11-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-11-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Vancouver');
insert into Reservation values('ed107e18-0ee9-11ea-8d71-362b9e155667', 'Compact', '2314973', TO_TIMESTAMP('2019-11-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-11-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Richmond');
insert into Reservation values('ed1080e8-0ee9-11ea-8d71-362b9e155667', 'Compact', '1045507', TO_TIMESTAMP('2019-11-21 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-12-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 'Location B', 'Vancouver');


insert into Rent values('a70849fe-0ee5-11ea-8d71-362b9e155667', '7456045', '3262337', TO_TIMESTAMP('2010-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2010-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Ross Geller', '5408017193962468', '11/21', 'a7088112-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a7084d82-0ee5-11ea-8d71-362b9e155667', '2834344', '3262337', TO_TIMESTAMP('2012-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Ross Geller', '5408017193962468', '11/21', 'a7088388-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a708512e-0ee5-11ea-8d71-362b9e155667', '9677675', '6541761', TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Rachel Green', '3868395659987364', '11/21', 'a7088694-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a7085296-0ee5-11ea-8d71-362b9e155667', '3126039', '3262337', TO_TIMESTAMP('2013-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Ross Geller', '5408017193962468', '11/21', 'a70887e8-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a70853c2-0ee5-11ea-8d71-362b9e155667', '9107066', '9146347', TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Phoebe Buffay', '9518492280392358', '11/21', 'a708891e-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a70854ee-0ee5-11ea-8d71-362b9e155667', '7908733', '2117530', TO_TIMESTAMP('2014-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Christian Stephens', '1786301858255509', '11/21', 'a7088a40-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a708561a-0ee5-11ea-8d71-362b9e155667', '7851114', '1045507', TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Monica Geller', '6078924321181467', '11/21', 'a7088b76-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a708573c-0ee5-11ea-8d71-362b9e155667', '1799982', '1564210', TO_TIMESTAMP('2015-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Joey Tribbiani', '4926532043218177', '11/21', 'a7088ca2-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a7085a52-0ee5-11ea-8d71-362b9e155667', '6053561', '5017504', TO_TIMESTAMP('2016-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2016-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Chandler Bing', '8272642169', '11/21', 'a7088dce-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a7085caa-0ee5-11ea-8d71-362b9e155667', '7118623', '1564210', TO_TIMESTAMP('2017-06-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2017-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Joey Tribbiani', '4926532043218177', '11/21', 'a708909e-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a7085e1c-0ee5-11ea-8d71-362b9e155667', '2938322', '2117530', TO_TIMESTAMP('2018-10-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2018-10-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Christian Stephens', '1786301858255509', '11/21', 'a70891d4-0ee5-11ea-8d71-362b9e155667');


insert into Rent values('a7085f52-0ee5-11ea-8d71-362b9e155667', '7456045', '3262337', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Ross Geller', '5408017193962468', '11/21', 'a70893a0-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a708607e-0ee5-11ea-8d71-362b9e155667', '2834344', '3262337', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Ross Geller', '5408017193962468', '11/21', 'a70894ea-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a70861aa-0ee5-11ea-8d71-362b9e155667', '9729239', '6541761', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Rachel Green', '3868395659987364', '11/21', '0362627371');
insert into Rent values('a708648e-0ee5-11ea-8d71-362b9e155667', '9677675', '6541761', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Rachel Green', '3868395659987364', '11/21', 'a7089616-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a70865d8-0ee5-11ea-8d71-362b9e155667', '3126039', '3262337', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Ross Geller', '5408017193962468', '11/21', 'a7089990-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a708670e-0ee5-11ea-8d71-362b9e155667', '9107066', '9146347', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Phoebe Buffay', '9518492280392358', '11/21', 'a7089aee-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a708683a-0ee5-11ea-8d71-362b9e155667', '7908733', '2117530', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Christian Stephens', '1786301858255509', '11/21', 'a7089c1a-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a7086966-0ee5-11ea-8d71-362b9e155667', '7851114', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Monica Geller', '6078924321181467', '11/21', 'a7089d46-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a7086a92-0ee5-11ea-8d71-362b9e155667', '1799982', '1564210', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Joey Tribbiani', '4926532043218177', '11/21', 'a7089e72-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a7086eca-0ee5-11ea-8d71-362b9e155667', '6053561', '5017504', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Chandler Bing', '8272642169', '11/21', 'a7089f94-0ee5-11ea-8d71-362b9e155667');
insert into Rent values('a7087050-0ee5-11ea-8d71-362b9e155667', '7118623', '1564210', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Joey Tribbiani', '4926532043218177', '11/21', 'ed10754e-0ee9-11ea-8d71-362b9e155667');
insert into Rent values('a70876ae-0ee5-11ea-8d71-362b9e155667', '2938322', '2117530', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Christian Stephens', '1786301858255509', '11/21', 'ed1077f6-0ee9-11ea-8d71-362b9e155667');
insert into Rent values('a708780c-0ee5-11ea-8d71-362b9e155667', '1799270', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Monica Geller', '6078924321181467', '11/21', 'ed107954-0ee9-11ea-8d71-362b9e155667');
insert into Rent values('a7087938-0ee5-11ea-8d71-362b9e155667', '9979910', '2314973', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Gilberto Paul', '9927268436663419', '11/21', 'ed107a8a-0ee9-11ea-8d71-362b9e155667');
insert into Rent values('a7087a64-0ee5-11ea-8d71-362b9e155667', '5427124', '1045507', TO_TIMESTAMP('2019-04-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Monica Geller', '6078924321181467', '11/21', 'ed107bb6-0ee9-11ea-8d71-362b9e155667');

insert into Rent values('a7087b90-0ee5-11ea-8d71-362b9e155667', '1799270', '1045507', TO_TIMESTAMP('2019-11-05 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-11-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Monica Geller', '6078924321181467', '11/21', 'ed107ce2-0ee9-11ea-8d71-362b9e155667');
insert into Rent values('a7087eb0-0ee5-11ea-8d71-362b9e155667', '9979910', '2314973', TO_TIMESTAMP('2019-11-21 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-12-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Gilberto Paul', '9927268436663419', '11/21', 'ed107e18-0ee9-11ea-8d71-362b9e155667');
insert into Rent values('a7087ff0-0ee5-11ea-8d71-362b9e155667', '5427124', '1045507', TO_TIMESTAMP('2019-11-21 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), TO_TIMESTAMP('2019-12-01 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 10, 'Monica Geller', '6078924321181467', '11/21', 'ed1080e8-0ee9-11ea-8d71-362b9e155667');

insert into Return values('a70849fe-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2010-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 200.99);
insert into Return values('a7084d82-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2012-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 250.99);
insert into Return values('a708512e-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 310.99);
insert into Return values('a7085296-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2013-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 300.99);
insert into Return values('a70853c2-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 210.99);
insert into Return values('a70854ee-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2014-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 220.99);
insert into Return values('a708561a-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 230.99);
insert into Return values('a708573c-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2015-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 240.99);
insert into Return values('a7085a52-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2016-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 250.99);

insert into Return values('a7085caa-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2017-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 260.99);
insert into Return values('a7085e1c-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2018-10-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 210.99);


insert into Return values('a7085f52-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 200.99);
insert into Return values('a708607e-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 250.99);
insert into Return values('a70861aa-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 320.99);
insert into Return values('a708648e-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 310.99);
insert into Return values('a70865d8-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 300.99);
insert into Return values('a708670e-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 210.99);
insert into Return values('a708683a-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 220.99);
insert into Return values('a7086966-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 230.99);
insert into Return values('a7086a92-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 240.99);
insert into Return values('a7086eca-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 250.99);

insert into Return values('a7087050-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 260.99);
insert into Return values('a70876ae-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 210.99);
insert into Return values('a708780c-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 270.99);
insert into Return values('a7087938-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 210.99);
insert into Return values('a7087a64-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-04-01 21:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 270.99);

insert into Return values('a7087b90-0ee5-11ea-8d71-362b9e155667', TO_TIMESTAMP('2019-06-20 11:56', 'YYYY-MM-DD hh24:mi:ss.ff'), 20, 'T', 270.99);

-- commit;