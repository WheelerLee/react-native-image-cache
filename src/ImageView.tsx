/**
 * Created by WheelerLee on 2019-08-06 14:20.
 * Contacts
 * Copyright 2019 https://github.com/WheelerLee
 */
import React from 'react';
import {Component} from 'react';
import {View, requireNativeComponent, StyleSheet, ViewStyle, StyleProp} from 'react-native';

const RNImageView = requireNativeComponent("RNImageView");

export interface ProgressEvent {
  /**
   * 已经接收的大小
   */
  receivedSize: number;
  /**
   * 文件总大小
   */
  expectedSize: number;
  /**
   * 进度百分比
   */
  progress: number;
}

export interface ErrorEvent {
  code?: string,
  message?: string
}

export interface ImageViewProps {
  /**
   * 样式
   */
  style?: StyleProp<ViewStyle>;
  /**
   * 图片源
   */
  source?: {
    /**
     * 图片地址
     */
    uri: string;
    /**
     * 图片显示质量
     */
    priority?: 'normal' | 'high' | 'low';
    /**
     * 默认显示的图片，加载前，加载失败等都会显示。和native设置的key对应
     */
    defaultSource?: string;
  };
  /**
   * 图片裁剪模式
   */
  resizeMode?: 'cover' | 'contain' | 'stretch';
  onLoadStart?: () => void;
  onProgress?: (event: ProgressEvent) => void;
  onError?: (event: ErrorEvent) => void;
  onLoadComplete?: () => void;
}

/**
 * 网络图片加载组件，自动包含缓存
 */
export default class ImageView extends Component<ImageViewProps> {

  constructor(props: ImageViewProps) {
    super(props);
  }

  render() {

    return (
      <View style={[styles.container, this.props.style]}>
        <RNImageView 
          {...this.props} 
          onProgress={(event: any) => this.props.onProgress && this.props.onProgress(event['nativeEvent'])} 
          onError={(event: any) => this.props.onError && this.props.onError(event['nativeEvent'])} 
          style={{flex: 1}}
        />
      </View>
    );

  }

}

interface Styles {
  container: ViewStyle
}

const styles = StyleSheet.create<Styles>({
  container: {
    overflow: 'hidden',
    alignItems: 'stretch'
  }
});