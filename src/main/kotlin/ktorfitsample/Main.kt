package ktorfitsample

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.*
import io.ktor.client.plugins.api.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

interface MyExampleApi {
    @FormUrlEncoded
    @POST("people/1/")
    suspend fun getPerson(
        @Field("key1") theKey: String,
        @Field("value1") theValue: String
    ): String
}

fun createRequestInterceptorPlugin(): ClientPlugin<Unit> {
    return createClientPlugin("RetrosheetRequestInterceptor") {
        onRequest { request, content ->
            (request.body as? FormDataContent)?.formData?.apply {
                plus(
                    Parameters.build {
                        // new
                        append("newKey", "i am new key")
                        append("newValue", "i am new value")

                        // existing
                        append("key1", "age")
                        append("value1", "1")
                    }
                )
                println("QuickTag: :createRequestInterceptorPlugin: intercepted")
            }
        }
    }
}

suspend fun main() {
    val ktorClient = HttpClient {
        install(createRequestInterceptorPlugin()) {}
    }

    val api = Ktorfit.Builder()
        .baseUrl("https://reqres.in/api/")
        .httpClient(ktorClient)
        .build()
        .createMyExampleApi()

    api.getPerson("name", "foso").also {
        println(it)
    }
}