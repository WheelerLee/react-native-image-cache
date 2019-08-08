//
//  RNImageView.m
//  HouseFlower
//
//  Created by liwei on 2018/4/11.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "RNImageView.h"
#import <React/RCTUtils.h>
#import <React/UIView+React.h>

@interface RNImageView()

@end

@implementation RNImageView

- (void) loadImage {
  
  if (_source) {
    if (_source[@"uri"]) {
      
      SDWebImageOptions options = 0;
      options |= SDWebImageRetryFailed;
      
      if (_source[@"priority"]) {
        if ([_source[@"priority"] isEqualToString:@"high"]) {
          options |= SDWebImageHighPriority;
        } else if ([_source[@"priority"] isEqualToString:@"low"]) {
          options |= SDWebImageLowPriority;
        }
      }
      
      if (self->_onLoadStart) {
        self->_onLoadStart(@{});
      }
      [self sd_setImageWithURL:[NSURL URLWithString:_source[@"uri"]] placeholderImage:[UIImage imageNamed:@"bg_default"] options:options progress:^(NSInteger receivedSize, NSInteger expectedSize, NSURL * _Nullable targetURL) {
        if (self->_onProgress) {
          self->_onProgress(@{
                              @"progress": @((int) 100 * receivedSize / expectedSize),
                              @"receivedSize": @(receivedSize),
                              @"expectedSize": @(expectedSize)
                              });
        }
      } completed:^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL) {
        if (error) {
          if (self -> _onError) {
            self -> _onError(@{
                               @"code": [NSNumber numberWithInteger:error.code],
                               @"message": error.description
                               });
          }
        } else {
          if (self -> onLoadComplete) {
            self -> onLoadComplete(@{});
          }
        }
      }];
    }
  }
  
}

- (void)setSource:(NSDictionary *)source {
  _source = source;
//  [self loadImage];
}

- (void)setResizeMode:(RCTResizeMode)resizeMode {
  if (_resizeMode != resizeMode) {
    _resizeMode = resizeMode;
    
    if (_resizeMode == RCTResizeModeRepeat) {
      // Repeat resize mode is handled by the UIImage. Use scale to fill
      // so the repeated image fills the UIImageView.
      self.contentMode = UIViewContentModeScaleToFill;
    } else {
      self.contentMode = (UIViewContentMode)resizeMode;
    }
  }
}
//
//- (void)setDefaultImage:(UIImage *)defaultImage {
//  _defaultImage = defaultImage;
//
//}
- (void)didSetProps:(NSArray<NSString *> *)changedProps {
  NSLog(@"%@", changedProps);
  [self loadImage];
}



@end
