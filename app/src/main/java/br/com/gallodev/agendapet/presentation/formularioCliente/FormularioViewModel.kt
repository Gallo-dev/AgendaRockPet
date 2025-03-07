package br.com.gallodev.agendapet.presentation.formularioCliente

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
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            activity.tirarFoto()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                mostrarDialogoExplicativoCamera(activity)
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            }
        }
    }

    fun verificarPermissaoGaleria(activity: FormularioClienteActivity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
            activity.escolherDaGaleria()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_IMAGES)) {
                mostrarDialogoExplicativoGaleria(activity)
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), GALERIA_PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun mostrarDialogoExplicativoCamera(activity: FormularioClienteActivity) {
        AlertDialog.Builder(activity)
            .setTitle("Permissão para Câmera")
            .setMessage("Precisamos da permissão para acessar a câmera para que você possa tirar fotos.")
            .setPositiveButton("Ok") { _, _ ->
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun mostrarDialogoExplicativoGaleria(activity: FormularioClienteActivity) {
        AlertDialog.Builder(activity)
            .setTitle("Permissão para Galeria")
            .setMessage("Precisamos da permissão para acessar a galeria para que você possa escolher fotos.")
            .setPositiveButton("Ok") { _, _ ->
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), GALERIA_PERMISSION_REQUEST_CODE)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    // Método para lidar com a resposta do usuário a permissão
    fun onRequestPermissionsResult(
        requestCode: Int,
        grantResults: IntArray,
        activity: FormularioClienteActivity
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    activity.tirarFoto()
                }
            }
            GALERIA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    activity.escolherDaGaleria()
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