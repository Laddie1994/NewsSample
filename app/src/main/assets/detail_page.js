var title;
var author;
var time;
var body;
var images;
var imageCount = 0;

function load_day() {
    document.bgColor="#FF0000";
    var font = document.getElementById("font");
    if (!font) {
        return;
    }
    font.style.color="white";
}

function load_night() {
    document.bgColor="#1f1f1f";
    var font = document.getElementById("font");
    if (!font) {
        return;
    }
    font.style.color="black";
}

function fill(detailBody) {
     var myBody = document.getElementByClassName('zw-word');
     myBody.innerHTML = detailBody
}

function changeFontSize(body) {
    var myBody = document.getElementByClassName('zw-word');
    myBody.style.fontSize="22px"
    myBody.innerHTML = body
}

function showSuperBigSize() {
	var myBody = document.getElementByClassName('duanluo');
    myBody.style.fontSize="26px";
}

function showBigSize() {
	var myBody = document.getElementByClassName('duanluo');
    myBody.style.fontSize="22px";
}

function showMidSize() {
	var myBody = document.getElementByClassName('duanluo');
    myBody.style.fontSize="18px";
}

function showSmallSize() {
	var myBody = document.getElementByClassName('duanluo');
    myBody.style.fontSize="16px";
}

function autoResizeVideo(objVideo){
     var Ratio = 4/3;
     var w = objVideo.width();
     var h = objVideo.height();
     var rW,rH;
     if(w/h == Ratio){
        rW = w;
        rH = h;
     }else if(w/h > Ratio){
        rW = w;
        rH = w/Ratio;
     }else if(w/h < Ratio){
        rW = Ratio*h;
        rH = h;
        if(rW >= w){
            rW = w;
            rH = w/Ratio;
        }
     }
     objVideo.width(rW);
     objVideo.height(rH);
}