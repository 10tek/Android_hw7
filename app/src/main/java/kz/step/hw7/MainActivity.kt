package kz.step.hw7

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {
    var signUpButton: Button? = null
    var nameEditText: EditText? = null
    var surnameEditText: EditText? = null
    var photoImageView: ImageView? = null
    var errorTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        initializeListeners()
    }

    private fun checkData(): Boolean {
        return nameEditText?.text.isNullOrEmpty() || surnameEditText?.text.isNullOrEmpty() || photoImageView?.drawable == null
    }

    private fun initializeViews() {
        signUpButton = findViewById(R.id.button_main_activity_sign_up)
        nameEditText = findViewById(R.id.edit_text_main_activity_name)
        surnameEditText = findViewById(R.id.edit_text_main_activity_surname)
        photoImageView = findViewById(R.id.image_view_main_activity_selfie)
        errorTextView = findViewById(R.id.text_view_main_activity_error)
    }

    private fun initializeListeners() {
        photoImageView?.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

        signUpButton?.setOnClickListener {
            if(checkData()){
                errorTextView?.text = "Пожалуйста, заполните все поля!"
            }else{
                val bitmap: Bitmap? = photoImageView?.drawToBitmap()
                val bs = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 50, bs)

                val intent = Intent(this@MainActivity, AuthActivity::class.java).apply {
                    putExtra(AuthActivity.EXTRA_TEXT_SURNAME, surnameEditText?.text.toString())
                    putExtra(AuthActivity.EXTRA_TEXT_NAME, nameEditText?.text.toString())
                    //putExtra(AuthActivity.EXTRA_IMAGE_PHOTO, bitmap)
                    putExtra(AuthActivity.EXTRA_IMAGE_BYTE_ARRAY, bs.toByteArray())
                }

                startActivity(intent)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Фотка сделана, извлекаем миниатюру картинки
            val thumbnailBitmap = data?.extras?.get("data") as Bitmap
            photoImageView?.setImageBitmap(thumbnailBitmap)
        }
    }

    companion object {
        private const val REQUEST_TAKE_PHOTO = 1
    }
}