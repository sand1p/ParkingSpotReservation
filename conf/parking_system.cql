CREATE KEYSPACE parking_system WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE parking_system.reservation (
    user_id uuid,
    id uuid,
    end_time timestamp,
    per_hour_charge double,
    spot_id uuid,
    start_time timestamp,
    status text,
    total_charges double,
    PRIMARY KEY (user_id, id)
) WITH CLUSTERING ORDER BY (id ASC)

CREATE TABLE parking_system.parking_spot (
    id uuid PRIMARY KEY,
    cost_per_minute int,
    is_available boolean,
    lat double,
    lng double,
    spot_number int
)
CREATE TABLE parking_system.user (
    mobile_number text PRIMARY KEY,
    email text,
    fname text,
    id timeuuid,
    password text
)
