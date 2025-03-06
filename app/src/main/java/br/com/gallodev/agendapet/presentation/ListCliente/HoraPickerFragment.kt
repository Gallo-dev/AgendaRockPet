package br.com.gallodev.agendapet.presentation.ListCliente

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar


class HoraPickerFragment(private val listener: (String) -> Unit): DialogFragment(),
TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val relogio = Calendar.getInstance()
        val hora = relogio.get(Calendar.HOUR_OF_DAY)
        val minuto = relogio.get(Calendar.MINUTE)

        return TimePickerDialog(
            requireContext(),
            this,
            hora,
            minuto,
            android.text.format.DateFormat.is24HourFormat(requireContext())
        )
    }

     override fun onTimeSet(view: TimePicker?, horaDoDia: Int, minuto: Int) {
        val selecionaHora = String.format("%02d : %02d", horaDoDia, minuto)
        listener(selecionaHora)
    }
}

