function popUp(url, title, w, h) {
    var screenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
    var screenTop = window.screenTop != undefined ? window.screenTop : screen.top;
    width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
    var left = ((width / 2) - (w / 2)) + screenLeft;
    var top = ((height / 2) - (h / 2)) + screenTop;
    window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
}