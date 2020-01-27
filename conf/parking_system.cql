#parking_system   Keyspace creation

CREATE KEYSPACE parking_system WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

# Reservation
create table reservation(
	id timeuuid,
	spot_id text,
	user_id text,
	status text,
	start_time timestamp,
	end_time timestamp,
	total_charges double,
	Primary Key ( user_id, status, start_time)
	) WITH CLUSTERING ORDER BY (status ASC, start_time DESC) ;

#  Parking Spot
CREATE TABLE parking_system.parking_spot (
    is_available boolean,
    cost_per_minute int,
    id uuid,
    lat double,
    lng double,
    spot_number int,
    PRIMARY KEY (is_available, cost_per_minute, id)
) WITH CLUSTERING ORDER BY (cost_per_minute ASC, id ASC)

# User
create table user(
	id timeuuid,
	mobile_number text,
	password text,
	fname text,
	email text,
	Primary Key(mobile_number)
)