Backend for Komersa App
--
*Env vars:*

```shell
# Linux
export DB_USERNAME="postgres" #default
export DB_PASSWORD="password" #your password
export DB_NAME="komersa_db"
export DB_URL="jdbc:postgresql//localhost:5432/${DB_NAME}"
```

```batch
# Windows
$Env: DB_USERNAME = "postgres" #default
$Env: DB_PASSWORD = "password" #your password
$Env: DB_NAME="komersa_db"
$Env: DB_URL="jdbc:postgresql//localhost:5432/$Env:DB_NAME"
```