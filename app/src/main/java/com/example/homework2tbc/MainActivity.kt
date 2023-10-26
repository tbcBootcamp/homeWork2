package com.example.homework2tbc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework2tbc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClick.setOnClickListener {
            val input = binding.etInput.text.toString()
            when {
                input == "" -> { binding.tvOutput.text = "ჩაწერეთ რიცხვი 1 დან 1000 მდე" }
                input == "0" -> { binding.tvOutput.text = "ნული" }
                input.toInt() < 0 || input.toInt() > 1000 -> { binding.tvOutput.text = "ჩაწერეთ რიცხვი 1 დან 1000 მდე" }
                else -> {
                    when (input.length) {
                        1 -> { binding.tvOutput.text = convertTwoDigitNumToWord(input) }
                        2 -> { binding.tvOutput.text = convertTwoDigitNumToWord(input) }
                        3 -> { binding.tvOutput.text = convertThreeDigitNumToWord(input) }
                        4 -> { binding.tvOutput.text = "ათასი" }
                        else -> { binding.tvOutput.text = "ჩაწერეთ რიცხვი 1 დან 1000 მდე" }
                    }
                }
            }
        }
    }

    private fun convertTwoDigitNumToWord(input: String, ): String  {
        var resultString = ""
        if (input.toInt() <= 20) {
            resultString = upToTwenty[input.toInt() - 1]
        } else if (input.toInt() < 100) {
            when (input[1]) {
                '0' -> resultString = tens[(input[0].toString().toInt() - 3)]
                else -> {
                    val untilTen = upToTwenty[(input[1].toString().toInt() - 1)]
                    val fromTenToTwenty = upToTwenty[(input[1].toString().toInt() + 9)]
                    val startingNum = when (input[0].toString().toInt()) {
                        2, 3 -> 0
                        4, 5 -> 1
                        6, 7 -> 2
                        else -> 3
                    }
                    when (input[0].toString().toInt()) {
                        2, 4, 6, 8 -> { resultString = tensWithNum[startingNum] + untilTen }
                        3, 5, 7, 9 -> { resultString = tensWithNum[startingNum] + fromTenToTwenty }
                    }
                }
            }
        }
        return resultString
    }

    private fun convertThreeDigitNumToWord(input: String): String {
        var resultString = ""
        val hundredsIndex = input[0].toString().toInt() - 1
        if (input.toInt() < 1000) {
            resultString = when (input[1]) {
                '0' -> {
                    when (input[2]) {
                        '0' -> hundreds[hundredsIndex]
                        else -> {
                            hundredsWithNum[hundredsIndex] + convertTwoDigitNumToWord(input.last().toString())
                        }
                    }
                }

                else -> {
                    hundredsWithNum[hundredsIndex] + convertTwoDigitNumToWord(input.drop(1))
                }
            }
        }
        return resultString
    }

    private val upToTwenty = listOf(
        "ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა",
        "ათი", "თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი",
        "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამტი", "ოცი"
    )
    private val tens = listOf(
        "ოცდაათი", "ორმოცი", "ორმოცდაათი", "სამოცი", "სამოცდაათი", "ოთხმოცი", "ოთხმოცდაათი", "ასი"
    )
    private val tensWithNum = listOf("ოცდა", "ორმოცდა", "სამოცდა", "ოთხმოცდა")
    private val hundreds = listOf(
        "ასი", "ორასი", "სამასი", "ოთხასი", "ხუთასი", "ექვსასი", "შვიდასი", "რვაასი", "ცხრაასი"
    )
    private val hundredsWithNum =
        listOf(
            "ას ", "ორას ", "სამას ", "ოთხას ", "ხუთას ", "ექვსას ", "შვიდას ", "რვაას ", "ცხრაას "
        )
}
