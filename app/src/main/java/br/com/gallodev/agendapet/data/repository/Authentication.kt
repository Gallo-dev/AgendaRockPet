package br.com.gallodev.agendapet.data.repository

import br.com.gallodev.agendapet.data.model.Cliente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Authentication(private val firevaseAuth: FirebaseAuth) {

    fun login(email: String, password: String, calback: (Boolean, String?) -> Unit) {
        firevaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    calback(true, null)
                } else {
                    calback(false, task.exception?.message)
                }
            }
    }

    fun register(
        email: String,
        password: String,
        cliente: Cliente,
        calback: (Boolean, String?) -> Unit
    ) {
        firevaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // obtein o id do usuario e retorna uma string vazia se for nulo
                    val uid = firevaseAuth.currentUser?.uid?: ""
                    // obtein a instância do Firestore e salve os dados do cliente
                    val db = FirebaseFirestore.getInstance()
                    db.collection("clientes").document(uid).set(cliente)
                        .addOnCompleteListener { docTask ->
                            if (docTask.isSuccessful) {
                                calback(true, null)
                            } else {
                                calback(false, docTask.exception?.message)
                            }
                        }
                }else {
                    calback(false, task.exception?.message)
                }
            }
    }
}