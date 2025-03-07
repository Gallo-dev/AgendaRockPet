package br.com.gallodev.agendapet.utils

fun String.formataTelefone():String {
    val numero = this.replace(Regex("[^0-9]"), "")
    if (numero.length == 11) {
        return "(${numero.substring(0, 2)}) ${numero.substring(2, 7)}-${numero.substring(7)}"
        } else if (numero.length == 10) {
        return "(${numero.substring(0, 2)}) ${numero.substring(2, 6)}-${numero.substring(6)}"
    }
        return this
}


