var pictureSource;
var destinationType;           

function init() {
	alert('in init');
    document.addEventListener("deviceReady",onDeviceReady,false);
}

function onDeviceReady(){
    alert('onDeviceReady ');
    console.log("onDeviceReady start");
    pictureSource = navigator.camera.PictureSourceType;
    destinationType = navigator.camera.DestinationType;
}
            
//Called when a photo is successfully retrieved
function onPhotoDataSuccess(imageData) {
    console.log(imageData);
    var largeImage = document.getElementById('largeImage');
    largeImage.style.display = 'block';
    largeImage.src = "data:image/jpeg;base64," + imageData;
}
            
//Called when a photo is successfully retrieved
function onPhotoURISuccess(imageURI) {
    //                console.log(imageURI);
    var largeImage = document.getElementById('largeImage');
    largeImage.style.display = 'block';
    largeImage.src = imageURI;
}
            
//Called when error occurs
function onFail(message) {
    alert('Error : '+message);
}
            
function capturePhoto() {
    alert('capturePhoto ');
    // Take picture using device camera and retrieve image as base64-encoded string
    navigator.camera.getPicture(onPhotoDataSuccess, onFail, {
        quality: 50, 
        destinationType: Camera.DestinationType.DATA_URL, 
        saveToPhotoAlbum: true, 
        targetWidth: 300, 
        targetHeight : 300
    });
}
            
function capturePhotoEdit() {
    // Take picture using device camera, alloe edit, and retrieve image as base64-encoded string
    navigator.camera.getPicture(onPhotoURISuccess, onFail, {
        quality: 20, 
        allowEdit: true, 
        destinationType: Camera.DestinationType.FILE_URI, 
        saveToPhotoAlbum: true, 
        targetWidth: 300, 
        targetHeight : 300
    });
}
            
function getPhoto(source) {
    //  Retrieve image file location from specified source
    alert('getPhoto ');
    navigator.camera.getPicture(onPhotoURISuccess, onFail, {
        quality: 50, 
        destinationType: Camera.DestinationType.FILE_URI, 
        sourceType: source, 
        targetWidth: 300, 
        targetHeight : 300
    });
}