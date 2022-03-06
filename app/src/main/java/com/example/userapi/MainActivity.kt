package com.example.userapi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.booksearchrxjavakotlin.adapter.BookListAdapter
import com.android.booksearchrxjavakotlin.network.BookListModel
import com.android.booksearchrxjavakotlin.viewmodel.MainActivityViewModel
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.userapi.adapters.UserAdapter
import com.example.userapi.databinding.ActivityMainBinding
import com.example.userapi.models.User
import com.example.userapi.utils.NetWorkHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var netWorkHelper : NetWorkHelper
    lateinit var requestQueue : RequestQueue
    lateinit var userAdapter : UserAdapter
    lateinit var viewModel:  MainActivityViewModel
    lateinit var bookListAdapter: BookListAdapter
    var url = "https://api.github.com/users"
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        netWorkHelper = NetWorkHelper(this)

        if (netWorkHelper.isNetWorkConnected()){
            binding.tv.text = "Connected"
            binding.tv.visibility = View.GONE
        }else{
            binding.tv.text = "Disconnected"
        }

        initSearchBox()
        initRecyclerView()

    }
/*    fun lala(){

        requestQueue = Volley.newRequestQueue(this)
        VolleyLog.DEBUG = true //Qanday ma'lumot kelayotganini Logda ko'rsatib turadi

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            object : Response.Listener<JSONArray>{
                override fun onResponse(response: JSONArray?) {
                    val type = object : TypeToken<List<User>>(){}.type
                    val list = Gson().fromJson<List<User>>(response.toString(),type)

                    userAdapter = UserAdapter(list)
                    binding.recycle.adapter = userAdapter

                    Log.d(TAG,"onResponse : ${response.toString()}")
                }

            },object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {

                }
            })

        jsonArrayRequest.tag = "tag1" //tag berilyapti
        requestQueue.add(jsonArrayRequest)
    }*/
private fun initSearchBox() {
    edt_txt_main.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            loadAPIData(s.toString())
        }
    })
}

    private fun initRecyclerView(){
        recycle.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration  = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            bookListAdapter = BookListAdapter()
            adapter =bookListAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadAPIData(input: String) {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getBookListObserver().observe(this, Observer<BookListModel>{
            if(it != null) {
                //update adapter...
                bookListAdapter.bookListData = it.items
                bookListAdapter.notifyDataSetChanged()
            }
            else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall(input)
    }
}