import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.apolloJsLockup.graphql.rocketreserver.LaunchListQuery
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.html.div
import kotlinx.html.dom.append
import org.w3c.dom.Node

fun main() {
    window.onload = {
        document.body?.sayHello()
    }

    val networkTest: NetworkTest = NetworkTest()
    networkTest.writeAllLaunches()
}

class NetworkTest {

    val bg = CoroutineScope(Dispatchers.Unconfined)

    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .build()

    @OptIn(FlowPreview::class)
    fun writeAllLaunches() {
        println("writeAllLaunches()")
        bg.launch {
            println("launch")
            apolloClient.query(LaunchListQuery(Optional.Present(null)))
                .toFlow()
                .onEach { println("$it") }
                .flatMapConcat { it.data?.launches?.launches?.filterNotNull()?.asFlow() ?: emptyFlow() }
                .collect { project ->
                    document.body?.append {
                        div {
                            +project.site.orEmpty()
                        }
                    }

                }
        }
    }
}

fun Node.sayHello() {
    append {
        div {
            +"Hello from JS"
        }
    }
}
