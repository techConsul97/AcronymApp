package com.sebqv97.acronymapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sebqv97.acronymapp.common.Constants
import com.sebqv97.acronymapp.common.UseCases
import com.sebqv97.acronymapp.common.utils.Resource
import com.sebqv97.acronymapp.common.utils.getWordsUseCaseErrorHandler
import com.sebqv97.acronymapp.common.utils.removeWhiteSpaces
import com.sebqv97.acronymapp.databinding.ActivityMainBinding
import com.sebqv97.acronymapp.domain.model.WordFormsItem
import com.sebqv97.acronymapp.presentation.WordFormsItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val tag = "State"
    private var searchFor = ""
    private var error:String? = null
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
        CoroutineScope(Dispatchers.Main).launch { wordsViewModel.liveWordsData.collect { state ->
            when (state) {
                is Resource.Loading -> Log.d(tag, "DATA LOADING")
                is Resource.Error ->{
                    state.errorType.let {
                        error = getWordsUseCaseErrorHandler(it!!)
                        updateUiWithError(error!!)
                    }
                }// Log.d(tag, state.errorType)
                is Resource.Success -> {
                    Log.d(
                        tag,
                        state.data.toString()
                            ?: "Success, but data is null, which is really problematic"
                    )
                    updateUi(state.data!!)
                }
            }
        }  }

    }

    private fun searchFor() {
        var searchText:String
         searchFor = ""
        binding.run {
            searchText = editTextSearchWord.text.toString().uppercase().removeWhiteSpaces()

                if(radioButtonGetAbbreviation.isChecked)
                    searchFor =  Constants.API_GET_ABBREVIATION
                else if(radioButtonGetWord.isChecked)
                    searchFor = Constants.API_GET_LONG_FORM
            editTextSearchWord.text.clear()
            wordName.text = searchText
            //map use-case GetWords's data
            val getWordsUseCase = UseCases.GetWords(queryType = searchFor, searchedWord = searchText)
           wordsViewModel.useCaseMapper(getWordsUseCase)

        }



    }

    private fun updateUi(data: WordFormsItem) {
        val overviewPageModel = data.toOverviewPageModel()
        val adapter = if(searchFor ==Constants.API_GET_LONG_FORM)
            ArrayAdapter<String>(this, R.layout.list_item, overviewPageModel.longForms)
                    else {
                        val singleWordArray = arrayListOf(data.shortForm)
                        ArrayAdapter<String>(this,R.layout.list_item,singleWordArray)
                    }
        binding.apply {
            listViewOverview.adapter = adapter
            listViewOverview.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val tappedString = overviewPageModel.longForms[position]
                    for(longForm in data.longForms){
                        while(longForm.getCurrentLongForm(tappedString)==null){}
                    }
                    val wordInfo = data.longForms
                    Toast.makeText(this@MainActivity,tappedString,Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateUiWithError(errorType:String){
        //save error into field Error
        error = errorType
        val adapter = ArrayAdapter(this@MainActivity,R.layout.list_item,listOf(error))

        binding.run { listViewOverview.adapter = adapter }
        error = null

    }

}