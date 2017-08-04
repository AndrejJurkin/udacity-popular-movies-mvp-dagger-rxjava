# Udacity - Popular Movies

## Getting Started

In order to access TMDb API you need provide your own API key.

You can either set your API key in `gradle.properties`

    TMDB_API_KEY="tmdb_api_key"

Or override it in `builld.gradle`

    buildTypes.each {
        it.buildConfigField 'String', 'TMDB_API_KEY', '"your_tmdb_api_key"'
    }


## Screenshots

![Udacity Popular Movies](/screenshots/home_screen.png) ![Udacity Popular Movies](/screenshots/movie_detail.png) ![Udacity Popular Movies](/screenshots/movie_detail_2.png)

