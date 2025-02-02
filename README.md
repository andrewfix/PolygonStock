### Create schema
```sh
psql -U your_user -d your_database -f src/main/resources/db/schema.sql
```

### For example:

#### Create user:
```http
POST /api/user/register
Content-Type: application/json

{
  "name": "Max",
  "email": "max@nohost.com",
  "password": "123456"
}
```

#### Login:
```http
POST /api/user/login
Content-Type: application/json

{
  "email": "max@nohost.com",
  "password": "123456"
}
```
_Returns Token_

#### Save user data:
```http
POST /api/user/save
Authorization: Bearer <Token>
Content-Type: application/json

{
  "ticker": "AAPL",
  "start": "2022-01-01",
  "end": "2022-02-03"
}
```

#### Get saved data:
```http
GET /api/user/saved?ticker=AAPL
Authorization: Bearer <Token>
```
