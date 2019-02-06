# Jobot

A useless, quick and dirty internet crawler which extracts the hyperlinks from the web pages. Jobot also saves the
metadata for each processed URL into the file `<USER_HOME>/.jobot/<URL>/links.txt`. The saved metadata is a list of the
URLs extracted from the corresponding HTML page.

# Build

```bash
./gradlew clean jar
```

# Run

```bash
java -jar build/libs/jobot.jar https://en.wikipedia.org/wiki/\(486958\)_2014_MU69
```

# Implementation Notes

Jobot modifies and filters the URLs upon processing:
* URL should not be equal to any of the last 1,000,000 extracted URLs
* Anchor and query parts of the URL are being dropped
* URL should begin with `http`, otherwise it's dropped entirely
* The corresponding HTTP response content type should begin with `text`, otherwise the content is dropped

The URL processing queue size is 1,000,000.
