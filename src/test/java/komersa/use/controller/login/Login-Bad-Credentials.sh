printf "\n=== Should return bad credentials and save to login attempt === \n"
curl -X POST -H "Content-Type: application/json" -d '{"name": "john", "password": "paassword"}' localhost:8080/api/auth/login | jq
psql postgresql://"${DB_USERNAME}":"${DB_PASSWORD}"@localhost:5432/komersa_db -c "SELECT * FROM login_attempt ORDER BY id DESC LIMIT 5"
