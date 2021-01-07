package com.reactnativecropimage

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.sun.istack.internal.Nullable

import jdk.nashorn.internal.runtime.regexp.joni.constants.Arguments

import java.util.HashMap

class CropImageModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private val DURATION_SHORT_KEY = "SHORT"
  private val DURATION_LONG_KEY = "LONG"

  private val reactContext: ReactApplicationContext? = null
  fun CropImageModule(reactContext: ReactApplicationContext?) {
    super(reactContext)
    this.reactContext = reactContext
  }

  fun getName(): String? {
    return "CropImage"
  }

  fun getConstants(): Map<String, Any>? {
    val constants: MutableMap<String, Any> = HashMap<String, Any>()
    constants[DURATION_SHORT_KEY] = "test"
    constants[DURATION_LONG_KEY] = "test"
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
    if (status == true) {
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
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }


}
