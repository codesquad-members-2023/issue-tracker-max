import { Theme, css } from '@emotion/react';
import { Button } from '@components/common/Button';
import { TextInput } from '@components/common/textInput/TextInput';
import { useState } from 'react';
import { validateId, validatePassword } from './validateInput';
import { loginUser } from 'apis/api';
import { useNavigate } from 'react-router-dom';
import { PATH } from 'constants/PATH';
import {
  setLocalStorageAccessToken,
  setLocalStorageImage,
  setLocalStorageLoginId,
  setLocalStorageRefreshToken,
  setLocalStorageUserId,
} from 'apis/localStorage';

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

  const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const data = await loginUser(id, password);
      const { loginId, image, accessToken, refreshToken } = data;
      const userId = JSON.parse(
        JSON.parse(atob(accessToken.split('.')[1])).body,
      ).userId;

      setLocalStorageUserId(userId);
      setLocalStorageLoginId(loginId);
      setLocalStorageImage(image);
      setLocalStorageAccessToken(accessToken);
      setLocalStorageRefreshToken(refreshToken);

      navigate(`/${PATH.ISSUE_LIST_PAGE}`);
    } catch (error) {
      if (error instanceof Error) {
        alert(JSON.parse(error.message).message);
      }
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
