# Uploader Service

## Modules

Each module is designed to work separately, they are only dependent to the `core` module. I designed in this way in
order to be able to update modules easily. For example if we decide to use PostgreSQL instead of MongoDB, we can do
that by only updating storage service

* **application**: Contains main class, property class implementation and includes all modules
* **core**: Contains service, dto interfaces. All other modules rely on this one
* **api**: Contains endpoints that is called by users
* **queue**: Responsible for RabbitMQ operations
* **storage**: Responsible for DB operations

## RabbitMQ Consumer

It consumes status update messages (produced by the converter service) and updates dabatase items


## Endpoints

### Upload File

This endpoints receives list of files to be uploaded.

* Validate input files
  *  Check if request is empty or contains more than allowed files
  *  Validate each file
     *  Contains a valid filename
     *  Has content type of pdf
     *  Size is below configured threshold
* Insert files that passes validation to DB with INIT status
* Produce messages to RabbitMQ for converting files
* Create endpoint response which includes successfully processing files and also the ones that failed validation

```
curl --location --request POST 'localhost:8080/file' \
--form 'file=@"/opt/tmp/myPdf1.pdf"' \
--form 'file=@"/opt/tmp/myPdf2.pdf"'
```


### Get All Files

Returns all uploaded files overview with their processing status

```
curl --location --request GET 'localhost:8080/file/all'
```

### Get Files by ID

Returns requested uploaded files overview. This is used for polling updates for tasks shown on UI.

```
curl --location --request GET 'localhost:8080/file?ids=1111,22222,33333'
```

### Get File Details by ID

Returns details of requested file. In addition to overview responses, task details contains converted plain text information

```
curl --location --request GET 'localhost:8080/1111'
```