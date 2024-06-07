# Backend for Komersa App

---

## Required config:

---
- *Set the env vars:*

```shell
# Linux
export DB_USERNAME="postgres" #default
export DB_PASSWORD="password" #your password
export DB_NAME="komersa_db"
export DB_URL="jdbc:postgresql//localhost:5432/komersa_db"
```

```batch
# Windows
$Env: DB_USERNAME = "postgres" #default
$Env: DB_PASSWORD = "password" #your password
$Env: DB_NAME="komersa_db"
$Env: DB_URL="jdbc:postgresql//localhost:5432/komersa_db"
```

- *Create the DB:*

```shell
# Linux
psql postgresql://${DB_USERNAME}:${DB_PASSWORD}@localhost:5432 -c "CREATE DATABASE ${DB_NAME}"
```

```batch
# Windows
powershell -Command "$DB_USERNAME = '%DB_USERNAME%'; $DB_PASSWORD = '%DB_PASSWORD%'; $DB_NAME = '%DB_NAME%'; $connectionString = 'Host=localhost;Port=5432;Username=$DB_USERNAME;Password=$DB_PASSWORD;'; Invoke-Sqlcmd -ConnectionString $connectionString -Query 'CREATE DATABASE %DB_NAME%'"
```

---
## Swagger Ui link :

```html
https://petstore.swagger.io/?url=https://raw.githubusercontent.com/komersa-app/komersa-app-back/main/docs/api.yaml
```

###### *© Komersa Inc*