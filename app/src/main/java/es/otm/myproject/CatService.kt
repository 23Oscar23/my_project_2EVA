package es.otm.myproject

import es.otm.myproject.models.BreedResponsive
import es.otm.myproject.models.CatsResponsive
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatService {

    @GET("/v1/images/search")
    suspend fun getCats(@Query("breed_ids") breedId: String): Response<List<CatsResponsive>>

    @GET("/v1/breeds/search")
    suspend fun getBreed(@Query("q") breedName: String): Response<List<BreedResponsive>>
}