package br.com.gallodev.agendapet.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.aplicaMascaraData() {
    this.addTextChangedListener(object : TextWatcher {
        private var isUpdating = false
        private var oldText = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (isUpdating || s.isNullOrBlank()) return

            isUpdating = true

            val unmasked = s.toString().replace(Regex("[^0-9]"), "")// Remove caracteres não numéricos
            val format = StringBuilder()
                for (i in unmasked.indices) {
                    if (i >= 8) break // Limita a formatação a 8 caracteres

                    format.append(unmasked[i])
                    if ((i == 1 || i == 3) && i < unmasked.length - 1) {
                        format.append("/")
                    }
                }
            if (format.toString() != s.toString()) {
                this@aplicaMascaraData.setText(format.toString())
                this@aplicaMascaraData.setSelection(format.length)
            }
            isUpdating = false
        }
        override fun afterTextChanged(s: Editable?) {}
    })
}