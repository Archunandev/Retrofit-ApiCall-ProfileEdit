package com.example.retrofit

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.retrofit.api.RetrofitInstance
import com.example.retrofit.model.profile
import com.example.retrofit.util.Utils
import com.example.retrofit.util.Utils.Companion.stopShimmer
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_get.*
import kotlinx.coroutines.NonCancellable.toString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.Arrays.toString
import java.util.Objects.toString


class ProfileFragment : Fragment() {

    @BindView(R.id.textInputEditTextName)
    lateinit var nameEditText: EditText

    @BindView(R.id.textInputEditTextEmail)
    lateinit var emailEditText: EditText

    @BindView(R.id.textInputEditTextMobile)
    lateinit var mobileEditText: EditText

    @BindView(R.id.textInputEditTextGender)
    lateinit var genderEditText: EditText

    @BindView(R.id.log_out)
    lateinit var logoutButton: Button

    @BindView(R.id.nested_scroll_view)
    lateinit var nestedScrollView: NestedScrollView

    @BindView(R.id.profile_image)
    lateinit var profileImage: CircleImageView

    @BindView(R.id.edit_profile)
    lateinit var editProfile: Button



    var profileUrl: String = ""

    lateinit var profileImageBitmap: Bitmap

    var dataList = ArrayList<profile>()

    var spinnerValue: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        ButterKnife.bind(this,view)



        return view
    }




    @OnClick(R.id.edit_profile)
    fun editProfile() {

        when (genderEditText.text.toString()) {
            "Male" -> spinnerValue = 1
            "Female" -> spinnerValue = 2
            "Other" -> spinnerValue = 3
            else -> {
                spinnerValue = 0
            }
        }


        val intent = Intent(activity, UpdateActivity::class.java)

        intent.putExtra("name", "" +nameEditText.text.toString())
        intent.putExtra("email", "" +emailEditText.text.toString())
        intent.putExtra("mobile", "" +mobileEditText.text.toString())
        intent.putExtra("gender", spinnerValue)
        intent.putExtra("profile_pic", profileUrl)
        activity?.startActivity(intent)

    }

    fun getProfileDetails() {



        if (Utils.isInternetAvailable(this.context!!)) {

            val progressDialog = ProgressDialog(this.context)
            progressDialog.setMessage("Loading...")
            progressDialog.setCancelable(true)
            progressDialog.setCanceledOnTouchOutside(true)
            progressDialog.show()


            val call: Call<profile> = RetrofitInstance.getClient.getUserProfile()

            call.enqueue(object : Callback<profile> {
                override fun onResponse(call: Call<profile>, response: Response<profile>) {
                    if (response.isSuccessful) {

                        progressDialog.dismiss()

                        Log.e("response", "" + response)
                        nameEditText.setText("" + response!!.body()!!.data.name)
                        emailEditText.setText("" + response!!.body()!!.data.email)
                        mobileEditText.setText("" + response!!.body()!!.data.phone)

                        if (response!!.body()!!.data.gender == "1") {
                            genderEditText.setText("Male")
                        } else if (response.body()!!.data.gender == "2") {
                            genderEditText.setText("Female")
                        } else {
                            genderEditText.setText("Other")
                        }
                        profileUrl = "" + response!!.body()!!.data.profile_pic
                        if (profileUrl != "null") {/*
                                Glide.with(this.context!!).load(result.data.profile_pic)
                                    .placeholder(R.drawable.ic_user).error(R.drawable.ic_user)
                                    .into(profileImage)
*/

                            Glide.with(this@ProfileFragment.context!!)
                                    .asBitmap()
                                    .load(profileUrl)
                                    .placeholder(R.drawable.ic_baseline_get_app_24)
                                    .error(R.drawable.ic_baseline_post_add_24)
                                    .into(object : CustomTarget<Bitmap>() {

                                        override fun onLoadCleared(placeholder: Drawable?) {
                                            // this is called when imageView is cleared on lifecycle call or for
                                            // some other reason.
                                            // if you are referencing the bitmap somewhere else too other than this imageView
                                            // clear it here as you can no longer have the bitmap
                                        }

                                        override fun onResourceReady(
                                                resource: Bitmap,
                                                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                                        ) {

                                            profileImageBitmap = resource
                                            profileImage.setImageBitmap(resource)
                                        }
                                    })


                        } else {

                        }

                        //  stopShimmer(shimmerFrameLayout, nestedScrollView)

                    }

                }

                override fun onFailure(call: Call<profile>, t: Throwable) {

                    Log.e("response", "error" + t.message)

                }


            })


        } else {
            Toast.makeText(context, "No Internet Available", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onResume() {
        super.onResume()
        getProfileDetails()

    }

    override fun onPause() {
        super.onPause()

    }
    }





