printf "\n=== Should return last login attempts ===\n"
printf "> FROM SQL :\n"
psql postgresql://"${DB_USERNAME}":"${DB_PASSWORD}"@localhost:5432/komersa_db -c "SELECT * FROM login_attempt ORDER BY id DESC LIMIT 5"
# normal : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIn0.8zk8Ej9TwsOA7A7Eol7UQOYTW-sqD30uAD4l1hnFizI
printf "> FROM API :\n"
curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwiaWF0IjoxNzE5ODM5MjQyfQ.PyQ0fUjC3B1LaB5a4QNM4YrAGMc7P9qZCgkzDU2ctQ8" localhost:8080/api/auth/loginAttempts | jq
