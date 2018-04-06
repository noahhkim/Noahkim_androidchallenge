# Stack Users

A list display of stack users and their information, with data retrieved from [Stackoverflow Users API Endpoint](https://api.stackexchange.com/2.2/users?site=stackoverflow)

## Third-Party Libraries

### Butterknife

I wanted to reduce writing redundant code for binding views. Also gives the code a much cleaner look

### Timber

For debugging. Saved me from writing log tag strings for each class

### Retrofit

Made it easy for me to asynchronously retrieve and upload JSON data from the endpoint. I didn't have to write boilerplate code for making http requests or manually parse JSON data

### Glide

For loading images, this is usually my go-to library. With just a few lines of code I was able to download the gravatars, cache them and display a loading progress bar while the images were being loaded.

### CircleImageView

For displaying circular imageviews for the gravatars
