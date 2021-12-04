
# Carefinder REST API

A RESTful service built upon an open source list of hospital data.


## Environment Variables

To run this project, you will need to add the following environment variables

`CONNECT_URI`

`DB_NAME`

`JWT_SECRET`


## Todo

- [ ] Implement Middleware for Authorization
- [x] Clean up Response error messages
- [x] Write code

## API Reference

- [/api/auth](#auth-routes)
- [/api/apikeys](#api-key-routes)
- [/api/hospitals](#hospital-routes)

### Auth Routes

#### Get All Registered Users

```http
  GET /api/auth/users
```
##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `key` | `string` | **Required**. A generated apikey |
| `authorization` | `string` | **Required**. A admin user's JWT|

#### Register User

```http
  POST /api/auth/register
```
##### Parameters
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`      | `string` | **Required**. Email to be created |
| `password`      | `string` | **Required**. Password to allow logging in|

##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `key` | `string` | **Required**. A generated apikey |


#### Login User

```http
  POST /api/auth/login
```
##### Parameters
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`      | `string` | **Required**. Email for existing user|
| `password`      | `string` | **Required**. Password to allow logging in|

##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `key` | `string` | **Required**. A generated apikey |

#### Update User's Role

```http
  PUT /api/auth/updaterole
```
##### Parameters
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`      | `string` | **Required**. Email for the user that is being changed|
| `role`      | `string` | **Required**. The new role|

##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `key` | `string` | **Required**. A generated apikey |
| `authorization` | `string` | **Required**. A admin user's JWT|

### API key routes

#### Get All/Searched API keys

```http
  GET /api/apikeys
```
##### Optional Parameters
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | Search for key by name|
| `key`      | `string` | Search for key by key|

##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `authorization` | `string` | **Required**. A admin user's JWT|

#### Create an API key

```http
  POST /api/apikeys
```
##### Parameters
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. Unique name for key|

#### Delete All/Searched API keys

```http
  DELETE /api/apikeys
```
##### Optional Parameters
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | Delete key by name|
| `key`      | `string` | Delete key by key|

##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `authorization` | `string` | **Required**. A admin user's JWT|

### Hospital routes

#### Get All/Search Hospitals

```http
  GET /api/hospitals
```

##### Optional Parameters
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `providerId`      | `string` | Search for hospital by providerId|
| `name`      | `string` | Search for hospital by name|
| `city`      | `string` | Search for hospital by city|
| `state`      | `string` | Search for hospital by state|
| `zipCode`      | `string` | Search for hospital by zip code|
| `county`      | `string` | Search for hospital by county|

##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `key` | `string` | **Required**. A generated apikey |

#### Create Hospital

```http
  POST /api/hospitals
```

##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `key` | `string` | **Required**. A generated apikey |
| `authorization` | `string` | **Required**. A admin user's JWT |

##### Sample Request Body (JSON)

```yaml
{
    "providerId": "String",
    "lat": 0.00,
    "long": 0.00,
    "name": "String",
    "address": "String",
    "city": "String",
    "state": "String",
    "zipCode": "String",
    "county": "String",
    "phoneNumber": "String",
    "hospitalType": "String",
    "ownership": "String",
    "emergencyServices": "String"
}
```

#### Update hospital by providerId

```http
  PUT /api/hospitals
```

##### Parameters
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `providerId`      | `string` | **Required**. ProviderId of hospital to update|

##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `key` | `string` | **Required**. A generated apikey |
| `authorization` | `string` | **Required**. A admin user's JWT |

##### Sample Request Body (JSON)
##### (All fields are optional, depends on what needs to be updated)

```yaml
{
    "providerId": "String",
    "name": "String",
    "city": "String",
    "state": "String",
    "zipCode": "String",
    "county": "String",
    "phoneNumber": "String",
}
```

#### Delete All/by Search Hospitals

```http
  DELETE /api/hospitals
```

##### Optional Parameters
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `providerId`      | `string` | Delete hospital by providerId|
| `name`      | `string` | Delete hospital by name|
| `city`      | `string` | Delete hospital by city|
| `state`      | `string` | Delete hospital by state|
| `zipCode`      | `string` | Delete hospital by zip code|
| `county`      | `string` | Delete hospital by county|

##### Headers
| Header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `key` | `string` | **Required**. A generated apikey |
| `authorization` | `string` | **Required**. A admin user's JWT |
