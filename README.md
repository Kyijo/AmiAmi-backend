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
- Image and video uploading
- Tagging images and videos
- User profile

## Project Structure

The project follows a standard Spring Boot structure with the following main packages:

- `com.amiami.backend`: Contains the main application class and configuration files.
- `com.amiami.backend.controllers`: Includes REST controllers for handling incoming HTTP requests and defining the API endpoints.
- `com.amiami.backend.services`: Provides service classes for the business logic implementation.
- `com.amiami.backend.repositories`: Contains the repositories for data access and persistence.
- `com.amiami.backend.models`: Includes entity classes representing the data model of the application.
- `com.amiami.backend.security`: Contains classes related to security and authentication.

## Technologies Used

The AmiAmi Backend is built using the following technologies and frameworks:

- Java
- Spring Boot
- Spring Data JPA

## Future features

What would I like to implement in the future?

- Comments under videos and pictures
- Like and dislike service
- Search images and videos by name
- Share images and videos
