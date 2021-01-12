package com.example.retrofit.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Environment
import android.text.TextUtils
import android.text.format.DateUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.example.retrofit.PostFragment
import com.facebook.shimmer.ShimmerFrameLayout
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt
import java.text.SimpleDateFormat
import java.util.*


class Utils {

    companion object {

        fun isValidPassword(password: String): Boolean {
            val passwordPattern =
                "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"

            return !TextUtils.isEmpty(password) && password.matches(passwordPattern.toRegex())
        }


        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        // For Checking Internet Availability....

        fun isInternetAvailable(c: Context): Boolean {

            val connectivity = c
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (connectivity != null) {

                val info = connectivity.allNetworkInfo
                if (info != null)
                    for (i in info.indices)
                        if (info[i].state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
            }

            return false
        }

        fun backButtonOnToolbar(mActivity: AppCompatActivity) {
            if (mActivity.supportActionBar != null) {
                mActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                mActivity.supportActionBar!!.setDisplayShowHomeEnabled(true)
            }
        }

        fun hideKeyBoard(view: View, mActivity: AppCompatActivity) {
            val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun doubleDigitNumber(dbDate: Int): String {

            var initialNumber = ""

            if (dbDate < 10) {
                initialNumber = "0$dbDate"
            } else {
                initialNumber = "" + dbDate
            }


            return initialNumber
        }

        fun convertCustomFileSize(file: File, maxSize: Int, imageSavedFileName: String): File {
            val image = BitmapFactory.decodeFile(file.absolutePath)
            var fileOut: File? = null


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
                val file_path =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/dems"
                val dir = File(file_path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                fileOut = File(dir, "" + imageSavedFileName + "" + ".png")
                val fOut = FileOutputStream(fileOut)
                cBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
                fOut.flush()
                fOut.close()

            } catch (e: Exception) {
                Log.e("Exceptione", "" + e.message)
            }
            return fileOut!!
        }

        fun convertCustomImageSize(image: Bitmap, maxSize: Int, imageSavedFileName: String): File {

            var fileOut: File? = null


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
                val file_path =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/dems"
                val dir = File(file_path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                fileOut = File(dir, "" + imageSavedFileName + "" + ".png")
                val fOut = FileOutputStream(fileOut)
                cBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
                fOut.flush()
                fOut.close()

            } catch (e: Exception) {
                Log.e("Exceptione", "" + e.message)
            }
            return fileOut!!
        }

        fun getBitmapFromURL(urlname: String): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val url = URL(urlname)
                val connection = url.openConnection() as HttpURLConnection
                connection.setDoInput(true)
                connection.connect()
                val input = connection.getInputStream()
                bitmap = BitmapFactory.decodeStream(input)
                return bitmap
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("Exception",""+e.message)
                return null
            }
            return bitmap
        }

        fun screenSize(activity: Activity): Int {
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(dm)
            val x = Math.pow((dm.widthPixels / dm.xdpi).toDouble(), 2.0)
            val y = Math.pow((dm.heightPixels / dm.ydpi).toDouble(), 2.0)
            return Math.sqrt(x + y).roundToInt()
        }

//        fun logoutFromApp(context: Context?) {
//            prefHelper.setIsLogin("0")
//            prefHelper.setBearerToken("")
//            prefHelper.setUserPassword("")
//            prefHelper.setUserId("")
//            val intent = Intent(context, PostFragment::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            context!!.startActivity(intent)
//        }

        fun startShimmer(
            shimmerFrameLayout: ShimmerFrameLayout,
            nestedScrollView: NestedScrollView
        ) {
            nestedScrollView.visibility = View.GONE
            shimmerFrameLayout.visibility = View.VISIBLE
            shimmerFrameLayout.startShimmer()

        }

        fun stopShimmer(
            shimmerFrameLayout: ShimmerFrameLayout,
            nestedScrollView: NestedScrollView
        ) {
            nestedScrollView.visibility = View.VISIBLE
            shimmerFrameLayout.visibility = View.GONE
            shimmerFrameLayout.stopShimmer()

        }

        fun startShimmerRL(
            shimmerFrameLayout: ShimmerFrameLayout,
            relativeLayout: RelativeLayout
        ) {
            relativeLayout.visibility = View.GONE
            shimmerFrameLayout.visibility = View.VISIBLE
            shimmerFrameLayout.startShimmer()

        }

        fun stopShimmerRL(
            shimmerFrameLayout: ShimmerFrameLayout,
            nestedScrollView: RelativeLayout
        ) {
            nestedScrollView.visibility = View.VISIBLE
            shimmerFrameLayout.visibility = View.GONE
            shimmerFrameLayout.stopShimmer()

        }

        fun convertDate(inputString: String): String {

            val originalFormat = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH)
            val targetFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = originalFormat.parse(inputString)
            val formattedDate = targetFormat.format(date)


            return "" + formattedDate

        }


