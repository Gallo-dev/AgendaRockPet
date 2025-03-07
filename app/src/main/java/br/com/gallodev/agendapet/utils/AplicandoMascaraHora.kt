package br.com.gallodev.agendapet.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.aplicaMascaraHora() {
    this.addTextChangedListener(object : TextWatcher {
        private var isUpdating = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (isUpdating || s.isNullOrEmpty()) return

            isUpdating = true

            val unmasked = s.toString().replace(Regex("[^0-9]"), "") // Apenas números
            val formatted = StringBuilder()

            for (i in unmasked.indices) {
                if (i >= 4) break
                formatted.append(unmasked[i])
                if (i == 1 && i < unmasked.length - 1) {
                    formatted.append(":")
                }
            }

            if (formatted.toString() != s.toString()) {
                this@aplicaMascaraHora.setText(formatted.toString())
                this@aplicaMascaraHora.setSelection(formatted.length)
            }

            isUpdating = false
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}