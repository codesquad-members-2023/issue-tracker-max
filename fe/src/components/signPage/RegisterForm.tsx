import { Theme, css } from '@emotion/react';
import { Button } from '@components/common/Button';
import { TextInput } from '@components/common/textInput/TextInput';
import { useState } from 'react';

export const RegisterForm: React.FC = () => {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const onIdChange = (id: string) => {
    setId(id);
  };

  const onPasswordChange = (password: string) => {
    setPassword(password);
  };

  const onConfirmPasswordChange = (password: string) => {
    setConfirmPassword(password);
  };

  const isIdError = (id.length > 0 && id.length < 6) || id.length > 17;
  const isPasswordError =
    (password.length > 0 && password.length < 6) || password.length > 12;
  const isPasswordMatchingError =
    confirmPassword.length > 0 && password !== confirmPassword;
  const submitButtonEnabled =
    id.length > 0 &&
    password.length > 0 &&
    confirmPassword.length > 0 &&
    !isIdError &&
    !isPasswordError &&
    !isPasswordMatchingError;

  return (
    <form css={formStyle}>
      <TextInput
        value={id}
        label="아이디"
        height={56}
        inputType="text"
        placeholder="아이디"
        isError={isIdError}
        captionString="아이디는 6자 이상 16자 이하로 입력해주세요."
        onChange={onIdChange}
      />

      <TextInput
        value={password}
        label="비밀번호"
        height={56}
        inputType="password"
        placeholder="비밀번호"
        isError={isPasswordError}
        captionString="비밀번호는 6자 이상 12자 이하로 입력해주세요."
        onChange={onPasswordChange}
      />

      <TextInput
        value={confirmPassword}
        label="비밀번호 확인"
        height={56}
        inputType="password"
        placeholder="비밀번호 확인"
        isError={isPasswordMatchingError}
        captionString="비밀번호가 일치하지 않습니다."
        onChange={onConfirmPasswordChange}
      />

      <Button
        className="submit-button"
        typeVariant="contained"
        size="L"
        disabled={!submitButtonEnabled}
      >
        가입하기
      </Button>
    </form>
  );
};

const formStyle = (theme: Theme) => css`
  display: flex;
  flex-direction: column;

  .submit-button {
    width: 320px;
    border-radius: ${theme.radius.l};
  }
`;
