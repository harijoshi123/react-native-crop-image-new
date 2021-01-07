@objc(CropImage)
class CropImage: NSObject {

    @objc(multiply:withB:withResolver:withRejecter:)
    func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        resolve(a*b)
    }
    
    private let DURATION_SHORT_KEY = "SHORT"
    private let DURATION_LONG_KEY  = "LONG"
    
    private var emitter: RCTEventEmitter = RCTEventEmitter()

    @objc
    static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    @objc
    func callbackMethod(_ stringArgument:String, _ numberArgument:Int, _ callback: RCTResponseSenderBlock) {
        callback(["Callback Received numberArgumentss: \(numberArgument) stringArguments: \(stringArgument)"])
    }

  @objc
  func promiseMethod(_ stringArgument:String, _ numberArgument:Int, _ resolve:RCTPromiseResolveBlock, _ reject:RCTPromiseRejectBlock)
  {
      if (stringArgument == "" || numberArgument == 0) {
          let error = NSError(domain: "", code: 200, userInfo: nil)
        reject("Empty fields", "Fields cannot be empty", error)
      }else{
          resolve("Promise Received numberArguments: \(numberArgument) stringArguments: \(stringArgument)")
      }
  }

  @objc
    func threeDifferentTypesMethod(_ stringArgument:String, _ numberArgument:Int, _ status:Bool, _ resolve:RCTPromiseResolveBlock, _ reject:RCTPromiseRejectBlock)
  {
    if !status {
        let error = NSError(domain: "", code: 200, userInfo: nil)
        reject("Invalid", "Status is False", error)
    }else{
        var jsonObject = [String:Any]()
        jsonObject["string"] = stringArgument
        jsonObject["int"] = numberArgument
        jsonObject["status"] = status
        resolve("Promise Recived : \(jsonObject)")
        }
    }
    
    @objc
    func sendEvent(eventName:String, _ params:[String:Any]) {
        self.emitter.sendEvent(withName: eventName, body: params)
    }
}
