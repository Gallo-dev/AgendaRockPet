package br.com.gallodev.agendapet.presentation.ListCliente

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.gallodev.agendapet.R
import br.com.gallodev.agendapet.data.AppDataBase.AppDatabase
import br.com.gallodev.agendapet.data.AppDataBase.ClienteDao
import br.com.gallodev.agendapet.data.model.Cliente
import br.com.gallodev.agendapet.databinding.ActivityFormularioClienteBinding
import br.com.gallodev.agendapet.presentation.extension.formataTelefone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
class FormularioClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormularioClienteBinding
    private lateinit var clienteDao: ClienteDao

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galeriaLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var viewModel: FormularioViewModel
    private var imagemPerfilBitmap: Bitmap? = null // Variável para armazenar a imagem do perfil
    private var imagemPerfilPath: String? = null // Variável para armazenar o caminho da imagem do perfil

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
        private const val GALERIA_PERMISSION_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityFormularioClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clienteDao = AppDatabase.getDatabase(this).clienteDao()

        viewModel = ViewModelProvider(this)[FormularioViewModel::class.java]

        binding.dataAtendimentoFormulario.setOnClickListener {
            showDatePickerDialog()
        }
        binding.horaAtendimentoFormulario.setOnClickListener {
            showTimePickerDialog()
        }

        title = "Dados do Cliente"

        configuraBotaoSalvar()
        configCameraLauncher()
        configGaleriaLauncher()
        editarFotoFerfil()


    }

    // Método para exibir o DatePickerDialog
    private fun showDatePickerDialog() {
        val dataPicker = DataPickerFragment { selectedDate ->
            binding.dataAtendimentoFormulario.setText(selectedDate)
        }
        dataPicker.show(supportFragmentManager, "dataPicker")
    }

    // Método para exibir o TimePickerDialog
    private fun showTimePickerDialog() {
        val timePicker = HoraPickerFragment { selectedTime ->
            binding.horaAtendimentoFormulario.setText(selectedTime)
        }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun editarFotoFerfil() {
        val botaoEditarFoto = binding.icCameraEdit
        botaoEditarFoto.setOnClickListener {
            escolherFoto()
        }
    }

    // Método para capturar imagem com a camera
    private fun configCameraLauncher() {
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data // Dados da imagem capturada pela câmera
                    val imagem = data?.extras?.get("data") as Bitmap?  // Obtém a imagem capturada
                    imagem?.let {
                        mostrarFoto(it)
                    }
                } else {
                    Log.e("FormularioClienteActivity", "Erro ao capturar imagem da câmera")
                }
            }
    }

    // Método para capturar imagem da galeria
    private fun configGaleriaLauncher(){
        galeriaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val bitmap = uriParaBitmap(uri)
                if (bitmap != null) {
                    Log.d("FormularioClienteActivity", "Imagem selecionada: $uri")
                    imagemPerfilBitmap = bitmap
                    mostrarFoto(bitmap)
                } else {
                    Log.e("FormularioClienteActivity", "Erro ao converter URI para Bitmap")
                }
                } else {
                    Log.e("FormularioClienteActivity", "Nenhuma imagem selecionada")
            }
        }
    }

    private fun escolherFoto() {
        val opcoes = arrayOf("Camera", "Galeria")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Escolher Foto")
        builder.setItems(opcoes) { _, which ->
            when (which) {
                0 -> viewModel.verificarPermissaoCamera(this)
                1 -> viewModel.verificarPermissaoGaleria(this)
            }
        }
        builder.show()
    }


    @SuppressLint("QueryPermissionsNeeded")
    fun tirarFoto() {
        // Lógica para tirar uma foto
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            cameraLauncher.launch(cameraIntent)
        } else{
            Log.e("FormularioClienteActivity", "Erro ao abrir a câmera")
        }
    }

    fun escolherDaGaleria() {
        // Lógica para escolher uma imagem da galeria
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            galeriaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else {
            val galeryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeryIntent, GALERIA_PERMISSION_REQUEST_CODE)
        }
    }

    private fun mostrarFoto(bitmap: Bitmap) {
        val fotoPerfil = findViewById<ImageView>(R.id.imagem_perfil)
        fotoPerfil.setImageBitmap(bitmap)
        imagemPerfilBitmap = bitmap // Atualiza a imagem do perfil
    }

    private fun uriParaBitmap(selectedFileURI: Uri): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, selectedFileURI)
                val bitmap = ImageDecoder.decodeBitmap(source)
                Log.d(
                    "FormularioClienteActivity",
                    "uriToBitmap: Bitmap decodificado com sucesso (API >= 28)"
                )
                bitmap
            } else {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(this.contentResolver, selectedFileURI)
                Log.d(
                    "FormularioClienteActivity",
                    "uriToBitmap: Bitmap decodificado com sucesso (API < 28)"
                )
                bitmap
            }
        } catch (e: Exception) {
            Log.e("FormularioClienteActivity", "Erro ao converter")
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }
    }


    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.botaoSalvarFormulario
        botaoSalvar.setOnClickListener {
            val clienteNovo = criaCliente()
            lifecycleScope.launch(Dispatchers.IO) {
                clienteDao.insert(cliente = clienteNovo)
                finish()
            }
        }
    }

    private fun criaCliente(): Cliente {
        val campoNomeTutor = binding.nomeClienteFormulario.text
        val nomeTuotor = campoNomeTutor.toString()

        val campoNomeAnimal = binding.nomeAnimalFormulario.text
        val nomeAnimal = campoNomeAnimal.toString()

        val telefoneTutor = binding.telefoneClienteFormulario.text.toString()
            .formataTelefone()

        val campoEmail = binding.emailClienteFormulario.text
        val emailTotor = campoEmail.toString()

        val campoObservacao = binding.observacaoClienteFormulario.text
        val observacaoAnimal = campoObservacao.toString()

        val campoData = binding.dataAtendimentoFormulario.text
        val dataAtendimento = campoData.toString()

        val campoHora = binding.horaAtendimentoFormulario.text
        val horaAtendimento = campoHora.toString()

       imagemPerfilPath = imagemPerfilBitmap?.let { salvarImagem(it) }
        Log.i("FormularioClienteActivity", "Caminho da imagem do perfil: $imagemPerfilPath")

        return Cliente(
            nomeTutor = nomeTuotor,
            nomeAnimal = nomeAnimal,
            dataAtendimento = dataAtendimento,
            horaAtendimento = horaAtendimento,
            telefoneTutor = telefoneTutor,
            emailTutor = emailTotor,
            observacaoAnimal = observacaoAnimal,
            imagemPerfil = imagemPerfilPath
        )
    }

    private fun salvarImagem(bitmap: Bitmap): String {
        val nomeArquivo = "imagem_perfil_${System.currentTimeMillis()}.jpg" // Nome do arquivo da imagem
        val arquivo = File(filesDir, nomeArquivo) // Caminho do arquivo da imagem
        try {
            val stream = FileOutputStream(arquivo)
            bitmap.compress((Bitmap.CompressFormat.JPEG), 100, stream)
            stream.flush()
            stream.close()
            return arquivo.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            return null.toString()
        }
    }


    override fun onRequestPermissionsResult( // Sobrescreve o método onRequestPermissionsResult
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray // Array de resultados de permissões
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) // Chamar o método da superclasse
        Log.i("FormularioClienteActivity", "onRequestPermissionsResult chamado")
        viewModel.onRequestPermissionsResult(requestCode, grantResults, this)
    }
}