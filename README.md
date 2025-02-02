 create schema:
psql -U your_user -d your_database -f src/main/resources/db/schema.sql
--------------------------------

For example

create user:
POST /api/user/register
{
"name" : "Max",
"email" : "max@nohost.com",
"password" : "123456"
}
--------------------------------
POST /api/user/login
{
"email" : "max@nohost.com",
"password" : "123456"
}

return Token
--------------------------------
POST /api/user/save
Bearer Token
{
"ticker": "AAPL",
"start": "2022-01-01",
"end": "2022-02-03"
}
--------------------------------
GET /api/user/saved?ticker=AAPL
Bearer Token
{
"ticker": "AAPL",
"start": "2024-03-01",
"end": "2024-03-13"
}
