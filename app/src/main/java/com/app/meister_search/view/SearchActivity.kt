package com.app.meister_search.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.meister_search.R
import com.app.meister_search.databinding.ActivitySearchBinding
import com.app.meister_search.databinding.InfoDialogBinding
import com.app.meister_search.model.CustomTask
import com.app.meister_search.viewmodel.SearchActivityViewModel
import java.util.*


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    lateinit var context: Context
    lateinit var searchActivityViewModel: SearchActivityViewModel
    val adapter = SearchAdapter()
    var customListOriginal: List<CustomTask> = arrayListOf()
    val customListFiltered: List<CustomTask> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        context = this@SearchActivity
        initialization()
        controllisteners()
    }

    private fun initialization() {
        searchActivityViewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        binding.recyclerResults.adapter = adapter

    }

    private fun dataLoaders(searchTerm: String) {

        searchActivityViewModel.getSearchResults(searchTerm)
        searchActivityViewModel.customTaskList?.observe(this, { apiResponse ->
            if (apiResponse != null && apiResponse.isNotEmpty()) {
                customListOriginal = apiResponse
                loadRecycler(apiResponse)
                showRecycler()
                hideLoadingIndicator()
            } else {
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
                        binding.recyclerResults.adapter = null
                        binding.recyclerResults.adapter?.notifyDataSetChanged()
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

        binding.chipAll.setOnClickListener {
            loadRecycler(customListOriginal)
            binding.recyclerResults.adapter?.notifyDataSetChanged()
        }

        binding.chipActive.setOnClickListener {
            val customListFiltered: List<CustomTask> = customListOriginal.filter { customTask ->
                customTask.status
            }
            loadRecycler(customListFiltered)
            binding.recyclerResults.adapter?.notifyDataSetChanged()
        }

        binding.chipArchived.setOnClickListener {
            val customListFiltered: List<CustomTask> = customListOriginal.filter { customTask ->
                !customTask.status
            }
            loadRecycler(customListFiltered)
            binding.recyclerResults.adapter?.notifyDataSetChanged()
        }

        adapter.onItemClick = { customTask ->
            showDialog(customTask.taskName, customTask.projectName)
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

    fun showDialog(taskName: String, projectName: String) {

        val binding: InfoDialogBinding =
            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.info_dialog, null, false)
        val dialog = Dialog(context)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(binding.root)
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show()

        binding.txtvwTaskname.text = getString(R.string.label_task_name) + taskName
        binding.txtvwProjectname.text = getString(R.string.label_projectname) + projectName

        binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        binding.imgvwClose.setOnClickListener { dialog.dismiss() }

    }

}