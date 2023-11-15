package com.bignerdranch.android.geoquiz1


import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.geoquiz1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val quizViewModel: QuizViewModel by lazy { ViewModelProvider(this)[QuizViewModel::class.java] } // Создает сохраненное состояние ViewModel
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val calbackValue = result.data?.getBooleanExtra("AnsverForMainActivity", false)
                    if (calbackValue == true) binding.chaterName.text = "ЖУЛИК!!"
                }
            }
        updateQuestion()
        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            enableAllButton(switch = false)
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
            enableAllButton(switch = false)
        }
        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            enableAllButton(switch = true)
        }
        binding.backButton.setOnClickListener {
            if (quizViewModel.currentIndex > 0) {
                quizViewModel.currentIndex = quizViewModel.currentIndex - 1
                updateQuestion()
            } else Toast.makeText(this, "Это первый вопрос", Toast.LENGTH_SHORT).show()
        }
        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer // эта переменная несет в себе правильный ответ на вопрос в виде boolean типа данных
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            launcher.launch(intent)

        }
    }


    private fun updateQuestion() {
        binding.questionTextView.text = resources.getString(quizViewModel.currentQuestionTextId)

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.couterTrueAnswer++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        updateCounterAnswer()
    }

    private fun updateCounterAnswer() {
        binding.counterTrueAnswer.text =
            "На данный момент ${quizViewModel.couterTrueAnswer} правильных ответов"
    }

    private fun enableAllButton(switch: Boolean) {
        binding.trueButton.isEnabled = switch
        binding.falseButton.isEnabled = switch
    }

}