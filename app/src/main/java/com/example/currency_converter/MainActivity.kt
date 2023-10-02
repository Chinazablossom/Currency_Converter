package com.example.currency_converter

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.currency_converter.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.BigInteger
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var isNotExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            // to display the radio buttons
            arrowFrom.setOnClickListener {
                toggle(arrowFrom, RG)
            }
            arrowto.setOnClickListener {
                toggle(arrowto, RG2)
            }

            //to convert the currency
            convertImg.setOnClickListener {
                convert()
            }

            ETfrom.setOnKeyListener { view, keyCode, _ ->
                handleKeyEvent(view, keyCode)
            }


        }

    }


    private fun toggle(image: ImageView, radioGroup: RadioGroup) {
        if (isNotExpanded) {
            radioGroup.visibility = View.GONE
            image.setImageResource(R.drawable.round_chevron_right_24)
            isNotExpanded = false
        } else {

            radioGroup.visibility = View.VISIBLE
            image.setImageResource(R.drawable.chevron_down)
            binding.RG.setOnCheckedChangeListener { group, _ ->
                when (group.checkedRadioButtonId) {
                    R.id.rbNaira -> binding.Imgfrom.setImageResource(R.drawable.nairaimg)
                    R.id.rbUSD -> binding.Imgfrom.setImageResource(R.drawable.__icon__united_states_)
                    R.id.rbPounds -> binding.Imgfrom.setImageResource(R.drawable.united_kingdom__gb_)
                    R.id.rbEuro -> binding.Imgfrom.setImageResource(R.drawable.euro)
                    R.id.rbBitcoin -> binding.Imgfrom.setImageResource(R.drawable.bitcoinimg)
                }
            }

            binding.RG2.setOnCheckedChangeListener { group, _ ->
                when (group.checkedRadioButtonId) {
                    R.id.rbNaira2 -> binding.Imgto.setImageResource(R.drawable.nairaimg)
                    R.id.rbUSD2 -> binding.Imgto.setImageResource(R.drawable.__icon__united_states_)
                    R.id.rbPounds2 -> binding.Imgto.setImageResource(R.drawable.united_kingdom__gb_)
                    R.id.rbEuro2 -> binding.Imgto.setImageResource(R.drawable.euro)
                    R.id.rbBitcoin2 -> binding.Imgto.setImageResource(R.drawable.bitcoinimg)
                }
            }
            isNotExpanded = true
        }


    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun BigDecimal.roundUp(): BigInteger {
        val numberBefore = this.toString().substringBefore(".").toBigInteger()
        val numberAfter = this.toString().substringAfter(".")
        val lastNumber = numberAfter.first().toString().toInt()

        return if (lastNumber >= 4) {
            numberBefore + BigInteger.ONE
        } else {
            numberBefore
        }
    }



    private fun convert() {

        val amountString = binding.ETfrom.text.toString()
        if (amountString.isEmpty()) {
            val num = "%s".format(0.0)
            binding.resultTxt.text = getString(R.string.resultStr, num)
            binding.TLfrom.error = "Field cannont be empty"
            return
        } else {
            binding.TLfrom.error = null
        }

        val values: BigDecimal
        val amount = try {
            amountString.toBigDecimal()
        } catch (e: NumberFormatException) {
            return
        }
        when {
            binding.RG.checkedRadioButtonId == R.id.rbNaira && binding.RG2.checkedRadioButtonId == R.id.rbNaira2 -> {
                values = BigDecimal.ONE
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale("en", "NG")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "NG")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbNaira && binding.RG2.checkedRadioButtonId == R.id.rbUSD2 -> {
                values = 0.0013.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance().format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance().format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }
            }
            binding.RG.checkedRadioButtonId == R.id.rbNaira && binding.RG2.checkedRadioButtonId == R.id.rbPounds2 -> {
                values = 0.0011.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale.UK).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale.UK).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbNaira && binding.RG2.checkedRadioButtonId == R.id.rbEuro2 -> {
                values = 0.0012.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbNaira && binding.RG2.checkedRadioButtonId == R.id.rbBitcoin2 -> {
                values = 0.00000005.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip = "%s".format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = "%s".format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }

            binding.RG.checkedRadioButtonId == R.id.rbUSD && binding.RG2.checkedRadioButtonId == R.id.rbUSD2 -> {
                values = BigDecimal.ONE
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance().format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance().format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbUSD && binding.RG2.checkedRadioButtonId == R.id.rbNaira2 -> {
                values = 769.55.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip = NumberFormat.getCurrencyInstance(Locale("en","NG")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en","NG")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbUSD && binding.RG2.checkedRadioButtonId == R.id.rbPounds2 -> {
                values = 0.82.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale.UK).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale.UK).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbUSD && binding.RG2.checkedRadioButtonId == R.id.rbEuro2 -> {
                values = 0.95.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbUSD && binding.RG2.checkedRadioButtonId == R.id.rbBitcoin2 -> {
                values = 0.000036.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip = "%s".format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = "%s".format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }

            binding.RG.checkedRadioButtonId == R.id.rbPounds && binding.RG2.checkedRadioButtonId == R.id.rbPounds2 -> {
                values = BigDecimal.ONE
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale.UK).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale.UK).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbPounds && binding.RG2.checkedRadioButtonId == R.id.rbNaira2 -> {
                values = 935.31.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale("en","NG")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en","NG")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbPounds && binding.RG2.checkedRadioButtonId == R.id.rbUSD2 -> {
                values = 1.22.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance().format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance().format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbPounds && binding.RG2.checkedRadioButtonId == R.id.rbEuro2 -> {
                values = 1.15.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbPounds && binding.RG2.checkedRadioButtonId == R.id.rbBitcoin2 -> {
                values = 0.000044.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip = "%s".format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = "%s".format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }

            binding.RG.checkedRadioButtonId == R.id.rbEuro && binding.RG2.checkedRadioButtonId == R.id.rbEuro2 -> {
                values = BigDecimal.ONE
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbEuro && binding.RG2.checkedRadioButtonId == R.id.rbNaira2 -> {
                values = 810.91.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale("en","NG")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en","NG")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbEuro && binding.RG2.checkedRadioButtonId == R.id.rbUSD2 -> {
                values = 1.06.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance().format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance().format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbEuro && binding.RG2.checkedRadioButtonId == R.id.rbPounds2 -> {
                values = 0.87.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale.UK).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance().format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbEuro && binding.RG2.checkedRadioButtonId == R.id.rbBitcoin2 -> {
                values = 0.000038.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip = "%s".format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = "%s".format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }

            binding.RG.checkedRadioButtonId == R.id.rbBitcoin && binding.RG2.checkedRadioButtonId == R.id.rbBitcoin2 -> {
                values = BigDecimal.ONE
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip = "%s".format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = "%s".format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbBitcoin && binding.RG2.checkedRadioButtonId == R.id.rbNaira2 -> {
                values = 21823976.27.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale("en","NG")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en","NG")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbBitcoin && binding.RG2.checkedRadioButtonId == R.id.rbUSD2 -> {
                values = 28015.10.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance().format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance().format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbBitcoin && binding.RG2.checkedRadioButtonId == R.id.rbPounds2 -> {
                values = 22966.44.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale.UK).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale.UK).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }
            binding.RG.checkedRadioButtonId == R.id.rbBitcoin && binding.RG2.checkedRadioButtonId == R.id.rbEuro2 -> {
                values = 26487.06.toBigDecimal()
                val total = amount * values
                val roundUp = binding.switchMaterial.isChecked

                if (roundUp) {
                    val roundedFormattedTip =
                        NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total.roundUp())
                    binding.resultTxt.text = getString(R.string.resultStr, roundedFormattedTip)

                } else {
                    val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "EU")).format(total)
                    binding.resultTxt.text = getString(R.string.resultStr, formattedTip)
                }

            }

        }

    }


}