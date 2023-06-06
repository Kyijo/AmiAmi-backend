# AmiAmi Backend

AmiAmi Backend is a server-side application for the AmiAmi project, which serves as an image and video sharing platform. This repository contains the backend codebase responsible for managing images, videos, users, and tags.

## Prerequisites

Before running the AmiAmi Backend, ensure that the following prerequisites are met:

- Java Development Kit (JDK) 11 or higher
- Apache Maven
- Docker

## Features

The AmiAmi Backend provides the following features:

- User authentication and registration
- Image uploading
- Tagging images
- User profile

## Project Structure

The project follows a standard Spring Boot structure with the following main packages:

- `dev.amiami`: Contains the main application class and configuration files.
- `dev.amiami.Controller`: Includes REST controllers for handling incoming HTTP requests and defining the API endpoints.
- `dev.amiami.Service`: Provides service classes for the business logic implementation.
- `dev.amiami.Repository`: Contains the repositories for data access and persistence.
- `dev.amiami.Model`: Includes entity classes representing the data model of the application.
- `dev.amiami.Security`: Contains classes related to security and authentication.
- `dev.amiami.DTO` : DTO for better responses
- `dev.amiami.Exception` : Class for custom exceptions to display on frontend
- `dev.amiami.Role` : Custom roles 

## Technologies Used

The AmiAmi Backend is built using the following technologies and frameworks:

- Java
- Spring Boot
- Spring Data JPA
- Google cloud bucket
- Docker
- Maven

## Future features

What would I like to implement in the future?

- Comments under videos and pictures
- Like and dislike service
- Share videos
- Use DTO instead of models for responses
