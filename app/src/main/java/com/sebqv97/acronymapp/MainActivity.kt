package com.sebqv97.acronymapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sebqv97.acronymapp.common.Constants
import com.sebqv97.acronymapp.common.utils.Resource
import com.sebqv97.acronymapp.common.utils.removeWhiteSpaces
import com.sebqv97.acronymapp.databinding.ActivityMainBinding
import com.sebqv97.acronymapp.domain.model.WordFormsItem
import com.sebqv97.acronymapp.presentation.WordFormsItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val tag = "State"
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val wordsViewModel: WordFormsItemViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(
            WordFormsItemViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        //take data from userInput and pass it to viewModel
        binding.buttonSearch.setOnClickListener { searchFor() }

        //create request
       // wordsViewModel.getWordForms(Constants.API_GET_LONG_FORM, "BB")//careful, UPPERCASE ONLY

        //Observe the data

        wordsViewModel.liveWordsData.observe(this) { state ->
            when (state) {
                is Resource.Loading -> Log.d(tag, "DATA LOADING")
                is Resource.Error ->{}// Log.d(tag, state.errorType)
                is Resource.Success -> {
                    Log.d(
                        tag,
                        state.data.toString()
                            ?: "Success, but data is null, which is really problematic"
                    )
                    updateUi(state.data!!)
                }
            }
        }
    }

    private fun searchFor() {
        var searchText:String
        var searchFor:String= ""
        binding.run {
            searchText = editTextSearchWord.text.toString().uppercase().removeWhiteSpaces()

                if(radioButtonGetAbbreviation.isChecked)
                    searchFor =  Constants.API_GET_ABBREVIATION
                else if(radioButtonGetWord.isChecked)
                    searchFor = Constants.API_GET_LONG_FORM
            editTextSearchWord.text.clear()
            wordName.text = searchText
            wordsViewModel.getWordForms(queryType = searchFor, searchedWord = searchText)

        }



    }

    private fun updateUi(data: WordFormsItem) {
        val overviewPageModel = data.toOverviewPageModel()
        val adapter = ArrayAdapter<String>(this, R.layout.list_item, overviewPageModel.longForms)
        binding.apply {
            listViewOverview.adapter = adapter
            listViewOverview.onItemClickListener =
                object : AdapterView.OnItemClickListener {

                    override fun onItemClick(
                        parent: AdapterView<*>, view: View,
                        position: Int, id: Long
                    ) {
                        val tappedString = overviewPageModel.longForms[position]
                        for(longForm in data.longForms){
                        while(longForm.getCurrentLongForm(tappedString)==null){}
                        }
                        val wordInfo = data.longForms
                        Toast.makeText(this@MainActivity,tappedString,Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }
}