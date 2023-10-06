const validateEmail = (email) => {
  return !!String(email)
    .match(
      /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/
    );
};

const validatePassword = (password) => {
  // 비밀번호 형식 검사
  return !!String(password).match(
    /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/
  );
};

const validateName = (name) => {
  // 이름 형식 검사
  return !!String(name).match(/^[a-zA-Zㄱ-ㅎ가-힣]*$/);
};

const validateContact = (contact) => {
  // 휴대전화번호 형식 검사
  return !!String(contact).match(/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/);
};

export { validateEmail, validateContact, validateName, validatePassword };
