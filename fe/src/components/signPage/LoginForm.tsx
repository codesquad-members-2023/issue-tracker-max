import { Theme, css } from '@emotion/react';
import { Button } from '@components/common/Button';
import { TextInput } from '@components/common/textInput/TextInput';
import { useState } from 'react';
import { validateId, validatePassword } from './validateInput';
import { loginUser } from 'apis/api';
import {
  setLocalStorageImageUrl,
  setLocalStorageLoginId,
  setLocalStorageUserId,
} from '@utils/localStorage';
import { useNavigate } from 'react-router-dom';
import { ISSUE_LIST_PAGE } from 'constants/PATH';

export const LoginForm: React.FC = () => {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const onIdChange = (id: string) => {
    setId(id);
  };

  const onPasswordChange = (password: string) => {
    setPassword(password);
  };

  const onSubmit = async () => {
    try {
      const response = await loginUser(id, password);
      const data = await response.json();
      const { userId, loginId, image } = data;

      setLocalStorageUserId(userId);
      setLocalStorageLoginId(loginId);
      setLocalStorageImageUrl(image);

      navigate(`/${ISSUE_LIST_PAGE}`);
    } catch (error) {
      console.error(error);
    }
  };

  const isIdError = validateId(id);
  const isPasswordError = validatePassword(password);
  const submitButtonEnabled =
    id.length > 0 && password.length > 0 && !isIdError && !isPasswordError;

  return (
    <form css={loginFormStyle} onSubmit={onSubmit}>
      <TextInput
        value={id}
        label="아이디"
        height={56}
        inputType="text"
        placeholder="아이디"
        isError={isIdError}
        caption="아이디는 6자 이상 16자 이하로 입력해주세요."
        onChange={onIdChange}
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

      <Button
        className="submit-button"
        typeVariant="contained"
        size="L"
        disabled={!submitButtonEnabled}
      >
        아이디로 로그인
      </Button>
    </form>
  );
};

const loginFormStyle = (theme: Theme) => css`
  width: 320px;
  display: flex;
  flex-direction: column;

  .submit-button {
    width: 100%;
    border-radius: ${theme.radius.l};
  }
`;
