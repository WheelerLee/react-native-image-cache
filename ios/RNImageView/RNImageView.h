//
//  RNImageView.h
//  HouseFlower
//
//  Created by liwei on 2018/4/11.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <React/RCTImageSource.h>
#import <React/RCTResizeMode.h>
#import "FLAnimatedImageView+WebCache.h"
#import <React/RCTUtils.h>
#import <React/UIView+React.h>

@interface RNImageView : FLAnimatedImageView

@property (nonatomic, strong) NSDictionary *source;
//@property (nonatomic, strong) UIImage *defaultImage;
@property (nonatomic, strong) UIImage *defaultSource;
@property (nonatomic, assign) RCTResizeMode resizeMode;

@property (nonatomic, copy) RCTDirectEventBlock onLoadStart;
@property (nonatomic, copy) RCTDirectEventBlock onProgress;
@property (nonatomic, copy) RCTDirectEventBlock onError;
@property (nonatomic, copy) RCTDirectEventBlock onLoadComplete;

@end
