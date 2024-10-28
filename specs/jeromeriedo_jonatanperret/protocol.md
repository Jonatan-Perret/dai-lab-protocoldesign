# Protocol specification
## Overview
Math protocol is a client-server protocol. The client connects to the server, sends simple calculation to do. Ex : ADD 2 2. The server sends the result or an error message if the calculation is not possible (format or other).

## Transport layer protocol
Math protocol uses TCP. The client establishes the connection. It has to know the IP address of the server. The server listens on TCP port 55555.
The client closes the connection when the user want to quit.

## Messages
There are two types of messages:
- ```ADD <operand1> <operand2>``` <br>
The client sends a string containing the operation and two other numbers.
- ```ERROR <errorcode> <errormessage>``` <br>
Error response message after an operation message, if the format isn't valid (missing or invalid operand) or not supported operation.

Both messages are UTF-8 encoded with “\n” as end-of-line character.

## Example dialogs










# V2
## Overview

The Simple Math Protocol (SMP) is a text-based communication protocol that enables clients to request basic mathematical computations from a server over a network. The primary purpose of SMP is to facilitate arithmetic operations—such as addition, subtraction, multiplication, and division—by sending simple textual commands. The server processes these commands and returns the results to the client. SMP is designed for simplicity, ease of implementation, and human readability.

## Transport Layer Protocol

SMP operates over the Transmission Control Protocol (TCP/IP) to ensure reliable and ordered delivery of messages between clients and servers.

### Connection Lifecycle
- Connection Establishment: The client initiates a TCP connection to the server on a predefined port.
- Session Management: Upon connection, the server sends a welcome message. The client and server then exchange messages as per the protocol specifications.
- Concurrency: The server must handle multiple simultaneous client connections without interference between sessions.
- Connection Termination: The client ends the session by sending an EXIT command. The server responds with a GOODBYE message and closes the connection.
### Port Usage
Server Port: The server listens on a specific TCP port for incoming connections. This port should be agreed upon or configurable in both the client and server applications.
## Messages

### General Rules
Encoding: All messages are encoded using UTF-8.
Message Format: Messages are plain text terminated by a newline character (\n).
Field Separation: Fields within a message are separated by a single space.
Case Sensitivity: All commands and operations are case-sensitive and must be in uppercase.
Whitespace Handling: Leading and trailing whitespace should be trimmed from messages before processing.
### Message Formats
Server Messages

Welcome Message
Sent upon a new client connection:

less
Copy code
WELCOME Supported operations: ADD SUB MUL DIV
Result Message
Sent after processing a valid command:

```RESULT <ResultValue>```
<ResultValue>: The numerical result of the computation.
Error Message
Sent when an error occurs:

php
Copy code
ERROR <ErrorCode> <ErrorMessage>
<ErrorCode>: A three-digit number indicating the error type.
<ErrorMessage>: A brief description of the error.
Standard Error Codes:

100 - Unknown operation
101 - Invalid operands
102 - Division by zero
103 - Malformed command
104 - Internal server error
Goodbye Message
Sent when terminating the connection:

Copy code
GOODBYE
Client Messages

Command Message
To request an operation:

php
Copy code
<Operation> <Operand1> <Operand2>
<Operation>: One of ADD, SUB, MUL, DIV.
<Operand1> and <Operand2>: Numerical values (integers or decimals).
Exit Message
To terminate the connection:

vbnet
Copy code
EXIT
Specific Elements

Supported Operations
Addition (ADD): Adds two numbers.
Subtraction (SUB): Subtracts the second number from the first.
Multiplication (MUL): Multiplies two numbers.
Division (DIV): Divides the first number by the second.
Data Types
Numerical Values: Accepts both integers and floating-point numbers in decimal notation without commas or spaces.
Error Handling
Unknown Operation (ERROR 100): Operation not supported.
Invalid Operands (ERROR 101): Operands are missing or not valid numbers.
Division by Zero (ERROR 102): Attempted division by zero.
Malformed Command (ERROR 103): Command does not follow the specified format.
Internal Server Error (ERROR 104): Unexpected server error.
Security Considerations
Authentication: Not included in the protocol.
Encryption: Data is transmitted in plain text. For security, use SSL/TLS wrapping.
Implementation Notes
Extensibility: Future operations can be added. Clients should handle unknown operations gracefully.
Timeouts: Clients should implement timeouts and attempt reconnection if the server is unresponsive.
Input Validation: Both client and server should validate inputs to prevent errors and security issues.
Case Sensitivity: Commands must be in uppercase. Lowercase inputs should result in an error.
Example Dialogs

Communication Workflow
Client connects to the server
csharp
Copy code
[TCP connection established]
Server sends Welcome Message
vbnet
Copy code
Server: WELCOME Supported operations: ADD SUB MUL DIV
Client sends a valid Command Message
arduino
Copy code
Client: ADD 12 30
Server sends Result Message
arduino
Copy code
Server: RESULT 42
Client sends an invalid Command Message
arduino
Copy code
Client: MULTIPLY 5 10
Server sends Error Message
vbnet
Copy code
Server: ERROR 100 Unknown operation
Client sends Exit Message
vbnet
Copy code
Client: EXIT
Server sends Goodbye Message and closes connection
arduino
Copy code
Server: GOODBYE
[Connection closed]
Example Session Transcript
vbnet
Copy code
[Client connects to the server]

Server: WELCOME Supported operations: ADD SUB MUL DIV

Client: ADD 15 27
Server: RESULT 42

Client: DIV 10 0
Server: ERROR 102 Division by zero

Client: SUB 100
Server: ERROR 103 Malformed command

Client: MUL 7 6
Server: RESULT 42

Client: EXIT
Server: GOODBYE

[Connection closed]
This transcript demonstrates a typical interaction, including successful computations, error handling, and session termination.

This specification is organized into the following sections:

Overview: Provides a general description of the protocol's purpose and functionality.
Transport Layer Protocol: Details how the protocol operates over TCP/IP, including connection lifecycle and port usage.
Messages: Describes the general rules and specific message formats used in communication between client and server.
Specific Elements: Covers supported operations, data types, error handling, security considerations, and implementation notes.
Example Dialogs: Offers sample interactions to illustrate the protocol in action.
Adhering to this specification will ensure interoperability between different client and server implementations of the Simple Math Protocol.