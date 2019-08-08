import { Component } from 'react';
import { ViewStyle } from 'react-native';
interface ProgressEvent {
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
interface ErrorEvent {
    code?: string;
    message?: string;
}
interface Props {
    /**
     * 样式
     */
    style?: ViewStyle;
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
export default class ImageView extends Component<Props> {
    constructor(props: Props);
    render(): JSX.Element;
}
export {};
//# sourceMappingURL=ImageView.d.ts.map