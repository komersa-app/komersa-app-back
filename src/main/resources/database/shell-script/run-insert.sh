# find ../insert -type f -name "I*.sql" -exec ls {} \;
# find ../insert -type f -name "I*.sql" -print0 | sort -nz | xargs -r0 psql postgresql://$DB_USERNAME:$DB_PASSWORD@localhost:5432/komersa_db -f \;

# find ../insert -type f -name "I*.sql" -print0 | xargs -r0 ls -v psql postgresql://$DB_USERNAME:$DB_PASSWORD@localhost:5432/komersa_db -f

find ../insert -type f -name "I*.sql" -print0 | sort -nz | xargs -r0 -I{} bash -c 'echo {}; psql "postgresql://'$DB_USERNAME':'$DB_PASSWORD'@localhost:5432/komersa_db" < "{}"'
