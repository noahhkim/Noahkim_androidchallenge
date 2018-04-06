# Stack Users

A display list of stack users and their badges, with data retrieved from [Stackoverflow Users API Endpoint](https://api.stackexchange.com/2.2/users?site=stackoverflow)

## Third-Party Libraries

### Butterknife

For binding views. I wanted to give the code a much cleaner look

### Timber

For debugging. Saved me from writing log tag strings for each class

### Retrofit

For retrieving and uploading data on a background thread. I didn't have to write http requests or manually parse JSON

### Glide

For gravatars. This is my go-to library; with just a few lines, I'm able to download the images, cache them and display a loading progress bar while the images are being loaded.

### CircleImageView

For displaying circular imageviews for the gravatars

## Notes

I had a lot of fun working on this assignment! I originally had a field to display user location but decided to remove it for the sake of simplicity. Thank you for letting me try this out and I'd greatly appreciate any feedback or suggestions for improvement :)

