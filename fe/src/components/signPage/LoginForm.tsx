import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { TextInput } from '@components/common/textInput/TextInput';
// import { useState } from 'react';

export const LoginForm: React.FC = () => {
  const theme = useTheme() as any;
  // const [id, setId] = useState('');
  // const [password, setPassword] = useState('');

  return (
    <form action="">
      <div
        css={{
          width: '320px',
          display: 'flex',
          flexDirection: 'column',
        }}
      >
        <TextInput
          value=""
          label="아이디"
          height={56}
          inputType="text"
          placeholder="아이디"
          isError={true}
          caption="아이디는 6자 이상 16자 이하로 입력해주세요."
          onChange={() => console.log('id입력')}
        />
        <TextInput
          value=""
          label="비밀번호"
          height={56}
          inputType="password"
          placeholder="비밀번호"
          isError={false}
          caption="비밀번호는 6자 이상 12자 이하로 입력해주세요."
          onChange={() => console.log('pw입력')}
        />
        <div
          css={{
            display: 'flex',
            flexDirection: 'column',
            gap: '16px',
          }}
        >
          <Button
            typeVariant="contained"
            size="L"
            disabled={true}
            css={{
              width: '100%',
              borderRadius: theme.radius.l,
            }}
          >
            아이디로 로그인
          </Button>
          <Button
            typeVariant="ghost"
            size="L"
            css={{
              width: '100%',
              borderRadius: theme.radius.l,
            }}
          >
            회원가입
          </Button>
        </div>
      </div>
    </form>
  );
};
