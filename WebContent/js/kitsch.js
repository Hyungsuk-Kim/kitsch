// 삭제 여부를 확인 후 해당 url로 이동하는 기능
function deregisterCheck(url) {
	if (confirm('정말로 탈퇴하시겠습니까?')) {
		location.href = url;
	}
}
//해당 url로 이동하는 기능
function goUrl(url) {
	location.href = url;
}

// 검색 폼의 공백을 체크하는 기능
function searchCheck() {
	var searchInput = document.searchForm.searchText;
	
	if (searchInput.value.trim() == '') {
		alert('검색어를 입력하세요.');
		searchInput.focus();
		return false;
	}
	return true;
}

// 삭제 여부를 확인 후 해당 url로 이동하는 기능
function deleteCheck(url) {
	if (confirm('정말로 삭제하시겠습니까?')) {
		goUrl(url);
	}
}

// 작성/수정 폼의 공백을  체크하는 기능
function boardWriteCheck(form) {
	form.submit.disabled = true;
	if (form.title.value.trim().length == 0) {
		alert('제목을 입력하세요.');
		form.title.focus();
		return;
	}
	if (form.writer.value.trim().length == 0) {
		alert('이름을 입력하세요.');
		form.writer.focus();
		return;
	}
	/*
	if (form.contents.value.trim().length == 0) {
		alert('내용을 입력하세요.');
		form.contents.focus();
		return;
	}
	*/
	if (CKEDITOR.instances.contents.getData().trim().length == 0) {
		alert('내용을 입력하세요.');
		CKEDITOR.instances.contents.focus();
		return;
	}
	
	form.submit();
}

// 폼 필드가 비어있는지 여부를 체크하여 에러 메시지를 출력
function checkNotEmpty(inputField, errorSpan) {
	if (inputField.value.trim().length == 0) {
		errorSpan.innerHTML = '이름을 입력하세요.';
	} else {
		errorSpan.innerHTML = '';
	}
}