# bookshop
Fresh installation instruction
--------------------------------
1. Create **application.properties** under ***src/main/resources***
2. Code for ***application.properties*** is posted in discord
3. Change ***spring.datasource.url*** to the credentials posted in discord

**API and Usage**

**POST /api/account/create**

Create account

**POST /api/account/{email}**

Fetch account detail

**PUT /api/users/{email}**

Update account detail

**POST /api/account/login**

Login account

--------------------------------

**POST /api/address/create/{email}**

Create address that links to email

**GET /api/address/{email}**

Fetch all address that links to that email

**PUT /api/address/modify/{addressId}**

Update address detail based on addressId

**DELETE /api/address/delete/{addressId}**

Delete address based on addressId

