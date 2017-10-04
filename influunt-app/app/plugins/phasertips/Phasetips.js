"use strict";

var _typeof2 = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

var _typeof = typeof Symbol === "function" && _typeof2(Symbol.iterator) === "symbol" ? function (obj) {
    return typeof obj === "undefined" ? "undefined" : _typeof2(obj);
} : function (obj) {
    return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof2(obj);
};

/**
 * PHASETIPS is a tooltip plugin for Phaser.io HTML5 game framework
 *
 * COPYRIGHT-2015
 * AUTHOR: MICHAEL DOBEKIDIS (NETGFX.COM)
 *
 **/

var Phasetips = function Phasetips(localGame, options) {

    var _this = this;
    var _options = options || {};
    var game = localGame || game; // it looks for a game object or falls back to the global one

    this.printOptions = function () {
        window.console.log(_options);
    };

    this.onHoverOver = function () {
        if (_this.tweenObj) {
            _this.tweenObj.stop();
        }
        if (_options.animation === "fade") {
            _this.tweenObj = game.add.tween(_this.mainGroup).to({
                alpha: 1
            }, _options.animationSpeedShow, Phaser.Easing.Linear.None, true, _options.animationDelay, 0, false);
        } else if (_options.animation === "slide") {} else if (_options.animation === "grow") {

            _this.mainGroup.pivot.setTo(_this.mainGroup.width / 2, _this.mainGroup.height);
            _this.mainGroup.pivot.setTo(_this.mainGroup.width / 2, _this.mainGroup.height);
            _this.mainGroup.x = _this.mainGroup.initialX + _this.mainGroup.width / 2;
            _this.mainGroup.y = _this.mainGroup.initialY + _this.mainGroup.height;
            _this.mainGroup.scale.setTo(0, 0);
            _this.mainGroup.alpha = 1;
            _this.tweenObj = game.add.tween(_this.mainGroup.scale).to({
                x: 1,
                y: 1
            }, _options.animationSpeedShow, Phaser.Easing.Linear.None, true, _options.animationDelay, 0, false);
        } else {
            _this.mainGroup.visible = true;
            _this.mainGroup.alpha = 1;
        }

        if (_options.onHoverCallback) {
            _options.onHoverCallback(_this);
        }
    };

    this.onHoverOut = function () {
        if (_this.tweenObj) {
            _this.tweenObj.stop();
        }

        if (_options.animation === "fade") {
            _this.tweenObj = game.add.tween(_this.mainGroup).to({
                alpha: 0
            }, _options.animationSpeedHide, Phaser.Easing.Linear.None, true, 0, 0, false);
        } else {
            _this.mainGroup.alpha = 0;
        }

        if (_options.onOutCallback) {
            _options.onOutCallback(_this);
        }
    };

    this.createTooltips = function () {

        // layout
        var _width = _options.width || "auto";
        var _height = _options.height || "auto";
        var _x = _options.x || "auto";
        var _y = _options.y || "auto";
        var _padding = _options.padding === undefined ? 20 : _options.padding;
        var _positionOffset = _options.positionOffset === undefined ? 20 : _options.positionOffset;
        var _bgColor = _options.backgroundColor || 0x000000;
        var _strokeColor = _options.strokeColor || 0xffffff;
        var _strokeWeight = _options.strokeWeight || 2;
        var _customArrow = _options.customArrow || false;
        var _enableCursor = _options.enableCursor || false;
        var _customBackground = _options.customBackground || false;
        var _fixedToCamera = _options.fixedToCamera || false;
        var _textStyle = _options.textStyle || {
            fontSize: 12,
            fill: "#ffffff",
            stroke: "#1e1e1e",
            strokeThickness: 1,
            wordWrap: true,
            wordWrapWidth: 200
        };

        //
        var _position = _options.position || "top"; // top, bottom, left, right, auto(?)
        var _animation = _options.animation || "fade"; // fade, slide, grow, none to manually show it
        var _animationDelay = _options.animationDelay || 0;
        var _content = _options.context || "Hello World"; // string, bitmapText, text, sprite, image, group
        var _object = _options.targetObject || game; // any object
        var _animationSpeedShow = _options.animationSpeedShow || 300;
        var _animationSpeedHide = _options.animationSpeedHide || 200;
        var _onHoverCallback = _options.onHoverCallback || function () {};
        var _onOutCallback = _options.onOutCallback || function () {};

        _options.animation = _animation;
        _options.animationDelay = _animationDelay;
        _options.animationSpeedShow = _animationSpeedShow;
        _options.animationSpeedHide = _animationSpeedHide;
        _options.onHoverCallback = _onHoverCallback;
        _options.onOutCallback = _onOutCallback;

        ////////////////////////////////////////////////////////////////////////////////////
        var tooltipBG;
        var tooltipContent;
        var tooltipArrow;

        _this.mainGroup = game.add.group();
        var mainGroup = _this.mainGroup;

        // add content first to calculate width & height in case of auto
        var type = typeof _content === "undefined" ? "undefined" : _typeof(_content);

        if (type === "string") {
            tooltipContent = new Phaser.Text(game, _padding / 2, _padding / 2, _content, _textStyle);
            tooltipContent.lineSpacing = _textStyle.lineSpacing || 0;
            tooltipContent.updateText();
            tooltipContent.update();
            tooltipContent.x = _padding / 2;
            tooltipContent.y = _padding / 2;
            var bounds = tooltipContent.getBounds();
            /* window.console.log(bounds);
             var debug = game.add.graphics(bounds.width, bounds.height);
             debug.x = _padding/2;
             debug.y = _padding/2;
             debug.beginFill(0xff0000, 0.6);
             debug.drawRect(0, 0, bounds.width, bounds.height, 1);
             window.console.log(debug.x)*/
        } else if (type === "object") {
            tooltipContent = _content;
        }

        if (_width !== "auto" && _height !== "auto") {
            mainGroup.width = _width;
            mainGroup.height = _height;
        } else {
            if (_customBackground === false) {
                mainGroup.width = tooltipContent.width + _padding;
                mainGroup.height = tooltipContent.height + _padding;
            } else {

                if (_customBackground.width > tooltipContent.width) {
                    mainGroup.width = _customBackground.width;
                    mainGroup.height = _customBackground.height;
                } else {
                    mainGroup.width = tooltipContent.width;
                    mainGroup.height = tooltipContent.height;
                }
            }
        }

        // Making it invisible
        mainGroup.alpha = 0;
        //////////////////////
        function updatePosition() {
            var _origPosition = _position;
            if (_x !== "auto" && _y !== "auto") {
                mainGroup.x = _x;
                mainGroup.y = _y;
                if (_fixedToCamera == true) {
                    mainGroup.fixedToCamera = true;
                    mainGroup.cameraOffset.setTo(mainGroup.x, mainGroup.y);
                }
            } else {
                var worldPos = _options.targetObject ? _options.targetObject.world : game.world;
                var objectX = worldPos.x || _options.targetObject.x;
                var objectY = worldPos.y || _options.targetObject.y;

                // sanity check
                if (_position === "bottom") {
                    if (Math.round(objectY + _object.height + _positionOffset) + mainGroup._height > game.height) {
                        _position = "top";
                    }
                } else if (_position === "top") {
                    if (Math.round(objectY - (_positionOffset + mainGroup._height)) < 0) {
                        _position = "bottom";
                    }
                }

                if (_position === "top") {
                    mainGroup.x = Math.round(objectX + (_object.width / 2 - mainGroup._width / 2));
                    mainGroup.y = Math.round(objectY - (_positionOffset + mainGroup._height));
                } else if (_position === "bottom") {
                    mainGroup.x = Math.round(objectX + (_object.width / 2 - mainGroup._width / 2));
                    mainGroup.y = Math.round(objectY + _object.height + _positionOffset);
                } else if (_position === "left") {
                    mainGroup.x = Math.round(objectX - (_positionOffset + mainGroup._width));
                    mainGroup.y = Math.round(objectY + _object.height / 2 - mainGroup._height / 2);
                    // mainGroup.scale.x = -1;
                } else if (_position === "right") {
                    mainGroup.x = Math.round(objectX + _object.width + _positionOffset);
                    mainGroup.y = Math.round(objectY + _object.height / 2 - mainGroup._height / 2);
                }

                if (_fixedToCamera == true) {
                    mainGroup.fixedToCamera = true;
                    mainGroup.cameraOffset.setTo(mainGroup.x, mainGroup.y);
                }
            }

            // clone world position
            mainGroup.initialWorldX = worldPos.x;
            mainGroup.initialWorldY = worldPos.y;

            mainGroup.initialX = mainGroup.x;
            mainGroup.initialY = mainGroup.y;

            // if the world position changes, there might be space for the tooltip
            // to be in the original position.
            _position = _origPosition;
        }

        updatePosition();

        ///////////////////////////////////////////////////////////////////////////////////


        if (_customBackground === false) {
            /// create bg
            tooltipBG = game.add.graphics(tooltipContent.width, tooltipContent.height);
            tooltipBG.beginFill(_bgColor, 1);
            tooltipBG.x = 0;
            tooltipBG.y = 0;
            tooltipBG.lineStyle(_strokeWeight, _strokeColor, 1);
            tooltipBG.drawRect(0, 0, tooltipContent.width + _padding, tooltipContent.height + _padding, 1);
        } else {
            tooltipBG = _customBackground;
        }

        // add all to group
        mainGroup.add(tooltipBG);
        mainGroup.add(tooltipContent);
        //if(debug)
        //mainGroup.add(debug);

        // add event listener
        _object.inputEnabled = true;
        if (_enableCursor) {
            _object.input.useHandCursor = true;
        }
        _object.events.onInputOver.add(_this.onHoverOver, this);
        _object.events.onInputDown.add(_this.onHoverOver, this);
        _object.events.onInputOut.add(_this.onHoverOut, this);
        _object.events.onInputUp.add(_this.onHoverOut, this);

        mainGroup.update = function () {
            var worldPos = _options.targetObject ? _options.targetObject.world : game.world;
            if (worldPos.x !== mainGroup.initialWorldX) {
                updatePosition();
            }
        };
    };

    this.createTooltips();

    return {
        printOptions: function printOptions() {
            _this.printOptions();
        },
        updatePosition: function updatePosition(x, y) {
            _this.mainGroup.x = x;
            _this.mainGroup.y = y;
        },
        destroy: function destroy() {
            _this.mainGroup.removeChildren();
            _this.mainGroup.destroy();
        },
        hideTooltip: function hideTooltip() {
            _this.mainGroup.visible = false;
            _this.mainGroup.alpha = 0;
        },
        showTooltip: function showTooltip() {
            _this.mainGroup.visible = true;
            _this.mainGroup.alpha = 1;
        }
    };
};

if ((typeof module === "undefined" ? "undefined" : _typeof(module)) === "object" && _typeof(module.exports) === "object") {
    module.exports = Phasetips;
}