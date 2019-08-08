"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
/**
 * Created by WheelerLee on 2019-08-06 14:20.
 * Contacts
 * Copyright 2019 https://github.com/WheelerLee
 */
var react_1 = __importDefault(require("react"));
var react_2 = require("react");
var react_native_1 = require("react-native");
var RNImageView = react_native_1.requireNativeComponent("RNImageView");
/**
 * 网络图片加载组件，自动包含缓存
 */
var ImageView = /** @class */ (function (_super) {
    __extends(ImageView, _super);
    function ImageView(props) {
        return _super.call(this, props) || this;
    }
    ImageView.prototype.render = function () {
        var _this = this;
        return (<react_native_1.View style={[styles.container, this.props.style]}>
        <RNImageView {...this.props} onProgress={function (event) { return _this.props.onProgress && _this.props.onProgress(event['nativeEvent']); }} onError={function (event) { return _this.props.onError && _this.props.onError(event['nativeEvent']); }} style={{ flex: 1 }}/>
      </react_native_1.View>);
    };
    return ImageView;
}(react_2.Component));
exports.default = ImageView;
var styles = react_native_1.StyleSheet.create({
    container: {
        overflow: 'hidden',
        alignItems: 'stretch'
    }
});
//# sourceMappingURL=ImageView.js.map