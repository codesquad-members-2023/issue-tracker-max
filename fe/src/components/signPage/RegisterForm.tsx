import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { TextInput } from '@components/common/textInput/TextInput';
// import { useState } from 'react';
import { ReactComponent as Plus } from '@assets/icons/plus.svg';

export const RegisterForm: React.FC = () => {
  // const availableFileSize = 1048576; //1MB
  const theme = useTheme() as any;
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
    <>
      <form action="">
        <div
          css={{
            width: '320px',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <div
            css={{
              position: 'relative',
              marginBottom: '40px',
            }}
          >
            <img
              // src={profile}
              alt="유저 프로필"
              css={{
                borderRadius: theme.radius.half,
                display: 'block',
                width: '200px',
                height: '200px',
                // background: profile
                //   ? 'transparent'
                //   : theme.neutral.surface.bold,
              }}
            />
            <label
              htmlFor="file"
              css={{
                marginLeft: '-16px',
                background: 'transparent',
                cursor: 'pointer',
                '&:active': {
                  opacity: theme.opacity.press,
                },
              }}
            >
              <div
                css={{
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  width: '56px',
                  height: '56px',
                  borderRadius: theme.radius.half,
                  position: 'absolute',
                  right: '0px',
                  bottom: '10px',
                  background: theme.palette.blue,
                  transition: 'transform 0.3s',
                  '&:hover': {
                    transform: 'scale(1.1)',
                  },
                }}
              >
                <Plus stroke={theme.neutral.surface.strong} />
              </div>
            </label>
            <input
              onChange={() => console.log('프로필 사진 업로드')}
              type="file"
              id="file"
              css={{ display: 'none' }}
              aria-label="프로필 사진 업로드"
            />
          </div>
          <div css={{ display: 'flex', gap: '8px' }}>
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
            <Button
              typeVariant="ghost"
              size="M"
              css={{
                width: 'fit-content',
                whiteSpace: 'nowrap',
                padding: '0',
              }}
            >
              중복체크
            </Button>
          </div>
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
          <TextInput
            value=""
            label="비밀번호 확인"
            height={56}
            inputType="password"
            placeholder="비밀번호 확인"
            isError={false}
            caption="비밀번호가 일치하지 않습니다."
            onChange={() => console.log('pw확인 입력')}
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
                width: '320px',
                borderRadius: theme.radius.l,
              }}
            >
              회원가입
            </Button>
          </div>
        </div>
      </form>
    </>
  );
};
