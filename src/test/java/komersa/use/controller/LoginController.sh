# shellcheck disable=SC2028
#echo "===\nShould return Token\n==="
#token="$(curl -X POST -H "Content-Type: application/json" -d '{"name": "john", "password": "password"}' localhost:8080/api/auth/login | jq ".token")"
#token="$(${response} | jq ".token")"
#echo "${token}"

# example : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwiaWF0IjoxNzE5NzU3MzQ1LCJleHAiOjE3MTk3NjA5NDV9.l4MPtg5zSvCR3j8xCtox4rK7qYSBFbbHkH4WGMSYHcs

# shellcheck disable=SC2028
#echo "===\nShould return bad credentials and save to login attempt\n==="
#curl -X POST -H "Content-Type: application/json" -d '{"name": "john", "password": "paassword"}' localhost:8080/api/auth/login | jq
curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwiaWF0IjoxNzE5NzU2OTExLCJleHAiOjE3MTk3NjA1MTF9.PllrypFFnLC6oVZaTC5Ek8qhr152VbBqZk6cAcb1XoI" localhost:8080/api/auth/loginAttempts | jq
#psql postgresql://"${DB_USERNAME}":"${DB_PASSWORD}"@localhost:5432/komersa_db -c "SELECT * FROM login_attempt ORDER BY id DESC"
