console.log("Running JS!");

function fetchLoad(id, route) {
	fetch(route).then(res => res.text()).then(body => {
		document.getElementById(id).innerHTML = body;
	})
}


document.getElementById("randomText").onclick = function () {
  fetchLoad("random", "/random");
}

const stringText = document.getElementById("randomStringText");
stringText.onclick = () => {
  const lengthInput = document.getElementById("lengthValue");
  const url = "/randomString/" + lengthValue.value;
  console.log(url);
  fetchLoad("randomString", url);
};