import { Theme, css } from '@emotion/react';
import { Button } from '@components/common/Button';
import { TextInput } from '@components/common/textInput/TextInput';
import { useState } from 'react';
import { signUpUser } from 'apis/api';
import { validateId, validatePassword } from './validateInput';
import { useNavigate } from 'react-router-dom';
import { PATH } from 'constants/PATH';

export const RegisterForm: React.FC = () => {
  const [loginId, setLoginId] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const navigate = useNavigate();

  const onLoginIdChange = (id: string) => {
    setLoginId(id);
  };

  const onPasswordChange = (password: string) => {
    setPassword(password);
  };

  const onConfirmPasswordChange = (password: string) => {
    setConfirmPassword(password);
  };

  const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      await signUpUser(loginId, password);

      navigate(PATH.SIGN_PAGE);
    } catch (error) {
      console.error(error);
    }
  };

  const isIdError = validateId(loginId);
  const isPasswordError = validatePassword(password);
  const isPasswordMatchingError =
    confirmPassword.length > 0 && password !== confirmPassword;
  const submitButtonEnabled =
    loginId.length > 0 &&
    password.length > 0 &&
    confirmPassword.length > 0 &&
    !isIdError &&
    !isPasswordError &&
    !isPasswordMatchingError;

  return (
    <form css={formStyle} onSubmit={onSubmit}>
      <TextInput
        value={loginId}
        label="아이디"
        height={56}
        inputType="text"
        placeholder="아이디"
        isError={isIdError}
        caption="아이디는 6자 이상 16자 이하로 입력해주세요."
        onChange={onLoginIdChange}
      />

      <TextInput
        value={password}
        label="비밀번호"
        height={56}
        inputType="password"
        placeholder="비밀번호"
        isError={isPasswordError}
        caption="비밀번호는 6자 이상 12자 이하로 입력해주세요."
        onChange={onPasswordChange}
      />

      <TextInput
        value={confirmPassword}
        label="비밀번호 확인"
        height={56}
        inputType="password"
        placeholder="비밀번호 확인"
        isError={isPasswordMatchingError}
        caption="비밀번호가 일치하지 않습니다."
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
