# VideoPlayer

**Table of Contents**

- [Technology stack](#technology-stack)
- [Architecture](#architecture)
- [Design patterns](#design-patterns)

## Technology Stack
The following are the tech stack used for this project:
- **Jetpack Compose**: Ui
- **Kotlin**  
- **Room**: Database
- **Hilt**: Dependency injection   
- **Ktlint**: Linting tool for Kotlin code.  
- **Detekt**: Static code analysis tool with Compose-specific rules.
- **Github Workflow**:  Automated workflows that perform specified checks(eg: ktlint, detekt...) during pull requests, ensuring code quality and consistency by raising errors if any checks fail.
- **Workmanager**:  Jetpack api used for handling background tasks. Handles downloads and saving the status to the database for tracking. Also handles internet connectivity. 

## Architecture
OnlineStoreSample app is structured in a mix of clean architecture, MVVM, and MVI pattern.
The whole project is clearly separated in layers - Presentation, Business logic and Data layer.

Project is a combination of multiple MFE's (Micro Front Ends)
- **designsystem**: Defines the app's theme, spacing, sizing, and reusable UI components.  
- **commonmodule**: Serves as a shared library across modules, encapsulating utilities, networking functionality, and other common components to ensure reusability and maintainability.
- **datastoragemodule**: DataStorageModule: Manages database operations and preference handling, providing a centralized solution for data storage and retrieval.
- **playermodule**: Contains the Media player.  

![Video Player Architecture](https://github.com/sudheeshmohan47/VideoPlayer/blob/master/architecture_diagram.jpg)

## Design Patterns

This app make use of the following patterns:
 - Dependency Inversion pattern which helps to avoid coupling
 - MVI is used in presentation layer for handling user interactions in the form of State changes with the help of Actions and Events

## Dependencies
All the dependencies are managed using .toml file inside the root -> gradle folder.
libs.versions.toml contains all the dependencies required for this project
