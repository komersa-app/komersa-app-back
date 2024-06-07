printf "\n=== Should return all admins ===\n"
curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwiaWF0IjoxNzE3NzUwNzI0fQ.z5kRtgub71TIk7lmYTLTGa-pKi3TeGu-AOAiN2_vt3w" localhost:8080/api/admins | jq
