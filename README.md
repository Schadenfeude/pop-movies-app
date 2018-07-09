# Popular Movies app
An app that allows you to see, sort, favourite etc. new, upcoming, popular movies.

## IMPORTANT
To be able to run this app you need an **API key** for TheMovieDb. To create your key go to
 http://themoviedb.org, create an account and then in your account's settings go to the API tab
 and follow the instructions. 
 After you create your key open your project's `gradle.properties` file 
 and add the following line:

 `MovieDbApiKey = {YOUR_KEY}`

## SPECIFICATIONS
### User Interface - Layout
 - [x] App is written solely in the Java Programming Language.
 - [x] UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the 
 movies by: most popular, highest rated.
 - [x] Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
 - [x] UI contains a screen for displaying the details for a selected movie.
 - [x] Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
 - [ ] Movie Details layout contains a section for displaying trailer videos and user reviews.

### User Interface - Function
 - [x] When a user changes the sort criteria (most popular, highest rated, and favorites) the main 
 view gets updated correctly.
 - [x] When a movie poster thumbnail is selected, the movie details screen is launched.
 - [ ] When a trailer is selected, app uses an Intent to launch the trailer.
 - [ ] In the movies detail screen, a user can tap a button(for example, a star) to mark it as a 
 Favorite.
 
### Network API Implementation
 - [x] In a background thread, app queries the `/movie/popular` or `/movie/top_rated` API for the sort 
 criteria specified in the settings menu.
 - [ ] App requests for related videos for a selected movie via the `/movie/{id}/videos` endpoint 
 in a background thread and displays those details when the user selects a movie.
 - [ ] App requests for user reviews for a selected movie via the `/movie/{id}/reviews` endpoint 
 in a background thread and displays those details when the user selects a movie.
 
### Data Persistence
 - [x] The titles and IDs of the user’s favorite movies are stored in a native SQLite database and 
 are exposed via a `ContentProvider`. This `ContentProvider` is updated whenever the user favorites 
 or unfavorites a movie. No other persistence libraries are used.
 - [x] When the "favorites" setting option is selected, the main view displays the entire favorites 
 collection based on movie ids stored in the `ContentProvider`.