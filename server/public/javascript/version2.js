//Jquery equivalent helpers
function fetchLoad(id, route) {
	fetch(route).then(res => res.text()).then(body => {
		document.getElementById(id).innerHTML = body;
	})
}

function fetchPost(route, data, success) {
	fetch(route, { 
		method: 'POST',
		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		body: Object.keys(data).map(key => encodeURIComponent(key)+'='+encodeURIComponent(data[key])).join('&')
	}).then(res => res.text()).then(body => success(body));
}

//Task List functions
const csrfToken = document.getElementById("csrfToken").value;
const loginRoute = document.getElementById("loginRoute").value;
const validateRoute = document.getElementById("validateRoute").value;
const createRoute = document.getElementById("createRoute").value;
fetchLoad("contents", loginRoute);

function login() {
	const username = document.getElementById("loginName").value;
	const password = document.getElementById("loginPass").value;
	fetchPost(validateRoute,
		{ username, password, csrfToken },
		data => {
			document.getElementById("contents").innerHTML = data;
		})
}

function createUser() {
	const username = document.getElementById("createName").value;
	const password = document.getElementById("createPass").value;
	fetchPost(createRoute,
		{ username, password, csrfToken }, 
		data => {
			document.getElementById("contents").innerHTML = data;
		})
}

function deleteTask(index) {
	fetchLoad("contents", "deleteTask2?index=" + index);
}

function addTask() {
	const task = document.getElementById("newTask").value;
	fetchLoad("contents", "/addTask2?task=" + encodeURIComponent(task));
}