package com.msu.morrison.Geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.msu.morrison.Geoquiz.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var answerChosen = false

    private var correctAnswersCount = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.trueButton.setOnClickListener{
            if (!answerChosen) {
                checkAnswer(true)
                disableButtons()
                answerChosen = true
                updateCorrectAnswersCount(true)
                checkAndDisplayScore()
            }
        }


        binding.falseButton.setOnClickListener{
           if (!answerChosen) {
               checkAnswer(false)
               disableButtons()
               answerChosen = true
               updateCorrectAnswersCount(false)
               checkAndDisplayScore()
           }
        }

        binding.nextButton.setOnClickListener{
            enableButtons()

            if (currentIndex == questionBank.size - 1){
                answerChosen = false
                checkAndDisplayScore()
            } else {
                answerChosen = false
                currentIndex = (currentIndex + 1) % questionBank.size
                updateQuestion()
            }


        }

        binding.previousButton.setOnClickListener{
            currentIndex = (currentIndex - 1) % questionBank.size

            updateQuestion()
        }

        binding.questionTextview.setOnClickListener{
            currentIndex = (currentIndex +1) % questionBank.size

            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateCorrectAnswersCount(isCorrect: Boolean){
        if (isCorrect) {
            correctAnswersCount++
        }
    }

    private fun checkAndDisplayScore(){
        if(currentIndex == questionBank.size - 1) {
            val score = (correctAnswersCount.toDouble() / questionBank.size) * 100.0
            val formattedScore = BigDecimal(score).setScale(1,RoundingMode.HALF_EVEN).toString() + "%"
            Toast.makeText(this, "Your score: $formattedScore", Toast.LENGTH_SHORT).show()

            correctAnswersCount = 0
        }

    }

    private fun disableButtons(){
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    private fun enableButtons(){
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResID
        binding.questionTextview.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

}