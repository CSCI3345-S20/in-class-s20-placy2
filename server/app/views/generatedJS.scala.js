@()

function fetchLoad(id, route) {
	fetch(route).then(res => res.text()).then(body => {
		document.getElementById(id).innerHTML = body;
	})
}

fetchLoad("contents", "@routes.TaskList2.login()");

function login() {
	const username = document.getElementById("loginName").value;
	const password = document.getElementById("loginPass").value;

	fetchLoad("contents", "/validate2?username=" + username + "&password=" + password);
}

function createUser() {
	const username = document.getElementById("createName").value;
	const password = document.getElementById("createPass").value;

	fetchLoad("contents", "/create2?username=" + username + "&password=" + password);
}

function deleteTask(index) {
	fetchLoad("contents", "deleteTask2?index=" + index);
}

function addTask() {
	const task = document.getElementById("newTask").value;
	fetchLoad("contents", "/addTask2?task=" + encodeURIComponent(task));
}