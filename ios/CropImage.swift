import UIKit
import Foundation

@objc(CropImage)
class CropImage: NSObject,UINavigationControllerDelegate,UIImagePickerControllerDelegate {

    @objc(multiply:withB:withResolver:withRejecter:)
    func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        resolve(a*b)
    }
    
    
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

    let queue = DispatchQueue(label: "com.example.my-serial-queue")

    @objc 
    func presentCropView() {
        queue.async {
            let window = UIWindow(frame: UIScreen.main.bounds)
            let viewController = UIViewController()
            viewController.view.backgroundColor = UIColor.green
            window.rootViewController = viewController
            
            let imagePicker = UIImagePickerController()
            
            imagePicker.delegate = self
            if UIImagePickerController.isSourceTypeAvailable(.photoLibrary) {
                imagePicker.sourceType = .photoLibrary
                imagePicker.allowsEditing = true
                viewController.present(imagePicker, animated: true, completion: nil)
            }
    }
}
    
    
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
        var image : UIImage!
        if let img = info[UIImagePickerController.InfoKey.originalImage] as? UIImage
        {
            image = img
            picker.dismiss(animated: true,completion: nil)
            if let data = image.pngData() {
                // emitter.sendEvent(withName:"EventReminder", body:["status": "Success", "data": data.base64EncodedData()])
            }
        }
    }
}

