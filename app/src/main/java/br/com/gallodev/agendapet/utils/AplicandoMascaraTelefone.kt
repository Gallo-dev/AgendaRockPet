package br.com.gallodev.agendapet.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.aplicaMascaraTel() {
    this.addTextChangedListener(object : TextWatcher {
        private var isUpdating = false
        private var oldText = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (isUpdating || s == null) return

            val unmasked = s.toString().replace(Regex("[^0-9]"), "")// Remove caracteres não numéricos
            val formatted = when (unmasked.length) {
                in 0..9 -> unmasked // Não formata se tiver menos de 10 caracteres
                10 -> "(${unmasked.substring(0, 2)}) ${unmasked.substring(2, 7)}-${unmasked.substring(7)}"
                11 -> "(${unmasked.substring(0, 2)}) ${unmasked.substring(2, 7)}-${unmasked.substring(7)}"
                else -> oldText // Não formata se tiver mais de 11 caracteres
            }
            if (formatted != s.toString()) {
                isUpdating = true
                this@aplicaMascaraTel.setText(formatted)
                this@aplicaMascaraTel.setSelection(formatted.length)
                isUpdating = false
            }
            oldText = formatted
        }
        override fun afterTextChanged(s: Editable?) {}

    })
}