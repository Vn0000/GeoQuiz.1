package com.bignerdranch.android.geoquiz1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.geoquiz1.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE = "CheatActivity_key" // ключ для интента для принятия данных
const val EXTRA_ANSWER_SHOWN = "AnsverForMainActivity" // ключ для интента для передачи данных


class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false
    private val binding: ActivityCheatBinding by lazy { ActivityCheatBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false)


        binding.showAnswerButton.setOnClickListener {
            binding.answerTextView.text = trunslite(answerIsTrue)
            setAnswerShownResult(true)
        }
    }

    private fun trunslite (answerIsTrue: Boolean): String {
        var textAnswer: String
        if (answerIsTrue == true) {textAnswer = "правда"} else textAnswer = "ложь"
        return textAnswer
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply { putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue) }
        }
    }
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)}
        setResult(RESULT_OK, data)
    }



}