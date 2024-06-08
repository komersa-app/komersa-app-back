printf "\n=== Should return all admins ===\n"
curl -X GET localhost:8080/api/admins | jq
