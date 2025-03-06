package br.com.gallodev.agendapet.presentation.ListCliente

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class FormularioViewModel : ViewModel() {
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
        private const val GALERIA_PERMISSION_REQUEST_CODE = 101
    }

    fun verificarPermissaoCamera(activity: FormularioClienteActivity) {
        Log.i("AddClientViewModel", "verificarPermissaoCamera chamado")
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.i("AddClientViewModel", "Permissão CAMERA já concedida")
            activity.tirarFoto()
        } else {
            Log.i("AddClientViewModel", "Permissão CAMERA não concedida, verificando se precisa de explicação")
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                Log.i("AddClientViewModel", "Precisa de explicação para permissão CAMERA")
                mostrarDialogoExplicativoCamera(activity)
            } else {
                Log.i("AddClientViewModel", "Não precisa de explicação para permissão CAMERA, solicitando permissão")
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            }
        }
    }

    fun verificarPermissaoGaleria(activity: FormularioClienteActivity) {
        Log.i("AddClientViewModel", "verificarPermissaoGaleria chamado")
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
            Log.i("AddClientViewModel", "Permissão GALERIA já concedida")
            activity.escolherDaGaleria()
        } else {
            Log.i("AddClientViewModel", "Permissão GALERIA não concedida, verificando se precisa de explicação")
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_IMAGES)) {
                Log.i("AddClientViewModel", "Precisa de explicação para permissão GALERIA")
                mostrarDialogoExplicativoGaleria(activity)
            } else {
                Log.i("AddClientViewModel", "Não precisa de explicação para permissão GALERIA, solicitando permissão")
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), GALERIA_PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun mostrarDialogoExplicativoCamera(activity: FormularioClienteActivity) {
        Log.i("AddClientViewModel", "mostrarDialogoExplicativoCamera chamado")
        AlertDialog.Builder(activity)
            .setTitle("Permissão para Câmera")
            .setMessage("Precisamos da permissão para acessar a câmera para que você possa tirar fotos.")
            .setPositiveButton("Ok") { _, _ ->
                Log.i("AddClientViewModel", "Usuário clicou em Ok no diálogo de explicação da câmera")
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                Log.i("AddClientViewModel", "Usuário clicou em Cancelar no diálogo de explicação da câmera")
                dialog.dismiss()
            }
            .show()
    }

    private fun mostrarDialogoExplicativoGaleria(activity: FormularioClienteActivity) {
        Log.i("AddClientViewModel", "mostrarDialogoExplicativoGaleria chamado")
        AlertDialog.Builder(activity)
            .setTitle("Permissão para Galeria")
            .setMessage("Precisamos da permissão para acessar a galeria para que você possa escolher fotos.")
            .setPositiveButton("Ok") { _, _ ->
                Log.i("AddClientViewModel", "Usuário clicou em Ok no diálogo de explicação da galeria")
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), GALERIA_PERMISSION_REQUEST_CODE)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                Log.i("AddClientViewModel", "Usuário clicou em Cancelar no diálogo de explicação da galeria")
                dialog.dismiss()
            }
            .show()
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        grantResults: IntArray,
        activity: FormularioClienteActivity
    ) {
        Log.i("AddClientViewModel", "onRequestPermissionsResult chamado")
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("AddClientViewModel", "Permissão CAMERA concedida pelo usuário")
                    activity.tirarFoto()
                } else {
                    Log.e("AddClientViewModel", "Permissão CAMERA negada pelo usuário")
                }
            }
            GALERIA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("AddClientViewModel", "Permissão GALERIA concedida pelo usuário")
                    activity.escolherDaGaleria()
                } else {
                    Log.e("AddClientViewModel", "Permissão GALERIA negada pelo usuário")
                }
            }
        }
    }

//    fun verificarPermissaoCamera(activity: FormularioClienteActivity) {
//        Log.i("FormularioViewModel", "verificarPermissaoCamera chamado")
//        if (ContextCompat.checkSelfPermission(
//                activity,
//                Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            activity.tirarFoto()
//        } else {
//            Log.i("FormularioViewModel", "Permissão de câmera concedida")
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    activity,
//                    Manifest.permission.CAMERA
//                )
//            ) {
//                mostrarDialogoExplicativoCamera(activity)
//            } else {
//                Log.i(
//                    "FormularioViewModel",
//                    "Não precisa de explicação para permissão CAMERA, solicitando permissão"
//                )
//                ActivityCompat.requestPermissions(
//                    activity,
//                    arrayOf(Manifest.permission.CAMERA),
//                    CAMERA_PERMISSION_REQUEST_CODE
//                )
//            }
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    private fun verificaPermissaoGaleria(activity: FormularioClienteActivity) {
//        Log.i("FomularioViewModel", "Verifica Permissão da Galeria")
//        if (ContextCompat.checkSelfPermission(
//                activity,
//                Manifest.permission.READ_MEDIA_IMAGES
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            activity.escolherDaGaleria()
//        }else {
//            Log.i("FormularioViewModel", "Permissão GALERIA não concedida, verificando se precisa de explicação")
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_IMAGES)) {
//                Log.i("FormularioViewModel", "Permissão GALERIA não concedida, solicitando explicação")
//                mostrarDialogoExplicativoGaleria(activity)
//            } else {
//                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), GALERIA_PERMISSION_REQUEST_CODE)
//            }
//        }
//
//    }
}