# apollo-js-lockup-example
Example project to demonstrate Apollo Kotlin lockups when using Kotlin/JS

## Usage

```
./gradlew jsBrowserRun
```

Observe that the browser locks up, constantly saturating a CPU core, when processing data after making the GraphQL network call.

## Solution

Use the Kotlin/JS `IR` backend instead of the default `LEGACY` one, as per [apollo-kotlin#4590](https://github.com/apollographql/apollo-kotlin/issues/4590):

```
kotlin {
-    js {
+    js(IR) {
     binaries.executable()
     //...
```
