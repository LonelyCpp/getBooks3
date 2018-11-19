# Get Books!

A simple android app that helps you keep your favorite books in one place! It uses the goodreads api to get all the data.

#### Features

- Search for any book you like
- Add a book to your favorite list
- See authors and  their related works

#### Libraries

- [Picasso](http://square.github.io/picasso/) - Image loading library
- [Volley](https://developer.android.com/training/volley/) - Network Library
- [XmlPullParser](https://developer.android.com/reference/org/xmlpull/v1/XmlPullParser) - XML parsing

#### Building
1. Clone the repo and import into android studio
2. Get a goodreads API key from https://www.goodreads.com/api
3. Make a resource file in res/values (eg. api_key.xml)
4. Add your API key as a string resource with name `GR_API_Key`
(eg. `<string name="GR_API_Key">_key_here_</string>`)
5. Build and run the project.

#### Screenshots

<img src="https://i.imgur.com/LfGunZh.jpg" alt="author" width="200px"/> <img src="https://imgur.com/ZqRGIxW.jpg" alt="search" width="200px"/>

<img src="https://imgur.com/DbFFkMi.jpg" alt="book" width="200px"/>

