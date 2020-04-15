window.onload = function() {
  var form = document.getElementById('post'),
      imageInput = document.getElementById('image-file');

  form.onsubmit = function() {
    var isValid = /\.jpe?g$/i.test(imageInput.value);
    if (!isValid) {
      alert('Not a JPG!!!');
    }

    return isValid;
  };
}