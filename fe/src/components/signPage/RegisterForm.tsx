import { Theme, css } from '@emotion/react';
import { Button } from '@components/common/Button';
import { TextInput } from '@components/common/textInput/TextInput';

export const RegisterForm: React.FC = () => {
  // const [id, setId] = useState('');
  // const [password, setPassword] = useState('');

  // textArea와 중복되는 코드 줄이기
  // const [uploadedProfile, setUploadedProfile] = useState<string | null>(null);

  // const [fileStatus, setFileStatus] = useState<DefaultFileStatusType>({
  //   typeError: false,
  //   sizeError: false,
  //   isUploading: false,
  //   uploadFailed: false,
  // });

  // const profile = uploadedProfile
  //   ? uploadedProfile
  //   : 'https://avatars.githubusercontent.com/u/180050?v=4';

  // const uploadImage = async (file: File) => {
  //   try {
  //     setFileStatus((prev) => ({ ...prev, isUploading: true }));

  //     const formData = new FormData();
  //     formData.append('file', file);

  //     const response = await fetch(`/{경로}`, {
  //       method: 'POST',
  //       body: formData,
  //     });

  //     if (!response.ok) {
  //       throw new Error('File upload failed');
  //     }

  //     const data = await response.json();
  //     setUploadedProfile(data.url);
  //     return data;
  //   } catch (error) {
  //     setFileStatus((prev) => ({ ...prev, uploadFailed: true }));
  //   } finally {
  //     setFileStatus((prev) => ({ ...prev, isUploading: false }));
  //   }
  // };

  return (
    <form css={formStyle}>
      <TextInput
        value=""
        label="아이디"
        height={56}
        inputType="text"
        placeholder="아이디"
        isError={false}
        captionString="아이디는 6자 이상 16자 이하로 입력해주세요."
        onChange={() => console.log('id입력')}
      />

      <TextInput
        value=""
        label="비밀번호"
        height={56}
        inputType="password"
        placeholder="비밀번호"
        isError={false}
        captionString="비밀번호는 6자 이상 12자 이하로 입력해주세요."
        onChange={() => console.log('pw입력')}
      />

      <TextInput
        value=""
        label="비밀번호 확인"
        height={56}
        inputType="password"
        placeholder="비밀번호 확인"
        isError={false}
        captionString="비밀번호가 일치하지 않습니다."
        onChange={() => console.log('pw확인 입력')}
      />

      <Button
        className="submit-button"
        typeVariant="contained"
        size="L"
        disabled={true}
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
