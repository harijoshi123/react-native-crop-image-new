#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(CropImage, NSObject);


- (NSDictionary *)constantsToExport {
    return @{ @"SHORT": @"Test",
              @"LONG": @"Test1"
    };
}

RCT_EXTERN_METHOD(multiply:(float)a withB:(float)b
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(callbackMethod:(NSString *)stringArgument :(nonnull NSNumber *)numberArgument :(RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(promiseMethod:(NSString *)stringArgument :(nonnull NSNumber *)numberArgument :(RCTPromiseResolveBlock)resolve :(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(threeDifferentTypesMethod:(NSString *)stringArgument :(nonnull NSNumber *)numberArgument :(nonnull BOOL *)status :(RCTPromiseResolveBlock)resolve :(RCTPromiseRejectBlock)reject)

// RCT_EXTERN_METHOD( sendEvent:(NSString *)eventName :(NSDictionary *)params )

RCT_EXTERN_METHOD(presentCropView)

@end

// @interface RCT_EXTERN_MODULE(CropImageViewManager, NSObject);

// RCT_EXTERN_METHOD(getImageEvent:(NSString *)eventName :(NSData *)params)
// RCT_EXTERN_METHOD(presentCropView)
// @end


