# Play Narrative

A Spring Boot web application for tracking your video game library and play sessions.

## Features

- Add games to your personal library
- Track ownership status and play intent
- Record games across multiple platforms
- View detailed game information
- Simple web interface with Thymeleaf templates

## Technologies Used

- **Backend**: Spring Boot 3.x, Spring Data JPA
- **Frontend**: Thymeleaf templates, HTML/CSS/JavaScript
- **Database**: H2 (in-memory for development)
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Running the Application

1. Clone the repository:
```bash
git clone https://github.com/YOUR_USERNAME/play-narrative.git
cd play-narrative
```

2. Run with Maven:
```bash
./mvnw spring-boot:run
```

3. Open your browser and navigate to: `http://localhost:8080`

## API Endpoints

- `GET /` - Home page
- `GET /games` - List all games in your library
- `GET /games/add` - Add a new game form
- `POST /games/add` - Save a new game
- `GET /library/game/{id}` - View game details

## Project Structure

```
src/
├── main/
│   ├── java/com/cocoding/playnarrative/
│   │   ├── controller/     # Web controllers
│   │   ├── model/         # JPA entities
│   │   ├── repository/    # Data repositories
│   │   └── PlaynarrativeApplication.java
│   ├── resources/
│   │   ├── application.properties
│   │   └── templates/     # Thymeleaf templates
│   └── resources/
└── test/
    └── java/com/cocoding/playnarrative/
        └── PlaynarrativeApplicationTests.java
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).