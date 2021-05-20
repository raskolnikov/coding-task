### Tasks

- Implement assignment using:
  - Language: **Java**
  - Framework: **Spring**
- Create two spring boot/cloud micro-services based on gradle.
- Package names should be created using this format:
     - {your-first-name}.{your-last-name}.external
     - {your-first-name}.{your-last-name}.internal  
- Both micro-services register themselves in Consul (Hashicorp)
- External micro-service is exposing ReST endpoints:

    - GET /message  
        - returns all messages 
      
     - POST /message
        - adds a message body: {"message":"some text"} and returns id created for this new message {"id":1} 

     - GET /message/{id}
        - returns specific message by id {"message":"some text", "id": 1}

     - DELETE /message/{id} 
       - confirms deletion  

- External micro-service is calling the internal one using Feign with service discovery
   provided by Consul.
- Internal micro-service persists messages in a simplest possible way (HashMap is fine). 7. The internal one is implementing same endpoints as external.


**********************************************************************************
**********************************************************************************

### Mehmet Aktas's Assignment Solution

### API documentation added via Swagger

After starting server please visit http://localhost:8080/swagger-ui/ to see full api spec

### Create Account

Path: /message/
Method: POST

Request Model :

```json
{

	"message" : "Some Message"

}
```

Response Model :

```json
{
    "id": 1
    
}
```


### Get Message

Path: /message/{messageId}
Method: GET

messageId = 1

Response Model :

```json
{

	"id" : 1,
	"message"  :  "First message"

}

```

### Retrieve Message List

Path: /message
Method: GET


Response Model :

```json
[
    {
      "id" : 1,
      "message"  :  "First message"
    },
    {
      "id" : 2,
      "message"  :  "Second message"
    }

]
```

### Delete Message

Path: /message/{messageId}
Method: DELETE

messageId = 1

Response Model :

```json
{
  
  "id": 0,
  "message": "Message {0} deleted"
  
}

```



