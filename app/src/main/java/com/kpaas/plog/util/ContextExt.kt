package com.kpaas.plog.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.kpaas.plog.R

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun showCustomToast(context: Context, message: String) {
    val inflater = LayoutInflater.from(context)
    val layout: View = inflater.inflate(R.layout.custom_toast, null)

    val text: TextView = layout.findViewById(R.id.toast_message)
    text.text = message

    val image: ImageView = layout.findViewById(R.id.toast_icon)
    image.setImageResource(R.drawable.ic_snackbar_star) // 아이콘을 원하는 대로 변경

    with(Toast(context)) {
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}


fun Context.stringOf(
    @StringRes resId: Int,
) = getString(resId)

fun Context.colorOf(
    @ColorRes resId: Int,
) = ContextCompat.getColor(this, resId)

fun Context.drawableOf(
    @DrawableRes resId: Int,
) = ContextCompat.getDrawable(this, resId)

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.openKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, 0)
}

fun Context.dialogFragmentResize(
    dialogFragment: DialogFragment,
    horizontalMargin: Float,
) {
    val dpToPixel = Resources.getSystem().displayMetrics.density
    val dialogHorizontalMarginInPixels = (dpToPixel * horizontalMargin + 0.5f).toInt() // 반올림 처리
    val deviceWidth = Resources.getSystem().displayMetrics.widthPixels
    dialogFragment.dialog?.window?.setLayout(
        deviceWidth - 2 * dialogHorizontalMarginInPixels,
        WindowManager.LayoutParams.WRAP_CONTENT,
    )
    dialogFragment.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun Context.pxToDp(px: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        px.toFloat(),
        resources.displayMetrics,
    ).toInt()
}

fun Context.statusBarColorOf(
    @ColorRes resId: Int,
) {
    if (this is Activity) {
        window?.statusBarColor = colorOf(resId)
    }
}

fun Context.showPermissionAppSettingsDialog() {
    AlertDialog.Builder(this)
        .setTitle("권한이 필요해요")
        .setMessage("이 앱은 파일 및 미디어 접근 권한이 필요해요.\n앱 세팅으로 이동해서 권한을 부여 할 수 있어요.")
        .setPositiveButton("이동하기") { dialog, _ ->
            navigateToAppSettings()
            dialog.dismiss()
        }
        .setNegativeButton("취소하기") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun Context.navigateToAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}