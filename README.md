# dff.CordovaPlugin.Alert

## Installation

```bash
$ cordova plugin add https://github.com/dff-solutions/dff.CordovaPlugin.Alert.git
```

## Usage

Plugin is available via global `Alert`.

### Constants

```js
TYPE_APPLICATION = 2; // (0x00000002)
TYPE_APPLICATION_ATTACHED_DIALOG = 1003; // (0x000003eb)
TYPE_APPLICATION_MEDIA = 1001; // (0x000003e9)
TYPE_APPLICATION_PANEL = 1000; // (0x000003e8)
TYPE_APPLICATION_STARTING = 3; // (0x00000003)
TYPE_APPLICATION_SUB_PANEL = 1002; // (0x000003ea)
TYPE_BASE_APPLICATION = 1; // (0x00000001)
TYPE_CHANGED = 2011; // (0x000007db)
TYPE_INPUT_METHOD_DIALOG = 2012; // (0x000007dc)
TYPE_KEYGUARD_DIALOG = 2009; // (0x000007d9)
TYPE_PHONE = 2002; // (0x000007d2)
TYPE_PRIORITY_PHONE = 2007; // (0x000007d7)
TYPE_PRIVATE_PRESENTATION = 2030; // (0x000007ee)
TYPE_SEARCH_BAR = 2001; // (0x000007d1)
TYPE_STATUS_BAR = 2000; // (0x000007d0)
TYPE_STATUS_BAR_PANEL = 2014; // (0x000007de)
TYPE_SYSTEM_ALERT = 2003; // (0x000007d3)
TYPE_SYSTEM_DIALOG = 2008; // (0x000007d8)
TYPE_SYSTEM_ERROR = 2010; // (0x000007da)
TYPE_SYSTEM_OVERLAY = 2006; // (0x000007d6)
TYPE_TOAST = 2005; // (0x000007d5)
TYPE_WALLPAPER = 2013; // (0x000007dd)

BUTTON_NEGATIVE = -2; // (0xfffffffe)
BUTTON_NEUTRAL = -3; // (0xfffffffd)
BUTTON_POSITIVE = -1; // (0xffffffff)
```

### Actions

```js
/**
 * @name alert
 * @param {function} success Callback for success
 * @param {function} error Callback for error
 * @param {Object} args Named arguments
 * @param {String} args.title Title of alert.
 * @param {String} args.type Type of alert. One of TYPE constants
 * @param {String} args.message Message of alert.
 * @param {String} args.cancelable If alert can be canceled
 * @param {String} args.positiveButton Text for positive button
 * @param {String} args.negativeButton Text for negative button
 * @param {String} args.neutralButton Text for neutral button.
 */
Alert
 .alert(function (button) {
     console.log(button); // compare with button constants
 }, function (reason) {
     console.error(reason);
 }, {
     title: 'Alert Title',
     message: 'Alert message',
     type: Alert.TYPE_SYSTEM_ALERT,
     cancelable: false
 });
```