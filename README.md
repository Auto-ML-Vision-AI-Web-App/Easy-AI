# POC

POC단계를 위한 repository입니다.  
6월까지 POC를 구현해보며, flow와 전체 architecture를 이해해봅니다.

- api-server/ : spring boot api server
- frontend/   : react app

## Getting-Started
```
path : api-server/main/java/com/eavy/EavyApplication 
> run (maven)
   
path : frontend/
> npm install
> npm run start
```

## REST API

### 학습 데이터 입력

**Request:**
```
HTTP Method = POST
Request URI = /upload
Parameters = {}
Headers = [Content-Type:"multipart/form-data;charset=UTF-8"]
Body = null
Session Attrs = {}
```

**Response - 정상적인 요청인 경우:**
```
Status = 200
Error message = null
Headers = [Vary:"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", Content-Type:"text/plain;charset=UTF-8", Content-Length:"7"]
Content type = text/plain;charset=UTF-8
Body = success
Forwarded URL = null
Redirected URL = null
Cookies = []
```

**Response - 잘못된 요청인 경우:**
```
Status = 400
Error message = null
Headers = [Vary:"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", Content-Type:"text/plain;charset=UTF-8", Content-Length:"4"]
Content type = text/plain;charset=UTF-8
Body = fail
Forwarded URL = null
Redirected URL = null
Cookies = []
```

### Vision AI 결과 확인

```
HTTP Method = GET
Request URI = /model/1
Parameters = {}
Headers = []
Body = null
Session Attrs = {}
```

**Response - 정상 요청인 경우:**
```
Status = 200
Error message = null
Headers = [Vary:"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", Content-Type:"application/json"]
Content type = application/json
Body = {"id":1,"name":"MLPClassifier","activation":"logistic","learningRate":"adaptive","hiddenLayerSizes":50,"momentum":0.1,"solver":"sgd"}
Forwarded URL = null
Redirected URL = null
Cookies = []
```

**Response - 잘못된 요청인 경우:**
```
Status = 404
Error message = null
Headers = [Vary:"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"]
Content type = null
Body =
Forwarded URL = null
Redirected URL = null
Cookies = []
```

### Vision AI 생성

.