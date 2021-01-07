package com.reactnativecropimage

import android.R.attr.data
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import androidx.annotation.Nullable
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.json.JSONObject
import java.util.*


class CropImageModule : ReactContextBaseJavaModule {

  private var reactContext: ReactApplicationContext

  companion object {
    private const val TAG = "CropImageModule"
    private const val DURATION_SHORT_KEY = "SHORT"
    private const val DURATION_LONG_KEY = "LONG"
  }

  constructor(reactContext: ReactApplicationContext) : super(reactContext) {

    // Add the listener for `onActivityResult`
    reactContext.addActivityEventListener(mActivityEventListener);
    this.reactContext = reactContext
  }

  override fun getName(): String {
    return "CropImage"
  }

  override fun getConstants(): Map<String, Any> {
    val constants: MutableMap<String, Any> = HashMap<String, Any>()
    constants[Companion.DURATION_SHORT_KEY] = "test"
    constants[Companion.DURATION_LONG_KEY] = "test"
    return constants
  }

  @ReactMethod
  fun callbackMethod(stringArgument: String, numberArgument: Int, callback: Callback) {
    val evt: WritableMap = Arguments.createMap()
    evt.putString("event", "callbackMethod")
    sendEvent(reactContext, "EventReminder", evt)
    callback.invoke(
      "Callback Received numberArgumentss: " + numberArgument +
        " stringArgumentss: " + stringArgument
    )
  }

  @ReactMethod
  fun promiseMethod(stringArgument: String, numberArgument: Int, promise: Promise) {
    val evt: WritableMap = Arguments.createMap()
    evt.putString("event", "promiseMethod")
    sendEvent(reactContext, "EventReminder", evt)
    try {
      promise.resolve("Promise Received numberArgumentss: $numberArgument stringArgumentss: $stringArgument")
    } catch (e: Exception) {
      promise.reject("Create Event Error", e)
    }
  }

  @ReactMethod
  fun threeDifferentTypesMethod(stringArgument: String?, numberArgument: Int, status: Boolean, promise: Promise) {
    val evt: WritableMap = Arguments.createMap()
    evt.putString("event", "threeDifferentTypesMethod")
    sendEvent(reactContext, "EventReminder", evt)
    if (status) {
      try {
        val jsonObj = JSONObject()
        jsonObj.put("string", stringArgument)
        jsonObj.put("int", numberArgument)
        jsonObj.put("status", status)
        promise.resolve(jsonObj.toString())
      } catch (e: Exception) {
        promise.reject("Error", e)
      }
    } else {
      promise.reject("Status: false")
    }
  }

  private fun sendEvent(reactContext: ReactApplicationContext?, eventName: String, @Nullable params: WritableMap) {
    reactContext?.apply {
      getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        .emit(eventName, params)
    } ?: let {
      Log.e(TAG, "Unable to send event : $eventName , $params")
    }
  }

  /*
    * Crop Image Code
    * */
  private val IMAGE_PICKER_REQUEST = 1
  private val E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST"
  private val E_PICKER_CANCELLED = "E_PICKER_CANCELLED"
  private val E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER"
  private val E_NO_IMAGE_DATA_FOUND = "E_NO_IMAGE_DATA_FOUND"

  private var mPickerPromise: Promise? = null

  private val mActivityEventListener: ActivityEventListener = object : BaseActivityEventListener() {

    override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, intent: Intent?) {
//      super.onActivityResult(activity, requestCode, resultCode, intent)

//      if (requestCode == IMAGE_PICKER_REQUEST) {
//        if (mPickerPromise != null) {
//          if (resultCode == Activity.RESULT_CANCELED) {
//            mPickerPromise!!.reject(E_PICKER_CANCELLED, "Image picker was cancelled")
//          } else if (resultCode == Activity.RESULT_OK) {
//            val uri = intent!!.data
//            if (uri == null) {
//              mPickerPromise!!.reject(E_NO_IMAGE_DATA_FOUND, "No image data found")
//            } else {
//              mPickerPromise!!.resolve(uri.toString())
//            }
//          }
//          mPickerPromise = null
//        }
//      }
      if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
        val result = CropImage.getActivityResult(intent)
        if (resultCode == RESULT_OK) {
          val resultUri = result.uri
          if (resultUri == null) {
            mPickerPromise!!.reject(E_NO_IMAGE_DATA_FOUND, "No image data found")
          } else {
            mPickerPromise!!.resolve(resultUri.toString())
          }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
          val error = result.error
          mPickerPromise!!.reject(E_PICKER_CANCELLED, "Image picker was cancelled")
        }
      }
    }
  }

  @ReactMethod
  fun pickImage(promise: Promise) {
    val currentActivity = currentActivity
    if (currentActivity == null) {
      promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist")
      return
    }

    // Store the promise to resolve/reject when picker returns data
    mPickerPromise = promise
//    try {
//      val galleryIntent = Intent(Intent.ACTION_PICK)
//      galleryIntent.type = "image/*"
//      val chooserIntent = Intent.createChooser(galleryIntent, "Pick an image")
//      currentActivity.startActivityForResult(chooserIntent, IMAGE_PICKER_REQUEST)
//    } catch (e: Exception) {
//      mPickerPromise!!.reject(E_FAILED_TO_SHOW_PICKER, e)
//      mPickerPromise = null
//    }
    try {
      CropImage.activity()
        .setGuidelines(CropImageView.Guidelines.ON)
        .start(currentActivity)
    } catch (e: Exception) {
      mPickerPromise!!.reject(E_FAILED_TO_SHOW_PICKER, e)
      mPickerPromise = null
    }
  }

}
