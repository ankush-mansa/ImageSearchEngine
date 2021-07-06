package com.example.dunzo_assignment.network

import com.example.dunzo_assignment.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Endpoints {

    @GET("rest/")
    fun getImageResult(
        @Query("method") method:String="flickr.photos.search",
        @Query("api_key") qpi_key:String="062a6c0c49e4de1d78497d13a7dbb360",
        @Query("text") search:String,
        @Query("format") formate:String="json",
        @Query("nojsoncallback") nojsoncallback:Int=1,
        @Query("per_page") perPage:Int,
        @Query("page") page:Int
    ):Call<SearchResult>
}