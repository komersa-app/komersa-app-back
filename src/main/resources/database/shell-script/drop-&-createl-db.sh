find . -type f -name "drop*.sql" -exec psql postgresql://$DB_USERNAME:$DB_PASSWORD@localhost:5432/postgres -f {} \; #-f './*.sql' #-c "drop database if exists komersa_db; create database komersa_db;"