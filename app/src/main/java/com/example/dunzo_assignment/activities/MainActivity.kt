package com.example.dunzo_assignment.activities

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dunzo_assignment.R
import com.example.dunzo_assignment.adapter.ImageSearchAdapter
import com.example.dunzo_assignment.model.Photo
import com.example.dunzo_assignment.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private var viewModel:MainActivityViewModel?=null
    private var adapter:ImageSearchAdapter?=null
    private var images : ArrayList<Photo> = ArrayList()
    private var load=false
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog= AlertDialog.Builder(this)
        dialog.setMessage("No Internet Connection")
        dialog.setTitle("Alert..")
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
     if(isNetworkConnected())
     {
         edit_text_search.setText(viewModel?.queryText ?: "")
         viewModel?.searchImages(viewModel?.queryText ?: "")
         adapter= ImageSearchAdapter(this, images)
         recycler_view_image_list?.setHasFixedSize(true)
         recycler_view_image_list?.layoutManager=GridLayoutManager(this, 2)
         recycler_view_image_list?.adapter=adapter

         initializeDataListener()
         searchKeyword()
         loadMore()
     }
     else
     {

         dialog.create().show()

     }
    }

    private fun initializeDataListener() {
        viewModel?.liveData?.observe(this, {
            progress_bar_loading?.visibility = View.GONE
            if (!load) {
                adapter?.setData(it as ArrayList<Photo>)
            } else {
                adapter?.addData(it as ArrayList<Photo>)
            }
        })
    }

    private fun searchKeyword() {
        edit_text_search?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    load = false
                    progress_bar_loading?.visibility = View.VISIBLE
                    viewModel?.searchImages(
                        it.toString()
                    )
                }
            }

        })
    }

    private fun loadMore() {
        nested_scroll_view?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                load = true
                progress_bar_loading?.visibility = View.VISIBLE
                viewModel?.loadMore()
            }

        })
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}