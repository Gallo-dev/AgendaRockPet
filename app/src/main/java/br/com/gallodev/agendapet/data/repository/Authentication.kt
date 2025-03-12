package br.com.gallodev.agendapet.data.repository

import android.util.Log
import br.com.gallodev.agendapet.data.model.Cliente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Authentication {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, senha: String, calback: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, senha)
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
        senha: String,
        cliente: Cliente,
        calback: (Boolean, String?) -> Unit
    )  {
        firebaseAuth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { cadastro ->
                Log.d("Authentication", "task: $cadastro")
                if (cadastro.isSuccessful) {
                    // obtein o id do usuario e retorna uma string vazia se for nulo
                    val uid = firebaseAuth.currentUser?.uid?: ""
                    Log.d("Authentication", "uid: $uid")
                    // obtein a instância do Firestore e salve os dados do cliente
                    val db = FirebaseFirestore.getInstance()
                    db.collection("clientes").document(uid).set(cliente)
                        .addOnCompleteListener { docTask ->
                            Log.d("Authentication", "docTask: $docTask")
                            if (docTask.isSuccessful) {
                                calback(true, null)
                            } else {
                                calback(false, docTask.exception?.message)
                            }
                        }
                }else {
                    calback(false, cadastro.exception?.message)
                }
            }
    }
}