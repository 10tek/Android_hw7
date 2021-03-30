package kz.step.hw7

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AuthActivity : AppCompatActivity() {
    var editDataButton: Button? = null
    var nameEditText: TextView? = null
    var surnameEditText: TextView? = null
    var photoImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        initializeViews()
        initializeListeners()
        val name = intent.getStringExtra(EXTRA_TEXT_NAME)
        val surname = intent.getStringExtra(EXTRA_TEXT_SURNAME)
        //val bitmap = intent.getParcelableExtra(EXTRA_IMAGE_PHOTO) as Bitmap?
        nameEditText?.text = "Имя $name"
        surnameEditText?.text = "Фамилия $surname"
        //photoImageView?.setImageBitmap(bitmap)
        if (intent.hasExtra(EXTRA_IMAGE_BYTE_ARRAY)) {
            val b = BitmapFactory.decodeByteArray(
                intent.getByteArrayExtra("byteArray"),
                0,
                intent.getByteArrayExtra("byteArray")!!.size
            )
            photoImageView?.setImageBitmap(b)
        }
    }

    private fun initializeViews() {
        editDataButton = findViewById(R.id.button_auth_activity_edit_data)
        nameEditText = findViewById(R.id.text_view_auth_activity_name)
        surnameEditText = findViewById(R.id.text_view_auth_activity_surname)
        photoImageView = findViewById(R.id.image_view_auth_activity_selfie)
    }

    private fun initializeListeners() {
        editDataButton?.setOnClickListener {
            val intent = Intent(this@AuthActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }


    companion object {
        const val EXTRA_TEXT_NAME = "name"
        const val EXTRA_TEXT_SURNAME = "surname"
        const val EXTRA_IMAGE_PHOTO = "BitmapImage"
        const val EXTRA_IMAGE_BYTE_ARRAY = "byteArray"
    }
}