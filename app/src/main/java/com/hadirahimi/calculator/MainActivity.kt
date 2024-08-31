package com.hadirahimi.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.hadirahimi.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var firstNumber = ""
    private var currentNumber = ""
    private var currentOperator = ""
    private var result = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        binding.apply {
            binding.layoutMain.children.filterIsInstance<Button>().forEach { button ->
                button.setOnClickListener {
                    val buttonText = button.text.toString()
                    when {
                        buttonText.matches(Regex("[0-9]")) -> {
                            if (currentOperator.isEmpty()) {
                                firstNumber += buttonText
                                tvResult.text = firstNumber
                            } else {
                                currentNumber += buttonText
                                tvResult.text = currentNumber
                            }
                        }

                        buttonText.matches(Regex("[+\\-*/]")) -> {
                            currentNumber = ""
                            if (tvResult.text.toString().isNotEmpty()) {
                                currentOperator = buttonText
                                tvResult.text = "0"
                            }
                        }

                        buttonText == "=" -> {
                            if (currentNumber.isNotEmpty() && currentOperator.isNotEmpty()) {
                                tvFormula.text = "$firstNumber$currentOperator$currentNumber"
                                result = evaluateExpression(firstNumber, currentNumber, currentOperator)
                                firstNumber = result
                                tvResult.text = result
                            }
                        }

                        buttonText == "." -> {
                            if (currentOperator.isEmpty()) {
                                if (!firstNumber.contains(".")) {
                                    firstNumber += if (firstNumber.isEmpty()) "0$buttonText"
                                    else buttonText
                                    tvResult.text = firstNumber
                                }
                            } else {
                                if (!currentNumber.contains(".")) {
                                    currentNumber += if (currentNumber.isEmpty()) "0$buttonText"
                                    else buttonText
                                    tvResult.text = currentNumber
                                }
                            }
                        }

                        // Nút xóa số
                        buttonText == "C" -> {
                            currentNumber = ""
                            firstNumber = ""
                            currentOperator = ""
                            tvResult.text = "0"
                            tvFormula.text = ""
                        }
                    }
                }
            }
        }
    }

    /**
     * Thực hiện tác vụ tính toán cơ bản
     * @param firstNumber Số thứ nhất
     * @param secondNumber Số thứ hai
     * @param operator Toán tử tương tác
     * @return Kết quả sau khi tính ở dạng chuỗi
     */
    private fun evaluateExpression(
        firstNumber: String,
        secondNumber: String,
        operator: String
    ): String {
        val num1 = firstNumber.toDouble()
        val num2 = secondNumber.toDouble()

        return when (operator) {
            "+" -> (num1 + num2).toString()
            "-" -> (num1 - num2).toString()
            "*" -> (num1 * num2).toString()
            "/" -> (num1 / num2).toString()
            else -> ""
        }
    }
}













































