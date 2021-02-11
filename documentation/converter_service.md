# Uploader Service

The service is designed to be easily scale up & down. If there are too many messages in RabbitMQ we can easily run the service
multiple times to consume messages without any loss or delay.

## Modules

Each module is designed to work separately, they are only dependent to the `core` module.

* **application**: Contains main class, property class implementation and includes all modules
* **core**: Contains service, dto interfaces. All other modules rely on this one
* **client**: Contains endpoints that is called by users
* **queue**: Responsible for RabbitMQ operations


## RabbitMQ Consumer

It consumes convert requests (produced by the uploader service). For each message:
* Creates a request for 3rd party pdf->text conversion service
* Handle error cases
* Convert response to internal data model
* In case of error, identify if it is a recoverable error or not and retry up to configured times if it is recoverable
* Produce message to status update queue for updating stored document's status