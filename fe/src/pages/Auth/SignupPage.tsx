import Button from "@components/common/Button";
import TextInput from "@components/common/TextInput";
import useInput from "@hooks/useInput";
import { postSignup } from "api";
import { AxiosError } from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthForm } from "./LoginPage";

export default function SignupPage() {
  const { isValid: isValidloginId, ...loginId } = useInput({
    initialValue: "",
    maxLength: 16,
    minLength: 6,
  });
  const { isValid: isValidPassword, ...password } = useInput({
    initialValue: "",
    maxLength: 16,
    minLength: 6,
  });
  const [errorMessage, setErrorMessage] = useState<string>("");
  const navigate = useNavigate();

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await postSignup(loginId.value, password.value);
      if (response.status === 200) {
        alert("회원가입이 완료되었습니다. 가입하신 계정으로 로그인해주세요 :)");
        navigate("/auth");
      }
    } catch (error) {
      if (error instanceof AxiosError && error.response) {
        const { message } = error.response.data;
        setErrorMessage(message);
      }
    }
  };

  return (
    <>
      <AuthForm onSubmit={onSubmit}>
        <TextInput
          name="아이디"
          variant="tall"
          hasError={!isValidloginId}
          placeholder="아이디"
          helpText="아이디는 최소 6자리여야 해요!"
          {...loginId}
        />
        <TextInput
          name="비밀번호"
          variant="tall"
          hasError={!isValidPassword}
          placeholder="비밀번호"
          helpText="비밀번호는 최소 6자리여야 해요!"
          type="password"
          {...password}
        />
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        <Button
          variant="container"
          size="L"
          className="login-btn"
          disabled={!isValidloginId || !isValidPassword}
          type="submit">
          회원가입
        </Button>
      </AuthForm>
      <Link to="/auth">
        <Button variant="ghost" size="M" className="change-auth-btn">
          이미 계정이 있으신가요? 로그인
        </Button>
      </Link>
    </>
  );
}
