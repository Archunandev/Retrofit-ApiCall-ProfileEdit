package com.example.retrofit

import android.Manifest
import android.app.AlertDialog
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.example.retrofit.api.RetrofitInstance
import com.example.retrofit.model.profile
import com.example.retrofit.util.Utils
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_update.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class UpdateActivity : AppCompatActivity() {


    @BindView(R.id.textInputEditTextName)
    lateinit var textInputEditTextName: EditText
    @BindView(R.id.textInputEditTextEmail)
    lateinit var textInputEditTextEmail: EditText
    @BindView(R.id.textInputEditTextMobile)
    lateinit var textInputEditTextMobile: EditText
    @BindView(R.id.gender_spinner)
    lateinit var genderSpinner: Spinner
    @BindView(R.id.profile_image)
    lateinit var profileImage: CircleImageView

    @BindView(R.id.textInputLayoutName)
    lateinit var textInputLayoutName: TextInputLayout
    @BindView(R.id.textInputLayoutEmail)
    lateinit var textInputLayoutEmail: TextInputLayout
    @BindView(R.id.textInputLayoutMobile)
    lateinit var textInputLayoutMobile: TextInputLayout
    @BindView(R.id.update_button)
    lateinit var updateButton: Button

    @BindView(R.id.toolbar)
    lateinit var topAppBar: Toolbar

    private var newProfileImage: File? = null
    private var newProfileImageCOnverted: File? = null


    var nameString: String = ""
    var emailString: String = ""
    var mobileString: String = ""
    var profileUrl: String = ""
    var spinnerInt: Int = 0
    var spinnerSelectedInt: Int = 0
    var boolImage: Boolean = true

    private val permission_storage =
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val PERMISSION_REQUEST_CODE = 103


    private val permission_camera =
            arrayOf(Manifest.permission.CAMERA)
    private val CAMERA_PERMISSION_REQUEST_CODE = 104

    var easyImage: EasyImage? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        ButterKnife.bind(this)

        addItemsOnSpinner()
        spinnerListener()
        getIntentValues(intent)
        Tool()



    }


    private fun spinnerListener() {

        genderSpinner.setOnTouchListener(View.OnTouchListener { v, event ->
            Utils.hideKeyBoard(genderSpinner, this@UpdateActivity)
            false
        })

        // Set an on item selected listener for spinner object
        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {

                when (parent.getItemAtPosition(position).toString()) {
                    "Male" -> spinnerSelectedInt = 1
                    "Female" -> spinnerSelectedInt = 2
                    "Other" -> spinnerSelectedInt = 3
                    else -> {
                        spinnerSelectedInt = 0

                    }
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }

        }


    }

    private fun addItemsOnSpinner() {

        val list: MutableList<String> = ArrayList()
        list.add("Select Gender")
        list.add("Male")
        list.add("Female")
        list.add("Other")

        val genderAdapter = object : ArrayAdapter<Any> (
                this, R.layout.spinner,
                list as List<Any>
        ) {
            override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
            ): View {
                return super.getDropDownView(position, convertView, parent).also { view ->
                    if (position == genderSpinner.selectedItemPosition) {
                        // view.setBackgroundColor(resources.getColor(R.color.color_light))
                        view.findViewById<TextView>(android.R.id.text1)
                                .setTextColor(resources.getColor(R.color.black))
                        if (position != 0) {
                            view.findViewById<TextView>(android.R.id.text1)
                                    .setCompoundDrawablesWithIntrinsicBounds(
                                            0,
                                            0,
                                            R.drawable.ic_baseline_edit_24,
                                            0
                                    )

                        } else {
                            view.findViewById<TextView>(android.R.id.text1)
                                    .setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                        }
                    } else {

                        view.findViewById<TextView>(android.R.id.text1)
                                .setTextColor(resources.getColor(android.R.color.black))
                        view.findViewById<TextView>(android.R.id.text1)
                                .setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                }
            }
        }

        genderAdapter.setDropDownViewResource(R.layout.spinner)
        genderSpinner.adapter = genderAdapter

    }

    fun getIntentValues(intent: Intent) {

        nameString = "" + intent.getStringExtra("name")
        emailString = "" + intent.getStringExtra("email")
        mobileString = "" + intent.getStringExtra("mobile")
        spinnerInt = intent.getIntExtra("gender", spinnerInt)
        profileUrl = "" + intent.getStringExtra("profile_pic")

        textInputEditTextName.setText(nameString)
        textInputEditTextEmail.setText(emailString)
        textInputEditTextMobile.setText(mobileString)
        genderSpinner.setSelection(spinnerInt)
        Glide.with(this@UpdateActivity).load(profileUrl).placeholder(R.drawable.ic_baseline_done_24)
            .into(profileImage)

    }

    @OnClick(R.id.update_button)
    fun updatebuttonclick() {

        textInputLayoutName.isErrorEnabled = false
        textInputLayoutMobile.isErrorEnabled = false
        textInputLayoutEmail.isErrorEnabled = false

        if (textInputEditTextName.text.toString() == "" || textInputEditTextName.text.toString().length < 3 || textInputEditTextMobile.text.toString().length > 75) {
            textInputLayoutName.error = "Enter Valid Name"
            textInputLayoutName.requestFocus()
            return
        }
        if (textInputEditTextEmail.text.toString() == "" || !Utils.isValidEmail(
                        textInputEditTextEmail.text.toString()
                )
        ) {
            textInputLayoutEmail.error = "Enter Valid Email"
            textInputLayoutEmail.requestFocus()
            return
        }
        if (textInputEditTextMobile.text.toString().length < 10) {
            textInputLayoutMobile.error = "Password Must be 10 Characters"
            textInputLayoutMobile.requestFocus()
            return
        }
        if (spinnerSelectedInt == 0) {
            Toast.makeText(this@UpdateActivity, "Select Gender", Toast.LENGTH_SHORT).show()
            return
        }
        if (!boolImage) {
            Toast.makeText(
                    this@UpdateActivity,
                    "Image Size is Too Small",
                    Toast.LENGTH_SHORT
            ).show()
            return
        }
        updateprofile()

    }

    @OnClick(R.id.profile_image_layout)
    fun profilePicClick() {


        if (checkPermission()) {
            if (checkCameraPermission()) {
                easyImage = EasyImage.Builder(applicationContext)
                        .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                        .allowMultiple(false)
                        .build()
                easyImage!!.openChooser(this)

            } else {
                if (checkCameraPermission()) {

                    requestCameraPermission()
                } else {
                    showCameraPermissionDialog()
                }
            }


        } else {
            if (checkPermission()) {

                requestPermission()
            } else {
                showPermissionDialog()
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        easyImage!!.handleActivityResult(
                requestCode,
                resultCode,
                data,
                this,
                object : DefaultCallback() {
                    override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {

                        newProfileImage = imageFiles[0].file

                        val lengthInBytes = imageFiles[0].file.length()
                        val lengthInKiloBytes = lengthInBytes / 1024
                        val lengthInMegaBytes = lengthInKiloBytes / 1024

                        Log.e("FileLength", "" + lengthInMegaBytes)

                        newProfileImageCOnverted = convertFileSize(imageFiles[0].file)

                        var bitmap = BitmapFactory.decodeFile(imageFiles[0].file.absolutePath)

                        val manufacturer = Build.MANUFACTURER
                        val options = BitmapFactory.Options()
                        options.inJustDecodeBounds = true
                        BitmapFactory.decodeFile(imageFiles[0].file.absolutePath, options)
                        val imageHeight = options.outHeight
                        val imageWidth = options.outWidth

                        if (manufacturer == "samsung" && imageWidth != imageHeight) {
                            bitmap =
                                    rotateBitmap(bitmap, 270.toFloat())
                            Glide.with(this@UpdateActivity).load(bitmap)
                                    .placeholder(R.drawable.ic_baseline_get_app_24).error(R.drawable.ic_baseline_post_add_24)
                                    .into(profileImage)

                        } else {
                            //to do none
                            Glide.with(this@UpdateActivity).load(bitmap)
                                    .placeholder(R.drawable.ic_baseline_get_app_24).error(R.drawable.ic_baseline_post_add_24)
                                    .into(profileImage)

                        }


                    }

                })

    }

    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }



    private fun convertFileSize(file: File): File {
        val image = BitmapFactory.decodeFile(file.absolutePath)
        var fileOut: File? = null
        val maxSize = 500

        var width = image.width.toFloat()
        var height = image.height.toFloat()

        val bitmapRatio = width / height as Float
        if (bitmapRatio > 1) {
            width = maxSize.toFloat()
            height = (width / bitmapRatio)
        } else {
            height = maxSize.toFloat()
            width = (height * bitmapRatio)
        }

        val cBitmap = Bitmap.createScaledBitmap(image, width.toInt(), height.toInt(), true)

        try {
            val file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dems"
            val dir = File(file_path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            fileOut = File(dir, "profile_image" + ".png")
            val fOut = FileOutputStream(fileOut)
            cBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut.flush()
            fOut.close()

        } catch (e: Exception) {
            Log.e("Exceptione", "" + e.message)
        }
        return fileOut!!
    }




    fun showPermissionDialog() {

        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setIcon(R.drawable.ic_baseline_get_app_24).setTitle("Alert")
        builder.setMessage("We need to access your storage to use this feature.Do you want to allow permission now?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
            dialog.dismiss()
        })
        builder.setNegativeButton("Close", null)
        //builder.show();
        val dialog = builder.create()
        dialog.show() //Only after .show() was called
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.black))
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.black))

    }


    fun showCameraPermissionDialog() {

        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setIcon(R.drawable.ic_baseline_get_app_24).setTitle("Alert")
        builder.setMessage("We need to access your Camera to use this feature. Do you want to allow permission now?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
            dialog.dismiss()
        })
        builder.setNegativeButton("Close", null)
        //builder.show();
        val dialog = builder.create()
        dialog.show() //Only after .show() was called
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.black))
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.black))

    }


    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }


    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        return result == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                this@UpdateActivity,
                permission_storage,
                PERMISSION_REQUEST_CODE
        )
    }


    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
                this@UpdateActivity,
                permission_camera,
                CAMERA_PERMISSION_REQUEST_CODE
        )
    }





    fun updateprofile() {


       if (Utils.isInternetAvailable(this)) {

//           val progressDialog = ProgressDialog(this)
//           progressDialog.setMessage("Updating...")
//           progressDialog.setCancelable(true)
//           progressDialog.setCanceledOnTouchOutside(true)
//           progressDialog.show()

           var body: MultipartBody.Part? = null

           Log.e("response", "error0")

           if (newProfileImageCOnverted != null) {

               val reqFile: RequestBody =
                       RequestBody.create(MediaType.parse("image"), newProfileImageCOnverted)
               body = MultipartBody.Part.createFormData(
                       "profile_pic",
                       newProfileImageCOnverted!!.name,
                       reqFile
               )
           }

           val userNameBody: RequestBody =
               RequestBody.create(
                   MediaType.parse("text/plain"),
                   textInputEditTextName.text.toString()
               )

           val userEmailBody: RequestBody =
               RequestBody.create(
                   MediaType.parse("text/plain"),
                   textInputEditTextEmail.text.toString()
               )
           val userMobileBody: RequestBody =
               RequestBody.create(
                   MediaType.parse("text/plain"),
                   textInputEditTextMobile.text.toString()
               )

           val genderBody: RequestBody =
               RequestBody.create(MediaType.parse("text/plain"), spinnerSelectedInt.toString())

           Log.e("response", "error1")

           Log.e("response", "error2")

           val call: Call<profile> = RetrofitInstance.getClient.updateUserProfile(body,userNameBody,userEmailBody,userMobileBody,genderBody)

           Log.e("response", "error2")

           call.enqueue(object : Callback<profile> {
               override fun onResponse(call: Call<profile>, response: Response<profile>) {

                   if (response.isSuccessful) {

                       Log.e("response", "error3")


                       Toast.makeText(
                               this@UpdateActivity,
                               "Update Success",
                               Toast.LENGTH_SHORT
                       ).show()
                      // progressDialog.dismiss()
                       finish()


                   }else {

                       Toast.makeText(
                               this@UpdateActivity,
                               "Kindly check all the fields",
                               Toast.LENGTH_SHORT
                       ).show()
                   }

               }

                   override fun onFailure(call: Call<profile>, t: Throwable) {

                       Log.e("response", "error" + t.message)

                   }


               })


                   }
       else{

           Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show()
       }

   }

    override fun onBackPressed() {
        alertDialog()
    }

    fun alertDialog() {

        if (nameString != textInputEditTextName.text.toString() || emailString != textInputEditTextEmail.text.toString() || mobileString != textInputEditTextMobile.text.toString() || spinnerInt.toString() != spinnerSelectedInt.toString() || newProfileImageCOnverted != null) {

            val builder = AlertDialog.Builder(this@UpdateActivity)
            builder.setTitle("Alert")
            builder.setIcon(R.drawable.ic_baseline_warning_24)
            builder.setMessage("Do you want to save the changes before exiting?")
            builder.setPositiveButton("YES") { dialog, which ->
              updateprofile()
                finish()

            }
            builder.setNegativeButton("NO") { dialog, which ->
                finish()

            }
            builder.setNeutralButton("CANCEL") { dialog, which ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        } else {
            finish()
        }
    }

    fun Tool() {
        topAppBar.setOnMenuItemClickListener { menuItem ->

            when (menuItem.itemId) {

                R.id.save -> {
                    alertDialog()
                    true
                }
                R.id.cancel -> {
                    alertDialog()
                    // Handle search icon press
                    true
                }
                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    true
                }
                else -> false
            }
        }
    }



}