        fun convertDbtoNotmalDate(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd MMM yyyy")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }

        fun getDateFromSchedule(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }

        fun getMonthFromSchedule(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("MMM")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }


        fun getScheduleDateFromDB(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd-MM-yyyy")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }
        fun getFancyScheduleDateFromDB(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd MMM yyyy")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }
        fun getScheduleDateFromDBBreakdown(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd-MM-yyyy")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }
        fun getScheduleTimeFromDB(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("hh:mm a")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }


        fun timeourError(activity: Activity, context: Context) {

            val builder = AlertDialog.Builder(activity)

            builder.setTitle("Error")

            builder.setMessage("Server Error!! Please Try Again Later")

            builder.setPositiveButton("Okay") { dialog, which ->

                /*    activity.finishAffinity()
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)*/
                dialog.dismiss()
            }
            /*builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }*/

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

//            dialog.show()
//            dialog.setCancelable(false)
//            dialog.setCanceledOnTouchOutside(false)
//            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
//                .setTextColor(context.resources.getColor(R.color.colorPrimary))
//            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
//                .setTextColor(context.resources.getColor(R.color.colorPrimary))

        }


        fun meetingTime(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("hh:mm a")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }

        fun meetingDate(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd-MM-yyyy")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }


        fun meetingStartDateTime(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd MMM yyyy | hh:mm a")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }

        fun meetingFancyStartDateTime(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd | hh:mm a")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }

        fun meetingFancyStartDateOnly(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }

        fun meetingFancyStartDateTimeOnly(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("hh:mm a")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }


        fun meetingDateCheck(inputString: String): String {
            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("dd MMM yyyy")
                val date = originalFormat.parse(inputString)
                formattedDate = targetFormat.format(date)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }

        fun getTodayDate(): String {

            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("dd-MM-yyyy")
            val formattedDate = df.format(c)


            return "" + formattedDate

        }

        fun getCurrentTime(): String {

            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("hh:mm a")
            val formattedDate = df.format(c)


            return "" + formattedDate

        }

        fun getMinutesAgoFormat(inputDate: String): String {

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            sdf.timeZone = TimeZone.getTimeZone("GMT")

            val time = sdf.parse(inputDate).time

            val now = System.currentTimeMillis()

            val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)


            return "" + ago

        }

        fun getDayOfWeek(): String {

            val c = Calendar.getInstance()
            c.get(Calendar.DAY_OF_WEEK)
            var c1 = c.get(Calendar.DAY_OF_WEEK) - 1

            return "" + c1

        }

        fun getDayOfWeekPlusOne(): String {

            val c = Calendar.getInstance()
            c.get(Calendar.DAY_OF_WEEK)
            var c1 = c.get(Calendar.DAY_OF_WEEK)

            return "" + c1

        }

        fun getFancyTodayDate(): String {

            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("dd MMM yyyy")
            val formattedDate = df.format(c)


            return "" + formattedDate

        }

        fun getTomorowDate(): String {

            val c = Calendar.getInstance()

            c.add(Calendar.DATE, 1)

            val c2 = c.time
            val df = SimpleDateFormat("dd-MM-yyyy")

            val formattedDate = df.format(c2)


            return "" + formattedDate

        }

        fun getFancyTomorowDate(): String {

            val c = Calendar.getInstance()

            c.add(Calendar.DATE, 1)

            val c2 = c.time
            val df = SimpleDateFormat("dd MMM yyyy")

            val formattedDate = df.format(c2)


            return "" + formattedDate

        }

        fun currentToUTC(input: String): String {
            var formattedDate: String = ""

            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                targetFormat.timeZone = TimeZone.getTimeZone("UTC")

                val date = originalFormat.parse(input)

                formattedDate = targetFormat.format(date)
                Log.e("formattedDate", "" + formattedDate)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }

            return "" + formattedDate
        }

        fun utcToNormalDate(inputString: String): String {

            var formattedDate: String = ""
            try {
                val originalFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val targetFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val timeZone = Calendar.getInstance().timeZone.id

                val date = originalFormat.parse(inputString)

                val local = Date(date.time + TimeZone.getTimeZone(timeZone).getOffset(date.time))

                formattedDate = targetFormat.format(local)
            } catch (e: Exception) {
                Log.e("Exception", "" + e.message)
            }



            return "" + formattedDate

        }

        fun getTodayTime(): String {

            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("dd-MM-yyyy")
            val formattedDate = df.format(c)


            return "" + formattedDate

        }

        fun setWhiteText(activity: Activity, toolBar: Toolbar, context: Context) {
            toolBar.setTitleTextColor(activity.resources.getColor(android.R.color.white))

            val drawable = toolBar.navigationIcon

            if (drawable != null) {
                drawable.mutate()
                drawable.setColorFilter(
                    ContextCompat.getColor(context, android.R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }





    }

}