printf "\n=== Should return Token === \n"
# example : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwiaWF0IjoxNzE5NzU3MzQ1LCJleHAiOjE3MTk3NjA5NDV9.l4MPtg5zSvCR3j8xCtox4rK7qYSBFbbHkH4WGMSYHcs
curl -X POST -H "Content-Type: application/json" -d '{"name": "admin", "password": "admin1123"}' localhost:8080/api/auth/login | jq
