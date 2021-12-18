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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.meister_search.R
import com.app.meister_search.databinding.ActivitySearchBinding
import com.app.meister_search.model.CustomTask
import com.app.meister_search.model.SearchResponse
import com.app.meister_search.persistence.AppDatabase
import com.app.meister_search.persistence.TaskArchive
import com.app.meister_search.persistence.TaskArchiveDao
import com.app.meister_search.viewmodel.SearchActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    lateinit var context: Context
    lateinit var searchActivityViewModel: SearchActivityViewModel
    val adapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        context = this@SearchActivity
        initialization()
        controllisteners()
        callInsert()
    }

    private fun initialization() {
        searchActivityViewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)

    }

    private fun dataLoaders(searchTerm: String) {

        searchActivityViewModel.getSearchResults(searchTerm)
        searchActivityViewModel.customTaskList?.observe(this, {
                apiResponse ->
            if (apiResponse!=null){
                loadRecycler(apiResponse)
                showRecycler()
                hideLoadingIndicator()
            }else
            {
                showSearchMessage()
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


        binding.chipActive.setOnClickListener {
            //TODO filter the list
        }

        binding.chipArchived.setOnClickListener {

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


    private fun loadRecycler(results: List<CustomTask>?) {
        binding.recyclerResults.adapter = adapter
        binding.recyclerResults.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        if (results != null) {
            adapter.resultList = results
        }
    }

    fun showLoadingIndicator() {
        binding.loadingIndicator.visibility = View.VISIBLE
    }

    fun hideLoadingIndicator() {
        binding.loadingIndicator.visibility = View.GONE
    }

    fun callInsert(){
        lifecycleScope.launch(Dispatchers.IO) {
            //searchActivityViewModel.insertSampleData()
        }
    }

}