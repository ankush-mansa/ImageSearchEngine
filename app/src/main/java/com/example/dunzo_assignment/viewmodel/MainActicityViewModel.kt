package com.example.dunzo_assignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.dunzo_assignment.constants.NetworkConstants
import com.example.dunzo_assignment.model.Photo
import com.example.dunzo_assignment.repository.SearchImageRepository

class MainActivityViewModel(application: Application) :AndroidViewModel(application) {
    private var repository=SearchImageRepository()
    var liveData:LiveData<List<Photo>>?=null
    var queryText="Sky"
    private var pageNo=0
    init {
        liveData=repository.getLiveData()
        searchImages(queryText)
    }
    fun searchImages(query:String)
    {
        queryText=query
        pageNo=1
        repository.getImages(query,NetworkConstants.PAGE_SIZE,pageNo)
    }
    fun loadMore()
    {
        pageNo+=1
        repository.getImages(queryText,NetworkConstants.PAGE_SIZE,pageNo)

    }
}