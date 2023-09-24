const validateEmail = (email) => {
  return !!String(email)
    .toLowerCase()
    .match(
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
};

const validatePassword = (password) => {
  // 비밀번호 형식 검사
  return !!String(password).match(
    /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/
  );
};

const validateName = (name) => {
  // 이름 형식 검사
  return !!String(name).match(/^[A-Za-z가-힣]{1,6}$/);
};

const validateContact = (contact) => {
  // 휴대전화번호 형식 검사
  return !!String(contact).match(/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/);
};

export { validateEmail, validateContact, validateName, validatePassword };
