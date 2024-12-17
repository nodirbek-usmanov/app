# Backend assessment task

## Context

Given a mobile application which allows users of a launderette to pay and plan their 
laundry processes.

The mobile app consumes a REST API (`App-API`) from a backend application, allowing for starting and
managing washing cycles, payments etc. A potential example for such a backend API is attached to this
task as a json-server API mock `mobile-app-api.json`. The backend application is responsible for managing
all processes, especially control the devices remotely to start or stop a washing program. 

The backend application provides the `App-API` to the app client with the following resources:

- `Cycle`: Represents an active or completed washing process started by an app user 
  using a real washing device.
  The attributes of a cycle like `status`, `stoppedAt` and `invoiceLines` change during 
  life-cycle of a cycle instance and represent the current process details. The backend
  application manages the cycle process logic in the background, in dependency to user actions or
  washing device status. 
- `Tariff`: Represents price information for a washing cycle operated on a device which is subject to this tariff.
  The price depends on the current day of the week.
- `Device`: Represents a device a user can use for washing laundry. A device references
  a `switchId`, which correlates to a physical switch component installed close to the real device,
  allowing remotely to start/cancel a program execution and determine its status.

For remote control of washing devices the backend application uses internally a simplified 
`Switch-API`. This API is provided by an external party via REST and is consumed by the backend application.
A potential example for such a remote control API is attached to this task as a json-server API mock `remote-switch-api.json`.
The `Switch-API` enables a device allocated to a `Switch` to start (status `ON`) and 
stop (status `OFF`) a washing procedure. If a program terminates on the device, the
corresponding switch changes the status automatically to status `OFF`.

A mobile app user is charged for a washing cycle according to the tariff when the washing process was started succesfully. The
user account's balance is stored in a wallet inside the backend application.

## Task

Provide a buildable and startable Spring Boot Kotlin application with the following functional and non-functional features:

- A REST API for the mobile application inspired by the attached `App-API` mock is provided
- Using the REST API it's possible to start a washing cycle including
  corresponding instrumentation of the `Switch-API` in the background.
- Detail information (status, dates/times, invoice data) is provided for running and 
  historical cycles (can be kept in memory)
- A user is only allowed to start a cycle if the wallet balance (in-memory) is sufficient
- The user's wallet balance is decreased when a cycle is succesfully started
- A started cycle completes automatically and is processed properly by the backend
  in the background when the corresponding device switch indicates a program termination
- Integration with the `Switch-API` is done asynchronously.

## Deliverables

- Sources of the backend application provided by an archive or within a GitHub repository
- Brief documentation 
  - how to build and start the application
  - how to call the REST API for evaluating the expected scenarios

## How to start the example REST mocks locally

- Install [json-server](https://github.com/typicode/json-server)
  - Run `npm install json-server`
- For starting `App-API` example:
  - Run `npx json-server mobile-app-api.json`
  - Access the API endpoints from http://localhost:3000/
- For starting `Switch-API` example:
  - Run `npx json-server remote-switch-api.json`
  - Access the API endpoints from http://localhost:3000/

