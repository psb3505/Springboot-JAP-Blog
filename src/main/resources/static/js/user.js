let index = {
  init: function () {
    $("#btn-save").on("click", () => {
      // function(){}, ()=>{} this를 바인딩하기 위해서 사용
      this.save();
    });
    $("#btn-update").on("click", () => {
      this.update();
    });
  },

  save: function () {
    // alert("user의 save 함수 호출됨");
    let data = {
      username: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val(),
    };

    // console.log(data);
    // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
    // dataType을 생략 했을 경우 jquery가 MIME 타입을 확인하고 자동으로 dataType 결정
    $.ajax({
      type: "POST",
      url: "/auth/joinProc",
      data: JSON.stringify(data), // http body 데이터
      contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
      dataType: "json", // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
    })
      .done(function (resp) {
        if(resp.status === 500) {
          alert("중복되는 아이디가 존재합니다.");
        } else {
          alert("회원가입이 완료되었습니다.");
        location.href = "/";
        }
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },  
  update: function () {
    let data = {
      id: $("#id").val(),
      username: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val(),
    };

    $.ajax({
      type: "PUT",
      url: "/user",
      data: JSON.stringify(data), // http body 데이터
      contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
      dataType: "json", // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
    })
      .done(function (resp) {
        alert("회원수정이 완료되었습니다.");
        location.href = "/";
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};

index.init();
