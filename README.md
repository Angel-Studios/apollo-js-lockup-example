# apollo-js-lockup-example
Example project to demonstrate Apollo Kotlin lockups when using Kotlin/JS

## Usage

```
./gradlew jsBrowserRun
```

Observe that the browser locks up, constantly saturating a CPU core, when processing data after making the GraphQL network call.
