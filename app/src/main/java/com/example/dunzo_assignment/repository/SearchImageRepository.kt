package com.example.dunzo_assignment.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dunzo_assignment.model.Photo
import com.example.dunzo_assignment.model.SearchResult
import com.example.dunzo_assignment.network.Endpoints
import com.example.dunzo_assignment.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchImageRepository {
    private val imageSearchEndpoint=RetrofitInstance.retrofit.create(Endpoints::class.java)
    private val mutableLiveData:MutableLiveData<List<Photo>> = MutableLiveData()
    fun getLiveData():LiveData<List<Photo>>
    {
        return mutableLiveData
    }
    fun getImages(searchQuery:String,pageSize:Int,pageNo:Int)
    {
        val call=imageSearchEndpoint.getImageResult(
            search = searchQuery,
            page = pageNo,
            perPage = pageSize
        )
        call.enqueue(object :Callback<SearchResult>
        {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if(response.isSuccessful && response.body()!=null )
                {
                    Log.d("ErrorInCode","+"+response.body())
                    val body=response.body() as SearchResult
                   body?.let {
                       body?.photos?.photo?.let {
                           mutableLiveData.postValue(it)
                       }

                   }

                }
                else
                {
                    Log.d("ErrorInCode","Something went wrong : "+response.message())
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.d("ErrorInCode","Network Error Occurred")
            }

        })



    }
}