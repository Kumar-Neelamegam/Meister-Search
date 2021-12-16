package com.app.meister_search.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.meister_search.R
import com.app.meister_search.databinding.ActivitySearchBinding
import com.app.meister_search.model.SearchResponse
import com.app.meister_search.viewmodel.SearchActivityViewModel
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class SearchActivity : AppCompatActivity() {

    var TAG = this.javaClass.name
    private lateinit var binding: ActivitySearchBinding
    lateinit var context: Context
    lateinit var searchActivityViewModel: SearchActivityViewModel
    val adapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        context = this@SearchActivity
        controllisteners()
    }

    private fun dataLoaders(searchTerm: String) {

        searchActivityViewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)

        val jsonObject = JSONObject()
        try {
            jsonObject.put("text", searchTerm)
        } catch (e: JSONException) {
            Log.d(TAG, "dataLoaders: ")
        }

        searchActivityViewModel.getSearchResults(jsonObject, "object")!!.observe(this,
            { searchResponse ->
                val results = searchResponse.results
                val paging = searchResponse.paging
                if (paging != null) {
                    if (paging.totalResults!! > 0) {
                        Log.e("dataLoaders: ", results?.projects?.get(0)?.name.toString())
                        loadRecycler(searchResponse)
                        showRecycler()
                    } else {
                        showSearchMessage()
                    }
                    hideLoadingIndicator()
                }
            })
    }

    private fun controllisteners() {
        //TODO
        /**
         * Popup to task info
         * Store data to roomdb
         */
        binding.apply {
            edtSearch.setOnTouchListener(OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val textView = v as TextView
                    if (event.x >= textView.width - textView.compoundPaddingEnd) {
                        textView.text = ""
                        binding.txtvwSearchmessage.setText(R.string.label_search_hint2)
                        return@OnTouchListener true
                    }
                }
                false
            })

            edtSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val txt = validateSearchText()
                    if (txt != null) {
                        showLoadingIndicator()
                        dataLoaders(txt)
                    }

                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

        }

    }

    fun showRecycler() {
        binding.recyclerResults.visibility = View.VISIBLE
        binding.layoutSearchHint2.visibility = View.GONE
    }

    fun showSearchMessage() {
        binding.recyclerResults.visibility = View.GONE
        binding.layoutSearchHint2.visibility = View.VISIBLE
        binding.txtvwSearchmessage.setText(R.string.error_notfound)
    }

    fun validateSearchText(): String? {
        return if (binding.edtSearch.text != null && binding.edtSearch.text!!.isNotEmpty()) {
            binding.edtSearch.text.toString()
        } else {
            null
        }
    }


    private fun loadRecycler(results: SearchResponse?) {
        binding.recyclerResults.adapter = adapter
        binding.recyclerResults.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        if (results != null) {
            adapter.searchResponse = results
            adapter.paging = results.paging
        }
    }

    fun showLoadingIndicator() {
        binding.loadingIndicator.visibility = View.VISIBLE
    }

    fun hideLoadingIndicator() {
        binding.loadingIndicator.visibility = View.GONE
    }

}