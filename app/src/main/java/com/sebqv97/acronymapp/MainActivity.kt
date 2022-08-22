package com.sebqv97.acronymapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.sebqv97.acronymapp.common.Constants
import com.sebqv97.acronymapp.common.utils.Resource
import com.sebqv97.acronymapp.databinding.ActivityMainBinding
import com.sebqv97.acronymapp.presentation.WordFormsItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val tag = "State"
    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(  layoutInflater) }
    val wordsViewModel: WordFormsItemViewModel by lazy { ViewModelProvider(this@MainActivity).get(WordFormsItemViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //create request
      wordsViewModel.getWordForms(Constants.API_GET_LONG_FORM,"www")

        //Observe the data

        wordsViewModel.liveWordsData.observe(this){
            state-> when(state){
                is Resource.Loading-> Log.d(tag,"DATA LOADING")
                is Resource.Error -> Log.d(tag,state.message?: "Error, but error message is null")
                is Resource.Success -> Log.d(tag,state.data.toString() ?: "Success, but data is null, which is really problematic")
            }
        }


        setContentView(binding.root)
    }
}