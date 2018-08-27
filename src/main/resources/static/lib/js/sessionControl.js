<script>
var xhr = new XMLHttpRequest();
xhr.open('Post', '/loginUser');
xhr.setRequestHeader('Content-type', 'application/json');
var userInfo={userID=, userPassword=
	};
xhr.send();

xhr.onreadystatechange = function () {
    if (xhr.readyState === XMLHttpRequest.DONE) {
      if (xhr.status === 200) {
        console.log(xhr.responseText);

        document.getElementById('content').innerHTML = xhr.responseText;
      } else {
        console.log('[' + xhr.status + ']: ' + xhr.statusText);
      }
    }
  };
</script>