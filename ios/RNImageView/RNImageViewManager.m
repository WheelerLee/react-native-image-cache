//
//  RNImageViewManager.m
//  HouseFlower
//
//  Created by liwei on 2018/4/11.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "RNImageViewManager.h"
#import "RNImageView.h"

@implementation RNImageViewManager

RCT_EXPORT_MODULE()

- (RNImageView *)view {
  RNImageView *view = [[RNImageView alloc] init];
//  view.contentMode = UIViewContentModeScaleAspectFill;
  view.resizeMode = RCTResizeModeCover;
  view.clipsToBounds =  YES;
  return view;
}

RCT_EXPORT_VIEW_PROPERTY(source, NSDictionary)
RCT_EXPORT_VIEW_PROPERTY(resizeMode, RCTResizeMode)
RCT_EXPORT_VIEW_PROPERTY(onLoadStart, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onProgress, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onError, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onLoadComplete, RCTDirectEventBlock)
//RCT_REMAP_VIEW_PROPERTY(defaultSource, defaultImage, UIImage)

@end
