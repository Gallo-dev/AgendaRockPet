package br.com.gallodev.agendapet.presentation.ListCliente

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment

class DataPickerFragment( private val listener: (String) -> Unit) : DialogFragment(),
DatePickerDialog.OnDateSetListener {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendario = Calendar.getInstance()
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, ano, mes, dia)
    }

    override fun onDateSet(view: DatePicker?, ano: Int, mes: Int, diaDoMes: Int) {
        val selecionaData = String.format("%02d/%02d/%04d", diaDoMes, mes + 1, ano)
        listener(selecionaData)
    }
}